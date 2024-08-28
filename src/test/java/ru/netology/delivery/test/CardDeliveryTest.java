package ru.netology.delivery.test;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CardDeliveryTest {
    @Test
    void shouldRegisterByAccountNumberDOMModification() {
        open("http://localhost:9999");

        String date1 = DataGenerator.generateDate(4);
        SelenideElement dateElement = $("[data-test-id=date] input");
        dateElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dateElement.setValue(date1);

        DataGenerator.UserInfo userInfo = DataGenerator.Registration.generateUser();
        $("[data-test-id=city] input").setValue(userInfo.getCity());
        $("[data-test-id=name] input").setValue(userInfo.getName());
        $("[data-test-id=phone] input").setValue(userInfo.getPhone());
        $("[data-test-id=agreement]").click();
        $("form button.button").click();

        $("[data-test-id=success-notification]")
                .shouldBe(visible, Duration.ofSeconds(3))
                .shouldHave(text("Встреча успешно запланирована на " + date1));

        String date2 = DataGenerator.generateDate(5);
        dateElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dateElement.setValue(date2);

        $("form button.button").click();

        SelenideElement replanNotification = $("[data-test-id=replan-notification]");

        replanNotification
                .shouldBe(visible, Duration.ofSeconds(3))
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));

        replanNotification.find(".notification__content button.button").click();

        $("[data-test-id=success-notification]")
                .shouldBe(visible, Duration.ofSeconds(3))
                .shouldHave(text("Встреча успешно запланирована на " + date2));
    }
}
