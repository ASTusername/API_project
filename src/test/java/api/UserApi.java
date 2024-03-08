package api;

import io.restassured.response.ValidatableResponse;
import models.ErrorResponseModel;
import models.UserResponseModel;

import static io.restassured.RestAssured.given;
import static spec.Specifications.mainRequest;
import static spec.Specifications.responseWithStatusCode;

public class UserApi {

    private static ValidatableResponse prepareGetRequest(String token, String userId) {
        return given(mainRequest)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/Account/v1/User/" + userId)
                .then()
                .log().all();
    }

    public static UserResponseModel getUser(String token, String userId) {
        return prepareGetRequest(token, userId)
                .spec(responseWithStatusCode(200))
                .extract().as(UserResponseModel.class);
    }

    public static ErrorResponseModel getUserNotAuth(String token, String userId) {
        return prepareGetRequest(token, userId)
                .spec(responseWithStatusCode(401))
                .extract().as(ErrorResponseModel.class);
    }
}
