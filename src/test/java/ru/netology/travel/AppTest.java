package ru.netology.travel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    //  TODO    mysql    OR    postgresql
    private String database = "postgresql";

    SqlGetters sqlGetters = new SqlGetters();

    @BeforeEach
    public void setUpAll() {
        open("http://localhost:8080");
    }

    @Test
    void successfulDebitBuy() {
        var order = new AppPage();
        order.debitTour(1);
        order.verifySuccess(database);
        assertEquals("APPROVED", sqlGetters.getStatus(database));
    }

    @Test
    void declinedDebitBuy() {
        var order = new AppPage();
        order.debitTour(2);
        order.verifyError();
        assertEquals("DECLINED", sqlGetters.getStatus(database));
    }

    @Test
    void successfulCreditBuy() {
        var order = new AppPage();
        order.creditTour(1);
        order.verifySuccess(database);
        assertEquals("APPROVED", sqlGetters.getStatus(database));
    }

    @Test
    void declinedCreditBuy() {
        var order = new AppPage();
        order.creditTour(2);
        order.verifyError();
        assertEquals("DECLINED", sqlGetters.getStatus(database));
    }
}
