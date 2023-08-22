package org.example.spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.example.enums.StatusCode;

import static org.example.consts.Constants.BASE_URI;

public final class Spec {

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification responseSpec(StatusCode statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode.getCode())
                .build();
    }
}
