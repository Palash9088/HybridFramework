package TestScripts;//import java.util.*;

import Pages.DashboardPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginUsingGmailTest extends TestBase {

    @Test(enabled = false)
    public void loginTest() {
        String expectedUsername = "Kailash Soni";
        DashboardPage dashboardPage = getDashboardPage();
        ArrayList<String> credentialList = new ArrayList<>();
        credentialList = readProperty();
        dashboardPage.loginSnapdeal(credentialList.get(0), credentialList.get(1));
        String actualUsername = dashboardPage.getSigninUsername();
        Assert.assertEquals(actualUsername, expectedUsername, "Text does not Matched");
    }

    @Test
    public void verifyHoverOptions() {
        List<String> expectedHoverOptionsList = new ArrayList<>(Arrays.asList("Your Account", "Your Orders", "Shortlist"));
        DashboardPage dashboardPage = getDashboardPage();
        List<String> actualHoverOptionsList = dashboardPage.getHoverOptions();

        Assert.assertEquals(actualHoverOptionsList,expectedHoverOptionsList);
    }

}
