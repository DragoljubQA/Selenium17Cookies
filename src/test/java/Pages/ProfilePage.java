package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProfilePage extends BaseTest {
    public ProfilePage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "submit")
    public List<WebElement> buttons;

    @FindBy(className = "mr-2")
    public List<WebElement> books;

    @FindBy(id = "delete-record-undefined")
    public WebElement deleteBook;

    @FindBy(id = "closeSmallModal-ok")
    public WebElement confirmRemoval;

    //----------------------------

    public void clickOnLogOut() {
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getText().equals("Log out")) {
                buttons.get(i).click();
            }
        }
    }

    public void removeBooks() {
        while (deleteBook.isDisplayed()) {
            deleteBook.click();
            confirmRemoval.click();
            driver.navigate().refresh();
        }
    }
}
