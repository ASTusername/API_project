package tests;

import api.AuthorizationApi;
import api.BooksApi;
import api.UserApi;
import helpers.WithLogin;
import models.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import static data.UserData.USERNAME;
import static data.UserData.USER_ID;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProfileBooksTests extends TestBase {
    private final int BOOK_NO = 0;
    ProfilePage profilePage = new ProfilePage();

    @Test
    @Tag("users_test")
    @WithLogin
    void successUserLoginTest() {

        LoginResponseModel authResponse =
                step("Авторизация через API", AuthorizationApi::login
                );
        UserResponseModel userResponseModel = step("Отправляем запрос user", () ->
                UserApi.getUser(authResponse.getToken(), authResponse.getUserId())
        );
        step("Проверяем наличие поля userId, userName", () -> {
            assertEquals(USER_ID, userResponseModel.getUserId());
            assertEquals(USERNAME, userResponseModel.getUsername());
        });
    }

    @Test
    @Tag("users_test")
    @WithLogin
    void errorWrongUserIdLoginTest() {

        LoginResponseModel authResponse =
                step("Авторизация через API", AuthorizationApi::login
                );
        ErrorResponseModel errorResponseModel = step("Отправляем запрос user", () ->
                UserApi.getUserNotAuth(authResponse.getToken(), "656dce53-7f16-402b-9378-893d765ce404")
        );
        step("Проверяем наличие поля code, message", () -> {
            assertEquals("User not authorized!", errorResponseModel.getMessage());
        });
    }

    @Test
    @Tag("books_test")
    @WithLogin
    void successUserAddBookTest() {

        BookCollectionResponse collection = BooksApi.requestBookCollection();

        LoginResponseModel authResponse =
                step("Авторизация через API", AuthorizationApi::login
                );
        step("Удаление всех книг из профиля через API", () ->
                BooksApi.deleteAllBooks(authResponse)
        );

        AddBookResponse addBookResponse = step("Добавление новой книги через API", () ->
                BooksApi.addBook(collection.getBooks()[BOOK_NO].getIsbn(), authResponse.getToken(), authResponse.getUserId())
        );
        step("Проверяем количество книг", () -> {
            assertEquals(1, addBookResponse.getBooks().size());
        });
    }


    @Test
    @Tag("books_test")
    @WithLogin
    void errorWrongUserAddBookTest() {
        BookCollectionResponse collection = BooksApi.requestBookCollection();

        LoginResponseModel authResponse =
                step("Авторизация через API", AuthorizationApi::login
                );
        step("Удаление всех книг из профиля через API", () ->
                BooksApi.deleteAllBooks(authResponse)
        );
        ErrorResponseModel errorBookResponse = step("Делаем запрос пользователь с неверным id на добавление книги", () ->
                BooksApi.addBookWrongUser(authResponse.getToken(), "2320f8ab-5a11-4a0e-b975-89b077a00000", collection.getBooks()[BOOK_NO].getIsbn())
        );
        step("Проверяем поля ошибки", () -> {
            assertEquals("1207", errorBookResponse.getCode());
            assertEquals("User Id not correct!", errorBookResponse.getMessage());
        });
    }

    @Test
    @Tag("books_test")
    @WithLogin
    void errorUserAddWrongBookTest1() {

        LoginResponseModel authResponse =
                step("Авторизация через API", AuthorizationApi::login
                );
        step("Удаление всех книг из профиля через API", () ->
                BooksApi.deleteAllBooks(authResponse)
        );
        ErrorResponseModel errorBookResponse = step("Делаем запрос на добавление отсутствующей в каталоге книги", () ->
                BooksApi.addWrongBook(authResponse.getToken(), authResponse.getUserId(), "9781449325866")
        );
        step("Проверяем поля ошибки", () -> {
            assertEquals("1205", errorBookResponse.getCode());
            assertEquals("ISBN supplied is not available in Books Collection!", errorBookResponse.getMessage());
        });
    }

/*    @Test
    @Tag("books_test")
    @WithLogin
    void successDeleteBookFromProfileTest() {

        BookCollectionResponse collection = BooksApi.requestBookCollection();

        LoginResponseModel authResponse =
                step("Авторизация через API", AuthorizationApi::login
                );
        step("Удаление всех книг из профиля через API", () ->
                BooksApi.deleteAllBooks(authResponse)
        );

        step("Добавление новой книги через API", () ->
                BooksApi.addBook(collection.getBooks()[BOOK_NO].getIsbn(), authResponse.getToken(), authResponse.getUserId())
        );
        step("Открытие профиля", () ->
                profilePage.googleConsent()
                        .openPage()
        );
        step("Проверка, что в коллекции есть книга", () ->
                profilePage.checkForBook()
        );
        step("Удаление книги через UI", () ->
                profilePage.deleteBook()
        );
        step("Подтверждение удаления книги", () ->
                profilePage.confirmDelete()
        );
        step("Проверка, что коллекция пуста", () ->
                profilePage.checkTableBody(collection.getBooks()[BOOK_NO].getTitle())
        );
    }*/
}
