# Intelligence Query Engine API

A Spring Boot REST API built for Insighta Labs to support advanced demographic data querying with filtering, sorting, pagination, and natural language search capabilities.

---

##  Project Overview

This system manages a dataset of demographic profiles and exposes APIs that allow clients to:

- Filter profiles by multiple conditions
- Sort results dynamically
- Paginate large datasets
- Query data using natural language (rule-based parsing)
- Retrieve structured demographic insights efficiently

##  Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL (or configured DB)
- Maven
- Lombok

##  Data Seeding

On application startup, the database is automatically populated using a `profiles.json` file located in:


src/main/resources/profiles.json


### Seeder Behavior:
- Loads 2026 profile records
- Converts JSON into entity objects
- Saves into database
- Prevents duplicate entries using uniqueness checks (e.g., name constraint)

##  Database Schema

Profiles table structure:

- id (UUID v7)
- name (unique)
- gender (male/female)
- gender_probability (float)
- age (integer)
- age_group (child, teenager, adult, senior)
- country_id (ISO code)
- country_name
- country_probability (float)
- created_at (timestamp UTC)


##  API Endpoints

### 1. Get All Profiles


GET /api/profiles


Supports:
- Filtering
- Sorting
- Pagination

### Query Parameters:

| Parameter | Description |

| gender | male / female |
| age_group | child / teenager / adult / senior |
| country_id | ISO country code |
| min_age | minimum age |
| max_age | maximum age |
| min_gender_probability | filter by confidence |
| min_country_probability | filter by confidence |
| sort_by | age / created_at / gender_probability |
| order | asc / desc |
| page | default 1 |
| limit | default 10, max 50 |

### Example Request:


/api/profiles?gender=male&country_id=NG&min_age=25&sort_by=age&order=desc&page=1&limit=10


### 2. Natural Language Search


GET /api/profiles/search?q=


### Example:


/api/profiles/search?q=young males from nigeria


##  Natural Language Parsing Logic

This project uses **rule-based parsing only (no AI/LLMs)**.

### Supported Rules:

| Input Phrase | Parsed Filters |

| young | age 16–24 |
| males | gender = male |
| females | gender = female |
| above X | min_age = X |
| below X | max_age = X |
| teenagers | age_group = teenager |
| adults | age_group = adult |
| people from X | country_id resolved from country map |

### Example Mappings:

- "young males" → gender=male + age 16–24
- "females above 30" → gender=female + min_age=30
- "people from angola" → country_id=AO
- "adult males from kenya" → gender=male + age_group=adult + country_id=KE

### Limitations:
- Does not support complex sentence structures
- No NLP/AI/ML model used
- Only keyword-based rule parsing
- Limited synonym handling


##  Error Handling

All errors follow this structure:

```json
{
  "status": "error",
  "message": "Error description"
}
Common Errors:
400 → Bad Request (missing parameters)
404 → Not Found
422 → Invalid parameters
500 → Server error

 CORS Configuration

CORS is enabled globally:

Access-Control-Allow-Origin: *

This ensures frontend and external grading systems can access the API.

  Time Configuration
All timestamps are in UTC
Format: ISO 8601

 Project Structure
src/main/java
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── dto
 ├── specification
 ├── parser
 ├── exception
 ├── config
 └── seeder
 
    How to Run Locally
mvn clean install
mvn spring-boot:run

   Deployment
Base URL: https://your-deployed-url.com
GitHub: https://github.com/your-username/repo-name

  Key Features
Advanced filtering system
Dynamic sorting
Pagination support
Rule-based natural language parsing
Global exception handling
Data seeding with duplicate prevention

  Note
No external AI/LLM services used
Fully backend-driven logic
Optimized for grading automation tests


Backend Engineering Assessment – Insighta Labs