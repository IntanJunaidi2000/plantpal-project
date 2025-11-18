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

## API Endpoints

### Users
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Plants
- `GET /api/plants` - Get all plants
- `GET /api/plants/{id}` - Get plant by ID
- `POST /api/plants` - Create new plant
- `PUT /api/plants/{id}` - Update plant
- `DELETE /api/plants/{id}` - Delete plant

### Care Logs
- `GET /api/carelogs` - Get all care logs
- `GET /api/carelogs/{id}` - Get care log by ID
- `POST /api/carelogs` - Create new care log
- `PUT /api/carelogs/{id}` - Update care log
- `DELETE /api/carelogs/{id}` - Delete care log

## Database Schema

### User
- `id`, `username`, `email`, `password`
- One-to-Many relationship with Plants

### Plant
- `id`, `nickname`, `species`, `location`
- Many-to-One relationship with User
- One-to-Many relationship with CareLogs

### CareLog
- `id`, `careType`, `careDate`, `notes`
- Many-to-One relationship with Plant

## Getting Started
1. Clone the repository
2. Configure MySQL database in `application.properties`
3. Run `mvn spring-boot:run`
4. API available at `http://localhost:8080/api`

## Test Data
Sample data is automatically loaded from `src/main/resources/data.sql`

---

**Next Phase**: React frontend integration