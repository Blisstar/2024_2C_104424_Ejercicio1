package memo1.ejercicio1;

public class YouDontHavePermissions extends Exception{

    public YouDontHavePermissions() {
        super("You dont have permissions to do this.");
    }

}
