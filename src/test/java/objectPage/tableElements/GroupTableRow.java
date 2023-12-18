package objectPage.tableElements;

import com.codeborne.selenide.SelenideElement;
import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;


public class GroupTableRow {
    private SelenideElement element;
    public GroupTableRow(SelenideElement element) {
        this.element = element;
    }

    public String getTitleOfGroup() {
        return element.$x("./td[2]").should(visible).getText();
    }

    public String getStatusOfGroup() {
        return element.$x("./td[3]").should(visible).getText();
    }

    public void clickTrashIcon(){
        element.$x("./td/button[text()='delete']").should(visible).click();
        element.$x("./td/button[text()='restore_from_trash']").shouldBe(visible, Duration.ofSeconds(40));

    }

    public void clickRestoreFromTrashIcon() {
        element.$x("./td/button[text()='restore_from_trash']").shouldBe(visible).click();
        element.$x("./td/button[text()='delete']").should(visible, Duration.ofSeconds(40));
    }

    public void waitStudentsCount(int expectedCount) {
        element.$x("./td[4]//span[text()='%s']".formatted(expectedCount)).shouldBe(visible);
    }

    public void clickZoomInButton(){
        element.$x(".//td/button[contains(., 'zoom_in')]").should(visible).click();
    }
}
