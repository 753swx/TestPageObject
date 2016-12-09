package POtest;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Created by Наташа on 08.12.2016.
 */
public class Tests {

    private WebDriver driver;

    private String login = "testtask2016";
    private String pass = "1123581321test";

    private String addressee = "useful_person@mail.ru";
    private String subject = "tfgx111hkj4";
    private String text = "some te11111111ft";


    @BeforeClass
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("https://mail.ru/");
    }

    @AfterClass
    public void tearDown() {

        driver.quit();
    }

    @Test
    public void AuthorizationTest() {
        LoginPage logPage = new LoginPage(driver);
        logPage.typeLogin(login);
        logPage.typePassword(pass);
        logPage.clickOnLoginButton();
//        System.out.println(logPage.getTitle());
        Assert.assertTrue(logPage.getTitle().contains("Входящие - Почта Mail.Ru"));

    }

    @Test(dependsOnMethods = { "AuthorizationTest" })
    public void PresenceInDrafts() {
        AuthorizedPage authPage = new AuthorizedPage(driver);
        authPage.createNewMail(addressee, subject, text);
        authPage.saveDraft();
        authPage.openDrafts();

//        String mailToSend = driver.findElement(By.cssSelector("a[data-subject='test'] div[class='b-datalist__item__addr']")).getText();
//        System.out.println("data-subject is: " + mailToSend);

//        System.out.println("return presenceBySubject is: " + authPage.presenceBySubject("texts"));

        Assert.assertTrue(authPage.presenceBySubject(subject));

    }

    @Test(dependsOnMethods = { "PresenceInDrafts" })
    public void CheckMailContent() {
        AuthorizedPage authPage = new AuthorizedPage(driver);
        authPage.openMailBySubject(subject);
        Assert.assertEquals(authPage.getAddressee(), addressee + ",");
        Assert.assertEquals(authPage.getSubject(), subject);
        Assert.assertTrue(authPage.getText().contains(text));
    }

    @Test(dependsOnMethods = { "CheckMailContent" })
    public void SendAndCheckDrafts(){
        AuthorizedPage authPage = new AuthorizedPage(driver);
        authPage.sendMail();
        authPage.openDrafts();
        authPage.openDrafts();
        Assert.assertTrue(authPage.absenceBySubject(subject));

    }
    @Test(dependsOnMethods = { "SendAndCheckDrafts" })
    public void CheckPresenceInSent(){
        AuthorizedPage authPage = new AuthorizedPage(driver);
        authPage.openSent();
        Assert.assertTrue(authPage.presenceBySubject(subject));
        authPage.logout();
    }
}
