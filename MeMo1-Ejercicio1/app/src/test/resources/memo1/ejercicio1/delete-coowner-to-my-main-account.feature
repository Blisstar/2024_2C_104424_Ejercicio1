Feature: Remove co-owners to my main account

    Scenario: Successfully remove co-owners
        Given a bank account, its owner client with DNI "42657569" and a coowner client with DNI "12345678" 
        When the owner removes the coowner client of his account as a co-owner by DNI
        Then the coowner client hasn't access to the account

    Scenario: Removing co-owner fails by giving ID of a customer who is not a co-owner
        Given a bank account, its owner client with DNI "12345678" and a DNI "42657569" that is of a client who is not coowner
        When the client tries to remove a co-owner of his account with that DNI
        Then an error occurs and warns that this client is not a co-owner of the account