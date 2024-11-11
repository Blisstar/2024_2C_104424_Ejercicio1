Feature: Cancel a bank account

    Scenario 1: A bank account is terminated successfully
        Given an account officer and a client with DNI "42657569" and his bank account 
        And his balance is 0 ARS
        When the officer terminates an account
        Then no one can operate the bank account

    Scenario 2: No deregistration due to still having balance
        Given an account officer and a client with DNI "42657569" and his bank account 
        And his balance is 1 ARS
        When the officer tries to terminate an account
        Then an error occurs because an account that still has a balance cannot be terminated