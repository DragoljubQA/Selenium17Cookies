package Base;

import Pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.time.Duration;

import static io.restassured.RestAssured.given;

public class BaseTest {

/*    Ulogujte se na demoqa(https://demoqa.com/ -> Book Store Application)
preko cookies-a, dodati dve knjige
            na svoj nalog, zatim se izlogovati brisanjem cookies-a.
            Ulogovati se ponovo preko log-in forme i potvrditi da se
            knjige i dalje nalaze na nalogu.*/

    public static WebDriver driver;
    public WebDriverWait wait;
    public Homepage homepage;
    public BookstorePage bookstorePage;
    public BookPage bookPage;
    public SidebarPage sidebarPage;
    public LoginPage loginPage;
    public ProfilePage profilePage;
    public ExcelReader excelReader;
    private Cookie cookie;

    @BeforeClass
    public void setUp() throws IOException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        homepage = new Homepage();
        bookstorePage = new BookstorePage();
        bookPage = new BookPage();
        sidebarPage = new SidebarPage();
        loginPage = new LoginPage();
        profilePage = new ProfilePage();

        //excelReader = new ExcelReader("C:\\Users\\drago\\Desktop\\TestData.xlsx");
        excelReader = new ExcelReader("src/test/java/TestData.xlsx");

    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void injectCookies() {
        Cookie cookie1 = new Cookie("userName", "dragoljubqa");
        Cookie cookie2 = new Cookie("userID", "362c8c84-7846-4bcd-9625-ef0df047ff54");
        //Cookie cookie3 = new Cookie("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6IkRyYWdvbGp1YlFBIiwicGFzc3dvcmQiOiJRd2VydHkxMjMhQCMiLCJpYXQiOjE2OTYzNTQzODl9.BD6J4ykFvcuepTHNKdmiAitCPeBMDahUK0_IbqnHNdk");
        Cookie cookie3 = getCookie();
        Cookie cookie4 = new Cookie("expires", "2023-10-10T17%3A33%3A09.582Z");
        driver.manage().addCookie(cookie1);
        driver.manage().addCookie(cookie2);
        driver.manage().addCookie(cookie3);
        driver.manage().addCookie(cookie4);
        driver.navigate().refresh();
    }

    public void injectCookiesViaAPI() {
        Cookie cookie1 = new Cookie("userName", "dragoljubqa");
        Cookie cookie2 = new Cookie("userID", "362c8c84-7846-4bcd-9625-ef0df047ff54");
        Cookie cookie3 = new Cookie("token", token());
        Cookie cookie4 = new Cookie("expires", "2023-10-10T17%3A33%3A09.582Z");
        driver.manage().addCookie(cookie1);
        driver.manage().addCookie(cookie2);
        driver.manage().addCookie(cookie3);
        driver.manage().addCookie(cookie4);
        driver.navigate().refresh();
    }

    public void removeCookies() {
        Cookie cookie1 = new Cookie("userName", "dragoljubqa");
        Cookie cookie2 = new Cookie("userID", "362c8c84-7846-4bcd-9625-ef0df047ff54");
        //Cookie cookie3 = new Cookie("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6IkRyYWdvbGp1YlFBIiwicGFzc3dvcmQiOiJRd2VydHkxMjMhQCMiLCJpYXQiOjE2OTYzNTQzODl9.BD6J4ykFvcuepTHNKdmiAitCPeBMDahUK0_IbqnHNdk");
        Cookie cookie3 = getCookie();
        Cookie cookie4 = new Cookie("expires", "2023-10-10T17%3A33%3A09.582Z");
        driver.manage().deleteCookie(cookie1);
        driver.manage().deleteCookie(cookie2);
        driver.manage().deleteCookie(cookie3);
        driver.manage().deleteCookie(cookie4);
        driver.navigate().refresh();
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public String token() {
        RestAssured.baseURI = "https://demoqa.com/Account/v1";
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(payload())
                .when().post("/GenerateToken")
                .then().log().all()
                .extract().response().asString();
        JsonPath js = new JsonPath(response);
        String tokenValue = js.getString("token");
        return tokenValue;
    }

    public String payload() {
        return "{\n" +
                "    \"userName\" : \"DragoljubQA\",\n" +
                "    \"password\" : \"Qwerty123!@#\""+
                "}";
    }

    @AfterClass
    public void tearDown() {
        //driver.manage().deleteAllCookies();
        //driver.quit();
    }
}
