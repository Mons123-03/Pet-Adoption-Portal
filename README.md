# 🐾 Pet Adoption Portal — Spring Boot + JPA + MySQL

The same Pet Adoption Portal, rebuilt on **Spring Boot** instead of raw Servlets. Backend is Java with Spring Boot (Spring Web + Spring Data JPA), the database is MySQL, and the frontend is the same HTML, CSS, Bootstrap, and vanilla JavaScript as before — dark theme, responsive layout, pet/human illustration, and information-rich dashboards.

## Tech Stack
| Layer | Technology |
|---|---|
| Frontend | HTML5, CSS3, Bootstrap 5 (dark theme), vanilla JavaScript (fetch API) |
| Backend | Java 17, Spring Boot 3 (Spring Web, Spring Data JPA) |
| Database | MySQL |
| ORM / Connectivity | Spring Data JPA (Hibernate) over JDBC — no manual SQL needed |
| Build tool | Maven |

## Why Spring Boot instead of Servlets
- **No `web.xml`, no manual servlet mapping** — `@RestController` + `@GetMapping`/`@PostMapping` annotations wire up routes automatically.
- **No manual JDBC/`PreparedStatement` code** — Spring Data JPA repositories (`JpaRepository`) generate the SQL for you from method names like `findByEmail(...)`.
- **No manual JSON building** — Jackson (bundled with Spring Boot) automatically converts Java objects to JSON. `application.properties` is configured with `spring.jackson.property-naming-strategy=SNAKE_CASE` so `petName` in Java becomes `"pet_name"` in the JSON the frontend expects.
- **Embedded server** — `mvn spring-boot:run` starts everything on `http://localhost:8080`, no separate Tomcat install or WAR deployment required.

## Project Structure
```
PetAdoptionPortalSpring/
├── pom.xml                                    # Maven dependencies (Spring Boot, JPA, MySQL driver)
├── database/schema.sql                        # Reference only — JPA auto-creates tables
├── uploads/                                    # Uploaded pet photos are saved here at runtime
└── src/main/
    ├── java/com/petadoption/
    │   ├── PetAdoptionPortalApplication.java   # Main class + seeds the default admin account
    │   ├── entity/                             # JPA entities: User, Pet, AdoptionRequest
    │   ├── repository/                         # Spring Data JPA repositories (no SQL to write)
    │   ├── dto/                                # Clean JSON shapes returned to the frontend
    │   ├── service/                            # Business logic (UserService, PetService, AdoptionRequestService, FileStorageService)
    │   ├── controller/                         # REST controllers (Auth, Pet, AdoptionRequest, Admin)
    │   ├── config/                             # WebConfig (serves /uploads/**), GlobalExceptionHandler
    │   └── util/SessionUtil.java                # Small helper for session/role checks
    └── resources/
        ├── application.properties              # MySQL connection, Jackson config, upload settings
        └── static/                             # Frontend — served directly by Spring Boot
            ├── index.html, login.html, register.html, my-requests.html,
            │   owner-dashboard.html, owner-requests.html, admin-dashboard.html
            ├── css/style.css                    # Dark theme
            └── js/common.js                     # Shared fetch helpers, icons, hero illustration
```

## Modules Implemented (same as the original brief)
1. **User Module** — Register, log in, browse/search pets, submit adoption requests, track request status.
2. **Pet Owner Module** — Add/edit/delete pet listings, upload pet photos, view & approve/reject requests received.
3. **Admin Module** — Manage users, manage all pet listings, approve/reject any request, view a live reports dashboard.

## Database Tables (auto-created by JPA)
- `users` — role column distinguishes ADOPTER / OWNER / ADMIN
- `pets` — linked to owner, status: AVAILABLE / PENDING / ADOPTED
- `adoption_requests` — links an adopter to a pet, status: PENDING / APPROVED / REJECTED

You don't need to run `database/schema.sql` manually — `spring.jpa.hibernate.ddl-auto=update` in `application.properties` creates/updates these tables automatically from the `@Entity` classes the first time you run the app. The SQL file is included only as a readable reference.

---

## Setup Instructions

### 1. Install prerequisites
- **JDK 17** or above (Spring Boot 3 requires it) — https://adoptium.net/
- **Maven** (or use your IDE's bundled Maven) — https://maven.apache.org/download.cgi
- **MySQL Server 8** — https://dev.mysql.com/downloads/mysql/

### 2. Create the database
You just need the empty database to exist — Spring Data JPA creates the tables:
```sql
CREATE DATABASE pet_adoption_portal;
```
(Or just let it happen automatically — `createDatabaseIfNotExist=true` is already set in the JDBC URL in `application.properties`.)

### 3. Configure your MySQL credentials
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pet_adoption_portal?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_mysql_password
```

### 4. Import into your IDE
**IntelliJ IDEA / Eclipse / VS Code (with Java extensions) / Spring Tool Suite:**
- File → Open → select the `PetAdoptionPortalSpring` folder.
- Your IDE should recognize `pom.xml` and automatically download all Spring Boot dependencies from Maven Central (needs an internet connection the first time).

### 5. Run the application
**From your IDE:** right-click `PetAdoptionPortalApplication.java` → Run.

**From the command line:**
```bash
cd PetAdoptionPortalSpring
mvn spring-boot:run
```

On first startup you'll see:
```
Default admin created: admin@petadoption.com / admin123
```

### 6. Open the app
```
http://localhost:8080/
```
That's it — no Tomcat setup, no WAR file, no `WEB-INF/lib` jar copying. Spring Boot's embedded server handles everything.

---

## How to Use
1. **Browse pets** on the home page — search and filter by category, keyword, and age.
2. **Sign up** as either:
   - **Adopter** — submit adoption requests and track them on "My Requests".
   - **Pet Owner** — list pets (with photo upload) and manage incoming requests on "Requests Received".
3. **Log in as Admin** (seeded account above) for the full control center: manage users, pet listings, requests, and a live stats dashboard.

## Design Notes
- **Passwords are stored in plain text**, matching the "keep it simple" brief (no Spring Security / password hashing library added). This is clearly commented in `User.java` and `UserService.java`. For a production system, add Spring Security with `BCryptPasswordEncoder`.
- **Session-based login** via plain `HttpSession` (Spring MVC controllers just accept it as a method parameter) — no Spring Security, no JWT tokens.
- **DTOs** (`UserDTO`, `PetDTO`, `AdoptionRequestDTO`) are used instead of returning JPA entities directly from controllers — this avoids lazy-loading serialization errors and keeps the exact JSON shape the frontend expects.
- **File uploads** use Spring's built-in `MultipartFile` — no extra library needed.
- **CommandLineRunner** in the main application class seeds the default admin account automatically — no manual SQL insert required.

## Future Enhancements (from the original brief)
- Online payment for adoption fees
- Email/SMS notifications
- Location-based (GPS) pet search
- In-app chat between owners and adopters
- AI-based pet recommendation system
- Mobile app version
- Spring Security with hashed passwords, for a production-ready version
