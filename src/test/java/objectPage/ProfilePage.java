package objectPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;


public class ProfilePage {
    private SelenideElement additionalInfo = $x("//h3/following-sibling::div//div[contains(text(), 'Full name')]/following-sibling::div");
    private SelenideElement avatarFullName = $("div.mdc-card h2");
    private SelenideElement editButton = $("button[title ='More options']");
    private SelenideElement form = $("form#update-item");
    private SelenideElement inputAvatar = form.$("input[type='file']");
    private SelenideElement inputDate = form.$("input[type='date']");
    private SelenideElement saveButton = form.$("button[type ='submit']");
    private SelenideElement closeEditingProfileButton = $x("//button[text()='close']");
    private SelenideElement getDateOfBirthInfo = $x("//div[@class='row svelte-vyyzan' and div[@class='label svelte-vyyzan' " +
            "and text()='Date of birth']]/div[@class='content svelte-vyyzan']");

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

    public void clearDateOfBirthField(){
        editButtonClick();
        inputDate.shouldBe(Condition.visible).clear();
        saveButtonClick();
        closeEditingProfileButtonClick();
    }

    public void inputDateOfBirth(String dateInput){
        inputDate.shouldBe(Condition.value(" "), Duration.ofSeconds(30));
        inputDate.should(Condition.visible).setValue(dateInput);
    }

    public void saveButtonClick(){
        saveButton.shouldBe(Condition.visible, Duration.ofSeconds(40)).click();
    }

    public void closeEditingProfileButtonClick(){
        closeEditingProfileButton.shouldBe(Condition.visible).click();
        closeEditingProfileButton.shouldBe(Condition.disappear, Duration.ofSeconds(30));
    }

    public String getDateOfBirth(){
        return getDateOfBirthInfo.should(Condition.visible).getText();
    }

}
