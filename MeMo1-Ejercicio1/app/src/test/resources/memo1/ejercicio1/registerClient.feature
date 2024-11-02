Feature: Register a client in the Banking System
    Scenario: A customer is successfully registered
        Given client register his data (dni, last name, first name, date of birth and addres) in the bank
        Then the client is in the System
    
    Scenario: Unable to register the customer for invalid DNI 
        Given client try to register his data and puts an invalid DNI 
        Then the operation should be denied for invalid dni
        And the client is not in the System

    Scenario: Unable to register the customer by DNI already registered in the bank
        Given client try to register his data and put in a DNI already registered in the banking system
        Then the operation shoul be denied due to there is already a client registered with that DNI