package memo1.ejercicio1;
import java.util.HashMap;

public class Account {
    private Long cbu;
    private String alias;
    private double balance;

    public Account(Long cbu, String alias) {
        this.cbu = cbu;
        this.alias = alias;
    }

    public Account(Long cbu, String alias, double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.cbu = cbu;
        this.alias = alias;
        this.balance = balance;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getCbu() {
        return cbu;
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    public boolean deposit(double amount) {
        if (amount < 0) {
            return false;
        }
        balance += amount;
        return true;
    }

    public boolean transfer(long otherAccountCbu, HashMap<Long, Account> accounts, double amountToTransfer){
        if (!accounts.containsKey(cbu)) throw new IllegalArgumentException("La cuenta con este CBU no existe.");
        Account otherAccount = accounts.get(otherAccountCbu);
        boolean operationSucces = this.withdraw(amountToTransfer);
        if (operationSucces) otherAccount.deposit(amountToTransfer);
        return operationSucces;
    }

    public String getAlias() {
        return alias;
    }
}
