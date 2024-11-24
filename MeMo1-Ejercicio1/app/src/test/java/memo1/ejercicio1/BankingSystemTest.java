package memo1.ejercicio1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

public class BankingSystemTest {
    private Account account;
    private Address address;
    private Client client;
    private Branch branch;

    @BeforeEach
    public void setUp() throws Exception {
        address = new Address("A", "B", "C", "Dorrego", 987);
        account = new Account(123456789L, "alias", client, branch);
        BankingSystem.getInstance().addAccount(account);

        Address clientAddress = new Address("A", "B", "C", "D", 1);
        client = new Client("12345678", "F", "J", LocalDate.of(1984,1,1), clientAddress);
        Address branchAddress = new Address("Argentina", "Buenos Aires", "CABA", "Calle 117", 158);
        branch = new Branch(1, "Suc. Belgrano", branchAddress);
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
        Account newAccount = new Account(123456789L, "nuevo alias", client, branch);
        assertThrows(AccountWithCBUAlreadyExists.class, () -> {
            BankingSystem.getInstance().addAccount(newAccount);
        });
    }

    @Test
    void addNewAccountWithAliasThatAlreadyExistsInBankingSystemThrowsException() {
        Account newAccount = new Account(987654321L, "alias", client, branch);
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

    @Test
    void registerBranchWorksCorrectly(){
        Branch branch = new Branch(123,"Suc Belgrano", address);
        
        assertDoesNotThrow(() -> {
            BankingSystem.getInstance().registerBranch(branch);
        });

    }

    @Test
    void registerABranchThatAlreadyExistsThrowsException(){
        Branch branch = new Branch(123,"Suc Belgrano", address);
        
        assertThrows(BranchAlreadyExists.class ,() -> {
            BankingSystem.getInstance().registerBranch(branch);
            BankingSystem.getInstance().registerBranch(branch);
        });
        
    }

    @Test
    void getBranchWorksCorrectly() throws Exception {
        Branch branch = new Branch(123,"Suc Belgrano", address);
        BankingSystem.getInstance().registerBranch(branch);

        assertEquals(branch, BankingSystem.getInstance().getBranch(branch.getNumber()));
    }

    @Test
    void getBranchOfNonexistentBranchThrowsException() throws Exception {
        assertThrows(NonexistentBranch.class, () -> BankingSystem.getInstance().getBranch(123));
    }
}
