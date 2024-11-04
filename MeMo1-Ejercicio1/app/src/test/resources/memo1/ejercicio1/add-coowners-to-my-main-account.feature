Feature: Add co-owners to my main account

    Scenario: Adding co-owners successfully
        Given a bank account, its owner client with DNI "42657569" and another client with DNI "12345678" 
        When the owner adds the other client to his account as a co-owner by DNI
        Then the other client is added as co-owner
        And the coowner has access to the account
        And the coowner can't add other coowners

    Scenario: Adding co-owner fails by giving DNI not registered with the bank
        Given a bank account, its owner client with DNI "12345678" and a DNI "42657569" that is not in the system
        When the client tries to add a co-owner to his account with that DNI
        Then an error occurs and warns that there is no client with that DNI registered in the bank