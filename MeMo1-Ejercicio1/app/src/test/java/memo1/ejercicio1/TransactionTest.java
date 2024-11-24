package memo1.ejercicio1;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionTest{
    private Account account1;
    private Account account2;

    @BeforeEach
    public void setUp() throws Throwable{
        Address clientAddressA = new Address("A", "B", "C", "D", 1);
        Address clientAddressB = new Address("A", "B", "C", "D", 2);
        Client clientA = new Client("12345678", "F", "J", LocalDate.of(1984,1,1), clientAddressA);
        Client clientB = new Client("87654321", "J", "F", LocalDate.of(1993,4,1), clientAddressB);
        Address branchAddress = new Address("Argentina", "Buenos Aires", "CABA", "Calle 117", 158);
        Branch branch = new Branch(1, "Suc. Belgrano", branchAddress);
        account1 = new Account(12345678L, "alias1", clientA, branch);
        account2 = new Account(98765432L, "alias2", clientB, branch);
    }

    @Test
    void defaultConstructorShouldInitializeCorrectly() throws Exception {
        LocalDateTime beforeTime = LocalDateTime.now();
        Transaction transaction = new Transaction(TransactionType.TRANSFER, 1100, account1, account2);
        LocalDateTime afterTime = LocalDateTime.now();

        assertTrue(beforeTime.isBefore(transaction.getRealizationDateTime()));
        assertTrue(afterTime.isAfter(transaction.getRealizationDateTime()));
        assertEquals(1100, transaction.getAmount());
        assertEquals(TransactionType.TRANSFER, transaction.getType());
        assertEquals(account1.getCbu(), transaction.getFirstAccount().getCbu());
        assertEquals(account2.getCbu(), transaction.getSecondAccount().getCbu());
    }

    @Test
    void theTransactionsShouldntHaveSameNumber() throws Exception {
        Transaction transaction1 = new Transaction(TransactionType.TRANSFER, 1100, account1, account2);
        Transaction transaction2 = new Transaction(TransactionType.TRANSFER, 1100, account1, account2);
        Transaction transaction3 = new Transaction(TransactionType.TRANSFER, 1100, account2, account1);

        assertTrue(transaction1.getNumber() != transaction2.getNumber());
        assertTrue(transaction1.getNumber() != transaction3.getNumber());
        assertTrue(transaction2.getNumber() != transaction3.getNumber());
    }
}