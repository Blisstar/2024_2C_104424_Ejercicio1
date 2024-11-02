package memo1.ejercicio1;

import java.time.LocalDate;
import java.util.HashMap;

public class BankingSystem {
    private static BankingSystem instance;

    private HashMap<Long, Account> accounts;
    private HashMap<String, Long> cbuByAlias;
    private HashMap<String, Client> clients;
    
    private BankingSystem() {
        accounts = new HashMap<>();
        cbuByAlias = new HashMap<>();
        clients = new HashMap<>();
    }

    public static BankingSystem getInstance() {
        if (instance == null) {
            synchronized (BankingSystem.class) {
                if (instance == null) {
                    instance = new BankingSystem();
                }
            }
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public void addAccount(Account account) throws Exception {
        if (accounts.containsKey(account.getCbu())) throw new AccountWithCBUAlreadyExists();
        if (cbuByAlias.containsKey(account.getAlias())) throw new AccountWithAliasAlreadyExists();
        cbuByAlias.put(account.getAlias(), account.getCbu());
        accounts.put(account.getCbu(), account);
    }

    public Account getAccountByCBU(long cbu) throws ThereIsNoAccountWithThatCBU {
        if (!accounts.containsKey(cbu)) throw new ThereIsNoAccountWithThatCBU();
        return accounts.get(cbu);
    }

    public Account getAccountByAlias(String alias) throws ThereIsNoAccountWithThatAlias {
        if (!cbuByAlias.containsKey(alias)) throw new ThereIsNoAccountWithThatAlias();
        return accounts.get(cbuByAlias.get(alias));
    }

    public void registerClient(String dni, String lastName, String firstName, LocalDate birtDate, Address address) throws Exception {
        if (clients.containsKey(dni)) throw new ClientAlreadyExists();
        clients.put(dni, new Client(dni, lastName, firstName, birtDate, address));
    }
}
