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
        assertEquals(LocalDate.of(2000,6,19).toString(), client.getBirthDate());
        assertEquals(address.toString(), client.getAddress());
    }

    @Test
    void defaultConstructorShouldInitializeMaritalStatusToSingle() throws Exception {
        Client client = new Client("42657569", "FF", "G", LocalDate.of(2000, 1, 1), address);
        assertFalse(client.isItMarried());
    }

    @Test
    void setAsMarriedWithMarriageDateWorksCorrectly() throws Exception {
        Client client1 = new Client("42657569", "FF", "G", LocalDate.of(2000, 1, 1), address);
        Client client2 = new Client("12345678", "FF", "G", LocalDate.of(2000, 1, 1), address);
        client1.setAsMarriedWithMarriageDate(LocalDate.of(2022,1,1), client2);
        assertTrue(client1.isItMarried());
        assertTrue(client2.isItMarried());
        assertEquals(LocalDate.of(2022,1,1).toString(), client1.getMarriageDate());
        assertEquals(LocalDate.of(2022,1,1).toString(), client2.getMarriageDate());
    }

    @Test
    void setAsMarriedWithMarriageDateIsBeforeBirthGiveAnError() throws Exception {
        Client client1 = new Client("42657569", "FF", "G", LocalDate.of(2000, 1, 1), address);
        Client client2 = new Client("12345678", "FF", "G", LocalDate.of(2000, 1, 1), address);
        assertThrows(MarriageDateCantBeBeforeBirth.class, () -> {
            client1.setAsMarriedWithMarriageDate(LocalDate.of(1999,12,31), client2);
        });
    }

    @Test
    void setAsSingleWorksCorrectly() throws Exception {
        Client client1 = new Client("42657569", "FF", "G", LocalDate.of(2000, 1, 1), address);
        Client client2 = new Client("12345678", "FF", "G", LocalDate.of(2000, 1, 1), address);
        client1.setAsMarriedWithMarriageDate(LocalDate.of(2022,1,1), client2);
        assertTrue(client1.isItMarried());
        assertTrue(client2.isItMarried());
        assertEquals(LocalDate.of(2022,1,1).toString(), client1.getMarriageDate());
        assertEquals(LocalDate.of(2022,1,1).toString(), client2.getMarriageDate());
        
        client1.setAsSingle();
        assertFalse(client1.isItMarried());
        assertFalse(client2.isItMarried());
        assertThrows(ClientIsntMarried.class, client1::getMarriageDate);
    }

    @Test
    void defaultConstructorInitializeWithInvalidDNIGiveAnError() throws Exception {
        assertThrows(InvalidDNI.class, () -> {
            Client client1 = new Client("ABCDFGH1", "FF", "G", LocalDate.of(2000, 1, 1), address);
        });

        assertThrows(InvalidDNI.class, () -> {
            Client client2 = new Client("123456", "FF", "G", LocalDate.of(2000, 1, 1), address);
        });

        assertThrows(InvalidDNI.class, () -> {
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
