Feature: Transferring money

  Scenario: Successfully transfer money from a account to other account using CBU
    Given An account A with CBU 123456789 and a balance of 1000.0 and other account B with CBU 987654321 and a balance of 300.0
    When account A transfer 200.0 to account B using CBU
    Then The account A balance should be 800.0
    And The account B balance should be 500.0
    And the transfer transaction is recorded in the bank

  Scenario: Cannot transfer more money than available balance
    Given An account A with CBU 123456789 and a balance of 250.0 and other account B with CBU 987654321 and a balance of 400.0
    When account A try to transfer 300.0 to account B using CBU
    Then The operation should be denied due to lack of funds in the account A

  Scenario: Cannot transfer a negative amount
    Given An account A with CBU 123456789 and a balance of 1900.0 and other account B with CBU 987654321 and a balance of 1100.0
    When account A try to transfer -500 to account B using CBU
    Then The operation should be denied for negative amount
    And The account A balance should be 1900.0
    And The account B balance should be 1100.0

  Scenario: Failed transfer due to nonexistent CBU
    Given An account A with CBU 123456789 and a balance of 400.0, an account B with CBU 987654321 and a balance of 1000.0 and a nonexistent CBU
    When account A try to transfer 300.0 to account B but enter a nonexistent CBU
    Then The operation should be denied for entering a non-existent CBU
    And The account A balance should be 400.0
    And The account B balance should be 1000.0

  Scenario: Successfully transfer money from a account to other account using Alias
    Given An account A with alias "uno.dos.tres" and a balance of 600.0 and other account B with alias "tres.dos.uno" and a balance of 800.0
    When account A transfer 350.0 to account B using alias
    Then The account A balance should be 250.0
    And The account B balance should be 1150.0
    And the transfer transaction is recorded in the bank

  Scenario: Failed transfer due to nonexistent Alias
    Given An account A with alias "uno.dos.tres" and a balance of 960.0, an account B with alias "tres.dos.uno" and a balance of 1230.0 and a nonexistent Alias
    When account A try to transfer 300.0 to account B but enter a nonexistent Alias
    Then The operation should be denied for entering a non-existent Alias
    And The account A balance should be 960.0
    And The account B balance should be 1230.0

  Scenario: Sender account cannot transfer if it isn't registered
    Given an unregistered account A with CBU 987654321 and other account B with CBU 123456789
    When account A transfer 700.0 to account B
    Then The operation should be denied for pending account to be registered
    And The account B balance should be 0.0

  Scenario: Receiver account cannot receive a transferring if it isn't registered
    Given an account A with CBU 987654321 and a balance of 900.0 and an unregistered account B with CBU 123456789
    When account A transfer 150.0 to account B
    Then The operation should be denied for pending account to be registered
    And The account A balance should be 900.0
    And The account B balance should be 0.0

  Scenario: Failed transfer by same source/destination account
    Given An account with CBU 123456789 and a balance of 1000.0
    When client tries to transfer 500.0 ARS to his same account
    Then The operation should be denied for entering the same origin/destination account
    And The account balance should be 1000.0