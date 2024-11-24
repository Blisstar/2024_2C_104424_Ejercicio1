package memo1.ejercicio1;

import java.time.LocalDateTime;

public class Transaction {
    private int number;
    private TransactionType type;
    private double amount;
    private LocalDateTime realizationDateTime;
    private Account firstAccount;
    private Account secondAccount;

    public Transaction(TransactionType type, double amount, Account firstAccount, Account secAccount) {
        this.number = TransactionNumberGenerator.getInstance().generateNextTransactionNumber();
        this.type = type;
        this.amount = amount;
        realizationDateTime = LocalDateTime.now();
        this.firstAccount = firstAccount;
        this.secondAccount = secAccount;
        BankingSystem.getInstance().addTransaction(this);
    }

    public int getNumber() {
        return number;
    }

    public LocalDateTime getRealizationDateTime() {
        return realizationDateTime;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public Account getFirstAccount() {
        return firstAccount;
    }

    public Account getSecondAccount(){
        return secondAccount;
    }
}
