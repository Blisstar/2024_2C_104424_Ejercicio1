Feature: Register a branch in the Banking System
    Scenario: Branch is successfully registered
        Given branch manager and a new branch office with number 312
        When the branch office is registered by the branch manager
        Then the branch is operational in the system with its respective location, assigned a number and a name.

    Scenario: The branch is not registered due to an existing number branch.
        Given the branch manager and a branch office with number 312 that is in the system
        When the branch is registered by the branch manager with a number already owned by another branch.
        Then the operation is denied due to the branch with that number already exists
        And the branch is not replaced in the system