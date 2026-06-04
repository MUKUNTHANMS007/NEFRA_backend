# NEFRA Connections Platform (Backend)

NEFRA Connections Platform is a robust, feature-rich backend application designed for a professional networking and community platform (similar to LinkedIn). Built with **Java 21** and **Spring Boot**, it provides RESTful APIs and real-time WebSocket communication to support a dynamic social networking experience.

## Key Features

*   **User Management & Authentication**: Secure user registration, login, and profile management.
*   **Networking**: Connect with other professionals, manage connection requests, and build your network.
*   **Posts & Feeds**: Create, share, and view posts in a dynamic feed.
*   **Real-time Chat**: Instant messaging powered by WebSockets for seamless communication.
*   **Company Profiles & Jobs**: View company details and manage job listings.
*   **Smart AI Integration**: Powered by Google GenAI (Gemini) for enhanced AI features (e.g., content generation, recommendations).
*   **Notifications**: Real-time updates and notification management.
*   **Advanced Search**: Discover users, companies, and posts across the platform.

## Tech Stack

*   **Language**: Java 21
*   **Framework**: Spring Boot 3.2.2
*   **Database**: MySQL
*   **ORM**: Spring Data JPA / Hibernate
*   **Real-time Communication**: Spring WebSockets
*   **AI SDK**: Google GenAI SDK (`google-genai`)
*   **Build Tool**: Maven
*   **Utilities**: Lombok

## Prerequisites

Before you begin, ensure you have the following installed:
*   [Java Development Kit (JDK) 21](https://jdk.java.net/21/)
*   [Maven](https://maven.apache.org/download.cgi)
*   [MySQL Server](https://dev.mysql.com/downloads/mysql/)

## Setup and Installation

1.  **Clone the repository**
    ```bash
    git clone https://github.com/MUKUNTHANMS007/NEFRA_backend.git
    cd NEFRA_backend
    ```

2.  **Database Configuration**
    *   Create a MySQL database named `nefra_db`.
    *   Update the database credentials in `src/main/resources/application.properties`:
        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/nefra_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
        spring.datasource.username=root
        spring.datasource.password=YOUR_MYSQL_PASSWORD
        ```

3.  **API Keys Configuration**
    *   To enable AI features, you need a Google Gemini API Key.
    *   Add your API key to `src/main/resources/application.properties`:
        ```properties
        gemini.api.key=YOUR_GEMINI_API_KEY
        ```

4.  **Build and Run the Application**
    You can build and run the application using Maven wrapper:
    ```bash
    ./mvnw clean install
    ./mvnw spring-boot:run
    ```
    The server will start on port `8081` (default). You can access the API at `http://localhost:8081`.

## Project Structure

```text
src/main/java/com/mukunthan/nefra_connections
├── config/         # Application configurations (WebSockets, Security, CORS)
├── controller/     # REST API Controllers (Auth, Chat, Posts, Users, etc.)
├── dto/            # Data Transfer Objects for API requests/responses
├── entity/         # JPA Entities representing database tables
├── enums/          # Enumerations used across the application
├── repository/     # Spring Data JPA Repositories for database operations
└── service/        # Business logic layer
```

## API Controllers Overview

*   `AuthController`: Handles user signup, login, and token generation.
*   `UserController` & `ProfileController`: Manages user details, avatars, and profile settings.
*   `ConnectionController`: Manages sending, accepting, and viewing connection requests.
*   `PostController`: Handles creating, updating, and fetching user posts.
*   `ChatController` & `ChatWSController`: Manages chat history and real-time WebSocket messaging.
*   `CompanyController`: Manages company pages and related jobs.
*   `AIController`: Endpoints leveraging Google GenAI for smart features.
*   `NotificationController`: Fetches and manages user notifications.
*   `SearchController`: Global search functionality.
