package ru.netology.travel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;

import java.time.Duration;
import java.util.*;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppPage {
    private SelenideElement buyButton = $("[class=button__content]");
    private SelenideElement creditBuyButton = $$("[class=button__content]").get(1);
    private SelenideElement continueButton = $$("[class=button__content]").get(2);
    private SelenideElement cardNumber = $("[class=input__control]");
    private SelenideElement month = $$("[class=input__control]").get(1);
    private SelenideElement year = $$("[class=input__control]").get(2);
    private SelenideElement owner = $$("[class=input__control]").get(3);
    private SelenideElement code = $$("[class=input__control]").get(4);

    public void debitTour(int number) {
        Random random = new Random();
        Faker faker = new Faker(new Locale("en"));
        buyButton.click();
        if (number == 1) {
            cardNumber.setValue("4444 4444 4444 4441");
        } else {
            cardNumber.setValue("4444 4444 4444 4442");
        }
            month.setValue(String.format("%02d", random.nextInt(11) + 1));
        year.setValue(String.valueOf(random.nextInt(4) + 22));
        owner.setValue(faker.name().fullName());
        code.setValue(String.format("%03d", random.nextInt(999) + 1));
        continueButton.click();
    }

    public void verify(String base){
        $("[class=notification__title]").shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Успешно"));
        $("[class=notification__content]").shouldBe(Condition.visible).shouldHave(Condition.exactText("Операция одобрена Банком."));
        var sqlGetters = new SqlGetters();
        assertEquals("APPROVED", sqlGetters.getStatus(base));
    }
}
