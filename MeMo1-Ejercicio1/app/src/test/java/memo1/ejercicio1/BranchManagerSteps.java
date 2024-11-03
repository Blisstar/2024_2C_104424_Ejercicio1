package memo1.ejercicio1;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BranchManagerSteps {
    private Address address1;
    private Address address2;
    private BranchManager manager;
    private int branchNumber;
    private boolean doBranchAlreadyExists;

    @Before
    public void beforeScenario(){
        address1 = new Address("Argentina", "Buenos Aires", "CABA", "Calle 117", 158);
        address2 = new Address("Perú", "Tumbes", "Cabeza", "Lagarto", 156);
    }

    @Given("the branch manager and a branch office with number {int} that is in the system")
    @Given("branch manager and a new branch office with number {int}")
    public void createBranchManager(int branchNumber) {   
        manager = new BranchManager();
        this.branchNumber = branchNumber;
    }

    @When("the branch office is registered by the branch manager")
    public void registerBranch() throws BranchAlreadyExists {
        manager.registerNewBranch(branchNumber, "Suc. Belgrano", address1);
    }


    @When("the branch is registered by the branch manager with a number already owned by another branch.")
    public void registerBranchsWithSameNumber() throws BranchAlreadyExists{
        doBranchAlreadyExists = false;
        manager.registerNewBranch(branchNumber, "Suc. Belgrano", address1);
        try {
            manager.registerNewBranch(branchNumber, "Suc. Cabeza de lagarto", address2);
        } catch (BranchAlreadyExists e) {
            doBranchAlreadyExists = true;
        }
    }

    @Then("the branch is operational in the system with its respective location, assigned a number and a name.")
    public void verifyRegisteredBranch() throws NonexistentBranch {
        Branch branch = BankingSystem.getInstance().getBranch(branchNumber);
        assertEquals(branchNumber, branch.getNumber());
        assertEquals("Suc. Belgrano", branch.getDenomination());
        assertEquals(address1.toString(), branch.getAddress());
    }

    @Then("the operation is denied due to the branch with that number already exists") 
    public void verifyDoBranchAlreadyExists(){
        assertTrue(doBranchAlreadyExists);
    }

    @Then("the branch is not replaced in the system") 
    public void verifyTheBranchIsNotReplaced() throws NonexistentBranch{
        Branch branch = BankingSystem.getInstance().getBranch(branchNumber);
        assertEquals(branchNumber, branch.getNumber());
        assertFalse("Suc. Cabeza de lagarto".equals(branch.getDenomination()));
        assertFalse(address2.toString().equals(branch.getAddress()));
    }
}