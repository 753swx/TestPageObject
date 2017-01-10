package Test;

import POtest.AuthorizedPage;
import POtest.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Tests {

    private WebDriver driver;
    private LoginPage logPage;
    private AuthorizedPage authPage;

    private String login = "testtask2016";
    private String pass = "1123581321test";
    private String addressee = "testtask2016@mail.ru";
    private String subject = "some subject";
    private String text = "some text";

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        logPage = new LoginPage(driver);
        authPage = new AuthorizedPage(driver);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("https://mail.ru/");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void AuthorizationTest() {
        logPage.typeLogin(login);
        logPage.typePassword(pass);
        logPage.clickOnLoginButton();
        Assert.assertTrue(logPage.isAuthorizationSuccessful());
    }

    @Test(dependsOnMethods = { "AuthorizationTest" })
    public void PresenceInDrafts() {
        authPage.createNewMail(addressee, subject, text);
        authPage.saveDraft();
        authPage.openDrafts();
        Assert.assertTrue(authPage.presenceBySubject(subject));
        authPage.contextClickLastMail();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    @Test(dependsOnMethods = { "PresenceInDrafts" })
//    public void CheckMailContent() {
//        authPage.openMailBySubject(subject);
//        Assert.assertEquals(authPage.getAddressee(), addressee + ",");
//        Assert.assertEquals(authPage.getSubject(), subject);
//        Assert.assertTrue(authPage.getText().contains(text));
//    }
//
//    @Test(dependsOnMethods = { "CheckMailContent" })
//    public void SendAndCheckDrafts(){
//        authPage.sendMail();
//        authPage.openDrafts();
//        Assert.assertTrue(authPage.absenceBySubject(subject));
//    }
//
//    @Test(dependsOnMethods = { "SendAndCheckDrafts" })
//    public void CheckPresenceInSent(){
//        authPage.openSent();
//        Assert.assertTrue(authPage.presenceBySubject(subject));
//        authPage.logout();
//    }
}
