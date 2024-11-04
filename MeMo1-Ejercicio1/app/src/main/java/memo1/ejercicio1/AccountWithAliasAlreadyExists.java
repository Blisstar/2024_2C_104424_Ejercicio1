package memo1.ejercicio1;

public class AccountWithAliasAlreadyExists extends Exception {
    public AccountWithAliasAlreadyExists() {
        super("An account with alias already exists");
    }
}
