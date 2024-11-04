Feature: Be owner of a bank account

    Scenario: I am a successful account holder
        Given a client with DNI "42657569" and a bank account
        When the client is the owner of the bank account
        Then he becomes the one who has the most control over the bank account
        And the client can add coowners