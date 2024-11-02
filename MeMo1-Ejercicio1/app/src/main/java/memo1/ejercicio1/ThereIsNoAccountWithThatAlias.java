package memo1.ejercicio1;

public class ThereIsNoAccountWithThatAlias extends Exception {
    public ThereIsNoAccountWithThatAlias() {
        super("There is no account with that alias.");
    }
}
