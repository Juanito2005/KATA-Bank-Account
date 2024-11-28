package dev.juanito;

public class BankAccount implements CalculateExtract {

    protected float balance;
    protected int numberConsignments;
    protected float annualRate;
    protected float monthlyComission;
    protected int numberWithdrawals;

    public BankAccount(float balance, float annualRate) {
        this.balance = balance;
        this.annualRate = annualRate;
        this.numberConsignments = 0;
        this.monthlyComission = 0;
        this.numberWithdrawals = 0;
    }

    @Override
    public void depositMoney(float amount) {
        balance += amount;
        numberConsignments++;
    }

    @Override
    public float withdrawMoney(float amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("You can't withdraw more money than the money you got in the account");
        }
        numberWithdrawals++;
        numberConsignments++;
        balance -= amount;
        return balance;
    }

    public void calculateInterest() {
        float monthlyInterest = (annualRate/12)* balance/100;
        balance += monthlyInterest;
    }

    @Override
    public void makeExtracts() {
        balance -= monthlyComission;
        calculateInterest();
    }

    @Override
    public String print() {
        StringBuilder bill = new StringBuilder(); // Inicializa el StringBuilder
        bill.append("Balance: ").append(balance).append("\n");
        bill.append("Number of consignments: ").append(numberConsignments).append("\n");
        bill.append("Number of withdrawals: ").append(numberWithdrawals).append("\n");
        bill.append("Annual rate: ").append(annualRate).append("\n");
        bill.append("Monthly commission: ").append(monthlyComission).append("\n");

        return bill.toString();
    }
}
