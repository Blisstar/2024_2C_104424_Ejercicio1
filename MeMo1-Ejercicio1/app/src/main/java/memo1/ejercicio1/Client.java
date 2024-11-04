package memo1.ejercicio1;

import java.time.LocalDate;
import java.util.HashMap;

public class Client {
    private String DNI;
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private LocalDate marriageDate;
    private Address address;
    private Long mainAccountCBU;
    private HashMap<Long, Account> accounts;

    public Client(String dni, String lastName, String firstName, LocalDate birthDate, Address address) throws InvalidDNI {
        if (dni.length() != 7 && dni.length() != 8) throw new InvalidDNI();
        if (!dni.matches("\\d+")) throw new InvalidDNI();
        this.DNI = dni;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.address = address;
        marriageDate = null;
        mainAccountCBU = null;
        accounts = new HashMap<>();
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

    public void setMainAccountCBU(Account account) {
        mainAccountCBU = account.getCbu();
        accounts.put(mainAccountCBU, account);
    }

    public boolean doItHaveMainAccount(){
        return mainAccountCBU != null;
    }

    public void addSecundaryAccount(Account account) {
        accounts.put(account.getCbu(), account);
    }

    public void addCoownerToMainAccount(String coownerDNI) throws Exception {
        accounts.get(mainAccountCBU).addCoowner(this, coownerDNI);
    }

    public boolean isYourMainAccount(Long cbu) {
        return (mainAccountCBU != null) && (cbu == mainAccountCBU);
    }

    public boolean isYourAccount(Long cbu) {
        return accounts.containsKey(cbu);
    }

    public Account getAccount(Long cbu) throws YouDontHavePermissions {
        if (!isYourAccount(cbu)) throw new YouDontHavePermissions();
        return accounts.get(cbu);
    }
}
