import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pom.CheckOutPage;
import pom.HomePage;
import pom.RestaurantsListPage;
import pom.RestaurantsPage;
import utils.Constants;
import utils.ExcelUtility;
import utils.ExtentReportUtil;
import utils.ScreenshotUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AllTests {

    private WebDriver driver;
    private String baseURL;
    private String browser;
    private HomePage homePage;
    private RestaurantsListPage restaurantsListPage;
    private RestaurantsPage restaurantsPage;
    private CheckOutPage checkOutPage;
    private ExtentReports report;
    private ExtentTest test;

    @BeforeClass
    public void beforeClassMethod() throws Exception {

        baseURL = Constants.URL;
        report = ExtentReportUtil.getInstance();
        ExcelUtility.setExcelFile(Constants.File_Path + Constants.File_Name, "swiggyData");
    }


    @BeforeMethod
    @Parameters({"browsers"})
    public void setUp(String browsers) {

        if (browsers.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "libs/geckodriver");
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
            browser = browsers;
        } else if (browsers.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver" , "libs/chromedriver");
            driver = new ChromeDriver();
            driver.manage().window().fullscreen();
            browser = browsers;
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(baseURL);

    }

    @DataProvider(name = "myData")
    public Object[][] dataProvider() {
        Object[][] testData = ExcelUtility.getTestData("testData");
        return testData;
    }

    @Test (dataProvider="myData" , enabled = false)
    public void orderFromRestaurantWithSingleOutlet(String searchQuery, String exactLocation, String mobileNumber, String password, String buildingAddress,
                                                    String landmarkAddress, String paymentMethod) throws InterruptedException {
        test = report.startTest("Order from Restaurant with Single Outlet selecting Existing Address - " + browser);

        homePage = new HomePage(driver, report, test);
        homePage.enterAddress(searchQuery, exactLocation);

        restaurantsListPage = new RestaurantsListPage(driver, report, test);
        restaurantsListPage.clickOnFirstAvailableRestaurantWithSingleOutlet();

        restaurantsPage = new RestaurantsPage(driver, report, test);
        restaurantsPage.addFoodItemAndCheckout();

        checkOutPage = new CheckOutPage(driver, report, test);
        checkOutPage.loginAndMakePayment(mobileNumber, password, paymentMethod);

    }

    @Test (dataProvider="myData", priority = 0 , enabled = true)
    public void orderFromRestaurantWithSingleOutletAddNewAddress(String searchQuery, String exactLocation, String mobileNumber, String password, String buildingAddress,
                                                                 String landmarkAddress, String paymentMethod) throws InterruptedException {
        test = report.startTest("Order from Restaurant with Single Outlet Adding New Address - " + browser);

        homePage = new HomePage(driver, report, test);
        homePage.enterAddress(searchQuery, exactLocation);

        restaurantsListPage = new RestaurantsListPage(driver, report, test);
        restaurantsListPage.clickOnFirstAvailableRestaurantWithSingleOutlet();

        restaurantsPage = new RestaurantsPage(driver, report, test);
        restaurantsPage.addFoodItemAndCheckout();

        checkOutPage = new CheckOutPage(driver, report, test);
        checkOutPage.loginAndMakePayment(mobileNumber, password, buildingAddress, landmarkAddress, paymentMethod);

    }

    @Test (dataProvider="myData", enabled = false)
    public void orderFromRestaurantWithMultipleOutlet(String searchQuery, String exactLocation, String mobileNumber, String password, String buildingAddress,
                                                      String landmarkAddress, String paymentMethod) throws InterruptedException {
        test = report.startTest("Order from Restaurant with Multiple Outlet - " + browser);

        homePage = new HomePage(driver, report, test);
        homePage.enterAddress(searchQuery, exactLocation);

        restaurantsListPage = new RestaurantsListPage(driver, report, test);
        restaurantsListPage.clickOnFirstAvailableRestaurantWithMulipleOutlet();

        restaurantsPage = new RestaurantsPage(driver, report, test);
        restaurantsPage.addFoodItemAndCheckout();

        checkOutPage = new CheckOutPage(driver, report, test);
        checkOutPage.loginAndMakePayment(mobileNumber, password, paymentMethod);

    }

    @Test (dataProvider="myData", enabled = false)
    public void applyCoupon(String searchQuery, String exactLocation, String mobileNumber, String password, String buildingAddress,
                                                      String landmarkAddress, String paymentMethod) throws InterruptedException {
        test = report.startTest("Apply coupon - " + browser);

        homePage = new HomePage(driver, report, test);
        homePage.enterAddress(searchQuery, exactLocation);

        restaurantsListPage = new RestaurantsListPage(driver, report, test);
        restaurantsListPage.clickOnFirstAvailableRestaurantWithSingleOutlet();

        restaurantsPage = new RestaurantsPage(driver, report, test);
        restaurantsPage.addFoodItemAndCheckout();

        checkOutPage = new CheckOutPage(driver, report, test);
        checkOutPage.loginAndMakePayment(mobileNumber, password, paymentMethod);
        checkOutPage.applyCouponAndVerify();

    }


    @AfterMethod
    public void tearDown(ITestResult testResult) throws IOException, InterruptedException {
        String path = ScreenshotUtil.takeScreenshot(driver, testResult.getName() + "-" + java.time.LocalDateTime.now());
        String imagePath = test.addScreenCapture(path);

        if (testResult.getStatus() == ITestResult.FAILURE) {
            test.log(LogStatus.FAIL, testResult.getThrowable().toString(), imagePath);
        } else {
            test.log(LogStatus.PASS, "Verification Successful", imagePath);
        }

        Thread.sleep(5000);
        driver.quit();
        report.endTest(test);
        report.flush();
    }

    @AfterClass
    public void afterClassMethod() throws InterruptedException {

    }
}