package memo1.ejercicio1;

import java.util.ArrayList;

public class AccountOfficer {
    private Branch assignedBranch;

    public AccountOfficer(int branchNumber) throws NonexistentBranch {
        assignedBranch = BankingSystem.getInstance().getBranch(branchNumber);
    }

    public void createAndRegisterAccount(String alias, String ownerClientDNI, ArrayList<String> coownerClientsDNI) throws Exception {       
        Client ownerClient = BankingSystem.getInstance().getClient(ownerClientDNI);
        if(ownerClient.doItHaveMainAccount()) throw new ClientAlreadyHasAnAcount();
        
        Account account = new Account(BankingSystem.getInstance().generateNextCBU(), alias, ownerClient, assignedBranch);
        BankingSystem.getInstance().addAccount(account);

        ownerClient.setMainAccount(account);
        if (coownerClientsDNI != null){
            for (String dni : coownerClientsDNI) {
                account.addCoowner(ownerClient, dni);
            }
        }
    }

    public void cancelAccount(Long cbu) throws ThereIsNoAccountWithThatCBU, AccountStillHasFunds {
        BankingSystem.getInstance().getAccountByCBU(cbu).cancel();
    }

    public void registerAccount(Long cbu) throws ThereIsNoAccountWithThatCBU {
        BankingSystem.getInstance().getAccountByCBU(cbu).register(assignedBranch);
    }
    
}
