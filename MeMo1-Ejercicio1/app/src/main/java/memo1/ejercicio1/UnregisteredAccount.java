package memo1.ejercicio1;

public class UnregisteredAccount extends Exception {
    public UnregisteredAccount() {
        super("The account is not registered.");
    }
}
