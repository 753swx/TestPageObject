package PreviousVersion;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;



public class AuthorizedPage {

    private WebDriver driver;

    public AuthorizedPage(WebDriver driver)
    {
        this.driver = driver;
    }

    By newMailButton = By.cssSelector("a[data-shortcut-title='N']");
    By addresseeField = By.cssSelector("textarea[data-original-name='To']");
    By subjectField = By.cssSelector("input[name='Subject']");
    By inputTextFrame = By.cssSelector("iframe[title='{#aria.rich_text_area}']");
    By inputTextField = By.id("tinymce");
    By saveDraftButton = By.cssSelector("div[data-name='saveDraft']");
    By draftsLink = By.cssSelector("a[data-mnemo='drafts']");
    By sendButton = By.cssSelector("div[data-name='send']");
    By sentMessage = By.cssSelector("div[class='message-sent__title']");
    By sentLink = By.cssSelector("a[href='/messages/sent/']");
    By logoutLink = By.cssSelector("a[id='PH_logoutLink']");

    public void createNewMail(String addressee, String subject, String text)
    {
        driver.findElement(newMailButton).click();
        driver.findElement(addresseeField).sendKeys(addressee);
        driver.findElement(subjectField).sendKeys(subject);
        driver.switchTo().frame(driver.findElement(inputTextFrame));
        driver.findElement(inputTextField).sendKeys(text);
        driver.switchTo().defaultContent();
    }

    public void actionCreateNewMail(String addressee, String subject, String text) {
        new Actions(driver).sendKeys("n").build().perform();
        driver.findElement(addresseeField).sendKeys(addressee);
        driver.findElement(subjectField).sendKeys(subject);
        driver.switchTo().frame(driver.findElement(inputTextFrame));
        driver.findElement(inputTextField).sendKeys(text);
        driver.switchTo().defaultContent();
    }

    public void saveDraft()
    {
        driver.findElement(saveDraftButton).click();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("черновиках")));
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    public void actionSaveDraft()
    {
        new Actions(driver).keyDown(Keys.CONTROL).sendKeys("s").keyUp(Keys.CONTROL).build().perform();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("черновиках")));
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    public void openDrafts() {
        try {
            driver.findElement(draftsLink).click();
        } catch (org.openqa.selenium.StaleElementReferenceException ex){
            openDrafts();
        }

    }

    public boolean presenceBySubject(String subject)
    {
        try {
            driver.findElement(By.cssSelector("a[data-subject='"+ subject +"']"));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException ex){
            return false;
        }
    }

    public void openMailBySubject(String subject)
    {
        driver.findElement(By.cssSelector("a[data-subject='"+ subject +"'][href$='drafts/']")).click();
    }

    public String getAddressee(){
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='js-compose-label compose__labels__label'][2]")));
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver.findElement(By.cssSelector("input#compose_to")).getAttribute("value");
    }

    public String getSubject(){
        return driver.findElement(By.cssSelector("input[name='Subject'")).getAttribute("value");
    }

    public String getText(){
        driver.switchTo().frame(driver.findElement(inputTextFrame));
        String textFromDrafts = driver.findElement(By.id("tinymce").cssSelector("div div div")).getText();
        driver.switchTo().defaultContent();
        return textFromDrafts;
    }

    public void sendMail(){
        driver.findElement(sendButton).click();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(sentMessage));
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    public boolean absenceBySubject(String subject){
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("a[data-subject='"+ subject +"'][href$='drafts/']")));
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            return true;
        } catch (org.openqa.selenium.TimeoutException e){
            try {
                openDrafts();
                driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                WebDriverWait wait = new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("a[data-subject='"+ subject +"'][href$='drafts/']")));
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                return true;
            } catch (org.openqa.selenium.TimeoutException e1){
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                return false;
            }
        }

//        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
//        try {
//            driver.findElement(By.cssSelector("a[data-subject='"+ subject +"']"));
//            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//            return false;
//        } catch (org.openqa.selenium.NoSuchElementException ex){
//            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//            return true;
//        }

    }

    public void openSent() {
        driver.findElement(sentLink).click();
    }

    public void logout(){
        driver.findElement(logoutLink).click();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("mailbox__auth__button")));
    }

    public void actionDeleteLastMail(){
        List<WebElement> dataListDiv = driver.findElements(By.cssSelector("div.b-datalist__body"));
        List<WebElement> mailList = driver.findElements(By.cssSelector("div[class='b-datalist b-datalist_letters b-datalist_letters_to']" +
                " div[data-bem='b-datalist__item']"));
        WebElement lastMail = mailList.get(0);
        String a = lastMail.findElement(By.tagName("a")).getAttribute("data-subject");
        int divSize = dataListDiv.size();
        int size = mailList.size();

        Actions action = new Actions(driver);
        action.contextClick(lastMail).build().perform();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        WebElement delMailFromContextMenu = driver.findElement(By.cssSelector("div[class='b-dropdown__list b-dropdown__list_contextmenu'] a[data-num='2']"));
        action.moveToElement(delMailFromContextMenu).build().perform();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        action.click().build().perform();
//        driver.findElement(By.cssSelector("div[class='b-dropdown__list b-dropdown__list_contextmenu'] a[data-num='2']")).click();
        System.out.println("mail list is displayed: " + lastMail.isDisplayed());
        System.out.println("data title: " + a);
        System.out.println("div list size: " + divSize);
        System.out.println("list size: " + size);

    }
}

