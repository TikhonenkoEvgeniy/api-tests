import io.restassured.RestAssured;
import org.example.pojo.UserData;
import org.example.spec.Spec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    private List<UserData> getUsers() {
        return RestAssured.given()
                .spec(Spec.requestSpec())
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
    }
}
