package memo1.ejercicio1;

public class Address {
    String country;
    String state;
    String city;
    String street;
    int streetNumber;

    public Address(String country, String state, String city, String street, int streetNumber) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public String toString() {
        return country + ", " + state + ", " + city + ", " + street + " " + streetNumber;
    }
}
