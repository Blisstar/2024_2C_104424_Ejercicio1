package memo1.ejercicio1;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    void defaultConstructorShouldInitializeCorrectly() {
        Client account = new Client("42657569", "Fernandez", "Joel", LocalDate.of(2000, 6, 19));
        assertEquals("42657569", account.getDNI());
        assertEquals("Fernandez", account.getLastName());
        assertEquals("Joel", account.getFirstName());
        assertEquals(LocalDate.of(2000,6,19), account.getBirthDate());

    }

    @Test
    void defaultConstructorShouldInitializeMaritalStatusToSingle() {
        Client account = new Client("42657569", "FF", "G", LocalDate.of(2000, 1, 1));
        assertFalse(account.isItMarried());
    }

    @Test
    void setAsMarriedWithWeddingDateWorksCorrectly() {
        Client account = new Client("42657569", "FF", "G", LocalDate.of(2000, 1, 1));
        account.setAsMarriedWithWeedingDate(LocalDate.of(2022,1,1));
        assertTrue(account.isItMarried());
        assertEquals(LocalDate.of(2022,1,1), account.getWeddingDate());
    }

}
