package memo1.ejercicio1;
public class BranchManager {

    public void registerNewBranch(int branchNumber, String denomination, Address address) throws BranchAlreadyExists {
        Branch branch = new Branch(branchNumber, denomination, address);
        BankingSystem.getInstance().registerBranch(branch);
    }

    public void modifyDenominationOfABranch(int i, String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyDenominationOfABranch'");
    }
}
