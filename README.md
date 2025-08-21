# 🎬 RESTful Film Management API

**A comprehensive RESTful web service for film data management with multi-format serialization**

A robust, enterprise-grade REST API built with Java EE that provides complete film management capabilities with support for JSON, XML, and custom string formats. The API features advanced serialization utilities, proper HTTP status handling, and a clean service-oriented architecture.

## 🌟 Key Features

### 🔌 RESTful API Design
- **Complete CRUD Operations** - Create, Read, Update, Delete films via HTTP methods
- **Multiple Content Types** - JSON, XML, and custom string format support
- **Proper HTTP Status Codes** - Semantic response codes for all operations
- **Content Negotiation** - Automatic format detection based on Accept headers
- **Error Handling** - Comprehensive error responses with meaningful messages

### 📊 Multi-Format Serialization
- **JSON Support** - Using Gson for efficient JSON processing
- **XML Support** - JAXB-based XML marshalling/unmarshalling
- **Custom String Format** - Proprietary string serialization for lightweight operations
- **Format Utilities** - Dedicated formatter classes for each data type

### 🏗️ Service-Oriented Architecture
- **FilmService Layer** - Business logic separation from data access
- **DAO Pattern** - Clean data access abstraction
- **Builder Pattern** - Flexible film object construction
- **Utility Classes** - Reusable formatting and response helpers

### 🔍 Advanced Search Capabilities
- **Search by ID** - Direct film retrieval
- **Search by Title** - Partial title matching with LIKE queries
- **List All Films** - Complete dataset retrieval
- **Empty Result Handling** - Proper 404 responses for missing data

## 🛠️ Technology Stack

- **Core Framework:**
  - Java Enterprise Edition (Java EE)
  - Jakarta Servlets API
  - RESTful Web Services

- **Data Serialization:**
  - JAXB (Jakarta XML Binding)
  - Gson (Google JSON)
  - Custom String Formatters

- **Database Layer:**
  - MySQL Database
  - JDBC with PreparedStatements
  - Connection Management

- **Architecture Patterns:**
  - REST API Design
  - Service Layer Pattern
  - Data Access Object (DAO)
  - Builder Pattern

## 🏗️ API Architecture

```
src/
├── controller/
│   └── FilmApiController.java      # REST endpoint controller
├── FilmService/
│   └── FilmService.java           # Business logic layer
├── database/
│   └── FilmDAO.java               # Data access layer
├── model/
│   ├── Film.java                  # Film entity
│   ├── FilmBuilder.java           # Builder pattern
│   └── FilmsList.java             # XML wrapper class
└── utils/
    ├── JsonFormatter.java         # JSON utilities
    ├── XmlFormatter.java          # XML utilities
    ├── StringFormatter.java       # String utilities
    └── ResponseHelper.java        # HTTP response utilities
```

## 📡 API Endpoints

### Base URL
```
http://localhost:8080/api/films
```

### Available Endpoints

| Method | Endpoint | Parameters | Description | Response Codes |
|--------|----------|------------|-------------|----------------|
| `GET` | `/api/films` | None | Get all films | 200, 500 |
| `GET` | `/api/films?id={id}` | `id` (int) | Get film by ID | 200, 404, 400 |
| `GET` | `/api/films?title={title}` | `title` (string) | Search films by title | 200, 404 |
| `POST` | `/api/films` | Film object in body | Create new film | 201, 400 |
| `PUT` | `/api/films` | Film object in body | Update existing film | 200, 400 |
| `DELETE` | `/api/films?id={id}` | `id` (int) | Delete film by ID | 204, 400 |

## 📋 Data Formats

### JSON Format
```json
{
  "id": 1,
  "title": "The Matrix",
  "year": 1999,
  "director": "The Wachowskis",
  "stars": "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss",
  "review": "A groundbreaking sci-fi thriller that redefined cinema"
}
```

### XML Format
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Film>
    <id>1</id>
    <title>The Matrix</title>
    <year>1999</year>
    <director>The Wachowskis</director>
    <stars>Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss</stars>
    <review>A groundbreaking sci-fi thriller that redefined cinema</review>
</Film>
```

### Custom String Format
```
id:1#title:The Matrix#year:1999#director:The Wachowskis#stars:Keanu Reeves, Laurence Fishburne#review:A groundbreaking sci-fi thriller
```

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK) 11+**
- **Apache Tomcat 9.0+** or compatible servlet container
- **MySQL Server 8.0+**
- **Maven** (for dependency management)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/rozaghaedi/Film-REST-API.git
   cd Film-REST-API
   ```

2. **Database Setup**
   ```sql
   CREATE DATABASE filmapi;
   USE filmapi;
   
   CREATE TABLE films (
       id INT AUTO_INCREMENT PRIMARY KEY,
       title VARCHAR(255) NOT NULL,
       year INT NOT NULL,
       director VARCHAR(255) NOT NULL,
       stars TEXT,
       review TEXT
   );
   ```

