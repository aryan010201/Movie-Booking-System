# 🎬 Movie Booking System

A backend REST API for a movie ticket booking platform, built with **Spring Boot** and **Java 21**. It supports managing movies, theatres, screens, and shows, along with seat-level booking, JWT-based authentication, and role-based access control (Admin, Theatre Manager, User).

## Features

- **Authentication & Authorization** — JWT-based signup/signin/signout with role-based access (`ROLE_ADMIN`, `ROLE_THEATRE_MANAGER`, `ROLE_USER`)
- **Movie Management** — Add, update, delete, search movies, and upload movie poster images
- **Theatre Management** — Create and manage theatres by name, city, and state
- **Screen Management** — Add and manage screens within a theatre
- **Show Management** — Schedule shows for a movie on a specific screen, with seat layouts
- **Seat-Level Booking** — View seat availability per show and book specific seats
- **Booking History** — Retrieve a user's past bookings
- **Global Exception Handling** — Centralized API error responses

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0.6 (Web MVC, Data JPA, Security, Validation) |
| Database | H2 (in-memory) |
| Auth | JWT (`jjwt`), Spring Security, BCrypt |
| Object Mapping | ModelMapper |
| Build Tool | Maven |
| Boilerplate Reduction | Lombok |

## Project Structure

```
src/main/java/com/moviebooking/project
├── config/            # App configuration & constants
├── controller/         # REST controllers (Auth, Movie, Theatre, Screen, Show, Booking)
├── exception/          # Custom exceptions & global exception handler
├── model/               # JPA entities (Movie, Theatre, Screen, Show, Seat, Booking, User, Role, ...)
├── Payload/DTOs/        # Data transfer objects
├── Payload/Response/    # Paginated/list API responses
├── repository/          # Spring Data JPA repositories
├── request/              # Request payload models
├── security/             # Spring Security config, JWT utils/filters, auth services
├── services/              # Business logic layer
└── utils/                 # Utility helpers
```

## API Overview

Base path: `/api`

### Auth (`/api/auth`)
| Method | Endpoint | Description |
|---|---|---|
| POST | `/signup` | Register a new user |
| POST | `/signin` | Log in and receive a JWT |
| POST | `/signout` | Log out |
| GET | `/user` | Get current authenticated user |
| POST | `/promoteUser` | Promote a user's role (admin) |

### Movies
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/movies` | Add a movie |
| GET | `/api/public/movies` | List all movies |
| GET | `/api/public/movies/{movieId}` | Get movie details |
| GET | `/api/public/movies/search` | Search movies |
| PUT | `/api/admin/movies/{movieId}` | Update a movie |
| DELETE | `/api/admin/movies/{movieId}` | Delete a movie |
| PUT | `/api/admin/movies/{movieId}/image` | Upload/update movie poster |

### Theatres
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/theatres` | Add a theatre |
| GET | `/api/public/theatres` | List all theatres |
| GET | `/api/public/theatres/{theatreId}` | Get theatre by ID |
| GET | `/api/public/theatres/name/{theatreName}` | Get theatre by name |
| GET | `/api/public/theatres/city/{city}` | List theatres by city |
| GET | `/api/public/theatres/state/{state}` | List theatres by state |
| PUT | `/api/theatres/{theatreId}` | Update a theatre |
| DELETE | `/api/theatres/{theatreId}` | Delete a theatre |

### Screens
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/theatres/{theatreId}/screens` | Add a screen to a theatre |
| GET | `/api/public/screens/` | List all screens |
| GET | `/api/public/theatres/{theatreId}/screens` | List screens by theatre |
| GET | `/api/public/screens/{screenId}` | Get screen by ID |
| PUT | `/api/screens/{screenId}` | Update a screen |
| DELETE | `/api/screens/{screenId}` | Delete a screen |

### Shows
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/screens/{screenId}/movies/{movieId}/shows/` | Schedule a show |
| GET | `/api/public/theatres/{theatreId}/shows/` | List shows by theatre |
| GET | `/api/public/movies/{movieId}/shows/` | List shows by movie |
| GET | `/api/public/shows/{showId}` | Get show details |
| GET | `/api/public/shows/{showId}/seats` | Get seat layout/availability for a show |
| PUT | `/api/screens/{screenId}/shows/{showId}` | Update a show |
| DELETE | `/api/shows/{showId}` | Delete a show |

### Bookings
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/bookings` | Create a booking |
| GET | `/api/bookings/history` | Get booking history for the current user |
| POST | `/api/bookings/{bookingId}` | Update/confirm a booking |

## Getting Started

### Prerequisites
- Java 21+
- Maven (or use the included Maven Wrapper)

### Run the Application

```bash
# Clone the repository
git clone https://github.com/aryan010201/Movie-Booking-System.git
cd Movie-Booking-System

# Run with Maven Wrapper
./mvnw spring-boot:run     # macOS/Linux
mvnw.cmd spring-boot:run   # Windows
```

The application starts on `http://localhost:8080` by default.

### Database

The project uses an **in-memory H2 database** by default (no external setup required). The H2 console is enabled and available at:

```
http://localhost:8080/h2-console
```

JDBC URL: `jdbc:h2:mem:test`

### Configuration

Key settings live in `src/main/resources/application.properties`, including the JWT secret, JWT expiration time, and the auth cookie name. Update these values (especially the JWT secret) before deploying to production.

## Running Tests

```bash
./mvnw test
```

## License

No license has been specified for this repository. Consider adding one (e.g., MIT, Apache 2.0) if you intend for others to use or contribute to this project.
