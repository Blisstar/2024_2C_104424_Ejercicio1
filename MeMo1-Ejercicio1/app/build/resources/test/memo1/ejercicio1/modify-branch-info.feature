Feature: Modify info about a branch
    Scenario: I successfully change the name of a branch office.
        Given a branch management and a branch office 123
        When the management changes the name of the branch office
        Then it will appear in the system with this new name

    Scenario: The location of a branch is changed successfully
        Given a branch management and a branch office 123
        When the management changes the location of the branch office
        Then it will appear in the system with that new location