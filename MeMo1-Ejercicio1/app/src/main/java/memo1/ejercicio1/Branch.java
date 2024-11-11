package memo1.ejercicio1;

import java.util.ArrayList;

public class Branch {
    private int number;
    private String denomination;
    private Address address;
    private ArrayList<Account> accounts;
    private boolean isCancelled;

    public Branch(int number, String denomination, Address address) {
        this.number = number;
        this.denomination = denomination;
        this.address = address;
        accounts = new ArrayList<>();
        this.isCancelled = false;
    }

    public int getNumber(){
        return number;
    }

    public String getDenomination() {
        return denomination;
    }

    public String getAddress(){
        return address.toString();
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void cancel() throws BranchHasAccountsWithBalance {
        for (Account account : accounts) {
            try {
                account.verifyDoesStillHasFunds();
            } catch (AccountStillHasFunds e) {
                throw new BranchHasAccountsWithBalance();
            }
        }
        accounts.clear();
        this.isCancelled = true;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void cancel(int backupBranchNumber) throws NonexistentBranch {
        Branch backupBranch = BankingSystem.getInstance().getBranch(backupBranchNumber);
        for (Account account : accounts) {
            account.register(backupBranch);
            backupBranch.addAccount(account);
        }
        accounts.clear();
        this.isCancelled = true;
    }
    
}
