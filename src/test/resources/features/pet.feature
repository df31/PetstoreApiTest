Feature: Everything about Pets Service

#POST /pet
  @add
  Scenario Outline: Add a new pet to the store
    Given a new pet with id <id>, name <name> and status <status>
    When adding the new pet to the store
    Then the pet is added
    Examples:
      | id  | name   | status    |
      | 101 | Seven  | available |
      | 201 | Valdo  | pending   |
      | 301 | Pirate | sold      |

#PUT /pet
  @update
  Scenario Outline: Update an existing pet
    Given an existing pet with id <id> wants to have name to <new_name> and new status to <new_status>
    When updating the pet to store
    Then the pet's information is updated
    Examples:
      | id  | new_name | new_status |
      | 101 | 7        | pending    |
      | 201 | Bano     | sold       |
      | 301 | Carrot   | available  |

#GET /pet/findByStatus
  @search @byStatus
  Scenario Outline: Find a pet by status
    When looking for a pet with status <status>
    Then the pets list are present
    Examples:
      | status    |
      | available |
      | pending   |
      | sold      |
  #    | fail      |

#GET /pet/{petId}
  @search @byId
  Scenario Outline: Find a pet by id
    When looking for a pet with id <id>
    Then the pet's information is present
    Examples:
      | id  |
      | 201 |
      | 101 |
      | 301 |

