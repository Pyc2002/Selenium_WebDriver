package objectPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriverWait wait;

    @FindBy(css = "form#login input[type='text']")
    private WebElement usernameField;
    @FindBy(css = "form#login input[type='password']")
    private WebElement passwordField;
    @FindBy(css = "form#login button")
    private WebElement loginButton;
    @FindBy(xpath = "//p[text() = 'Invalid credentials.']")
    private WebElement mistake401;

    public LoginPage(WebDriver webDriver, WebDriverWait wait) {
        PageFactory.initElements(webDriver, this);
        this.wait = wait;
    }

    public void login(String username, String password){
        typeUsernameField(username);
        typePassword(password);
        clickLoginButton();
    }

    public void typeUsernameField(String username){
        wait.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
    }

    public void typePassword(String password){
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
    }

    public void clickLoginButton(){
        wait.until(ExpectedConditions.visibilityOf (loginButton)).click();
    }
    public String getLoginMistake(){
        return wait.until(ExpectedConditions.visibilityOf(mistake401)).getText();
    }
}
