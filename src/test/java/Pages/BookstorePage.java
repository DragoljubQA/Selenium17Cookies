package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BookstorePage extends BaseTest {
    public BookstorePage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "mr-2")
    public List<WebElement> books;

    //-----------------------

    public void addBook(int bookIndex) {
        books.get(bookIndex).click();
    }
}
