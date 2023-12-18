package objectPage.tableElements;

import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;


public class StudentTableRow {

    private SelenideElement element;
    public StudentTableRow(SelenideElement element) {
        this.element = element;
    }

    public String getTitleOfStudent() {
        return element.$x("./td[2]").getText();
    }

    public String getStatusOfStudent() {
        return element.$x("./td[4]").getText();
    }

    public void clickStudentTrashIcon(){
        element.$x("./td/button[text()='delete']").click();
        element.$x("./td/button[text()='restore_from_trash']")
                .shouldBe(visible, Duration.ofSeconds(40));
    }

    public void clickStudentRestoreFromTrashIcon() {
        element.$x("./td/button[text()='restore_from_trash']").click();
        element.$x("./td/button[text()='delete']")
                .shouldBe(visible, Duration.ofSeconds(30));
    }
}

