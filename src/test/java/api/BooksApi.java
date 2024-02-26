package api;

import models.*;

import java.util.ArrayList;
import java.util.List;

import static data.BaseURIs.collectionURI;
import static spec.Specifications.*;
import static io.restassured.RestAssured.given;

public class BooksApi {
    public static void deleteAllBooks(LoginResponseModel loginResponse) {
        given(mainRequest)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .queryParam("UserId", loginResponse.getUserId())
                .when()
                .delete(collectionURI)
                .then()
                .spec(response204);
    }

    public static BookCollectionResponse requestBookCollection() {
        return given(mainRequest)
                    .when()
                    .get("/BookStore/v1/Books")
                    .then()
                    .spec(response200)
                    .extract().as(BookCollectionResponse.class);
    }

    public static AddBookResponse addBook(String isb, String token, String userId) {

        List<IsbnBookModel> books = new ArrayList<>();
        books.add(new IsbnBookModel(isb));

        AddBookBodyModel bookData = new AddBookBodyModel();
        bookData.setUserId(userId);
        bookData.setCollectionOfIsbns(books);
        return given(mainRequest)
                        .header("Authorization", "Bearer " + token)
                        .body(bookData)
                        .when()
                        .post(collectionURI)
                        .then()
                        .spec(response201)
                        .extract().as(AddBookResponse.class);
    }
}
