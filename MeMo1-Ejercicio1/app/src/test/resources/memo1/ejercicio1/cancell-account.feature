Feature: Cancell a bank account

    Scenario 1: A bank account is terminated successfully
        Given an account officer and a client with DNI "42657569" and his bank account
        When the officer terminates an account
        Then no one can operate the bank account