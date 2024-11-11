Feature: Cancel a client
    Scenario: A client is terminated successfully
        Given a client A with DNI "42657569" who holds a bank account with balance 0 ARS
        And other client B with DNI "12345678" who has a bank account with client A as co-owner
        When the client leaves the banking system
        Then the client's bank account is terminated 
        And the client dissociates himself from the accounts in which he was co-owner 
        And the client is maintained becomes inoperative
    
    Scenario: The client is unable to unsubscribe due to a bank account with a balance.
        Given a client A with DNI "42657569" who holds a bank account with balance 10 ARS
        When the client tries to deregister from the banking system
        Then an error occurs because he first has to withdraw all his funds from his bank account to be able to unsubscribe