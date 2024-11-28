package dev.juanito;

public class CurrentAccount extends BankAccount{

    protected float overdraft;

    public CurrentAccount(float balance, float annualRate) {
        super(balance, annualRate);
        this.overdraft = 0;
    }

    @Override
    public float withdrawMoney(float amount) {
        if (amount > balance) {
            overdraft += (amount - balance);
            balance = 0;
        } else{
            balance -= amount;
        }
        return balance;
    }

    @Override
    public void depositMoney(float amount) {
        if (overdraft > 0) {
            if (amount >= overdraft) {
                amount -= overdraft;
                overdraft = 0;
                balance += amount;
            } else {
                overdraft -= amount;
            }
        } else {
            super.depositMoney(amount);
        }
    }

    @Override
    public void makeExtracts() {
        super.makeExtracts();
    }

    @Override
    public String print() {
        StringBuilder bill = new StringBuilder(); // Inicializa el StringBuilder
        bill.append("Balance: ").append(balance).append("\n");
        bill.append("Number of consignments: ").append(numberConsignments).append("\n");
        bill.append("Monthly commission: ").append(monthlyComission).append("\n");
        bill.append("Overdraft: ").append(overdraft).append("\n");
        return bill.toString();
    }

}
