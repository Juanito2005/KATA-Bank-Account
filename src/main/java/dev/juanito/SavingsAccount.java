package dev.juanito;

public class SavingsAccount extends BankAccount {

    public boolean status;

    public SavingsAccount(float initialBalance, float annualRate) {
        super(initialBalance, annualRate);
        this.status = initialBalance >= 10000;
    }

    private void updateStatus() {
        this.status = balance >= 10000;
    }

    @Override
    public void depositMoney(float amount) {
        if (status) {
            super.depositMoney(amount);
        }
        updateStatus();
    }

    @Override
    public float withdrawMoney(float amount) {
        if (!status) {
            throw new IllegalArgumentException("You can't withdraw money cause your account is inactive");
        }
        float result = super.withdrawMoney(amount);
        updateStatus();
        return result;
    }

    @Override
    public void makeExtracts() {
        if (numberWithdrawals > 4) {
            monthlyComission += (numberWithdrawals - 4) * 1000;
        }
    }

    @Override
    public String print() {
        StringBuilder bill = new StringBuilder(); // Inicializa el StringBuilder
        bill.append("Balance: ").append(balance).append("\n");
        bill.append("Number of consignments: ").append(numberConsignments).append("\n");
        bill.append("Monthly commission: ").append(monthlyComission).append("\n");

        return bill.toString();
    }

}
