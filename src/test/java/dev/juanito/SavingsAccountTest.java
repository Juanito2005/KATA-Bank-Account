package dev.juanito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SavingsAccountTest {

    private SavingsAccount savingsAccount;

    @BeforeEach
    void setUp() {
        // Initialize the SavingsAccount with a balance of 15000 and annual rate of 5%
        savingsAccount = new SavingsAccount(15000, 5);
    }

    @Test
    void testInitialStatusActive() {
        assertTrue(savingsAccount.status, "The account should be active when the balance is >= 10000");
    }

    @Test
    void testDepositMoneyUpdatesBalanceAndStatus() {
        savingsAccount.depositMoney(5000);
        assertEquals(20000, savingsAccount.balance, 0.01, "The balance should increase after a deposit");
        assertTrue(savingsAccount.status, "The account should remain active after depositing money");
    }

    @Test
    void testWithdrawMoneyUpdatesBalanceAndStatus() {
        savingsAccount.withdrawMoney(5000);
        assertEquals(10000, savingsAccount.balance, 0.01, "The balance should decrease after a withdrawal");
        assertTrue(savingsAccount.status, "The account should remain active after withdrawal");

        // Withdraw enough to make the account inactive
        savingsAccount.withdrawMoney(5000);
        assertEquals(5000, savingsAccount.balance, 0.01, "The balance should reflect the withdrawal");
        assertFalse(savingsAccount.status, "The account should become inactive when the balance drops below 10000");
    }

    @Test
    void testWithdrawMoneyInactiveAccountThrowsException() {
        // Make the account inactive
        savingsAccount.withdrawMoney(6000);

        // Attempt to withdraw from an inactive account
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            savingsAccount.withdrawMoney(1000);
        });
        assertEquals("You can't withdraw money cause your account is inactive", exception.getMessage());
    }

    @Test
    void testMakeExtractsAddsMonthlyCommissions() {
        // Withdraw 5 times to incur commission on the fifth withdrawal
        savingsAccount.withdrawMoney(1000); // 1
        savingsAccount.withdrawMoney(1000); // 2
        savingsAccount.withdrawMoney(1000); // 3
        savingsAccount.withdrawMoney(1000); // 4
        savingsAccount.withdrawMoney(1000); // 5 (commission starts)

        savingsAccount.makeExtracts();
        assertEquals(1000, savingsAccount.monthlyComission, 0.01, "Monthly commission should reflect extra withdrawals");
        assertEquals(10000, savingsAccount.balance, 0.01, "Balance should include monthly commission deduction");
    }

    @Test
    void testPrint() {
        savingsAccount.depositMoney(2000);
        String expectedOutput = """
                Balance: 17000.0
                Number of consignments: 1
                Monthly commission: 0.0
                """;
        assertEquals(expectedOutput, savingsAccount.print(), "The print output should match the expected format");
    }
}

