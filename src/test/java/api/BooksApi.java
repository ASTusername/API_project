package api;

import models.*;

import java.util.ArrayList;
import java.util.List;

import static data.EndPoints.booksURI;
import static data.EndPoints.collectionURI;
import static io.restassured.RestAssured.given;
import static spec.Specifications.mainRequest;
import static spec.Specifications.responseWithStatusCode;

public class BooksApi {

    public static void deleteAllBooks(LoginResponseModel loginResponse) {
        given(mainRequest)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .queryParam("UserId", loginResponse.getUserId())
                .when()
                .delete(collectionURI)
                .then()
                .spec(responseWithStatusCode(204));
    }

    public static BookCollectionResponse requestBookCollection() {
        return given(mainRequest)
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .spec(responseWithStatusCode(200))
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
                .spec(responseWithStatusCode(201))
                .extract().as(AddBookResponse.class);
    }

    public static ErrorResponseModel addWrongBook(String token, String userId, String isbn) {
        PutBookModel putBookModel = new PutBookModel();
        putBookModel.setUserId(userId);
        putBookModel.setIsbn(isbn);

        return given(mainRequest)
                .header("Authorization", "Bearer " + token)
                .when()
                .body(putBookModel)
                .put(booksURI + isbn)
                .then()
                .log().all()
                .spec(responseWithStatusCode(400))
                .extract().as(ErrorResponseModel.class);
    }

    public static ErrorResponseModel addBookWrongUser(String token, String userId, String isbn) {
        PutBookModel putBookModel = new PutBookModel();
        putBookModel.setUserId(userId);
        putBookModel.setIsbn(isbn);

        return given(mainRequest)
                .header("Authorization", "Bearer " + token)
                .when()
                .body(putBookModel)
                .put(booksURI + isbn)
                .then()
                .log().all()
                .spec(responseWithStatusCode(401))
                .extract().as(ErrorResponseModel.class);
    }
}
