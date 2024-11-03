package memo1.ejercicio1;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import java.time.LocalDate;

// Pruebas funcionales basadas en los escenarios Gherkin

public class ClientSteps {
    private Address address;
    private String registeredLastName;
    private String unregisteredLastName;
    private String registeredDNI;
    private String birthDate;
    private String marriageDate;
    private boolean invalidDNI;
    private boolean clientAlreadyExists;
    private boolean marriageDateIsBeforeBirthDate;

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
    }

    @Given("client register his data \\(last name, first name, date of birth and address) with DNI {string} in the bank")
    @Given("client try to register his data and puts an invalid DNI {string}")
    public void createClientWithDNI(String dni) throws Exception {   
        invalidDNI = false;
        try {
            BankingSystem.getInstance().registerClient(dni, "F", "J", LocalDate.of(2000, 6, 19), address);
        } catch (InvalidDNI e) {
            invalidDNI = true;
        }   
        
    }

    @Given("client try to register his data and put in a DNI {string} already registered in the banking system")
    public void createClientWithDNIAlreadyExistentInTheSystem(String dni) throws Exception {
        clientAlreadyExists = false;
        registeredLastName = "Fernandez";
        unregisteredLastName = "F";
        registeredDNI = dni;
        try {
            BankingSystem.getInstance().registerClient(dni, registeredLastName, "J", LocalDate.of(2000, 6, 19), address);
            BankingSystem.getInstance().registerClient(dni, unregisteredLastName, "J", LocalDate.of(2000, 6, 19), address);
        }catch (ClientAlreadyExists e) {
            clientAlreadyExists = true;
        }
        
    }

    @Given("a client with DNI {string} and date of birth {string} and the banking system")
    public void createClientWithDNIAndBirthDate(String dni, String bDate) throws Exception{
        registeredDNI = dni;
        birthDate = bDate;
        BankingSystem.getInstance().registerClient(dni, "F", "J", stringToDate(bDate), address);
    }

    @When("the system registers that the client is married with marriage date {string}")
    public void registerIfClientIsMarried(String mDate) throws MarriageDateCantBeBeforeBirth, Exception{
        marriageDate = mDate;
        marriageDateIsBeforeBirthDate = false;
        try {
            BankingSystem.getInstance().getClient(registeredDNI).setAsMarriedWithMarriageDate(stringToDate(mDate));
        } catch (MarriageDateCantBeBeforeBirth e) {
            marriageDateIsBeforeBirthDate = true;
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
        assertEquals(registeredLastName, BankingSystem.getInstance().getClient(registeredDNI).getLastName());
        assertNotEquals(unregisteredLastName, BankingSystem.getInstance().getClient(registeredDNI).getLastName());
    }

    @Then("it is stored in the system if the client is married")
    public void verifyIsItMarried() throws Exception{
        assertTrue(BankingSystem.getInstance().getClient(registeredDNI).isItMarried());
    }

    @Then("the date of marriage is recorded.")
    public void verifyMarriageDateIsCorrect() throws ClientIsntMarried, Exception{
        assertEquals(marriageDate, BankingSystem.getInstance().getClient(registeredDNI).getMarriageDate());
    }

    @Then("the operation is denied due to marriage date is before date of birth")
    public void verifyMarriageDateIsBeforeBirthDate(){
        assertTrue(marriageDateIsBeforeBirthDate);
    }

    @Then("the client is not set as married.")
    public void verifyIsItNotMarried() throws Exception{
        assertFalse(BankingSystem.getInstance().getClient(registeredDNI).isItMarried());
    }

}
