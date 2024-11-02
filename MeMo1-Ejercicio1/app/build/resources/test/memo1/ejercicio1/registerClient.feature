Feature: Register a client in the Banking System

    Scenario: A customer is successfully registered
        Given client register his data (last name, first name, date of birth and address) with DNI "42657569" in the bank
        Then the client with DNI "42657569" is in the System
    
    Scenario: Unable to register the customer for invalid DNI 
        Given client try to register his data and puts an invalid DNI "123456"
        Then the operation should be denied for invalid DNI
        And the client with DNI "123456" is not in the System

    Scenario: Unable to register the customer by DNI already registered in the bank
        Given client try to register his data and put in a DNI "42657569" already registered in the banking system
        Then the operation shoul be denied due to there is already a client registered with that DNI
        And the client is not replaced in the system