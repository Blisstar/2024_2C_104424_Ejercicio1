Feature: Depositing money
 
  Scenario: Successfully deposit money into an account
    Given An account with CBU 123456789 and a balance of 1000.0
    When I deposit 200.0 into the account
    Then The account balance should be 1200.0

  Scenario: Cannot deposit a negative amount
    Given An account with CBU 123456789 and a balance of 1000.0
    When I try to deposit -100.0 into the account
    Then The operation should be denied for negative amount
    And The account balance should remain 1000.0

  