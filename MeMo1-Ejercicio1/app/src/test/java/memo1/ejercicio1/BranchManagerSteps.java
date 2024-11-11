package memo1.ejercicio1;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

public class BranchManagerSteps {
    private Address address1;
    private Address address2;
    private BranchManager manager;
    private AccountOfficer officer1;
    private AccountOfficer officer2;
    private Account account1;
    private Account account2;
    private int branchNumber1;
    private int branchNumber2;
    private boolean doBranchAlreadyExists;
    private boolean doesBranchHaveAccountsWithBalance;

    @Before
    public void beforeScenario(){
        address1 = new Address("Argentina", "Buenos Aires", "CABA", "Calle 117", 158);
        address2 = new Address("Perú", "Tumbes", "Cabeza", "Lagarto", 156);
    }

    @Given("the branch manager and a branch office with number {int} that is in the system")
    @Given("branch manager and a new branch office with number {int}")
    public void createBranchManager(int branchNumber) {   
        manager = new BranchManager();
        this.branchNumber1 = branchNumber;
    }

    @Given("the branch management and a branch office {int} with its account officers and bank accounts")
    public void createBranchManagerWithABranchAnAccountOfficerAndAnAccount(int branchNumber) throws Exception  {   
        manager = new BranchManager();
        this.branchNumber1 = branchNumber;
        manager.registerNewBranch(branchNumber, "Suc. Belgrano", address1);
        officer1 = new AccountOfficer(branchNumber);
        BankingSystem.getInstance().registerClient("42657569", "f", "j", LocalDate.of(2000,1,1), address1);
        officer1.createAndRegisterAccount("alias1", "42657569", null);
        account1 = BankingSystem.getInstance().getAccountByAlias("alias1");
    }

    @Given("some of accounts have a balance greater than zero")
    public void someAccountsWithBalance() throws UnregisteredAccount {
        account1.deposit(1000);
    }

    @Given("a branch management and a branch office {int}")
    public void branchManagerAndABranch(int branchNumber) throws BranchAlreadyExists{
        manager = new BranchManager();
        this.branchNumber1 = branchNumber;
        manager.registerNewBranch(branchNumber, "Suc. Belgrano", address1);
    }

    @Given("the branch management and two branches {int} and {int} with their account officers and their bank accounts")
    public void twoBranches(int branchNumber1, int branchNumber2) throws Exception {
        manager = new BranchManager();
        this.branchNumber1 = branchNumber1;
        this.branchNumber2 = branchNumber2;
        manager.registerNewBranch(branchNumber1, "Suc. Belgrano", address1);
        manager.registerNewBranch(branchNumber2, "Suc. Cabeza", address2);
        officer1 = new AccountOfficer(branchNumber1);
        officer2 = new AccountOfficer(branchNumber2);
        BankingSystem.getInstance().registerClient("42657569", "f", "j", LocalDate.of(2000,1,1), address1);
        BankingSystem.getInstance().registerClient("12345678", "f", "j", LocalDate.of(2000,1,1), address2);
        officer1.createAndRegisterAccount("alias1", "42657569", null);
        officer2.createAndRegisterAccount("alias2", "12345678", null);
        account1 = BankingSystem.getInstance().getAccountByAlias("alias1");
        account2 = BankingSystem.getInstance().getAccountByAlias("alias2");
    }

    @When("the management changes the name of the branch office")
    public void modifyDenominationOfABranch() throws NonexistentBranch {
        manager.modifyDenominationOfABranch(branchNumber1, "Suc. Balvanera");
    }

    @When("the management changes the location of the branch office")
    public void modifyAddressOfABranch() throws NonexistentBranch {
        manager.modifyAddressOfABranch(branchNumber1, address2);
    }

    @When("the management tries to deregister the branch office")
    @When("the branch is de-registered by management")
    public void cancelABranch() throws NonexistentBranch {
        doesBranchHaveAccountsWithBalance = false;
        try {
            manager.cancelABranch(branchNumber1);
        } catch (BranchHasAccountsWithBalance e) {
            doesBranchHaveAccountsWithBalance = true;
        }
        
    }

    @When("the branch office is registered by the branch manager")
    public void registerBranch() throws BranchAlreadyExists {
        manager.registerNewBranch(branchNumber1, "Suc. Belgrano", address1);
    }

    @When("the branch is registered by the branch manager with a number already owned by another branch.")
    public void registerBranchsWithSameNumber() throws BranchAlreadyExists{
        doBranchAlreadyExists = false;
        manager.registerNewBranch(branchNumber1, "Suc. Belgrano", address1);
        try {
            manager.registerNewBranch(branchNumber1, "Suc. Cabeza de lagarto", address2);
        } catch (BranchAlreadyExists e) {
            doBranchAlreadyExists = true;
        }
    }

    @When("management terminates one of these branches with the other branch as a backup")
    public void cancelOneOfTheBranchWithOtherBranchAsBackup() throws NonexistentBranch {
        manager.cancelABranch(branchNumber1, branchNumber2);
    }

    @Then("it will appear in the system with this new name")
    public void verifyChangeOfName() throws NonexistentBranch {
        assertEquals("Suc. Balvanera", BankingSystem.getInstance().getBranch(branchNumber1).getDenomination());
    }

    @Then("the branch is operational in the system with its respective location, assigned a number and a name.")
    public void verifyRegisteredBranch() throws NonexistentBranch {
        Branch branch = BankingSystem.getInstance().getBranch(branchNumber1);
        assertEquals(branchNumber1, branch.getNumber());
        assertEquals("Suc. Belgrano", branch.getDenomination());
        assertEquals(address1.toString(), branch.getAddress());
    }

    @Then("the operation is denied due to the branch with that number already exists") 
    public void verifyDoBranchAlreadyExists(){
        assertTrue(doBranchAlreadyExists);
    }

    @Then("the branch is not replaced in the system") 
    public void verifyTheBranchIsNotReplaced() throws NonexistentBranch{
        Branch branch = BankingSystem.getInstance().getBranch(branchNumber1);
        assertEquals(branchNumber1, branch.getNumber());
        assertFalse("Suc. Cabeza de lagarto".equals(branch.getDenomination()));
        assertFalse(address2.toString().equals(branch.getAddress()));
    }

    @Then("the branch is made inoperative for its account officers, and the bank accounts that are located there are cancelled")
    public void verifyUnregisteredAccount() throws ThereIsNoAccountWithThatCBU {
        assertFalse(account1.isRegistered());
    }

    @Then("it will appear in the system with that new location")
    public void verifyChangeOfAddress() throws NonexistentBranch {
        assertEquals(address2.toString(), BankingSystem.getInstance().getBranch(branchNumber1).getAddress());
    }

    @Then("the operation is denied because there are active accounts with a balance in the branch")
    public void verifyDoesBranchHaveAccountsWithBalance() {
        assertTrue(doesBranchHaveAccountsWithBalance);
    }

    @Then("the branch becomes inoperative and the other branch takes over its bank accounts so that the bank accounts are now located in the backup branch")
    public void verifyAccount1AndAccount2AreInTheSameBranch() throws NonexistentBranch {
        assertTrue(account1.isRadicatedIn(branchNumber2));
        assertTrue(account2.isRadicatedIn(branchNumber2));
        assertTrue(account1.isRegistered());
        assertTrue(account2.isRegistered());
        assertTrue(BankingSystem.getInstance().getBranch(branchNumber1).isCancelled());
        assertFalse(BankingSystem.getInstance().getBranch(branchNumber2).isCancelled());
    }
}