package pom;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogInPage {

    WebDriver driver;
    ExtentReports report;
    ExtentTest test;

    public LogInPage(WebDriver driver, ExtentReports report, ExtentTest test){
        this.driver = driver;
        this.report = report;
        this.test = test;
        PageFactory.initElements(driver, this);

    }

    @FindBy(id = "mobile")
    WebElement mobileTextBox;

    @FindBy(id = "password")
    WebElement passwordTextBox;

    @FindBy(linkText = "Login")
    WebElement logUserInBtn;

    public void typeInMobileTextBox(String mobileNumber) {
        mobileTextBox.sendKeys(mobileNumber);
        test.log(LogStatus.INFO, "Entered phone number");
    }

    public void typeInPasswordTextBox(String password) {
        passwordTextBox.sendKeys(password);
        test.log(LogStatus.INFO, "Entered Password");
    }

    public void clickOnLoginUserBtn() {
        logUserInBtn.click();
        test.log(LogStatus.INFO, "Logging In");
    }

    //Sign in with UN and PW
    public void loginUser(String mobileNumber, String password) {
        typeInMobileTextBox(mobileNumber);
        typeInPasswordTextBox(password);
        clickOnLoginUserBtn();
    }


}
