Feature: Transferring money

  Scenario: Successfully transfer money from a account to other account
    Given A account A with CBU 123456789 and a balance of 1000.0 and other account B with CBU 987654321 and a balance of 300.0
    When account A transfer 200.0 to account B
    Then The account A balance should be 800.0
    And The account B balance should be 500.0

  Scenario: Cannot tranfer more money than available balance
    Given A account A with CBU 123456789 and a balance of 250.0 and other account B with CBU 987654321 and a balance of 400.0
    When account A try to transfer 300.0 to account B
    Then The operation should be denied due to lack of funds in the account A

  Scenario: Cannot tranfer a negative amount
    Given A account A with CBU 123456789 and a balance of 1900.0 and other account B with CBU 987654321 and a balance of 1100.0
    When account A try to transfer -500 to account B
    Then The operation should be denied
    And The account A balance should be 1900.0
    And The account B balance should be 1100.0