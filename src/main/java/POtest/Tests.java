package POtest;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Scanner;
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

    private String addressee = "useful_person@mail.ru";
    private String subject = "tfgx1  11hkj4";
    private String text = "some te11111111ft";

    @BeforeClass
    public void setUp() {
        driver = new FirefoxDriver();
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
//        Assert.assertTrue(logPage.getTitle().contains("Входящие - Почта Mail.Ru"));
        Assert.assertTrue(logPage.isAuthorizationSuccessfull());
    }

    @Test(dependsOnMethods = { "AuthorizationTest" })
    public void PresenceInDrafts() {
        authPage.createNewMail(addressee, subject, text);
        authPage.saveDraft();
        authPage.openDrafts();
        Assert.assertTrue(authPage.presenceBySubject(subject));
    }

    @Test(dependsOnMethods = { "PresenceInDrafts" })
    public void CheckMailContent() {
        authPage.openMailBySubject(subject);
        Assert.assertEquals(authPage.getAddressee(), addressee + ",");
        Assert.assertEquals(authPage.getSubject(), subject);
        Assert.assertTrue(authPage.getText().contains(text));
    }

    @Test(dependsOnMethods = { "CheckMailContent" })
    public void SendAndCheckDrafts(){
        authPage.sendMail();
//        authPage.openDrafts();
        authPage.openDrafts();
        Assert.assertTrue(authPage.absenceBySubject(subject));

    }

    @Test(dependsOnMethods = { "SendAndCheckDrafts" })
    public void CheckPresenceInSent(){
        authPage.openSent();
        Assert.assertTrue(authPage.presenceBySubject(subject));
        authPage.logout();
    }
}