3. **Configure Database Connection**
   Update `FilmDAO.java`:
   ```java
   private String user = "your_username";
   private String password = "your_password";
   private String url = "jdbc:mysql://localhost:3306/filmapi";
   ```

4. **Deploy to Server**
   ```bash
   mvn clean package
   cp target/film-api.war $TOMCAT_HOME/webapps/
   ```

5. **Start Server**
   ```bash
   $TOMCAT_HOME/bin/startup.sh
   ```

## 📖 Usage Examples

### Retrieve All Films
```bash
curl -X GET "http://localhost:8080/api/films" \
     -H "Accept: application/json"
```

### Get Film by ID
```bash
curl -X GET "http://localhost:8080/api/films?id=1" \
     -H "Accept: application/json"
```

### Search Films by Title
```bash
curl -X GET "http://localhost:8080/api/films?title=matrix" \
     -H "Accept: application/json"
```

### Create New Film
```bash
curl -X POST "http://localhost:8080/api/films" \
     -H "Content-Type: application/json" \
     -d '{
       "title": "Inception",
       "year": 2010,
       "director": "Christopher Nolan",
       "stars": "Leonardo DiCaprio, Marion Cotillard",
       "review": "A mind-bending thriller about dreams within dreams"
     }'
```

### Update Existing Film
```bash
curl -X PUT "http://localhost:8080/api/films" \
     -H "Content-Type: application/json" \
     -d '{
       "id": 1,
       "title": "The Matrix Reloaded",
       "year": 2003,
       "director": "The Wachowskis",
       "stars": "Keanu Reeves, Laurence Fishburne",
       "review": "The sequel to the groundbreaking original"
     }'
```

### Delete Film
```bash
curl -X DELETE "http://localhost:8080/api/films?id=1"
```

## 🔧 Content Type Support

### Request Headers
- `Content-Type: application/json` - For JSON payloads
- `Content-Type: text/xml` - For XML payloads  
- `Content-Type: text/string` - For custom string format

### Response Headers
- `Accept: application/json` - JSON response
- `Accept: text/xml` - XML response
- `Accept: text/string` - Plain text response

## ⚙️ Configuration

### Database Configuration
```java
// FilmDAO.java
private String user = "your_db_user";
private String password = "your_db_password";  
private String url = "jdbc:mysql://localhost:3306/filmapi";
```

### Serialization Settings
- **JSON Pretty Printing** - Enabled via Gson configuration
- **XML Formatting** - JAXB formatted output enabled
- **Custom Delimiters** - Configurable in StringFormatter

## 🛡️ Error Handling

### HTTP Status Codes
- **200 OK** - Successful GET/PUT operations
- **201 Created** - Successful POST operations
- **204 No Content** - Successful DELETE operations
- **400 Bad Request** - Invalid input data or format
- **404 Not Found** - Film not found for given ID/title
- **500 Internal Server Error** - Server-side processing errors

### Error Response Format
```json
{
  "error": "Film not found with ID: 999",
  "status": 404
}
```

## 🧪 Testing

### Manual Testing with cURL
```bash
# Test JSON format
curl -X GET "http://localhost:8080/api/films" \
     -H "Accept: application/json"

# Test XML format  
curl -X GET "http://localhost:8080/api/films" \
     -H "Accept: text/xml"

# Test string format
curl -X GET "http://localhost:8080/api/films" \
     -H "Accept: text/string"
```

### API Testing Tools
- **Postman** - Import collection for comprehensive testing
- **Insomnia** - REST client testing
- **curl** - Command-line testing
- **JUnit** - Unit testing framework integration

## 🔄 Service Layer Architecture

### FilmService Methods
```java
// Core CRUD operations
public Film getFilmById(int id)
public ArrayList<Film> getFilmsByTitle(String title)  
public ArrayList<Film> getAllFilms()
public void insertFilm(Film film)
public void updateFilm(Film film)
public void deleteFilm(int id)

// Serialization methods
public String serializeFilm(Film film, String format)
public String serializeFilms(ArrayList<Film> films, String format)
public Film deserializeFilm(String data, String format)
```


### Development Guidelines:
- Follow RESTful API conventions
- Maintain consistent error handling
- Add comprehensive JavaDoc comments
- Test all serialization formats
- Validate input parameters


## 🚀 Future Enhancements

- **API Versioning** - Support for multiple API versions
- **Authentication** - JWT token-based security
- **Rate Limiting** - Request throttling and quotas
- **OpenAPI Documentation** - Swagger integration
- **GraphQL Support** - Alternative query language
- **Caching** - Redis integration for performance
- **Monitoring** - API analytics and health checks
- **Docker Support** - Containerized deployment


