package memo1.ejercicio1;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountOfficerTest {
    private Address address;
    private Branch branch;
    private String ownerClient;
    private ArrayList<String> coownerClients;

    @BeforeEach
    public void setUp() throws Exception {
        address = new Address("Argentina", "Buenos Aires", "CABA", "Calle", 123);
        branch = new Branch(123, "Suc. Belgrano", address);
        BankingSystem.getInstance().registerBranch(branch);

        coownerClients = new ArrayList<>();
        BankingSystem.getInstance().registerClient("12345678", "F", "J", LocalDate.of(2000,1,1), address);
        BankingSystem.getInstance().registerClient("87654321", "F", "J", LocalDate.of(2000,1,1), address);
        BankingSystem.getInstance().registerClient("13579975", "F", "J", LocalDate.of(2000,1,1), address);
        ownerClient = "12345678";
        coownerClients.add("87654321");
        coownerClients.add("13579975");
    }

    @AfterEach
    public void tearDown() {
        BankingSystem.resetInstance();
    }

    @Test
    void defaultConstructorWorksCorrectly() {
        assertDoesNotThrow(() -> {
            AccountOfficer officer = new AccountOfficer(123);
        });
    }

    @Test
    void defaultConstructorFailsWithNumberOfNonexistentBranch() {
        assertThrows(NonexistentBranch.class, () -> {
            AccountOfficer officer = new AccountOfficer(321);
        });
    }

    @Test
    void createAndRegisterAccountWorksCorrectly() throws NonexistentBranch {
        AccountOfficer officer = new AccountOfficer(123);

        assertDoesNotThrow(() -> {
            officer.createAndRegisterAccount("alias",ownerClient, coownerClients);
        });
    }

    @Test
    void createAnAccountForAClientWhoAlreadyHasAnAccountThrowsException() throws Throwable {
        AccountOfficer officer = new AccountOfficer(123);
        officer.createAndRegisterAccount("alias1",ownerClient, coownerClients);

        assertThrows(ClientAlreadyHasAnAcount.class, () -> {
            officer.createAndRegisterAccount("alias2",ownerClient, coownerClients);
        });
    }

    @Test
    void cancelAnAccountWorksCorrectly() throws Throwable {
        AccountOfficer officer = new AccountOfficer(123);
        officer.createAndRegisterAccount("alias",ownerClient, coownerClients);
        Account account = BankingSystem.getInstance().getAccountByAlias("alias");
        
        assertDoesNotThrow(() -> {
            officer.cancelAccount(account.getCbu());
        });
        assertThrows(UnregisteredAccount.class, () -> {
            account.deposit(100);
        });
    }

    @Test
    void registerAnAccountWorksCorrectly() throws Exception{
        AccountOfficer officer = new AccountOfficer(123);
        officer.createAndRegisterAccount("alias",ownerClient, coownerClients);
        Account account = BankingSystem.getInstance().getAccountByAlias("alias");
        officer.cancelAccount(account.getCbu());
        officer.registerAccount(account.getCbu());

        assertDoesNotThrow(() -> {
            account.deposit(100);
        });
    }
}
