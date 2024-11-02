package memo1.ejercicio1;

import java.time.LocalDate;

public class Client {
    private String DNI;
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private LocalDate marriageDate;
    private Address address;

    public Client(String dni, String lastName, String firstName, LocalDate birthDate, Address address) throws InvalidDNI {
        if (dni.length() != 7 && dni.length() != 8) throw new InvalidDNI();
        if (!dni.matches("\\d+")) throw new InvalidDNI();
        this.DNI = dni;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.address = address;
        this.marriageDate = null;
    }

    public String getDNI() {
        return DNI;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Boolean isItMarried() {
        return marriageDate != null;
    }

    public void setAsMarriedWithMarriageDate(LocalDate marriageDate) throws MarriageDateCantBeBeforeBirth {
        if (marriageDate.isBefore(birthDate)) {
            throw new MarriageDateCantBeBeforeBirth();
        }
        this.marriageDate = marriageDate;
    }

    public LocalDate getMarriageDate() throws ClientIsntMarried {
        if (marriageDate == null) {
            throw new ClientIsntMarried();
        }
        return marriageDate;
    }

    public void setAsSingle() {
        this.marriageDate = null;
    }

    public String getAddress() {
        return address.toString();
    }
}
