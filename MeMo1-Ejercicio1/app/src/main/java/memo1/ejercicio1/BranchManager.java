package memo1.ejercicio1;
public class BranchManager {
    private int idEmployee;

    public BranchManager() {
        idEmployee = BankingSystem.getInstance().generateNextIDEmployee();
    }

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
}
