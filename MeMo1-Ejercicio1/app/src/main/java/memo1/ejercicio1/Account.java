package memo1.ejercicio1;
import java.util.HashMap;

public class Account {
    private Long cbu;
    private double balance;

    public Account() {
        this.balance = 0.0;
    }

    public Account(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.balance = balance;
    }

    public Account(Long cbu, double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.cbu = cbu;
        this.balance = balance;
    }

    public Long getCbu() {
        return cbu;
    }

    public void setCbu(Long cbu) {
        this.cbu = cbu;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.balance = balance;
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

}
