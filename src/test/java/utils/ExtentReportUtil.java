package utils;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentReportUtil {

    public static ExtentReports getInstance() {
        ExtentReports extent;
        String Path = "//Users//Home//Desktop//Test - " + java.time.LocalDate.now() + ".html";
        extent = new ExtentReports(Path, false);
        return extent;
    }

}
