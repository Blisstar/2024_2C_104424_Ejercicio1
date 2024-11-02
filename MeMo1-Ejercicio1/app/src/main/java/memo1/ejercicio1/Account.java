package memo1.ejercicio1;

public class Account {
    private Long cbu;
    private String alias;
    private double balance;
    private boolean isItRegister;

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

    public void withdraw(double amount) throws Exception {
        if (!isItRegister) throw new UnregisteredAccount();
        if (amount <= 0) throw new IllegalArgumentException("Amount cannot be negative or zero.");
        if (amount > balance) throw new InsufficientFunds();
        balance -= amount;
    }

    public boolean deposit(double amount) throws UnregisteredAccount {
        if (!isItRegister) throw new UnregisteredAccount();
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative.");
        balance += amount;
        return true;
    }

    private void transfer(Account otherAccount,double amountToTransfer) throws Exception {
        if (!isItRegister) throw new UnregisteredAccount();
        otherAccount.deposit(amountToTransfer);
        this.withdraw(amountToTransfer);
    }

    public void transfer(long otherAccountCbu, double amountToTransfer) throws Exception {
        Account otherAccount = BankingSystem.getInstance().getAccountByCBU(otherAccountCbu);
        this.transfer(otherAccount, amountToTransfer);
    }

    public void transfer(String otherAccountAlias, double amountToTransfer) throws Exception {
        Account otherAccount = BankingSystem.getInstance().getAccountByAlias(otherAccountAlias);
        this.transfer(otherAccount, amountToTransfer);
    }

    public String getAlias() {
        return alias;
    }

    public void register() {
        isItRegister = true;
    }

    public void cancel() {
        isItRegister = false;
    }
}
