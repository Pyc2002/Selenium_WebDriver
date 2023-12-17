package objectPage.tableElements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class StudentTableRow {

    private SelenideElement element;
    public StudentTableRow(SelenideElement element) {
        this.element = element;
    }


//    private SelenideElement titleOfStudent = element.$x("./td[2]");
//    private SelenideElement statusOfStudent = element.$x("./td[4]");
//    private SelenideElement trashIcon = element.$x("./td/button[text()='delete']");
//    private SelenideElement restoreFromTrashIcon = element.$x("./td/button[text()='restore_from_trash']");


    public String getTitleOfStudent() {
//        return titleOfStudent.should(Condition.visible).getText();
        return element.$x("./td[2]").getText();
    }

    public String getStatusOfStudent() {
//        return statusOfStudent.should(Condition.visible).getText();
        return element.$x("./td[4]").getText();
    }

    public void clickStudentTrashIcon(){
//        trashIcon.should(Condition.visible).click();
//        restoreFromTrashIcon.shouldBe(Condition.visible, Duration.ofSeconds(40));

        element.$x("./td/button[text()='delete']").click();
        element.$x("./td/button[text()='restore_from_trash']")
                .shouldBe(visible, Duration.ofSeconds(40));
    }

    public void clickStudentRestoreFromTrashIcon() {
//        restoreFromTrashIcon.should(Condition.visible).click();
//        trashIcon.shouldBe(Condition.visible, Duration.ofSeconds(40));

        element.$x("./td/button[text()='restore_from_trash']").click();
        element.$x("./td/button[text()='delete']")
                .shouldBe(visible, Duration.ofSeconds(30));
    }
}

