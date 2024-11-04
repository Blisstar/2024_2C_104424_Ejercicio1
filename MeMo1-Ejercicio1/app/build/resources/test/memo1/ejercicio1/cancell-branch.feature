Feature: Cancell a branch
    Scenario 1: Branch office is successfully closed down
        Given the branch management and a branch office 195 with its account officers and bank accounts
        When the branch is de-registered by management
        The branch is then made inoperative for its account officers, and the bank accounts that are located there are cancelled