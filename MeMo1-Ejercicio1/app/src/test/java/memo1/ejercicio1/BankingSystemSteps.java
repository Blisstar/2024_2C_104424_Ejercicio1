package memo1.ejercicio1;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

public class BankingSystemSteps{
    private Address address;
    private Branch branch;
    private AccountOfficer officer;
    private Account account1;
    private Account account2;

    @Before
    public void beforeScenario() throws Exception {
        address = new Address("Argentina", "Buenos Aires", "CABA", "Calle", 123);
        branch = new Branch(123, "Suc. Belgrano", address);
    }

    @Given("a bank and all bank accounts")
    public void bankAndItsAccounts() throws Exception{
        BankingSystem.getInstance().registerBranch(branch);
        officer = new AccountOfficer(123);
        
        BankingSystem.getInstance().registerClient("12345678", "F", "J", LocalDate.of(1971, 1, 1), address);
        BankingSystem.getInstance().registerClient("87654321", "F", "J", LocalDate.of(1971, 1, 1), address);

        officer.createAndRegisterAccount("alias1", "12345678", null);
        officer.createAndRegisterAccount("alias2", "87654321", null);
        account1 = BankingSystem.getInstance().getAccountByAlias("alias1");
        account2 = BankingSystem.getInstance().getAccountByAlias("alias2");
    }

    @When("the bank records the transactions performed by the bank accounts")
    public void accountsDoTransations() throws Exception{
        account1.deposit(1200);
        account1.withdraw(150);

        account2.deposit(1520);
        account2.withdraw(220);

        account1.transfer(account2.getCbu(), 150);
        account2.transfer(account1.getCbu(), 200);
    }

    @Then("each transaction is recorded with a correlative number that identifies it, date and time of realization, type of transaction, amount and the accounts involved")
    public void verifyIfTheTransactionsWereRecorded() {
        assertEquals(4, account1.getTransactions().size());
        assertEquals(4, account2.getTransactions().size());
        assertEquals(6, BankingSystem.getInstance().getTransactionsCount());
    }

    @Then("the withdrawal transaction is recorded in the banking system")
    @Then("the deposit transaction is recorded in the bank")
    public void verifyIfTheTransactionWasRecorded() {
        assertEquals(1, BankingSystem.getInstance().getTransactionsCount());
    }
}