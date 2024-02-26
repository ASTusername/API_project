package tests;

import api.AuthorizationApi;
import api.BooksApi;
import helpers.WithLogin;
import models.BookCollectionResponse;
import models.LoginResponseModel;
import pages.ProfilePage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;


public class ProfileBooksTests extends TestBase {
    private final int BOOK_NO = 0;
    ProfilePage profilePage = new ProfilePage();

    @Test
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

    }
}
