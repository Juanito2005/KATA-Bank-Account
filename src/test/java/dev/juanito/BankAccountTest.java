package dev.juanito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        bankAccount = new BankAccount(10000, 12);
    }

    @Test
    void testDepositMoney() {
        bankAccount.depositMoney(2000);
        assertEquals(12000, bankAccount.balance, 0.01);
        assertEquals(1, bankAccount.numberConsignments);
    }

    @Test
    void testWithdrawMoney_ValidAmount() {
        float remainingBalance = bankAccount.withdrawMoney(5000);
        assertEquals(5000, remainingBalance, 0.01);
        assertEquals(1, bankAccount.numberWithdrawals);
    }

    @Test
    void testWithdrawMoney_InvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            bankAccount.withdrawMoney(20000);
        });
    }

    @Test
    void testCalculateInterest() {
        bankAccount.calculateInterest();
        float expectedInterest = (12 / 12.0f) * 10000 / 100;
        assertEquals(10000 + expectedInterest, bankAccount.balance, 0.01);
    }

    @Test
    void testMakeExtracts() {
        bankAccount.monthlyComission = 500; // Definimos una comisión mensual de 500
        bankAccount.makeExtracts();

        float expectedInterest = (12 / 12.0f) * 10000 / 100; // 1% mensual
        float expectedBalance = 9995 - 500 + expectedInterest; // Saldo después de comisión e interés
        assertEquals(expectedBalance, bankAccount.balance);
    }

    @Test
    void testPrint() {
        bankAccount.depositMoney(2000);
        bankAccount.withdrawMoney(1000);
        String expectedOutput = """
                Balance: 11000.0
                Number of consignments: 2
                Number of withdrawals: 1
                Annual rate: 12.0
                Monthly commission: 0.0
                """;
        assertEquals(expectedOutput, bankAccount.print());
    }
}

