package memo1.ejercicio1;

import static org.junit.Assert.*;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import java.time.LocalDate;
import java.util.Random;

public class AccountSteps {
    private Client clientA;
    private Client clientB;
    private Account account;
    private Account accountA;
    private Account accountB;
    private boolean insufficientFunds;
    private boolean negativeAmount;
    private boolean isItNonexistentCBU;
    private boolean usingCBU;
    private boolean isItNonexistentAlias;
    private boolean isThereUnregisteredAccount;

    private Address address;
    private Branch branch;

    @Before
    public void beforeScenario() throws InvalidDNI{
        Address clientAddressA = new Address("A", "B", "C", "D", 1);
        Address clientAddressB = new Address("A", "B", "C", "D", 2);
        clientA = new Client("12345678", "F", "J", LocalDate.of(1984,1,1), clientAddressA);
        clientB = new Client("87654321", "J", "F", LocalDate.of(1993,4,1), clientAddressB);
        address = new Address("Argentina", "Buenos Aires", "CABA", "Calle 117", 158);
        branch = new Branch(1, "Suc. Belgrano", address);
    }

    @Given("I create an account with CBU {long}")
    public void createAccountWithDefaultBalance(long cbu) {
        account = new Account(cbu, "alias", clientA, branch);
    }

    @Given("I create an account with CBU {long} and a balance of {double}")
    public void createAccountWithInitialBalance(long cbu, double balance) {
        account = new Account(cbu, "alias", clientA, branch, balance);
    }

    @Given("An account with CBU {long} and a balance of {double}")
    public void anAccountWithCBUAndBalance(long cbu, double balance) {
        account = new Account(cbu, "alias", clientA, branch, balance);
    }

    @Given("An account A with CBU {long} and a balance of {double}, an account B with CBU {long} and a balance of {double} and a nonexistent CBU")
    @Given("An account A with CBU {long} and a balance of {double} and other account B with CBU {long} and a balance of {double}")
    public void twoAccountsWithCBUAndBalance(long cbuA, double balanceA, long cbuB, double balanceB) throws Exception {
        accountA = new Account(cbuA, "alias1", clientA, branch, balanceA);
        accountB = new Account(cbuB, "alias2", clientB, branch, balanceB);
        BankingSystem.getInstance().addAccount(accountA);
        BankingSystem.getInstance().addAccount(accountB);
        usingCBU = true;
    }

    @Given("An account A with alias {string} and a balance of {double} and other account B with alias {string} and a balance of {double}")
    @Given("An account A with alias {string} and a balance of {double}, an account B with alias {string} and a balance of {double} and a nonexistent Alias")
    public void twoAccountsWithAliasAndBalance(String aliasA, double balanceA, String aliasB, double balanceB) throws Exception {
        accountA = new Account(123456789L, aliasA, clientA, branch, balanceA);
        accountB = new Account(987654321L, aliasB, clientB, branch, balanceB);
        BankingSystem.getInstance().addAccount(accountA);
        BankingSystem.getInstance().addAccount(accountB);
        usingCBU = false;
    }

    @Given("an unregistered account A with CBU {long} and other account B with CBU {long}")
    public void unregisteredSenderAccountAndOtherAccount(long cbuA, long cbuB) throws Exception {
        accountA = new Account(cbuA, "alias1", clientA, branch);
        accountB = new Account(cbuB, "alias2", clientB, branch);
        BankingSystem.getInstance().addAccount(accountA);
        BankingSystem.getInstance().addAccount(accountB);
        accountA.cancel();
    }

    @Given("an account A with CBU {long} and a balance of {double} and an unregistered account B with CBU {int}")
    public void unregisteredReceiverAccountAndOtherAccount(long cbuA, double balanceA, long cbuB) throws Exception {
        accountA = new Account(cbuA, "alias1", clientA, branch, balanceA);
        accountB = new Account(cbuB, "alias2", clientB, branch);
        BankingSystem.getInstance().addAccount(accountA);
        BankingSystem.getInstance().addAccount(accountB);
        accountB.cancel();
    }

