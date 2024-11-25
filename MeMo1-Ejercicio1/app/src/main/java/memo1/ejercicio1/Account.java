package memo1.ejercicio1;

import java.util.ArrayList;
import java.util.HashMap;

public class Account {
    private Long cbu;
    private String alias;
    private Client owner;
    private HashMap<String, Client> coowners;
    private double balance;
    private Branch branch;
    private ArrayList<Transaction> transactions;

    public Account(Long cbu, String alias, Client owner, Branch assignedBranch) {
        this.cbu = cbu;
        this.alias = alias;
        this.owner = owner;
        coowners = new HashMap<>();
        transactions = new ArrayList<>();
        balance = 0;
        register(assignedBranch);
        owner.setMainAccount(this);
    }

    public Account(Long cbu, String alias, Client owner, Branch assignedBranch, double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.cbu = cbu;
        this.alias = alias;
        this.owner = owner;
        transactions = new ArrayList<>();
        register(assignedBranch);
        owner.setMainAccount(this);
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
        if (!isRegistered()) throw new UnregisteredAccount();
        if (amount <= 0) throw new IllegalArgumentException("Amount cannot be negative or zero.");
        if (amount > balance) throw new InsufficientFunds();
        balance -= amount;
        transactions.add(new Transaction(TransactionType.WITHDRAWAL, amount, this, null));
    }

    public void deposit(double amount) throws UnregisteredAccount {
        if (!isRegistered()) throw new UnregisteredAccount();
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative.");
        balance += amount;
        transactions.add(new Transaction(TransactionType.DEPOSIT, amount, this, null));
    }

    public Transaction receiveTransfer(Account senderAccount, double amountToReceive) throws UnregisteredAccount{
            if (!isRegistered()) throw new UnregisteredAccount();
            if (amountToReceive < 0) throw new IllegalArgumentException("Amount cannot be negative.");
            balance += amountToReceive;
            Transaction transaction = new Transaction(TransactionType.TRANSFER, amountToReceive, senderAccount, this);
            transactions.add(transaction);
            return transaction;
    }

    private void transfer(Account otherAccount, double amountToTransfer) throws Exception {
        if (!isRegistered()) throw new UnregisteredAccount();
        if (amountToTransfer < 0) throw new IllegalArgumentException("Amount cannot be negative.");
        if (amountToTransfer > balance) throw new InsufficientFunds();
        transactions.add(otherAccount.receiveTransfer(this, amountToTransfer));
        balance -= amountToTransfer;
    }

    public void transfer(long otherAccountCbu, double amountToTransfer) throws Exception {
        if (otherAccountCbu == cbu) throw new SameAccountOriginDestination();
        Account otherAccount = BankingSystem.getInstance().getAccountByCBU(otherAccountCbu);
        this.transfer(otherAccount, amountToTransfer);
    }

    public void transfer(String otherAccountAlias, double amountToTransfer) throws Exception {
        if (otherAccountAlias == alias) throw new SameAccountOriginDestination();
        Account otherAccount = BankingSystem.getInstance().getAccountByAlias(otherAccountAlias);
        this.transfer(otherAccount, amountToTransfer);
    }

    public String getAlias() {
        return alias;
    }

    public void register(Branch assignedBranch) {
        branch = assignedBranch;
        branch.addAccount(this);
    }

    public void cancel() throws AccountStillHasFunds {
        verifyDoesStillHasFunds();
        branch = null;
    }

    public boolean isRadicatedIn(int branchNumber) {
        if(!isRegistered()) return false;
        return branchNumber == branch.getNumber();
    }

    public void addCoowner(Client client, String coownerDNI) throws Exception {
        if (!isRegistered()) throw new UnregisteredAccount();
        if (!client.isYourMainAccount(cbu)) throw new YouDontHavePermissions();
        Client coowner = BankingSystem.getInstance().getClient(coownerDNI);
        coowner.addSecundaryAccount(this);
        coowners.put(coownerDNI, coowner);
    }

    public void deleteCoowner(Client client, String coownerDNI) throws Exception {
        if (!isRegistered()) throw new UnregisteredAccount();
        if (!client.isYourMainAccount(cbu)) throw new YouDontHavePermissions();
        if (!coowners.containsKey(coownerDNI)) throw new ThisClientIsNotCoowner();
        coowners.get(coownerDNI).deleteSecundaryAccount(cbu);
        coowners.remove(coownerDNI);
    }

    public boolean isRegistered() {
        return branch != null && !branch.isCancelled();
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void verifyDoesStillHasFunds() throws AccountStillHasFunds {
        if (balance > 0) throw new AccountStillHasFunds();
    }

}
