package Pages;//import java.util.*;

import Base.PredefinedActions;
import Utils.PropertyFileReading;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class EmailPage extends PredefinedActions {
    PropertyFileReading EmailPageProp;
    static EmailPage emailPage;

    private EmailPage() {
        try {
            EmailPageProp = new PropertyFileReading("src/main/resources/EmailProp.properties");
        } catch (IOException e) {
            throw new RuntimeException("Dashboard property file not found");
        }
    }

    //Made page object here
    static EmailPage getPageObject() {
        if (emailPage == null)
            emailPage = new EmailPage();
        return emailPage;
    }

    String getOtp(String emailId, String password) {
        openNewTab();
        Set<String> windowHandlesId = getAllWindowHandlesId();
        Iterator<String> itr = windowHandlesId.iterator();
        String mainWindow = itr.next();
        String childWindow = itr.next();
        switchToSpecificWindow(childWindow);

        if (emailId.contains("rediffmail"))
            navigateTo("https://mail.rediff.com/cgi-bin/login.cgi");

        enterText(getElement(EmailPageProp.getPropertyValue("rediffEmail"), true), emailId);
        WebElement pass = getElement(EmailPageProp.getPropertyValue("rediffPassword"), true);
        enterText(pass, password);
        clickOnElement(getElement(EmailPageProp.getPropertyValue("rediffsignInBtn"), true));


        String otp = getElementText(EmailPageProp.getPropertyValue("rediffMailOtp"), true).split(" ")[0];
        closeBrowser();
        switchToSpecificWindow(mainWindow);
        return otp;
    }
}
