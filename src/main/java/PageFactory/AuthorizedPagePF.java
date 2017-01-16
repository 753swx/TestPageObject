package PageFactory;


import BusinessObjects.Mail;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AuthorizedPagePF extends Page{

    public AuthorizedPagePF(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "a[data-shortcut-title='N']")
    private WebElement newMailButton;

    @FindBy(css = "textarea[data-original-name='To']")
    private WebElement addresseeField;

    @FindBy(css = "input[name='Subject']")
    private WebElement subjectField;

    @FindBy(css = "iframe[title='{#aria.rich_text_area}']")
    private WebElement inputTextFrame;

    @FindBy(id = "tinymce")
    private WebElement inputTextField;

    @FindBy(css = "div[data-name='saveDraft']")
    private WebElement saveDraftButton;

    @FindBy(css = "a[data-mnemo='drafts']")
    private WebElement draftsLink;

    @FindBy(css = "div[data-name='send']")
    private WebElement sendButton;

    @FindBy(css = "div[class='message-sent__title']")
    private WebElement sentMessage;

    @FindBy(css = "a[href='/messages/sent/']")
    private WebElement sentLink;

    @FindBy(css = "a[id='PH_logoutLink']")
    private WebElement logoutLink;

    public AuthorizedPagePF createNewMail(String addressee, String subject, String text)
    {
        newMailButton.click();
        addresseeField.sendKeys(addressee);
        subjectField.sendKeys(subject);
        driver.switchTo().frame(inputTextFrame);
        inputTextField.sendKeys(text);
        driver.switchTo().defaultContent();
        return this;
    }

    public AuthorizedPagePF actionCreateNewMail(String addressee, String subject, String text)
    {
        new Actions(driver).sendKeys("n").build().perform();
        addresseeField.sendKeys(addressee);
        subjectField.sendKeys(subject);
        driver.switchTo().frame(inputTextFrame);
        inputTextField.sendKeys(text);
        driver.switchTo().defaultContent();
        return this;
    }

    public AuthorizedPagePF actionCreateNewMail(Mail mail)
    {
        new Actions(driver).sendKeys("n").build().perform();
        addresseeField.sendKeys(mail.getAddressee());
        subjectField.sendKeys(mail.getSubject());
        driver.switchTo().frame(inputTextFrame);
        inputTextField.sendKeys(mail.getText());
        driver.switchTo().defaultContent();
        return this;
    }

    public AuthorizedPagePF saveDraft()
    {
        saveDraftButton.click();
        setImplicitlyWait(0);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("черновиках")));
        setImplicitlyWait(20);
        return this;
    }

    public AuthorizedPagePF actionSaveDraft()
    {
        new Actions(driver).keyDown(Keys.CONTROL).sendKeys("s").keyUp(Keys.CONTROL).build().perform();
        setImplicitlyWait(0);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("черновиках")));
        setImplicitlyWait(20);
        return this;
    }

    public AuthorizedPagePF openDrafts() {
        try {
            draftsLink.click();
        } catch (org.openqa.selenium.StaleElementReferenceException ex){
            openDrafts();
        }

        return this;
    }

