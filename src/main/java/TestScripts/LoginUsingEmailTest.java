package TestScripts;//import java.util.*;

import Pages.DashboardPage;
import Utils.ExcelFileReading;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginUsingEmailTest extends TestBase {

    @Test(dataProvider = "loginDataProvider")
    public void loginUsingEmailTest(String email, String password, String name) {
        DashboardPage dashboardPage = getDashboardPage();
        dashboardPage.doLoginWithEmail(email, password, name);
    }

    @DataProvider(name = "loginDataProvider")
    public Object[][] dataProvider() {
        Object[][] data = ExcelFileReading.getAllRows("src/main/resources/Demo2.xlsx", "LoginData");
        return data;
    }


}
