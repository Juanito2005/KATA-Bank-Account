package dev.juanito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrentAccountTest {

    private CurrentAccount currentAccount;

    @BeforeEach
    void setUp() {
        // Initialize the CurrentAccount with a balance of 5000 and an annual rate of 3%
        currentAccount = new CurrentAccount(5000, 3);
    }

    @Test
    void testWithdrawWithinBalance() {
        currentAccount.withdrawMoney(3000);
        assertEquals(2000, currentAccount.balance, 0.01, "Balance should decrease correctly after withdrawal within limit");
        assertEquals(0, currentAccount.overdraft, 0.01, "Overdraft should remain 0 when withdrawal is within balance");
    }

    @Test
    void testWithdrawExceedingBalance() {
        currentAccount.withdrawMoney(7000);
        assertEquals(0, currentAccount.balance, 0.01, "Balance should drop to 0 when overdraft is used");
        assertEquals(2000, currentAccount.overdraft, 0.01, "Overdraft should increase by the amount exceeding the balance");
    }

    @Test
    void testDepositRepayingOverdraftPartially() {
        currentAccount.withdrawMoney(7000); // Create overdraft of 2000
        currentAccount.depositMoney(1000);

        assertEquals(0, currentAccount.balance, 0.01, "Balance should remain 0 while overdraft is not fully paid");
        assertEquals(1000, currentAccount.overdraft, 0.01, "Overdraft should decrease by the deposited amount");
    }

    @Test
    void testDepositRepayingOverdraftFully() {
        currentAccount.withdrawMoney(7000); // Create overdraft of 2000
        currentAccount.depositMoney(3000);

        assertEquals(1000, currentAccount.balance, 0.01, "Balance should increase after overdraft is fully repaid");
        assertEquals(0, currentAccount.overdraft, 0.01, "Overdraft should be fully cleared after deposit");
    }

    @Test
    void testDepositWithNoOverdraft() {
        currentAccount.depositMoney(2000);
        assertEquals(7000, currentAccount.balance, 0.01, "Balance should increase when there's no overdraft");
        assertEquals(0, currentAccount.overdraft, 0.01, "Overdraft should remain 0 when no overdraft exists");
    }

    @Test
    void testMakeExtracts() {
        currentAccount.withdrawMoney(7000); // Create overdraft of 2000
        currentAccount.makeExtracts();

        // Verify balance and overdraft remain unchanged since makeExtracts doesn't affect them directly in this implementation
        assertEquals(0, currentAccount.balance, 0.01, "Balance should remain 0 after makeExtracts");
        assertEquals(2000, currentAccount.overdraft, 0.01, "Overdraft should remain unchanged after makeExtracts");
    }

    @Test
    void testPrint() {
        currentAccount.withdrawMoney(7000); // Create overdraft of 2000
        String expectedOutput = """
                Balance: 0.0
                Number of consignments: 0
                Monthly commission: 0.0
                Overdraft: 2000.0
                """;
        assertEquals(expectedOutput, currentAccount.print(), "The print output should match the expected format");
    }
}

