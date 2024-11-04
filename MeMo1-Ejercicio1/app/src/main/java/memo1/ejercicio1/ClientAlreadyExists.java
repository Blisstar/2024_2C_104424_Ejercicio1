package memo1.ejercicio1;

public class ClientAlreadyExists extends Exception{
    public ClientAlreadyExists(){
        super("The client with that dni is already exists");
    }
}
