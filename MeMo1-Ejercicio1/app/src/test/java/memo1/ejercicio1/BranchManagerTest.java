package memo1.ejercicio1;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BranchManagerTest {
    private Address address;

    @BeforeEach
    public void setUp() {
        address = new Address("Argentina", "Buenos Aires", "CABA, Belgrano", "Calle", 123);
    }

    @AfterEach
    public void tearDown() {
        BankingSystem.resetInstance();
    }

    @Test
    void defaultConstructorWorksCorrectly() {
        assertDoesNotThrow(() -> {
            BranchManager manager = new BranchManager();
        });
    }

    @Test
    void registerNewBranchWorksCorrectly() throws BranchAlreadyExists{
        BranchManager manager = new BranchManager();
        manager.registerNewBranch(123,"Suc. Belgrano", address);

        assertDoesNotThrow(() -> BankingSystem.getInstance().getBranch(123));
    }

    @Test
    void modifyDenominationOfABranchWorksCorrectly() throws Exception{
        BranchManager manager = new BranchManager();
        manager.registerNewBranch(123,"Suc. Belgrano", address);
        manager.modifyDenominationOfABranch(123, "Suc. Belgranito");

        assertEquals("Suc. Belgranito", BankingSystem.getInstance().getBranch(123).getDenomination());
    }

    @Test
    void modifyAddressOfABranchWorksCorrectly() throws Exception{
        BranchManager manager = new BranchManager();
        manager.registerNewBranch(123,"Suc. Belgrano", address);
        Address newAddress = new Address("Perú", "Tumbes", "Cabeza de lagarto", "Basilica", 10);
        manager.modifyAddressOfABranch(123, newAddress);

        assertEquals(newAddress.toString(), BankingSystem.getInstance().getBranch(123).getAddress());
    }
}
