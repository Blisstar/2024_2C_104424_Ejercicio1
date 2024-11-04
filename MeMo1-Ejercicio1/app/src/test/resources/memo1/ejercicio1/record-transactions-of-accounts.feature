Feature: Record bank account transactions
    Scenario: Recording all transactions of all accounts
        Given a bank and all bank accounts
        When the bank records the transactions performed by the bank accounts
        Then each transaction is recorded with a correlative number that identifies it, date and time of realization, type of transaction, amount and the accounts involved