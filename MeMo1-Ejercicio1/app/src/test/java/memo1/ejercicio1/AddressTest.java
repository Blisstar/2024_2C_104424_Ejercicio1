package memo1.ejercicio1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressTest {

    @Test
    void defaultConstructorShouldInitializeCorrectly() throws Exception {
        Address address = new Address("Argentina", "Buenos Aires", "CABA", "Las Heras", 321);
        assertEquals("Argentina, Buenos Aires, CABA, Las Heras 321", address.toString());
    }
}
