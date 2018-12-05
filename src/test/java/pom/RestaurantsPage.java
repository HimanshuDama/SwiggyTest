package pom;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class RestaurantsPage {

    WebDriver driver;
    ExtentReports report;
    ExtentTest test;
    WebDriverWait wait;

    public RestaurantsPage(WebDriver driver, ExtentReports report, ExtentTest test) {
        this.driver = driver;
        this.report = report;
        this.test = test;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//div[text()[contains(.,'ADD')]]/parent::div")
    List<WebElement> addBtn;

    @FindBy(xpath = "//*[text()[contains(.,'Customisable')]]/parent::div") //preceding-sibling::div[text()[contains(.,'ADD')]]"
            List<WebElement> customizableAddBtn;

    @FindBy(xpath = "//span[text()[contains(.,'Add Item')]]/parent::div")
    WebElement addItemBtn;

    @FindBy(xpath = "//div[text()[contains(.,'Checkout')]]")
    WebElement checkOutBtn;

    // This will add food item that do not have an option of customization
    public void clickOnAddFoodItem() {
        for (int i = 0; i < addBtn.size(); i++) {

            if (customizableAddBtn.size() <= 0) {
                wait.until(ExpectedConditions.elementToBeClickable(addBtn.get(i))).click();
                test.log(LogStatus.INFO, "Added Food item to Card");
                break;

            } else {
                if (!addBtn.get(i).equals(customizableAddBtn.get(i))) {
                    wait.until(ExpectedConditions.elementToBeClickable(addBtn.get(i))).click();
                    test.log(LogStatus.INFO, "Added Food item to Card");
                    break;
                } else if (i == (addBtn.size() - 1)) {
                    //if no food item found without customization option
                    test.log(LogStatus.WARNING, "No Food item found without customization, selecting food items with customization option");
                    clickOnAddBtnForCustomizableFoodItem();
                    break;
                }
            }
        }
    }

    //This will add food item that are customizable
    public void clickOnAddBtnForCustomizableFoodItem() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'ADD ITEM')]")));
        customizableAddBtn.get(0).click();
        addItemBtn.click();
        test.log(LogStatus.INFO, "Clicked on Add Button for Customizable Food Item");
    }

    public void clickOnCheckoutBtn() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Checkout')]")));
        checkOutBtn.click();
        test.log(LogStatus.INFO, "Clicked on Checkout");
    }

    public void addFoodItemAndCheckout() {
        clickOnAddFoodItem();
        clickOnCheckoutBtn();
    }

    public void addCustomizedFoodItemAndCheckout() {
        clickOnAddBtnForCustomizableFoodItem();
        clickOnCheckoutBtn();
    }

}
