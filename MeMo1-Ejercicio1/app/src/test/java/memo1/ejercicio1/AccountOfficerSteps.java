package memo1.ejercicio1;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.util.ArrayList;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;

public class AccountOfficerSteps {
    private Address address;
    private Branch branch;
    private AccountOfficer officer;
    private String ownerDNI;
    private ArrayList<String> coownerClients;
    private String alias;
    private Account account;
    private boolean itAlreadyHasAnAcount;

    @Before
    public void beforeScenario() throws Exception {
        address = new Address("Argentina", "Buenos Aires", "CABA", "Calle", 123);
        branch = new Branch(123, "Suc. Belgrano", address);
        coownerClients = new ArrayList<>();
        alias = "alias";
    }

    @Given("an account officer with his branch, an client holder DNI {string} and two co-owners DNIs {string} , {string}")
    public void ownerAndCoowners(String ownerDNI, String coownerDNI1, String coownerDNI2) throws Exception{
        BankingSystem.getInstance().registerBranch(branch);
        officer = new AccountOfficer(123);
        
        this.ownerDNI = ownerDNI;
        coownerClients.add(coownerDNI1);
        coownerClients.add(coownerDNI2);

        BankingSystem.getInstance().registerClient(ownerDNI, "F", "J", LocalDate.of(1971, 1, 1), address);
        BankingSystem.getInstance().registerClient(coownerDNI1, "F", "J", LocalDate.of(1971, 1, 1), address);
        BankingSystem.getInstance().registerClient(coownerDNI2, "F", "J", LocalDate.of(1971, 1, 1), address);
    }

    @Given("an account officer with his branch office and an client DNI {string} who already has an account")
    public void anOwnerWithAnAccount(String ownerDNI) throws Exception{
        BankingSystem.getInstance().registerBranch(branch);
        officer = new AccountOfficer(123);

        this.ownerDNI = ownerDNI;
        BankingSystem.getInstance().registerClient(ownerDNI, "F", "J", LocalDate.of(1971, 1, 1), address);
        officer.createAndRegisterAccount(alias, ownerDNI, null);
    }

    @Given("an account officer with his branch and an client DNI {string} who has a cancelled account")
    public void anOwnerWithACancelledAccount(String ownerDNI) throws Exception{
        BankingSystem.getInstance().registerBranch(branch);
        officer = new AccountOfficer(123);

        this.ownerDNI = ownerDNI;
        BankingSystem.getInstance().registerClient(ownerDNI, "F", "J", LocalDate.of(1971, 1, 1), address);
        officer.createAndRegisterAccount(alias, ownerDNI, null);
        account = BankingSystem.getInstance().getAccountByAlias(alias);
        officer.cancelAccount(account.getCbu());
    }

    @When("the account officer registers an new account for the client")
    @When("the account officer registers an account for them")
    public void officerCreateAndRegisterAnAccount() throws Exception{
        itAlreadyHasAnAcount = false;
        try {
            officer.createAndRegisterAccount(alias, ownerDNI, coownerClients);
            account = BankingSystem.getInstance().getAccountByAlias(alias);
        } catch (ClientAlreadyHasAnAcount e) {
            itAlreadyHasAnAcount = true;
        }
    }

    @When("the account officer registers the cancelled account")
    public void officerRegisterAnAccount() throws ThereIsNoAccountWithThatCBU{
        officer.registerAccount(account.getCbu());
    }

    @Then("The account officer automatically registers the account with his branch")
    public void verifyCorrectBranch() {
        assertTrue(account.isRadicatedIn(branch.getNumber()));
    }

    @Then("enables its use for the account holder and co-owners")
    public void verifyAccountCanDeposit(){
        assertDoesNotThrow(() -> {
            account.deposit(100);
        });
    }

    @Then("the operation should be denied for trying to create an account to an client who already has an account")
    public void verifyItAlreadyHasAnAccount(){
        assertTrue(itAlreadyHasAnAcount);
    }
}
