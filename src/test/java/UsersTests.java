import io.restassured.RestAssured;
import org.example.enums.StatusCode;
import org.example.pojo.UserData;
import org.example.pojo.UserRequestBody;
import org.example.pojo.UserResponseBody;
import org.example.spec.Spec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class UsersTests {

    @Test
    public void checkIdContainsAvatar() {
        getUsers().forEach(userData -> {
            Assertions.assertTrue(userData.getAvatar().contains(String.format("/%d-image.jpg", userData.getId())));
        });
    }

    @Test
    public void checkEmailEnding() {
        Assertions.assertTrue(getUsers().stream().allMatch(userData -> userData.getEmail().endsWith("@reqres.in")));
    }

    @Test
    public void createNewUser() {
        UserRequestBody body = new UserRequestBody("Maksim Petrov", "Worker");

        UserResponseBody response = RestAssured.given()
                .spec(Spec.requestSpec())
                .body(body)
                .when()
                .post("api/users")
                .then().log().all()
                .spec(Spec.responseSpec(StatusCode.CREATE))
                .extract().body().as(UserResponseBody.class);

        Assertions.assertEquals(body.getName(), response.getName());
        Assertions.assertEquals(body.getJob(), response.getJob());
        Assertions.assertNotNull(response.getId());
        Assertions.assertNotNull(response.getCreatedAt());
    }

    @Test
    public void updateUser() {
        UserRequestBody body = new UserRequestBody("morpheus", "zion resident");

        UserResponseBody response = RestAssured.given()
                .spec(Spec.requestSpec())
                .body(body)
                .when()
                .put("api/users/2")
                .then().log().all()
                .spec(Spec.responseSpec(StatusCode.OK))
                .extract().body().as(UserResponseBody.class);

        Assertions.assertEquals(body.getName(), response.getName());
        Assertions.assertEquals(body.getJob(), response.getJob());
        Assertions.assertNull(response.getId());
        Assertions.assertNull(response.getCreatedAt());
        Assertions.assertNotNull(response.getUpdatedAt());
    }

    private List<UserData> getUsers() {
        return RestAssured.given()
                .spec(Spec.requestSpec())
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .spec(Spec.responseSpec(StatusCode.OK))
                .extract().body().jsonPath().getList("data", UserData.class);
    }
}
