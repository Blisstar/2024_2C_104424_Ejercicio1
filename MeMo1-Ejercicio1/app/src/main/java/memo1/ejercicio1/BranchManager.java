package memo1.ejercicio1;
public class BranchManager {

    public void registerNewBranch(int branchNumber, String denomination, Address address) throws BranchAlreadyExists {
        Branch branch = new Branch(branchNumber, denomination, address);
        BankingSystem.getInstance().registerBranch(branch);
    }

    public void modifyDenominationOfABranch(int branchNumber, String newDenomination) throws NonexistentBranch {
        BankingSystem.getInstance().getBranch(branchNumber).setDenomination(newDenomination);
    }

    public void modifyAddressOfABranch(int branchNumber, Address address) throws NonexistentBranch {
        BankingSystem.getInstance().getBranch(branchNumber).setAddress(address);
    }

    public void cancelABranch(int branchNumber) throws NonexistentBranch, BranchHasAccountsWithBalance {
        BankingSystem.getInstance().getBranch(branchNumber).cancel();
    }

    public void cancelABranch(int branchToCancelNumber, int backupBranchNumber) throws NonexistentBranch {
        BankingSystem.getInstance().getBranch(branchToCancelNumber).cancel(backupBranchNumber);
    }
}
