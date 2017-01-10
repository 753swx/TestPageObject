package PageFactory;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class TestPF {
    private WebDriver driver;
    private LoginPagePF logPage;
    private AuthorizedPagePF authPage;

    private String login = "testtask2016";
    private String pass = "1123581321test";
    private String addressee = "testtask2016@mail.ru";
    private String subject = "some subject";
    private String text = "some text";

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        logPage = new LoginPagePF(driver);
        logPage.setImplicitlyWait(20);
        driver.get("https://mail.ru/");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void AuthorizationTest() {
        logPage.typeLogin(login)
                .typePassword(pass);
        authPage = logPage.clickOnLoginButton();
        Assert.assertTrue(logPage.isAuthorizationSuccessful());
    }

    @Test(dependsOnMethods = { "AuthorizationTest" })
    public void PresenceInDrafts() {
        authPage.createNewMail(addressee, subject, text)
                .saveDraft()
                .openDrafts();
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
        authPage.sendMail()
                .openDrafts();
        Assert.assertTrue(authPage.absenceBySubject(subject));
    }

    @Test(dependsOnMethods = { "SendAndCheckDrafts" })
    public void CheckPresenceInSent(){
        authPage.openSent();
        Assert.assertTrue(authPage.presenceBySubject(subject));
        authPage.logout();
    }
}
