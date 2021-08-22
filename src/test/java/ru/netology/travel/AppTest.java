package ru.netology.travel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
//  TODO    mysql    OR     postgresql
    private String database = "postgresql";
    @BeforeEach
    public void setUpAll() {
        open("http://localhost:8080");
    }

    @Test
    void successfulDebitBuy() {
        var order = new AppPage();
        order.debitTour(1);
        order.verify(database);
    }

    @Test
    void successfulCreditBuy() {
        var order = new AppPage();
        order.debitTour(2);
        order.verify(database);
        var sqlGetters = new SqlGetters();
        assertEquals("APPROVED", sqlGetters.getStatus("postgresql"));
    }
}
