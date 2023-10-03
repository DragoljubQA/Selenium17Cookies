package Tests;

import Base.BaseTest;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BookstoreTest extends BaseTest {

    @BeforeMethod
    public void pageSetUp() {
        driver.manage().window().maximize();
        driver.navigate().to("https://demoqa.com/");
    }

    @Test
    public void test() throws InterruptedException {
        homepage.clickOnBookstore();
        logIn();
        Thread.sleep(1000);
        Assert.assertEquals(profilePage.books.size(), 0);
        setCookie(driver.manage().getCookieNamed("token"));
        profilePage.clickOnLogOut();
        Thread.sleep(1000);
        injectCookies();
        sidebarPage.clickOnSidebarButton("Book Store");
        bookstorePage.addBook(0);
        bookPage.clickOnAddToCollection();
        driver.navigate().refresh();
        bookPage.clickOnBackToBookstore();
        bookstorePage.addBook(1);
        bookPage.clickOnAddToCollection();
        driver.navigate().refresh();
        removeCookies();
        logIn();
        Assert.assertEquals(profilePage.books.size(), 2);
        profilePage.removeBooks();
        Assert.assertEquals(profilePage.books.size(), 0);
    }

    public void logIn() {
        sidebarPage.clickOnSidebarButton("Login");
        loginPage.inputUsername("DragoljubQA");
        loginPage.inputPassword("Qwerty123!@#");
        loginPage.clickOnLoginButton();
    }

    @Test
    public void apiCall() {
        homepage.clickOnBookstore();
        injectCookiesViaAPI();
    }
}
