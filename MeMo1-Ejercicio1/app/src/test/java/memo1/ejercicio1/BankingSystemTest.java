package memo1.ejercicio1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

public class BankingSystemTest {
    private Account account;

    @BeforeEach
    public void setUp() throws Exception {
        //Address address = new Address("A", "B", "C", "Dorrego", 987);
        account = new Account(123456789L, "alias");
        BankingSystem.getInstance().addAccount(account);
    }

    @AfterEach
    public void tearDown() {
        BankingSystem.resetInstance();
    }

    @Test
    void addAndGetAccountWorksCorrectly() throws Exception {
        assertEquals(account, BankingSystem.getInstance().getAccountByCBU(123456789L));
        assertEquals(account, BankingSystem.getInstance().getAccountByAlias("alias"));
    }

    @Test
    void addNewAccountWithCBUThatAlreadyExistsInBankingSystemThrowsException() {
        Account newAccount = new Account(123456789L, "nuevo alias");
        assertThrows(AccountWithCBUAlreadyExists.class, () -> {
            BankingSystem.getInstance().addAccount(newAccount);
        });
    }

    @Test
    void addNewAccountWithAliasThatAlreadyExistsInBankingSystemThrowsException() {
        Account newAccount = new Account(987654321L, "alias");
        assertThrows(AccountWithAliasAlreadyExists.class, () -> {
            BankingSystem.getInstance().addAccount(newAccount);
        });
    }

    @Test
    void getAccountWithANonexistentCBUThrowsException() {
        assertThrows(ThereIsNoAccountWithThatCBU.class, () -> {
            BankingSystem.getInstance().getAccountByCBU(135799753L);
        });
    }

    @Test
    void getAccountWithANonexistentAliasThrowsException() {
        assertThrows(ThereIsNoAccountWithThatAlias.class, () -> {
            BankingSystem.getInstance().getAccountByAlias("aliasSinCuenta");
        });
    }

    @Test
    void registerClientWorksCorrectly() throws Exception{
        Address address = new Address("A", "B", "C", "Calle", 122);
        
        assertDoesNotThrow(() -> {
            BankingSystem.getInstance().registerClient("42657569","Fernandez", "Joel", LocalDate.of(2000, 6, 19), address);
        });
    }

    @Test
    void registerClientThatItsDniAlreadyExistsThrowsException() throws Exception{
        Address address = new Address("A", "B", "C", "Calle", 122);
        
        assertDoesNotThrow(() -> {
            BankingSystem.getInstance().registerClient("42657569","Fernandez", "Joel", LocalDate.of(2000, 6, 19), address);
        });

        assertThrows(ClientAlreadyExists.class, () -> {
            BankingSystem.getInstance().registerClient("42657569","Fernandez", "Joel", LocalDate.of(2000, 6, 19), address);
        });
    }
}
