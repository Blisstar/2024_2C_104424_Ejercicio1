Feature: Register an account in the Banking System

    Scenario: An accounts is successfully registered
        Given client register his data (last name, first name, date of birth and address) with DNI "42657569" in the bank
        Then the client with DNI "42657569" is in the System