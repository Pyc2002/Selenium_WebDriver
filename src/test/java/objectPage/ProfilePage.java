package objectPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.io.File;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;


public class ProfilePage {
    private SelenideElement additionalInfo = $x("//h3/following-sibling::div//div[contains(text(), 'Full name')]/following-sibling::div");
    private SelenideElement avatarFullName = $("div.mdc-card h2");
    private SelenideElement editButton = $("button[title ='More options']");
    private SelenideElement form = $("form#update-item");
    private SelenideElement inputAvatar = form.$("input[type='file']");

    public String getAdditionalInfoText(){
       return additionalInfo.should(Condition.visible).getText();
    }

    public String getAvatarFullName(){
        return avatarFullName.should(Condition.visible).getText();
    }

    public void editButtonClick(){
        editButton.should(Condition.visible).click();
    }

    public void uploadNewAvatar(File file){
        inputAvatar.shouldBe(Condition.visible).uploadFile(file);
    }
    public String getAvatarValue(){
        String inputValue = inputAvatar.shouldBe(Condition.visible).getValue();
        return Objects.requireNonNull(inputValue)
                .substring(inputValue.lastIndexOf("\\") + 1);
    }
}
