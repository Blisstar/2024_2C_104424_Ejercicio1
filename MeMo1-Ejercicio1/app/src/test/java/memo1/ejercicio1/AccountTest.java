package memo1.ejercicio1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Pruebas unitarias

class AccountTest {

    @Test
    void constructorWithoutBalanceShouldInitializeBalanceToZero() {
        Account account = new Account(123456789L, "alias");
        assertEquals(0.0, account.getBalance());
    }

    @Test
    void constructorShouldThrowExceptionIfBalanceIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Account(123456789L, "alias",-50.0));
    }

    @Test
    void constructorWithoutBalanceShouldInitializeCorrectly() {
        Account account = new Account(123456789L, "alias");
        assertEquals(123456789L, account.getCbu());
        assertEquals("alias", account.getAlias());
        assertEquals(0.0, account.getBalance());
    }

    @Test
    void constructorWithBalanceShouldInitializeCorrectly() {
        Account account = new Account(123456789L, "alias", 100.0);
        assertEquals(123456789L, account.getCbu());
        assertEquals("alias", account.getAlias());
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void depositShouldIncreaseBalance() {
        Account account = new Account(123456789L, "alias");
        account.deposit(50.0);
        assertEquals(50.0, account.getBalance());
    }

    @Test
    void depositShouldReturnFalseForNegativeAmount() {
        Account account = new Account(123456789L, "alias");
        assertFalse(account.deposit(-10.0));
    }

    @Test
    void withdrawShouldDecreaseBalance() {
        Account account = new Account(123456789L, "alias",100.0);
        assertTrue(account.withdraw(50.0));
        assertEquals(50.0, account.getBalance());
    }

    @Test
    void withdrawShouldReturnFalseIfAmountExceedsBalance() {
        Account account = new Account(123456789L, "alias",100.0);
        assertFalse(account.withdraw(150.0));
    }

    @Test
    void withdrawShouldReturnFalseForNegativeAmount() {
        Account account = new Account(123456789L, "alias",100.0);
        assertFalse(account.withdraw(-10.0));
    }

    @Test
    void withdrawShouldAllowExactAmount() {
        Account account = new Account(123456789L, "alias",100.0);
        assertTrue(account.withdraw(100.0));
        assertEquals(0.0, account.getBalance());
    }


}
