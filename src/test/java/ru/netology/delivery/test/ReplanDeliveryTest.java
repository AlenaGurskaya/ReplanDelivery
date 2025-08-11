package ru.netology.delivery.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class ReplanDeliveryTest {

    DataGenerator.UserInfo user = DataGenerator.Registration.generateUser("ru");

    @Test
    public void shouldBeSuccessMessage() {
        open("http://localhost:9999/");
        //Ввод в поле Город
        $("[data-test-id='city'] input").setValue(user.getCity());
        //Очистка поля Дата
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        //Ввод в поле Дата
        $("[data-test-id='date'] input").setValue(DataGenerator.generateDate(3));
        //Ввод в поле Фамилия и Имя
        $("[data-test-id='name'] input").setValue(user.getName());
        //Ввод в поле Телефон
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        //Нажатие на чек-бокс
        $("[data-test-id='agreement']").click();
        //Нажатие на кнопку Запланировать
        $$("button").find(exactText("Запланировать")).click();
        //Ввод новой даты
        String newDate = DataGenerator.generateDate(4);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(newDate);
        //Нажатие на кнопку Запланировать
        $$("button").find(exactText("Запланировать")).click();
        $$("[data-test-id='replan-notification'] button").find(exactText("Перепланировать")).click();
        //Проверка
        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .$(".notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + newDate));
    }
}
