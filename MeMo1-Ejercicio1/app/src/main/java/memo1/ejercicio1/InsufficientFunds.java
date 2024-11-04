package memo1.ejercicio1;

public class InsufficientFunds extends Exception {
    public InsufficientFunds() {
        super("There are insufficient funds");
    }
}
