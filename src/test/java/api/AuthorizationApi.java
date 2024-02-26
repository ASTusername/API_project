package api;

import models.CredentialsModel;
import models.LoginResponseModel;

import static data.UserData.PASSWORD;
import static data.UserData.USERNAME;
import static data.BaseURIs.loginURI;
import static spec.Specifications.*;
import static io.restassured.RestAssured.given;

public class AuthorizationApi {

    public static LoginResponseModel login(){
        CredentialsModel credentialsModel = new CredentialsModel();
        credentialsModel.setUserName(USERNAME);
        credentialsModel.setPassword(PASSWORD);

        return
                given(mainRequest)
                        .body(credentialsModel)
                        .when()
                        .post(loginURI)
                        .then()
                        .spec(response200)
                        .extract().as(LoginResponseModel.class);
    }
}
