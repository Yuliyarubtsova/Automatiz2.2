package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

    @BeforeEach
    void shouldOpenSite() {
        open("http://localhost:9999");
    }

    @Test
    void shouldRegisterByValidInformation() {
        $("[data-test-id=city] input").setValue("Москва");
        String newData = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=date] input").setValue(newData);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79146568935");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована на")).shouldBe(visible, Duration.ofSeconds(15));
        $(withText(newData)).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldRegisterByInValidCity() {
        $("[data-test-id=city] input").setValue("Лондон");
        String newData = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=date] input").setValue(newData);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79146568935");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(visible);
//
    }

    @Test
    void shouldRegisterByInValidData() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=date] input").setValue("01.04.2021");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79146568935");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Заказ на выбранную дату невозможен")).shouldBe(visible);
    }

    @Test
    void shouldRegisterByInValidName() {
        $("[data-test-id=city] input").setValue("Москва");
        String newData = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=date] input").setValue(newData);
        $("[data-test-id=name] input").setValue("Sergey 1234");
        $("[data-test-id=phone] input").setValue("+79146568935");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы")).shouldBe(visible);
    }

    @Test
    void shouldRegisterByInValidPhone() {
        $("[data-test-id=city] input").setValue("Москва");
        String newData = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=date] input").setValue(newData);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("89146568935");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678")).shouldBe(visible);
    }
}
