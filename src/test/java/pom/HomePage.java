package pom;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {

    WebDriver driver;
    ExtentReports report;
    ExtentTest test;

    public HomePage(WebDriver driver, ExtentReports report, ExtentTest test){
        this.driver = driver;
        this.report = report;
        this.test = test;
        PageFactory.initElements(driver, this);

    }

    @FindBy(linkText = "Login")
    WebElement loginBtn;

    @FindBy(linkText = "Sign up")
    WebElement signUpBtn;

    @FindBy (id = "location")
    WebElement locationTextBox;

    @FindBy (xpath = "//span[contains(@class, 'icon-location')]/following-sibling::span")
    List <WebElement> searchResults;

    @FindBy (xpath = "//span[text()='FIND FOOD']/parent::a")
    WebElement findFoodBtn;


    //Search Restaurants with location
    public void typeLocation(String location){
        locationTextBox.sendKeys(location);
        test.log(LogStatus.INFO, "Entered location");
    }

    //Selecting exact location from search results
    public void selectFromSearchResults(String searchingText){

        for (WebElement result : searchResults) {
            if (result.getText().equals(searchingText)) {
                result.click();
                test.log(LogStatus.INFO, "Selected location from search results");
                break;
            }
        }
    }

    public void clickFindFoodBtn(){
        findFoodBtn.click();
        test.log(LogStatus.INFO, "Find Food button clicked ");
    }

    public void enterAddress(String searchQuery , String exactLocation){
        typeLocation(searchQuery);
        selectFromSearchResults(exactLocation);
    }


}
