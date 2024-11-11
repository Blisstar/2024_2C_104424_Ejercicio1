Feature: Bank want to know if a client is married

    Scenario: It is registered in the bank if a client is married
        Given a client A with DNI "42657569" and date of birth "2000-06-05" 
        And other client B with DNI "12345678" and date of birth "2000-06-17" 
        When the system registers two clients are spouses with marriage date "2022-02-02"
        Then the two clients become spouses with a marriage date

    Scenario: A client cannot be set as married if the marriage date is invalid.
        Given a client A with DNI "12345678" and date of birth "2000-06-17"
        And other client B with DNI "42657569" and date of birth "2000-06-05" 
        When the system registers two clients are spouses with marriage date "1999-01-01"
        Then the operation is denied due to marriage date is before date of birth
        And they are not registered as spouses