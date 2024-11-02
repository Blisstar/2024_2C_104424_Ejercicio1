package memo1.ejercicio1;

public class ThereIsNoAccountWithThatCBU extends Exception {
    public ThereIsNoAccountWithThatCBU() {
        super("There is no account with that cbu in the system");
    }
}
