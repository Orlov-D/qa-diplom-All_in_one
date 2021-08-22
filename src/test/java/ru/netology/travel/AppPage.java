package ru.netology.travel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;

import java.time.Duration;
import java.util.*;

import static com.codeborne.selenide.Selenide.*;

public class AppPage {
    private SelenideElement buyButton = $("[class=button__content]");
    private SelenideElement creditBuyButton = $$("[class=button__content]").get(1);
    private SelenideElement continueButton = $$("[class=button__content]").get(2);
    private SelenideElement cardNumber = $("[class=input__control]");
    private SelenideElement month = $$("[class=input__control]").get(1);
    private SelenideElement year = $$("[class=input__control]").get(2);
    private SelenideElement owner = $$("[class=input__control]").get(3);
    private SelenideElement code = $$("[class=input__control]").get(4);

    public void filForm(int number) {
        Random random = new Random();
        Faker faker = new Faker(new Locale("en"));
        switch (number) {
            case 1:
                cardNumber.setValue("4444 4444 4444 4441");
            case 2:
                cardNumber.setValue("4444 4444 4444 4442");
            default:
                cardNumber.setValue(faker.finance().creditCard());
        }
        month.setValue(String.format("%02d", random.nextInt(11) + 1));
        year.setValue(String.valueOf(random.nextInt(4) + 22));
        owner.setValue(faker.name().fullName());
        code.setValue(String.format("%03d", random.nextInt(999) + 1));
        continueButton.click();
    }

    public void debitTour(int number) {
        buyButton.click();
        filForm(number);
        SqlGetters.setCredit(false);
    }

    public void creditTour(int number) {
        creditBuyButton.click();
        filForm(number);
        SqlGetters.setCredit(true);
    }

    public void verifySuccess(String base) {
        $("[class=notification__title]").shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Успешно"));
        $("[class=notification__content]").shouldBe(Condition.visible).shouldHave(Condition.exactText("Операция одобрена Банком."));
    }

    public void verifyError() {
        $$("[class=notification__title]").get(1).shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Ошибка"));
        $$("[class=notification__content]").get(1).shouldBe(Condition.visible).shouldHave(Condition.exactText("Ошибка! Банк отказал в проведении операции."));
    }
}
