package memo1.ejercicio1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {
    private static Address address;

    @BeforeAll
    static void init() {
        address = new Address("Argentina", "Buenos Aires", "CABA", "Paseo Colon", 123);
    }

    @Test
    void defaultConstructorShouldInitializeCorrectly() throws Exception {
        Client client = new Client("42657569", "Fernandez", "Joel", LocalDate.of(2000, 6, 19), address);
        assertEquals("42657569", client.getDNI());
        assertEquals("Fernandez", client.getLastName());
        assertEquals("Joel", client.getFirstName());
        assertEquals(LocalDate.of(2000,6,19), client.getBirthDate());
        assertEquals(address.toString(), client.getAddress());
    }

    @Test
    void defaultConstructorShouldInitializeMaritalStatusToSingle() throws Exception {
        Client client = new Client("42657569", "FF", "G", LocalDate.of(2000, 1, 1), address);
        assertFalse(client.isItMarried());
    }

    @Test
    void setAsMarriedWithMarriageDateWorksCorrectly() throws Exception {
        Client client = new Client("42657569", "FF", "G", LocalDate.of(2000, 1, 1), address);
        client.setAsMarriedWithMarriageDate(LocalDate.of(2022,1,1));
        assertTrue(client.isItMarried());
        assertEquals(LocalDate.of(2022,1,1), client.getMarriageDate());
    }

    @Test
    void setAsMarriedWithMarriageDateIsBeforeBirthGiveAnError() throws Exception {
        Client client = new Client("42657569", "FF", "G", LocalDate.of(2000, 1, 1), address);
        assertThrows(MarriageDateCantBeBeforeBirth.class, () -> {
            client.setAsMarriedWithMarriageDate(LocalDate.of(1999,12,31));
        });
    }

    @Test
    void setAsSingleWorksCorrectly() throws Exception {
        Client client = new Client("42657569", "FF", "G", LocalDate.of(2000, 1, 1), address);
        client.setAsMarriedWithMarriageDate(LocalDate.of(2022,1,1));
        assertTrue(client.isItMarried());
        assertEquals(LocalDate.of(2022,1,1), client.getMarriageDate());
        client.setAsSingle();
        assertFalse(client.isItMarried());
        assertThrows(ClientIsntMarried.class, client::getMarriageDate);
    }

    @Test
    void defaultConstructorInitializeWithInvalidDNIGiveAnError() throws Exception {
        assertThrows(IncorrectDNI.class, () -> {
            Client client1 = new Client("ABCDFGH1", "FF", "G", LocalDate.of(2000, 1, 1), address);
        });

        assertThrows(IncorrectDNI.class, () -> {
            Client client2 = new Client("123456", "FF", "G", LocalDate.of(2000, 1, 1), address);
        });

        assertThrows(IncorrectDNI.class, () -> {
            Client client3 = new Client("123456789", "FF", "G", LocalDate.of(2000, 1, 1), address);
        });

        assertDoesNotThrow(() -> {
            Client client4 = new Client("1234567", "FF", "G", LocalDate.of(2000, 1, 1), address);
        });

        assertDoesNotThrow(() -> {
            Client client5 = new Client("12345678", "FF", "G", LocalDate.of(2000, 1, 1), address);
        });
    }
}
