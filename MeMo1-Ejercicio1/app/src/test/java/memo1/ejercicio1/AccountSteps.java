package memo1.ejercicio1;

import static org.junit.Assert.*;
import io.cucumber.java.en.*;

// Pruebas funcionales basadas en los escenarios Gherkin

public class AccountSteps {
    private Account account;
    private Account accountA;
    private Account accountB;
    private boolean operationResult;

    @Given("I create an account with CBU {long}")
    public void createAccountWithDefaultBalance(long cbu) {
        account = new Account();
        account.setCbu(cbu);
    }

    @Given("I create an account with CBU {long} and a balance of {double}")
    public void createAccountWithInitialBalance(long cbu, double balance) {
        account = new Account(cbu, balance);
    }

    @Given("An account with CBU {long} and a balance of {double}")
    public void anAccountWithCBUAndBalance(long cbu, double balance) {
        account = new Account(cbu, balance);
    }

    @Given("A account A with CBU {long} and a balance of {double} and other account B with CBU {long} and a balance of {double}")
    public void twoAccountsWithCBUAndBalance(long cbuA, double balanceA, long cbuB, double balanceB){
        accountA = new Account(cbuA, balanceA);
        accountB = new Account(cbuB, balanceB);
    }

    @When("I deposit {double} into the account")
    public void depositIntoAccount(double amount) {
        operationResult = account.deposit(amount);
    }

    @When("I try to deposit {double} into the account")
    public void tryToDepositIntoAccount(double amount) {
        operationResult = account.deposit(amount);
    }

    @When("I withdraw {double} from the account")
    public void withdrawFromAccount(double amount) {
        operationResult = account.withdraw(amount);
    }

    @When("I try to withdraw {double} from the account")
    public void tryToWithdrawFromAccount(double amount) {
        operationResult = account.withdraw(amount);
    }

    @When("account A transfer {double} to account B")
    public void transferFromAAccountToOtherAccount(double amount) { operationResult = accountA.transfer(accountB, amount); }

    @When("account A try to transfer {double} to account B")
    public void tryToTransferFromAAccountToOtherAccount(double amount) { operationResult = accountA.transfer(accountB, amount); }

    @Then("The account balance should be {double}")
    public void verifyAccountBalance(double expectedBalance) {
        assertEquals(expectedBalance, account.getBalance(), 0.01);
    }

    @Then("The operation should be denied")
    public void verifyOperationDenied() {
        assertFalse(operationResult);
    }

    @Then("The account balance should remain {double}")
    public void verifyBalanceRemains(double expectedBalance) {
        assertEquals(expectedBalance, account.getBalance(), 0.01);
    }

    @Then("The account {char} balance should be {double}")
    public void verifyBalanceRemains(char a, double expectedBalance) {
        Account aAccount;
        switch (a) {
            case 'A':
                aAccount = accountA;
                break;

            case 'B':
                aAccount = accountB;
                break;
        }
        assertEquals(expectedBalance, aAccount.getBalance(), 0.01);
    }

    @Then("The operation should be denied due to insufficient funds")
    public void verifyInsufficientFunds() {
        assertFalse(operationResult);
    }

    @Then("The operation should be denied due to lack of funds in the account A")
    verifyInsufficientFunds();
}
