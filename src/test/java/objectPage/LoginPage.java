package objectPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private SelenideElement usernameField = $("form#login input[type='text']");
    private SelenideElement passwordField = $("form#login input[type='password']");
    private SelenideElement loginButton = $("form#login button");
    private SelenideElement errorBlock = $("div.error-block");


    public void login(String username, String password){
        typeUsernameField(username);
        typePassword(password);
        clickLoginButton();
    }

    public void typeUsernameField(String username){
        usernameField.should(Condition.visible).setValue(username);
    }

    public void typePassword(String password){
        passwordField.should(Condition.visible).setValue(password);
    }

    public void clickLoginButton(){
        loginButton.should(Condition.visible).click();
    }
    public String getLoginMistake(){
        return errorBlock.should(Condition.visible).getText().replace("\n", " ");
    }
}
