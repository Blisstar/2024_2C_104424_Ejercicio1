package memo1.ejercicio1;

public class AccountWithCBUAlreadyExists extends Exception {
    public AccountWithCBUAlreadyExists() {
        super("An account with this CBU already exists");
    }
}
