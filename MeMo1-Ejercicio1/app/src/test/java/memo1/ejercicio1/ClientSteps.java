package memo1.ejercicio1;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import java.time.LocalDate;

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
    
    @Given("a client with DNI {string} and date of birth {string} and the banking system")
    public void createClientWithDNIAndBirthDate(String dni, String bDate) throws Exception{
        registeredDNI1 = dni;
        BankingSystem.getInstance().registerClient(dni, "F", "J", stringToDate(bDate), address);
    }

    @Given("a client with DNI {string} and a bank account")
    public void clientAndMainAccount(String dni) throws Exception{
        registeredDNI1 = dni;
        BankingSystem.getInstance().registerClient(dni, "F", "J", LocalDate.of(2000,1,1), address);
        manager.registerNewBranch(123, "Suc", address);
        officer = new AccountOfficer(123);
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

    @When("the system registers that the client is married with marriage date {string}")
    public void registerIfClientIsMarried(String mDate) throws MarriageDateCantBeBeforeBirth, Exception{
        marriageDate = mDate;
        marriageDateIsBeforeBirthDate = false;
        try {
            BankingSystem.getInstance().getClient(registeredDNI1).setAsMarriedWithMarriageDate(stringToDate(mDate));
        } catch (MarriageDateCantBeBeforeBirth e) {
            marriageDateIsBeforeBirthDate = true;
        }
        
    }

    @When("the client is the owner of the bank account")
    public void registerNewAccountForClient() throws Exception {
        officer.createAndRegisterAccount("aliasOfOwner", registeredDNI1, null);
        account = BankingSystem.getInstance().getAccountByAlias("aliasOfOwner");
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

    @Then("it is stored in the system if the client is married")
    public void verifyIsItMarried() throws Exception{
        assertTrue(BankingSystem.getInstance().getClient(registeredDNI1).isItMarried());
    }

    @Then("the date of marriage is recorded.")
    public void verifyMarriageDateIsCorrect() throws ClientIsntMarried, Exception{
        assertEquals(marriageDate, BankingSystem.getInstance().getClient(registeredDNI1).getMarriageDate());
    }

    @Then("the operation is denied due to marriage date is before date of birth")
    public void verifyMarriageDateIsBeforeBirthDate(){
        assertTrue(marriageDateIsBeforeBirthDate);
    }

    @Then("the client is not set as married.")
    public void verifyIsItNotMarried() throws Exception{
        assertFalse(BankingSystem.getInstance().getClient(registeredDNI1).isItMarried());
    }

    @Then("he becomes the one who has the most control over the bank account")
    public void verifyIfTheClientIsTheAccountHolder() throws Exception{
        assertTrue(BankingSystem.getInstance().getClient(registeredDNI1).isYourMainAccount(account.getCbu()));
    }

    @Then("the client can add coowners")
    public void verifyIfClientAddCoownersToItsAccount() throws Exception {
        String coownerDNI = new StringBuilder(registeredDNI1).reverse().toString();
        BankingSystem.getInstance().registerClient(coownerDNI, "F", "J", LocalDate.of(2000,1,1), address);
        
        Client owner = BankingSystem.getInstance().getClient(registeredDNI1);
        owner.addCoownerToMainAccount(coownerDNI);
        
        Client coowner = BankingSystem.getInstance().getClient(coownerDNI);
        assertTrue(coowner.isYourAccount(account.getCbu()));
        assertFalse(coowner.isYourMainAccount(account.getCbu()));
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
}
