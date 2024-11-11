Feature: Cancel a branch

    Scenario: Branch office is successfully closed down
        Given the branch management and a branch office 195 with its account officers and bank accounts
        When the branch is de-registered by management
        Then the branch is made inoperative for its account officers, and the bank accounts that are located there are cancelled
    
    Scenario: Failure to disenroll because the branch has accounts with balance
        Given the branch management and a branch office 195 with its account officers and bank accounts
        And some of accounts have a balance greater than zero
        When the management tries to deregister the branch office
        Then the operation is denied because there are active accounts with a balance in the branch

    Scenario: Deregistering a branch with a backup branch for its bank accounts
        Given the branch management and two branches 195 and 134 with their account officers and their bank accounts
        When management terminates one of these branches with the other branch as a backup
        Then the branch becomes inoperative and the other branch takes over its bank accounts so that the bank accounts are now located in the backup branch