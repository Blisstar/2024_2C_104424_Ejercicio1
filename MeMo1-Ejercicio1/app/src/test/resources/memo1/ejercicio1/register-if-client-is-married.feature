Feature: Bank want to know if a client is married

    Scenario: It is registered in the bank if a client is married
        Given a client with DNI "42657569" and date of birth "2000-06-05" and the banking system
        When the system registers that the client is married with marriage date "2022-02-02"
        Then it is stored in the system if the client is married
        And the date of marriage is recorded.

    Scenario: A client cannot be set as married if the marriage date is invalid.
        Given a client with DNI "94464669" and date of birth "2000-06-17" and the banking system
        When the system registers that the client is married with marriage date "1999-01-01"
        Then the operation is denied due to marriage date is before date of birth
        And the client is not set as married.