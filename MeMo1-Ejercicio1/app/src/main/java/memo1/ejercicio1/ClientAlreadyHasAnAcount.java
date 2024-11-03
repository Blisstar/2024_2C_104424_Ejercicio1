package memo1.ejercicio1;

public class ClientAlreadyHasAnAcount extends Exception{
    public ClientAlreadyHasAnAcount(){
        super("The client already has an account");
    }
}