    @When("I deposit {double} into the account")
    @When("I try to deposit {double} into the account")
    public void depositIntoAccount(double amount) throws UnregisteredAccount {
        negativeAmount = false;
        try {
            account.deposit(amount);
        } catch (IllegalArgumentException e) {
            negativeAmount = true;
        }
    }

    @When("I withdraw {double} from the account")
    @When("I try to withdraw {double} from the account")
    public void withdrawFromAccount(double amount) throws Exception{
        insufficientFunds = false;
        try {
            account.withdraw(amount);
        } catch (InsufficientFunds e){
            insufficientFunds = true;
        }
    }

    @When("account A try to transfer {double} to account B using CBU")
    @When("account A transfer {double} to account B using CBU")
    @When("account A transfer {double} to account B using alias")
    @When("account A transfer {double} to account B")
    public void transferFromAAccountToOtherAccount(double amount) throws Exception {
        negativeAmount = false;
        insufficientFunds = false;
        isThereUnregisteredAccount = false;
        try {
            if (usingCBU){
                accountA.transfer(accountB.getCbu(), amount);
            }else {
                accountA.transfer(accountB.getAlias(), amount);
            }
        } catch (InsufficientFunds e) {
            insufficientFunds = true;
        }catch (IllegalArgumentException e) {
            negativeAmount = true;
        }catch (UnregisteredAccount e) {
            isThereUnregisteredAccount = true;
        }
    }

    @When("account A try to transfer {double} to account B but enter a nonexistent CBU")
    @When("account A try to transfer {double} to account B but enter a nonexistent Alias")
    public void accountTryToTransferToAAccountButEnterAnNonexistentCBU(double amount) throws Exception {
        isItNonexistentCBU = false;
        isItNonexistentAlias = true;
        Random random = new Random();
        long nonexistentCBU;
        do {
            nonexistentCBU = random.nextLong();
        } while ((nonexistentCBU == accountA.getCbu()) || (nonexistentCBU == accountB.getCbu()));

        String nonexistentAlias;
        do {
            nonexistentAlias = Long.toString(random.nextLong());
        } while ((nonexistentAlias.equals(accountA.getAlias())) || (nonexistentAlias.equals(accountB.getAlias())));

        try {
            if (usingCBU){
                accountA.transfer(nonexistentCBU, amount);
            } else {
                accountA.transfer(nonexistentAlias, amount);
            }

        } catch (ThereIsNoAccountWithThatCBU e) {
            isItNonexistentCBU = true;
        }catch (ThereIsNoAccountWithThatAlias e){
            isItNonexistentAlias = true;
        }
    }

    @Then("The account balance should be {double}")
    public void verifyAccountBalance(double expectedBalance) {
        assertEquals(expectedBalance, account.getBalance(), 0.01);
    }

    @Then("The account balance should remain {double}")
    public void verifyBalanceRemains(double expectedBalance) {
        assertEquals(expectedBalance, account.getBalance(), 0.01);
    }

    @Then("The account A balance should be {double}")
    public void verifyBalanceRemainsOfAccountA(double expectedBalance) { assertEquals(expectedBalance, accountA.getBalance(), 0.01); }

    @Then("The account B balance should be {double}")
    public void verifyBalanceRemainsOfAccountB(double expectedBalance) { assertEquals(expectedBalance, accountB.getBalance(), 0.01); }

    @Then("The operation should be denied for entering a non-existent CBU")
    public void verifyNonexistentCBU() {
        assertTrue(isItNonexistentCBU);
    }


    @Then("The operation should be denied due to lack of funds in the account A")
    @Then("The operation should be denied due to insufficient funds")
    public void verifyInsufficientFunds() {
        assertTrue(insufficientFunds);
    }

    @Then("The operation should be denied for negative amount")
    public void verifyNegativeAmount() {
        assertTrue(negativeAmount);
    }

    @Then("The operation should be denied for entering a non-existent Alias")
    public void theOperationShouldBeDeniedForEnteringANonExistentAlias() {
        assertTrue(isItNonexistentAlias);
    }

    @Then("The operation should be denied for pending account to be registered")
    public void verifyIsThereUnregisteredAccount() {
        assertTrue(isThereUnregisteredAccount);
    }
}
