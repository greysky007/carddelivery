import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class WebFormTest {

    private String planningDate = generateDate(4);


    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldSetCorrectName1() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("input[placeholder=\"Город\"]").setValue("Москва");
        $("span [placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        $("span [placeholder=\"Дата встречи\"]").setValue(planningDate);
        $("span [name=\"name\"]").setValue("Иванов Иван");
        $("span [name=\"phone\"]").setValue("+79874561287");
        $(".checkbox__box").click();
        $(".button__text").click();
        $x("//*[contains(text(),\"Успешно!\")]").should(visible, Duration.ofSeconds(18));
        $(".notification__content").should(visible, Duration.ofSeconds(18))
                .shouldHave(exactText("Встреча успешно забронирована на " + planningDate));


    }

    @Test
    void shouldSetCorrectName2() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("input[placeholder=\"Город\"]").setValue("Москва");
        $("span [placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        $("span [placeholder=\"Дата встречи\"]").setValue(planningDate);
        $("span [name=\"name\"]").setValue("Иванов-Петров Иван");
        $("span [name=\"phone\"]").setValue("+79874561287");
        $(".checkbox__box").click();
        $(".button__text").click();
        $x("//*[contains(text(),\"Успешно!\")]").should(visible, Duration.ofSeconds(18));
        $x("//*[contains(text(),\"Успешно!\")]").should(visible, Duration.ofSeconds(18));
        $(".notification__content").should(visible, Duration.ofSeconds(18))
                .shouldHave(exactText("Встреча успешно забронирована на " + planningDate));

    }

    @Test
    void shouldSetInvalidName() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("input[placeholder=\"Город\"]").setValue("Москва");
        $("span [placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        $("span [placeholder=\"Дата встречи\"]").setValue(planningDate);
        $("span [name=\"name\"]").setValue("Ivanov");
        $("span [name=\"phone\"]").setValue("34534");
        $(".checkbox__box").click();
        $(".button__text").click();
        $x("//span[contains (text(),'Имя')]").should(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));


    }

    @Test
    void shouldSetInvalidCity() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("input[placeholder=\"Город\"]").setValue("Прага");
        $("span [placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        $("span [placeholder=\"Дата встречи\"]").setValue(planningDate);
        $("span [name=\"name\"]").setValue("Иванов Иван");
        $("span [name=\"phone\"]").setValue("+79874561287");
        $(".checkbox__box").click();
        $(".button__text").click();
        $x("//span[contains (text(),'Доставка')]").should(visible)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));


    }

    @Test
    void shouldSetInvalidNumber() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("input[placeholder=\"Город\"]").setValue("Москва");
        $("span [placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        $("span [placeholder=\"Дата встречи\"]").setValue(planningDate);
        $("span [name=\"name\"]").setValue("Иванов Иван");
        $("span [name=\"phone\"]").setValue("34534");
        $(".checkbox__box").click();
        $(".button__text").click();
        $x("//span[contains (text(),'Телефон')]").should(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));


    }


}



