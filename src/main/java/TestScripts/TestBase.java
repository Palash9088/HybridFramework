package TestScripts;//import java.util.*;

import Base.PredefinedActions;
import Pages.DashboardPage;
import Utils.PropertyFileReading;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.util.ArrayList;


public class TestBase {
    private DashboardPage dashboardPage;

    @BeforeMethod
    void setDriver() {
        PredefinedActions.openBrowser("https://www.snapdeal.com/", "chrome");
    }

    DashboardPage getDashboardPage() {
        if (dashboardPage == null)
            dashboardPage = DashboardPage.getDashboardPage();
        return dashboardPage;
    }

    public ArrayList<String> readProperty() {
        PropertyFileReading prop;
        ArrayList<String> credentialList = new ArrayList<>();
        try {
            prop = new PropertyFileReading("src/main/resources/CredentialsProp.properties");
            credentialList.add(prop.getPropertyValue("gUserName"));
            credentialList.add(prop.getPropertyValue("gPassword"));
        } catch (IOException e) {
            throw new RuntimeException("Not valid file");
        }
    return credentialList;
    }

    @AfterMethod(enabled = false)
    void tearDown(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE)
            PredefinedActions.takeScreenshot(result.getName());
        PredefinedActions.quitBrowser();
    }
}
