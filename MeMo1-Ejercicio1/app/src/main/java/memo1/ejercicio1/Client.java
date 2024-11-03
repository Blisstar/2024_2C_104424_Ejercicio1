package memo1.ejercicio1;

import java.time.LocalDate;
import java.util.ArrayList;

public class Client {
    private String DNI;
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private LocalDate marriageDate;
    private Address address;
    private Account mainAccount;
    private ArrayList<Account> secundaryAccounts;

    public Client(String dni, String lastName, String firstName, LocalDate birthDate, Address address) throws InvalidDNI {
        if (dni.length() != 7 && dni.length() != 8) throw new InvalidDNI();
        if (!dni.matches("\\d+")) throw new InvalidDNI();
        this.DNI = dni;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.address = address;
        marriageDate = null;
        mainAccount = null;
        secundaryAccounts = new ArrayList<>();
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

    public String getBirthDate() {
        return birthDate.toString();
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

    public String getMarriageDate() throws ClientIsntMarried {
        if (marriageDate == null) {
            throw new ClientIsntMarried();
        }
        return marriageDate.toString();
    }

    public void setAsSingle() {
        this.marriageDate = null;
    }

    public String getAddress() {
        return address.toString();
    }

    public void setMainAccount(Account account) {
        mainAccount = account;
    }

    public boolean doItHaveMainAccount(){
        return mainAccount != null;
    }

    public void addSecundaryAccount(Account account) {
        secundaryAccounts.add(account);
    }
}
