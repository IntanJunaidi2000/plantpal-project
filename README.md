# PlantPal
Spring API Java Project 
Created by Intan Junaidi

## Project Description
PlantPal is a comprehensive plant care management system that helps users track their plant collection and maintain optimal care schedules. 
The web application solves the common problem of forgetting to water or care for plants by providing:

- User Management: Secure accounts for plant owners.
- Plant Registry: Track multiple plants with details like species, location, pictures, and health.
- Care Logging: Record all care activities (watering, fertilizing, pruning, etc.) with care dates.
- Smart Reminders: Calculate next care dates based on plant needs.

## Entities
### 1. User

Represents a registered user in the system.

| Field               | Type            | Description                                                                          |
| ------------------- | --------------- | ------------------------------------------------------------------------------------ |
| **id**              | `Long`          | Primary key (auto-generated unique identifier)                                       |
| **username**        | `String`        | Unique username for login and identification                                         |
| **email**           | `String`        | User’s email address                                                                 |
| **password**        | `String`        | Hashed password for authentication                                                   |
| **experienceLevel** | `String`        | User’s plant care experience (e.g., *Beginner*, *Intermediate*, *Expert*)			   |
| **createdAt**       | `LocalDateTime` | Account creation timestamp                                                           |
| **plants**          | `List<Plant>`   | One-to-many relationship — the user’s registered plants                              |

### 2. Plant

Represents a plant owned by a user.

| Field                     | Type            | Description                                                        |
| ------------------------- | --------------- | ------------------------------------------------------------------ |
| **id**                    | `Long`          | Primary key                                                        |
| **nickname**              | `String`        | Custom name given by the user                                      |
| **species**               | `String`        | Scientific or common name of the plant                             |
| **location**              | `String`        | Where the plant is placed (e.g., *Living Room*, *Balcony*)         |
| **acquiredDate**          | `LocalDate`     | Date when the plant was acquired                                   |
| **photoUrl**              | `String`        | Optional image link for the plant                                  |
| **healthStatus**          | `String`        | Overall health condition (*Healthy*, *Wilting*, *Needs Attention*) |
| **wateringFrequencyDays** | `int`           | Interval between watering sessions                                 |
| **user**                  | `User`          | Many-to-one relationship — owner of the plant                      |
| **careLogs**              | `List<CareLog>` | One-to-many relationship — history of care activities              |

### 3. CareLog

Logs specific care activities performed on a plant.

| Field           | Type            | Description                                                         |
| --------------- | --------------- | ------------------------------------------------------------------- |
| **id**          | `Long`          | Primary key                                                         |
| **careType**    | `String`        | Type of care performed (*Watering*, *Fertilizing*, *Pruning*, etc.) |
| **careDate**    | `LocalDateTime` | Timestamp when the care activity occurred                           |
| **notes**       | `String`        | Additional details about the care session                           |
| **nextDueDate** | `LocalDateTime` | Automatically calculated reminder for the next care activity        |
| **plant**       | `Plant`         | Many-to-one relationship — the plant this log belongs to            |


## Dummy Data

This project includes a data.sql file that preloads sample data into a sql database for testing and demonstration.

Located in src/main/resources/data.sql

