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
import org.testng.Assert;

import java.util.List;

public class CheckOutPage {

    WebDriver driver;
    ExtentReports report;
    ExtentTest test;
    WebDriverWait wait;

    public CheckOutPage(WebDriver driver, ExtentReports report, ExtentTest test) {
        this.driver = driver;
        this.report = report;
        this.test = test;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//div[text()[contains(.,'LOG IN')]]/parent::div")
    WebElement loginBtn;

    @FindBy(xpath = "//div[text()[contains(.,'SIGN UP')]]/parent::div")
    WebElement signUpBtn;

    @FindBy(xpath = "//div[text()[contains(.,'Deliver')]]")
    List<WebElement> deliverBtn;

    @FindBy(xpath = "//div[text()[contains(.,'Add new Address')]]")
    WebElement addNewAddressBtn;

    @FindBy(id = "building")
    WebElement buildingTextBox;

    @FindBy(id = "landmark")
    WebElement landmarkTextBox;

    @FindBy(xpath = "//a[text()[contains(.,'SAVE ADDRESS')]]")
    WebElement saveAddressBtn;

    @FindBy(xpath = "//div[./text()='Wallet']")
    WebElement walletPayment;

    @FindBy(xpath = "//div[./text()='Cards']")
    WebElement cardPayment;

    @FindBy(id = "cardNumber")
    WebElement cardNumberTextBox;

    @FindBy(id = "expiry")
    WebElement expiryTextBox;

    @FindBy(id = "cvv")
    WebElement cvvTextBox;

    @FindBy(id = "name")
    WebElement nameTextBox;

    @FindBy(xpath = "//div[./text()='Food Cards']")
    WebElement foodCardPayment;

    @FindBy(xpath = "//div[./text()='UPI']")
    WebElement upiPayment;

    @FindBy(xpath = "//div[./text()='Netbanking']")
    WebElement netBankingPayment;

    @FindBy(xpath = "//div[./text()='Apply Coupon']")
    WebElement applyCouponBtn;

    @FindBy (xpath = "//button[./text()='APPLY COUPON']/../div/div/span")
    List<WebElement> couponName;

    @FindBy(xpath = "//button[./text()='APPLY COUPON']")
    List<WebElement> applyCouponSideBarBtn;

    @FindBy(xpath = "//div[text()[contains(.,'Offer applied')]]/preceding-sibling::div")
    WebElement couponAppliedName;


    public void clickOnLoginBtn() {
        loginBtn.click();
        test.log(LogStatus.INFO, "Clicked on Login Btn");
    }

    //Select Existing address from list of available address
    public void selectExistingAddress() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Deliver Here')]")));
        deliverBtn.get(0).click();
        test.log(LogStatus.INFO, "Clicked on First available address");
    }

    public void clickOnAddNewAddressBtn() {
        addNewAddressBtn.click();
        test.log(LogStatus.INFO, "Clicked on Add new address button");
    }

    public void typeInBuildingTextBox(String buildingAddress) {

        buildingTextBox.sendKeys(buildingAddress);
        test.log(LogStatus.INFO, "Typed building Address");
    }

    public void typeInLandmarkTextBox(String landmarkAddress) {
        landmarkTextBox.sendKeys(landmarkAddress);
        test.log(LogStatus.INFO, "Typed landmark Address");
    }


    public void clickOnSaveAddressBtn() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'SAVE ADDRESS & PROCEED')]")));
        saveAddressBtn.click();
        test.log(LogStatus.INFO, "Clicked on Save address Button");
    }

    //Add and Save new address
    public void saveNewAddress(String buildingAddress, String landmarkAddress) throws InterruptedException {
        Thread.sleep(2000);
        typeInBuildingTextBox(buildingAddress);
        typeInLandmarkTextBox(landmarkAddress);
        clickOnSaveAddressBtn();
    }

    //pay with payment method
    public void payWithSelectedPaymentMethod(String paymentMethod) {
        paymentMethod = paymentMethod.toLowerCase();
        switch (paymentMethod) {

            case "wallet":
                payWithWallet();
                break;

            case "card":
                payWithCard();
                break;

            case "foodcard":
                payWithFoodCard();
                break;

            case "upi":
                payWithUPI();
                break;

            case "netbanking":
                payWithNetBanking();
                break;
        }
    }

    private void payWithWallet() {
        walletPayment.click();
    }

    private void payWithCard() {
        cardPayment.click();
        test.log(LogStatus.INFO, "Paying with Card");
        cardNumberTextBox.sendKeys("4242424242424242");
        expiryTextBox.sendKeys("11/22");
        cvvTextBox.sendKeys("111");
        nameTextBox.sendKeys("Fname Lname");


    }

    private void payWithFoodCard() {
        foodCardPayment.click();
        test.log(LogStatus.INFO, "Paying with FoodCard");
        cardNumberTextBox.sendKeys("4242424242424242");
        expiryTextBox.sendKeys("11/22");
        cvvTextBox.sendKeys("111");
        nameTextBox.sendKeys("Fname Lname");

    }

    private void payWithUPI() {
        upiPayment.click();
        test.log(LogStatus.INFO, "Paying with UPI");
    }

    private void payWithNetBanking() {
        netBankingPayment.click();

        test.log(LogStatus.INFO, "Paying with Net Banking");
    }

    //Login, Select Existing Address, Make a payment
    public void loginAndMakePayment(String mobileNumber, String password, String paymentMethod) {
        clickOnLoginBtn();
        LogInPage logInPage = new LogInPage(driver, report, test);
        logInPage.loginUser(mobileNumber, password);
        selectExistingAddress();
        payWithSelectedPaymentMethod(paymentMethod);
    }

    //Login, Add new address, Make a payment
    public void loginAndMakePayment(String mobileNumber, String password, String buildingAddress, String landmarkAddress, String paymentMethod) throws InterruptedException {
        clickOnLoginBtn();
        LogInPage logInPage = new LogInPage(driver, report, test);
        logInPage.loginUser(mobileNumber, password);
        clickOnAddNewAddressBtn();
        saveNewAddress(buildingAddress, landmarkAddress);
        payWithSelectedPaymentMethod(paymentMethod);
    }

    public void clickApplyCouponBtn() {
        applyCouponBtn.click();
    }

    public String getCouponName() {
        return couponName.get(0).getText();
    }

    public void clickApplyCouponSiedeBarBtn() {
        applyCouponSideBarBtn.get(0).click();
    }

    public String getAppliedCouponName(){
        return couponAppliedName.getText();
    }

    //Apply coupon and Verify the same
    public void applyCouponAndVerify(){
        clickApplyCouponBtn();
        String applyingCoupon = getCouponName();
        clickApplyCouponSiedeBarBtn();
        String couponAppliedName = getAppliedCouponName();
        Assert.assertEquals(applyingCoupon , couponAppliedName);
        test.log(LogStatus.INFO, "Coupon Verified");
    }


}
