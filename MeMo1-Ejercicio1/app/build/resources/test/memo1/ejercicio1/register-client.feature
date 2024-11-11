Feature: Register a client in the Banking System

    Scenario: A customer is successfully registered
        Given a person with DNI "42657569" and his data (last name, first name, date of birth and address)
        When the person registers as a client at the bank
        Then the client with DNI "42657569" is in the System
    
    Scenario: Unable to register the customer for invalid DNI
        Given a person with DNI "42657569" and his data (last name, first name, date of birth and address)
        When the person try to registers as a client and puts an invalid DNI "123456"
        Then the operation should be denied for invalid DNI
        And the client with DNI "123456" is not in the System

    Scenario: Unable to register the customer by DNI already registered in the bank
        Given a client with DNI "42657569" and a person
        When the person try to registers as a client and puts in a DNI "42657569" already registered in the banking system
        Then the operation shoul be denied due to there is already a client registered with that DNI
        And the client is not replaced in the system