//    public boolean presenceBySubject(String subject)
//    {
//        try {
//            driver.findElement(By.cssSelector("a[data-subject='"+ subject +"']"));
//            return true;
//
//        } catch (org.openqa.selenium.NoSuchElementException ex){
//            return false;
//        }
//    }

    public boolean presenceBySubjectInDrafts(String subject)
    {
        setImplicitlyWait(0);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.titleContains("Черновики"));
        setImplicitlyWait(20);

        List<WebElement> mailLists = driver.findElements(By.cssSelector("div[class='b-datalist b-datalist_letters b-datalist_letters_to']"));
        WebElement actualList = null;

        for (int i = 0; i < mailLists.size(); i++) {
            if(mailLists.get(i).isDisplayed()){
                actualList = mailLists.get(i);
            }
        }
        try {
            actualList.findElement(By.cssSelector("a[data-subject='"+ subject +"']"));
            return true;

        } catch (org.openqa.selenium.NoSuchElementException ex){
            return false;
        }
    }

    public boolean presenceBySubjectInSent(String subject)
    {
        try {
            setImplicitlyWait(0);
            WebDriverWait wait = new WebDriverWait(driver, 20);
            wait.until(ExpectedConditions.titleContains("Отправленные"));
            setImplicitlyWait(20);

            List<WebElement> mailLists = driver.findElements(By.cssSelector("div[class='b-datalist b-datalist_letters b-datalist_letters_to']"));
            WebElement actualList = null;

            for (int i = 0; i < mailLists.size(); i++) {
                if(mailLists.get(i).isDisplayed()){
                    actualList = mailLists.get(i);
                }
            }
            actualList.findElement(By.cssSelector("a[data-subject='"+ subject +"']"));
            return true;

        }catch (org.openqa.selenium.NoSuchElementException ex){
            try{
                openSent();
                List<WebElement> mailLists = driver.findElements(By.cssSelector("div[class='b-datalist b-datalist_letters b-datalist_letters_to']"));
                WebElement actualList2 = null;

                for (int i = 0; i < mailLists.size(); i++) {
                    if(mailLists.get(i).isDisplayed()){
                        actualList2 = mailLists.get(i);
                    }
                }
                actualList2.findElement(By.cssSelector("a[data-subject='"+ subject +"']"));
                return true;
            }catch (org.openqa.selenium.NoSuchElementException e) {
                return false;
            }catch (org.openqa.selenium.StaleElementReferenceException stEx){
                presenceBySubjectInSent(subject);
                return false;
            }
        }
    }

    public AuthorizedPagePF openMailBySubjectFromDrafts(String subject)
    {
        driver.findElement(By.cssSelector("a[data-subject='"+ subject +"'][href$='drafts/']")).click();
        return this;
    }

    public String getAddressee(){
        setImplicitlyWait(0);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='js-compose-label compose__labels__label'][2]")));
        setImplicitlyWait(20);
        return driver.findElement(By.cssSelector("input#compose_to")).getAttribute("value");
    }

    public String getSubject(){
        return driver.findElement(By.cssSelector("input[name='Subject'")).getAttribute("value");
    }

    public String getText(){
        driver.switchTo().frame(inputTextFrame);
        String textFromDrafts = driver.findElement(By.id("tinymce").cssSelector("div div div")).getText();
        driver.switchTo().defaultContent();
        return textFromDrafts;
    }

    public AuthorizedPagePF sendMail(){
        sendButton.click();
        setImplicitlyWait(0);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='message-sent__title']")));
        setImplicitlyWait(20);
        return this;
    }

    public boolean absenceBySubject(String subject){
        try {
            setImplicitlyWait(0);
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("a[data-subject='"+ subject +"']")));
            setImplicitlyWait(20);
            return true;
        } catch (org.openqa.selenium.TimeoutException e){
            try {
                openDrafts();
                setImplicitlyWait(0);
                WebDriverWait wait = new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("a[data-subject='"+ subject +"']")));
                setImplicitlyWait(20);
                return true;
            } catch (org.openqa.selenium.TimeoutException e1){
                setImplicitlyWait(20);
                return false;
            }
        }
    }

    public AuthorizedPagePF openSent() {
        try {
            sentLink.click();
        } catch (org.openqa.selenium.StaleElementReferenceException ex){
            openSent();
        }
        return this;
    }

    public LoginPagePF logout(){
        logoutLink.click();
        setImplicitlyWait(0);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("mailbox__auth__button")));
        return new LoginPagePF(driver);
    }

    public AuthorizedPagePF actionDeleteLastMail(){
        setImplicitlyWait(0);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.titleContains("Отправленные"));
        setImplicitlyWait(20);
//        List<WebElement> mailListsDIV = driver.findElements(By.cssSelector("div[class='b-datalist b-datalist_letters b-datalist_letters_to']"));
        List<WebElement> mailListsDIV = driver.findElements(By.cssSelector("div[class='b-datalist b-datalist_letters b-datalist_letters_to']"));
        WebElement visibleMailListsDiv = null;

        for (int i = 0; i < mailListsDIV.size(); i++) {

            if(mailListsDIV.get(i).isDisplayed()){
                visibleMailListsDiv = mailListsDIV.get(i);
            }
        }

        List<WebElement> mailLists = visibleMailListsDiv.findElements(By.cssSelector("div[data-bem='b-datalist__item']"));
        WebElement lastMail = mailLists.get(0);
//        String a = lastMail.findElement(By.tagName("a")).getAttribute("data-subject");
//        int divSize = mailListsDIV.size();
//        int size = mailLists.size();

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

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        return this;
    }


}
