package memo1.ejercicio1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BranchTest {

     @Test
    void defaultConstructorShouldInitializeCorrectly() throws Exception {
        Address address = new Address("Argentina", "Buenos Aires", "CABA", "Las Heras", 321);
        Branch branch = new Branch(123, "Suc. Belgrano", address);

        assertEquals(123, branch.getNumber());
        assertEquals("Suc. Belgrano", branch.getDenomination());
        assertEquals(address.toString(), branch.getAddress());
    }

    @Test
    void setDenominationWorksCorrectly() {
        Address address = new Address("Argentina", "Buenos Aires", "CABA", "Las Heras", 321);
        Branch branch = new Branch(123, "Suc. Belgrano", address);

        branch.setDenomination("Suc. Belgranito");
        assertEquals("Suc. Belgranito", branch.getDenomination());
    }

    @Test
    void setAddresWorksCorrectly() {
        Address address1 = new Address("Argentina", "Buenos Aires", "CABA", "Las Heras", 321);
        Branch branch = new Branch(123, "Suc. Belgrano", address1);

        Address address2 = new Address("Uruguay", "Buenos Aires", "CABA", "Las Heras", 321);
        branch.setAddress(address2);
        
        assertEquals(address2.toString(), branch.getAddress());
    }

}
