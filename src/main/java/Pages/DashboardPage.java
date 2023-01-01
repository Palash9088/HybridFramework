package Pages;//import java.util.*;

import Base.PredefinedActions;
import Utils.PropertyFileReading;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DashboardPage extends PredefinedActions {
    private static DashboardPage dashboardPage;
    //PropertyFileReading propOperation;
    PropertyFileReading dashboardPageProp;
    String parentWindow;


    private DashboardPage() {
     /*   try {
            propOperation = new PropertyFileReading("src/main/resources/CredentialsProp.properties");
        } catch (IOException e) {
            throw new RuntimeException("Credentials property file not found");
            //Custom Exception
        }*/
        try {
            dashboardPageProp = new PropertyFileReading("src/main/resources/DashboardPage.properties");
        } catch (IOException e) {
            throw new RuntimeException("Dashboard property file not found");
        }
    }

    synchronized static public DashboardPage getDashboardPage() {
        if (dashboardPage == null)
            dashboardPage = new DashboardPage();
        return dashboardPage;
    }

    private void hoverOnSignIn() {
        WebElement target = getElement(dashboardPageProp.getPropertyValue("signInMenu"), true);
        hoverOnElement(target, true);
    }

    private void clickOnLoginButton() {
        //getElement("xpath", "//a[text()='login']", true).click();
        clickOnElement(dashboardPageProp.getPropertyValue("loginBtn"), true);
        System.out.println("Clicked on Login");
    }

    private void switchToFrame() {
        switchToFrameByElement(getElement(dashboardPageProp.getPropertyValue("frameSwitch"), true));
        System.out.println("Switched to Frame");
    }

    private void loginUsingGmail(String gUserName, String gPassword) {
        WebElement element = getElement(dashboardPageProp.getPropertyValue("gLoginBtn"), true);
        // javaScriptClick(element);
        clickOnElement(element);
        System.out.println("Clicked on google login Button");
        Set<String> windowHandles = getAllWindowHandlesId();
        Iterator<String> iterator = windowHandles.iterator();
        parentWindow = iterator.next();
        String childWindow = iterator.next();
        switchToSpecificWindow(childWindow);

        enterText(dashboardPageProp.getPropertyValue("gEmail"), true, gUserName);
        //  getElement("xpath", "//input[@aria-label='Email or phone']", true).sendKeys("kailashsoni007test@gmail.com");
        System.out.println("Entered Email");

        getElement(dashboardPageProp.getPropertyValue("nextBtn"), true).click();
        System.out.println("Clicked on Next");

        WebElement password = getElement(dashboardPageProp.getPropertyValue("gPass"), true);
        enterText(password, gPassword);
        System.out.println("Entered Password");

        //getElement("xpath", "//span[@class='VfPpkd-vQzf8d'][text()='Next']", true).click();
        clickOnElement(dashboardPageProp.getPropertyValue("nextBtnPass"), true);
    }

    private void switchToParentWindow() {
        switchToSpecificWindow(parentWindow);
        System.out.println("Switched to Parent window");
    }

    public void loginSnapdeal(String gUserName, String gPassword) {
        hoverOnSignIn();
        clickOnLoginButton();
        switchToFrame();
        loginUsingGmail(gUserName, gPassword);
        switchToParentWindow();

    }

    public List<String> getHoverOptions() {
        hoverOnSignIn();
        List<String> elementListText = getElementListText(dashboardPageProp.getPropertyValue("beforeLoginList"), true);
        return elementListText;
    }

    public String getSigninUsername() {
        String signInName = "Sign In";
        while (signInName.equals("Sign In")) {
            signInName = getElement(dashboardPageProp.getPropertyValue("userName"), true).getText();
        }
        return signInName;
        // return getElement("xpath", "//span[contains(@class,'accountUserName')]", true).getText();
    }

    public void doLoginWithEmail(String email, String password, String name) {
        hoverOnSignIn();
        clickOnLoginButton();
        switchToFrame();
        enterText(getElement(dashboardPageProp.getPropertyValue("loginText"), true), email);
        clickOnElement(getElement(dashboardPageProp.getPropertyValue("continueButton"), true));
        EmailPage emailPage = EmailPage.getPageObject();
        String otp = emailPage.getOtp(email,password);
        switchToFrame();
        enterText(getElement(dashboardPageProp.getPropertyValue("otpInputValue"),true),otp);
        clickOnElement(getElement(dashboardPageProp.getPropertyValue("otpLoginBtn"),true));

    }
}
