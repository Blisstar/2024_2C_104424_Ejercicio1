package memo1.ejercicio1;

public class Branch {
    private int number;
    private String denomination;
    private Address address;
    private boolean isCancelled;

    public Branch(int number, String denomination, Address address) {
        this.number = number;
        this.denomination = denomination;
        this.address = address;
        this.isCancelled = false;
    }

    public int getNumber(){
        return number;
    }

    public String getDenomination() {
        return denomination;
    }

    public String getAddress(){
        return address.toString();
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void cancel() {
        this.isCancelled = true;
    }

    public boolean isCancelled() {
        return isCancelled;
    }
    
}
