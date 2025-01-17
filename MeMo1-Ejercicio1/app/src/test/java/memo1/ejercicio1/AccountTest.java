package memo1.ejercicio1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class AccountTest {
    private Address address;
    private Branch branch;
    private Client clientA;
    private Client clientB;

    @BeforeEach
    public void setUp() throws InvalidDNI{
        Address clientAddressA = new Address("A", "B", "C", "D", 1);
        Address clientAddressB = new Address("A", "B", "C", "D", 2);
        clientA = new Client("12345678", "F", "J", LocalDate.of(1984,1,1), clientAddressA);
        clientB = new Client("87654321", "J", "F", LocalDate.of(1993,4,1), clientAddressB);
        address = new Address("Argentina", "Buenos Aires", "CABA", "Calle 117", 158);
        branch = new Branch(1, "Suc. Belgrano", address);
    }

    @AfterEach
    public void tearDown() {
        BankingSystem.resetInstance();
    }

    @Test
    void constructorWithoutBalanceShouldInitializeBalanceToZero() {
        Account account = new Account(123456789L, "alias", clientA, branch);
        assertEquals(0.0, account.getBalance());
    }

    @Test
    void constructorShouldThrowExceptionIfBalanceIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Account(123456789L, "alias", clientA, branch, -50.0));
    }

    @Test
    void registerWorksCorrectly() throws Exception {
        Account accountA = new Account(123456789L, "alias1", clientA, branch);
        Account accountB = new Account(987654321L, "alias2", clientB, branch);

        accountA.cancel();
        accountB.cancel();

        accountB.register(branch);
        BankingSystem.getInstance().addAccount(accountA);
        BankingSystem.getInstance().addAccount(accountB);

        assertThrows(UnregisteredAccount.class, () -> accountA.withdraw(10));
        assertThrows(UnregisteredAccount.class, () -> accountA.deposit(300));
        assertThrows(UnregisteredAccount.class, () -> accountA.transfer(accountB.getCbu(),50));
        assertThrows(UnregisteredAccount.class, () -> accountA.transfer(accountB.getAlias(),500));
    }

    @Test
    void constructorWithoutBalanceShouldInitializeCorrectly() {
        Account account = new Account(123456789L, "alias", clientA, branch);
        assertEquals(123456789L, account.getCbu());
        assertEquals("alias", account.getAlias());
        assertEquals(0.0, account.getBalance());
    }

    @Test
    void constructorWithBalanceShouldInitializeCorrectly() {
        Account account = new Account(123456789L, "alias", clientA, branch, 100.0);
        assertEquals(123456789L, account.getCbu());
        assertEquals("alias", account.getAlias());
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void depositShouldIncreaseBalance() throws UnregisteredAccount {
        Account account = new Account(123456789L, "alias", clientA, branch);
        account.deposit(50.0);
        assertEquals(50.0, account.getBalance());
    }

    @Test
    void depositShouldReturnFalseForNegativeAmount() {
        Account account = new Account(123456789L, "alias", clientA, branch);
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-10.0);
        });
    }

    @Test
    void withdrawShouldDecreaseBalance() throws Exception {
        Account account = new Account(123456789L, "alias", clientA, branch, 100.0);
        account.withdraw(50.0);
        assertEquals(50.0, account.getBalance());
    }

    @Test
    void withdrawShouldReturnFalseIfAmountExceedsBalance() throws Exception {
        Account account = new Account(123456789L, "alias", clientA, branch, 100.0);
        assertThrows(InsufficientFunds.class, () -> {
                account.withdraw(150.0);
        });
    }

    @Test
    void withdrawShouldReturnFalseForNegativeAmount() throws Exception {
        Account account = new Account(123456789L, "alias", clientA, branch, 100.0);
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(-10.0);
        });
    }

    @Test
    void withdrawShouldAllowExactAmount() throws Exception {
        Account account = new Account(123456789L, "alias", clientA, branch, 100.0);
        account.withdraw(100.0);
        assertEquals(0.0, account.getBalance());
    }

    @Test
    void transferShouldDecreaseBalanceOfSenderAccount() throws Exception {
        Account senderAccount = new Account(123456789L, "alias1", clientA, branch, 1000.0);
        Account receiverAccount = new Account(987654321L, "alias2", clientB, branch, 0.0);

        BankingSystem.getInstance().addAccount(senderAccount);
        BankingSystem.getInstance().addAccount(receiverAccount);

        senderAccount.transfer(987654321L, 700.0);
        assertEquals(300.0, senderAccount.getBalance());
    }

    @Test
    void transferShouldIncreaseBalanceOfReceiverAccount() throws Exception {
        Account senderAccount = new Account(123456789L, "alias1", clientA, branch, 800.0);
        Account receiverAccount = new Account(987654321L, "alias2", clientB, branch, 0.0);

        BankingSystem.getInstance().addAccount(senderAccount);
        BankingSystem.getInstance().addAccount(receiverAccount);

        senderAccount.transfer(987654321L, 555.0);
        assertEquals(555.0, receiverAccount.getBalance());
    }
}
