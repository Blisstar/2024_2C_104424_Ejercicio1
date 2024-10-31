package memo1.ejercicio1;

import java.time.LocalDate;

public class Client {
    String DNI;
    String lastName;
    String firstName;
    LocalDate birthDate;
    LocalDate weddingDate;

    public Client(String dni, String lastName, String firstName, LocalDate birthDate) {
        this.DNI = dni;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.weddingDate = null;
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
        return weddingDate != null;
    }

    public void setAsMarriedWithWeedingDate(LocalDate weedingDate) {
        this.weddingDate = weedingDate;
    }

    public LocalDate getWeddingDate() {
        return weddingDate;
    }
}
