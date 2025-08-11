package ru.netology.delivery.test;

import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class ReplanDeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
            //Ввод в поле Город
        $("[data-test-id='city'] input").setValue(validUser.getCity());
            //Очистка поля Дата
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
            //Ввод в поле Дата
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
            //Ввод в поле Фамилия и Имя
        $("[data-test-id='name'] input").setValue(validUser.getName());
            //Ввод в поле Телефон
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
            //Нажатие на чек-бокс
        $("[data-test-id='agreement']").click();
            //Нажатие на кнопку Запланировать
        $$("button").find(exactText("Запланировать")).click();
            //Ввод новой даты
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(secondMeetingDate);
            //Нажатие на кнопку Запланировать
        $$("button").find(exactText("Запланировать")).click();
        $$("[data-test-id='replan-notification'] button").find(exactText("Перепланировать")).click();
            //Проверка
        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .$(".notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }
}