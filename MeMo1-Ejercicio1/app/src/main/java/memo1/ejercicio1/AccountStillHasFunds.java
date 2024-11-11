package memo1.ejercicio1;

public class AccountStillHasFunds extends Exception{
    public AccountStillHasFunds() {
        super("The account still has funds, you can't cancel it");
    }
}
