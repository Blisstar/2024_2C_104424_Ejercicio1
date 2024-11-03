package memo1.ejercicio1;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BranchManagerTest {
    private Address address;

    @BeforeEach
    public void setUp() {
        address = new Address("Argentina", "Buenos Aires", "CABA, Belgrano", "Calle", 123);
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
}
