Feature: Register an account in the Banking System

    Scenario: An account is successfully registered
        Given an account officer with his branch, an client holder DNI "42657569" and two co-owners DNIs "78945612" , "1234567"
        When the account officer registers an account for them
        Then The account officer automatically registers the account with his branch 
        And enables its use for the account holder and co-owners

    Scenario: An account is not registered for a customer who already has an account
        Given an account officer with his branch office and an client DNI "42657569" who already has an account
        When the account officer registers an new account for the client
        Then the operation should be denied for trying to create an account to an client who already has an account

    Scenario: A cancelled account is registered successfully
        Given an account officer with his branch and an client DNI "42657569" who has a cancelled account
        When the account officer registers the cancelled account
        Then The account officer automatically registers the account with his branch 
        And enables its use for the account holder and co-owners