package memo1.ejercicio1;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionTest{
    private Account account1;
    private Account account2;

    @BeforeEach
    public void setUp(){
        account1 = new Account(12345678L, "alias1");
        account2 = new Account(98765432L, "alias2");
    }

    @Test
    void defaultConstructorShouldInitializeCorrectly() throws Exception {
        LocalDateTime beforeTime = LocalDateTime.now();
        Transaction transaction = new Transaction(TransactionType.TRANSFER, 1100, account1, account2);
        LocalDateTime afterTime = LocalDateTime.now();

        assertEquals(0, transaction.getNumber());
        assertTrue(beforeTime.isBefore(transaction.getRealizationDateTime()));
        assertTrue(afterTime.isAfter(transaction.getRealizationDateTime()));
        assertEquals(1100, transaction.getAmount());
        assertEquals(TransactionType.TRANSFER, transaction.getType());
        assertEquals(account1.getCbu(), transaction.getFirstAccount().getCbu());
        assertEquals(account2.getCbu(), transaction.getSecondAccount().getCbu());
    }
}