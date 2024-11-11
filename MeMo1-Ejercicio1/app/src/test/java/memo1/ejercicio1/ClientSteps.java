package memo1.ejercicio1;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class ClientSteps {
    private Address address;
    private String registeredLastName;
    private String unregisteredLastName;
    private String registeredDNI1;
    private String registeredDNI2;
    private String marriageDate;
    private BranchManager manager;
    private AccountOfficer officer;
    private Account account;
    private boolean invalidDNI;
    private boolean clientAlreadyExists;
    private boolean marriageDateIsBeforeBirthDate;
    private boolean isNonexistentCoowner;
    private boolean doesAccountStillHaveFunds;

    private LocalDate stringToDate(String date) {
        String[] split = date.split("-");

        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);

        return LocalDate.of(year, month, day);
    }

    @Before
    public void beforeScenario(){
        address = new Address("Argentina", "Buenos Aires", "CABA", "Calle 117", 158);
        manager = new BranchManager();
    }

    @Given("a person with DNI {string} and his data \\(last name, first name, date of birth and address)")
    public void aPersonWithDNI(String dni) {
        registeredDNI1 = dni;
    }

    @Given("a client with DNI {string} and a person")
    public void aClientWithDNI(String dni) throws Exception {
        registeredDNI1 = dni;
        registeredLastName = "Fernandez";
        BankingSystem.getInstance().registerClient(dni, registeredLastName, "J", LocalDate.of(2000, 6, 19), address);
    }
    
    @Given("a client A with DNI {string} and date of birth {string}")
    public void createClientAWithDNIAndBirthDate(String dni, String bDate) throws Exception{
        registeredDNI1 = dni;
        BankingSystem.getInstance().registerClient(dni, "F", "J", stringToDate(bDate), address);
    }

    @Given("other client B with DNI {string} and date of birth {string}")
    public void createClientBWithDNIAndBirthDate(String dni, String bDate) throws Exception{
        registeredDNI2 = dni;
        BankingSystem.getInstance().registerClient(dni, "F", "J", stringToDate(bDate), address);
    }

    @Given("a client A with DNI {string} who holds a bank account with balance {double} ARS")
    public void clientAndMainAccount(String dni, double balance) throws Exception{
        registeredDNI1 = dni;
        BankingSystem.getInstance().registerClient(dni, "F", "J", LocalDate.of(2000,1,1), address);
        manager.registerNewBranch(123, "Suc", address);
        officer = new AccountOfficer(123);
        officer.createAndRegisterAccount("alias1", dni, null);
        account = BankingSystem.getInstance().getAccountByAlias("alias1");
        account.deposit(balance);
    }

    @Given("other client B with DNI {string} who has a bank account with client A as co-owner")
    public void clientAndMainAccountWithACoowner(String dni) throws Exception{
        registeredDNI2 = dni;
        BankingSystem.getInstance().registerClient(dni, "F", "J", LocalDate.of(2000,1,1), address);
        ArrayList<String> coowners = new ArrayList<>();
        coowners.add(registeredDNI1);
        officer.createAndRegisterAccount("alias2", dni, coowners);
    }

    
    @Given("a bank account, its owner client with DNI {string} and another client with DNI {string}")
    public void twoClientsWithOwnAccounts(String dni1, String dni2) throws Exception {
        registeredDNI1 = dni1;
        registeredDNI2 = dni2;
        LocalDate bDate = LocalDate.of(2000,1,1);
        BankingSystem.getInstance().registerClient(dni1, "F", "J", bDate, address);
        BankingSystem.getInstance().registerClient(dni2, "F", "J", bDate, address);
        manager.registerNewBranch(123, "Suc", address);
        officer = new AccountOfficer(123);
        officer.createAndRegisterAccount("aliasOfOwner", dni1, null);
        account = BankingSystem.getInstance().getAccountByAlias("aliasOfOwner");
    }

    @Given("a bank account, its owner client with DNI {string} and a DNI {string} that is not in the system")
    public void aClientAndADNIThatIsNotInTheSystem(String dni1, String dni2) throws Exception {
        registeredDNI1 = dni1;
        registeredDNI2 = dni2;
        LocalDate bDate = LocalDate.of(2000,1,1);
        BankingSystem.getInstance().registerClient(dni1, "F", "J", bDate, address);
        manager.registerNewBranch(123, "Suc", address);
        officer = new AccountOfficer(123);
        officer.createAndRegisterAccount("aliasOfOwner", dni1, null);
        account = BankingSystem.getInstance().getAccountByAlias("aliasOfOwner");
    }

    @When("the person registers as a client at the bank")
    public void aPersonRegistersAsAClient() throws Exception {
        BankingSystem.getInstance().registerClient(registeredDNI1, "F", "J", LocalDate.of(2000, 6, 19), address);
    }

    @When("the person try to registers as a client and puts an invalid DNI {string}")
    public void createClientWithDNI(String dni) throws Exception {   
        invalidDNI = false;
        try {
            BankingSystem.getInstance().registerClient(dni, "F", "J", LocalDate.of(2000, 6, 19), address);
        } catch (InvalidDNI e) {
            invalidDNI = true;
        }   
        
    }

    @When("the person try to registers as a client and puts in a DNI {string} already registered in the banking system")
    public void createClientWithDNIAlreadyExistentInTheSystem(String dni) throws Exception {
        clientAlreadyExists = false;
        unregisteredLastName = "F";
        try {
            BankingSystem.getInstance().registerClient(dni, unregisteredLastName, "J", LocalDate.of(2000, 6, 19), address);
        }catch (ClientAlreadyExists e) {
            clientAlreadyExists = true;
        }
        
    }

    @When("the system registers two clients are spouses with marriage date {string}")
    public void registerIfClientIsMarried(String mDate) throws MarriageDateCantBeBeforeBirth, Exception{
        marriageDate = mDate;
        marriageDateIsBeforeBirthDate = false;
        Client client2 = BankingSystem.getInstance().getClient(registeredDNI2);
        try {
            BankingSystem.getInstance().getClient(registeredDNI1).setAsMarriedWithMarriageDate(stringToDate(mDate), client2);
        } catch (MarriageDateCantBeBeforeBirth e) {
            marriageDateIsBeforeBirthDate = true;
        }
        
    }

    @When("the client tries to add a co-owner to his account with that DNI")
    @When("the owner adds the other client to his account as a co-owner by DNI")
    public void addCoowner() throws Exception {
        isNonexistentCoowner = false;
        try {
            BankingSystem.getInstance().getClient(registeredDNI1).addCoownerToMainAccount(registeredDNI2);
        } catch (ThereIsNoClientWithThatDNI e) {
            isNonexistentCoowner = true;
        }
    }

    @When("the client leaves the banking system")
    @When("the client tries to deregister from the banking system")
    public void cancelClient() throws Exception {
        doesAccountStillHaveFunds = false;
        try {
            BankingSystem.getInstance().getClient(registeredDNI1).cancel();
        } catch (AccountStillHasFunds e) {
            doesAccountStillHaveFunds = true;
        }
        
    }

    @Then("the client with DNI {string} is in the System")
    public void verifyClientIsInTheSystem(String dni) {
        assertDoesNotThrow(() -> BankingSystem.getInstance().getClient(dni));
    }

    @Then("the operation should be denied for invalid DNI")
    public void verifyInvalidDNI(){
        assertTrue(invalidDNI);
    }

    @Then("the client with DNI {string} is not in the System") 
    public void verifyClientIsntInTheSystem(String dni){
        assertThrows(ThereIsNoClientWithThatDNI.class, () -> BankingSystem.getInstance().getClient(dni));
    }

    @Then("the operation shoul be denied due to there is already a client registered with that DNI")
    public void verifyClientAlreadyExists(){
        assertTrue(clientAlreadyExists);
    }

    @Then("the client is not replaced in the system")
    public void verifyReplacedClient() throws Exception{
        assertEquals(registeredLastName, BankingSystem.getInstance().getClient(registeredDNI1).getLastName());
        assertNotEquals(unregisteredLastName, BankingSystem.getInstance().getClient(registeredDNI1).getLastName());
    }

    @Then("the two clients become spouses with a marriage date")
    public void verifyIsItMarried() throws Exception{
        Client client1 = BankingSystem.getInstance().getClient(registeredDNI1);
        Client client2 = BankingSystem.getInstance().getClient(registeredDNI2);
        assertTrue(client1.isItMarried());
        assertTrue(client2.isItMarried());
        assertEquals(marriageDate, client1.getMarriageDate());
        assertEquals(marriageDate, client2.getMarriageDate());
    }

    @Then("the operation is denied due to marriage date is before date of birth")
    public void verifyMarriageDateIsBeforeBirthDate(){
        assertTrue(marriageDateIsBeforeBirthDate);
    }

    @Then("they are not registered as spouses")
    public void verifyIsItNotMarried() throws Exception{
        Client client1 = BankingSystem.getInstance().getClient(registeredDNI1);
        Client client2 = BankingSystem.getInstance().getClient(registeredDNI2);
        assertFalse(client1.isItMarried());
        assertFalse(client2.isItMarried());
    }

    @Then("the other client is added as co-owner")
    public void verifyIfClientIsCoowner() throws Exception {
        Client coowner = BankingSystem.getInstance().getClient(registeredDNI2);
        assertTrue(coowner.isYourAccount(account.getCbu()));
        assertFalse(coowner.isYourMainAccount(account.getCbu()));
    }

    @Then("the coowner has access to the account")
    public void verifyIfCoownerHasAccessToTheAccount(){
        assertDoesNotThrow(() -> {
            Account a = BankingSystem.getInstance().getClient(registeredDNI2).getAccount(account.getCbu());
        });
        
    }

    @Then("the coowner can't add other coowners")
    public void verifyCoownerCantAddOtherCoowners() {
        assertThrows(YouDontHavePermissions.class, () -> {
            account.addCoowner(BankingSystem.getInstance().getClient(registeredDNI2), registeredDNI2);
        });
        
    }

    @Then("an error occurs and warns that there is no client with that DNI registered in the bank")
    public void verifyIsNonexistentCoowner() {
        assertTrue(isNonexistentCoowner);
    }

    @Then("the client's bank account is terminated")
    public void verifyIsAccountTerminated(){
        assertFalse(account.isRegistered());
    }

    @Then("the client dissociates himself from the accounts in which he was co-owner")
    public void verifyIfClientAIsCoowner() throws Exception{
        Client clientA = BankingSystem.getInstance().getClient(registeredDNI1);
        Account accountB = BankingSystem.getInstance().getAccountByAlias("alias2");
        assertFalse(clientA.isYourAccount(accountB.getCbu())); 
    }

    @Then("the client is maintained becomes inoperative")
    public void verifyIsClientTerminated() throws Exception{
        Client clientA = BankingSystem.getInstance().getClient(registeredDNI1);
        assertFalse(clientA.isActive());
    }

    @Then("an error occurs because he first has to withdraw all his funds from his bank account to be able to unsubscribe")
    public void verifyDoesAccountStillHaveFunds() {
        assertTrue(doesAccountStillHaveFunds);
    }
}
