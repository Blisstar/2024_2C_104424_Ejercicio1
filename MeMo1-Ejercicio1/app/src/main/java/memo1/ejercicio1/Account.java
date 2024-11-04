package memo1.ejercicio1;

public class Account {
    private Long cbu;
    private String alias;
    private double balance;
    private Branch branch;

    public Account(Long cbu, String alias) {
        this.cbu = cbu;
        this.alias = alias;
        branch = null;
    }

    public Account(Long cbu, String alias, double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.cbu = cbu;
        this.alias = alias;
        this.balance = balance;
        branch = null;
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
        if (branch == null) throw new UnregisteredAccount();
        if (amount <= 0) throw new IllegalArgumentException("Amount cannot be negative or zero.");
        if (amount > balance) throw new InsufficientFunds();
        balance -= amount;
    }

    public boolean deposit(double amount) throws UnregisteredAccount {
        if (branch == null) throw new UnregisteredAccount();
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative.");
        balance += amount;
        return true;
    }

    private void transfer(Account otherAccount,double amountToTransfer) throws Exception {
        if (branch == null) throw new UnregisteredAccount();
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

    public void register(Branch assignedBranch) {
        branch = assignedBranch;
    }

    public void cancel() {
        branch = null;
    }

    public boolean isRadicatedIn(int branchNumber) {
        if(branch == null) return false;
        return branchNumber == branch.getNumber();
    }

    public void addCoowner(Client client, String coownerDNI) throws Exception {
        if (!client.isYourMainAccount(cbu)) throw new YouDontHavePermissions();
        Client coowner = BankingSystem.getInstance().getClient(coownerDNI);
        coowner.addSecundaryAccount(this);
    }
}
