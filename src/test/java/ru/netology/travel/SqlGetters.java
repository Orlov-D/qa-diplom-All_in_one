package ru.netology.travel;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public final class SqlGetters {

    @SneakyThrows
    private Connection getConnection(String base) {
        if (base.equalsIgnoreCase("postgresql")) {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/rand", "app", "pass");
        } else {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        }
    }

    @SneakyThrows
    private String getLastPaymentId(String base) {
        Connection conn = getConnection(base);
        var dataStmt = conn.createStatement().executeQuery("SELECT * FROM order_entity ORDER BY created DESC");
        String paymentId = null;
        try (var rs = dataStmt) {
            while (rs.next()) {
                paymentId = rs.getString("payment_id");
                break;
            }
        }
        return paymentId;
    }

    @SneakyThrows
    public String getStatus(String base) {
        Connection conn = getConnection(base);
        var dataStmt = conn.createStatement().executeQuery("SELECT * FROM payment_entity ORDER BY created DESC");
        String status = null;
        try (var rs = dataStmt) {
            while (rs.next()) {
                status = rs.getString("status");
                var transactionId = rs.getString("transaction_id");
                if (transactionId.equalsIgnoreCase(getLastPaymentId(base))) {
                    break;
                }
            }
        }
        return status;
    }
//
//    @SneakyThrows
//    public int[] getBalance() {
//        var conn = getConnection();
//        var dataStmt = conn.createStatement().executeQuery("SELECT * FROM cards");
//        int balanceFirst = NULL;
//        int balanceSecond = NULL;
//        int[] cards = new int[2];
//        try (var rs = dataStmt) {
//            while (rs.next()) {
//                int balance_in_kopecks = rs.getInt("balance_in_kopecks");
//                var cardNumber = rs.getString("number");
//                if (cardNumber.equals("5559 0000 0000 0002")) {
//                    cards[1] = balance_in_kopecks;
//                } else {
//                    cards[0] = balance_in_kopecks;
//                }
//            }
//        }
//        return cards;
//    }
}
