package spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class Specifications {
    public static RequestSpecification mainRequest = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().headers()
            .log().body()
            .log().uri();

    public static ResponseSpecification responseWithStatusCode(int status) {
        ResponseSpecification response = new ResponseSpecBuilder()
                .expectStatusCode(status)
                .log(ALL)
                .build();
        return response;
    }
}