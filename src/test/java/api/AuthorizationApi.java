package api;

import models.CredentialsModel;
import models.LoginResponseModel;

import static data.EndPoints.loginURI;
import static data.UserData.PASSWORD;
import static data.UserData.USERNAME;
import static io.restassured.RestAssured.given;
import static spec.Specifications.mainRequest;
import static spec.Specifications.responseWithStatusCode;

public class AuthorizationApi {

    public static LoginResponseModel login() {
        CredentialsModel credentialsModel = new CredentialsModel();
        credentialsModel.setUserName(USERNAME);
        credentialsModel.setPassword(PASSWORD);

        return
                given(mainRequest)
                        .body(credentialsModel)
                        .when()
                        .post(loginURI)
                        .then()
                        .spec(responseWithStatusCode(200))
                        .extract().as(LoginResponseModel.class);
    }

}
