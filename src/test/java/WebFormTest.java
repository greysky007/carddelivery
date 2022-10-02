import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class WebFormTest {

private String output;
    @BeforeEach
    void datin() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, 5); // Adding 5 days
        output = sdf.format(c.getTime());



    }

    @Test

    void shouldSetCorrectName1() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("input[placeholder=\"Город\"]").setValue("Москва");
        $("span [placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        $("span [placeholder=\"Дата встречи\"]").setValue(output);
        $("span [name=\"name\"]").setValue("Иванов Иван");
        $("span [name=\"phone\"]").setValue("+79874561287");
        $(".checkbox__box").click();
        $(".button__text").click();
        $x("//*[contains(text(),\"Успешно!\")]").should(visible, Duration.ofSeconds(18));
        String text = $(".notification__content").should(visible, Duration.ofSeconds(18)).getText();
        Assertions.assertEquals("Встреча успешно забронирована на " + output, text);

    }
    @Test

    void shouldSetCorrectName2() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("input[placeholder=\"Город\"]").setValue("Москва");
        $("span [placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        $("span [placeholder=\"Дата встречи\"]").setValue(output);
        $("span [name=\"name\"]").setValue("Иванов-Петров Иван");
        $("span [name=\"phone\"]").setValue("+79874561287");
        $(".checkbox__box").click();
        $(".button__text").click();
        $x("//*[contains(text(),\"Успешно!\")]").should(visible, Duration.ofSeconds(18));
        String text = $(".notification__content").should(visible, Duration.ofSeconds(18)).getText();
        Assertions.assertEquals("Встреча успешно забронирована на " + output, text);

    }

    @Test
    void shouldSetInvalidName() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("input[placeholder=\"Город\"]").setValue("Москва");
        $("span [placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        $("span [placeholder=\"Дата встречи\"]").setValue(output);
        $("span [name=\"name\"]").setValue("Ivanov");
        $("span [name=\"phone\"]").setValue("34534");
        $(".checkbox__box").click();
        $(".button__text").click();
        String text = $x("//*[@id=\"root\"]/div/form/fieldset/div[3]/span/span/span[3]").should(visible).getText();

        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);

    }
@Test
    void shouldSetInvalidCity() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("input[placeholder=\"Город\"]").setValue("Прага");
        $("span [placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        $("span [placeholder=\"Дата встречи\"]").setValue(output);
        $("span [name=\"name\"]").setValue("Иванов Иван");
        $("span [name=\"phone\"]").setValue("+79874561287");
        $(".checkbox__box").click();
        $(".button__text").click();
        String text = $x("//*[@id=\"root\"]/div/form/fieldset/div[1]/div/span/span/span[2]").should(visible).getText();

        Assertions.assertEquals("Доставка в выбранный город недоступна", text);

    }
    @Test
    void shouldSetInvalidNumber() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("input[placeholder=\"Город\"]").setValue("Москва");
        $("span [placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        $("span [placeholder=\"Дата встречи\"]").setValue(output);
        $("span [name=\"name\"]").setValue("Иванов Иван");
        $("span [name=\"phone\"]").setValue("34534");
        $(".checkbox__box").click();
        $(".button__text").click();
        String text = $x("//*[@id=\"root\"]/div/form/fieldset/div[4]/span/span/span[3]").should(visible).getText();

        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);

    }
}



