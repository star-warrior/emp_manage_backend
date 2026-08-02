# Progress Report

## Implemented

### Project Setup
- Spring Boot 4.1.0 / Java 26 project initialized
- Dependencies: Web, Security, Data JPA, PostgreSQL, Validation, Mail, JWT, Lombok, Swagger, DevTools, Testing

### DB Entities (7)
- **User** — id, name, email, password, active, timestamps; `@ManyToOne` Department, `@ManyToOne` Role, `@ManyToMany` Projects
- **Role** — id, role_name (enum: EMPLOYEE, MANAGER, ADMIN)
- **Department** — id, department_name
- **Projects** — id, name, description, status, timestamps
- **AuditLog** — id, actor (acting user), department, project, role, action (enum), description, createdAt
- **PasswordResetToken** — id, token (UUID), expiresAt, used, createdAt

### Repository
- **UserRepository** — `findByEmail`, `findByActive`
- **RoleRepository** — `findByRoleName`
- **DepartmentRepository** — `findByDepartmentName`
- **ProjectsRepository** — `findByName`, `findByStatus`
- **AuditLogRepository** — `findByActorId`, `findByDepartmentId`, `findByProjectId`, `findByRoleId`, `findByAction`, `findByCreatedAtBetween`
- **PasswordResetTokenRepository** — `findByToken`, `findByEmployeeIdAndUsedFalse`, `deleteByEmployeeId`

### Services Layer (Interfaces + Implementations)
- **RoleService** — `addRole`, `removeRole`, `getRoleById`, `getRoleByName`, `getAllRoles`
- **DepartmentService** — `addDepartment`, `updateDepartment`, `getDepartmentById`, `getAllDepartments`, `removeDepartmentById`
- **UserService** — `createUser`, `updateUser`, `removeUser`, `getUserById`, `getAllUsers`, `getUsersByDepartment`, `getUsersByRole`, `getUsersByProject`
- **ProjectService** — `createProject`, `updateProject`, `removeProject`, `getProjectById`, `getAllProjects`, `getProjectsByStatus`
- **AuditLogService** — `record`, `getAuditLogById`, `getAllAuditLogs`, `getAuditLogsByActor`, `getAuditLogsByDepartment`, `getAuditLogsByProject`, `getAuditLogsByRole`, `getAuditLogsByAction`, `getAuditLogsByDateRange`
- **EmailService** — `sendMail(to, subject, htmlBody)` — async HTML email sending
- **PasswordResetService** — `requestPasswordReset`, `resetPassword` — token-based password reset flow

### DTOs (`dto/`)
- **RoleDto** — `CreateRequest`, `Response`
- **DepartmentDto** — `CreateRequest`, `UpdateRequest`, `Response` (with validation)
- **UserDto** — `CreateRequest`, `UpdateRequest`, `Response` (with validation)
- **ProjectDto** — `CreateRequest`, `UpdateRequest`, `Response` (with validation)
- **AuditLogDto** — `Response` (with validation)
- **AuthDto** — `LoginRequest`, `AuthResponse`
- **PasswordResetDto** — `ForgotPasswordRequest`, `ResetPasswordRequest` (with validation)

### Controllers (`controller/`)
- **AuthenticationController** — `POST /v1/auth/login` returns JWT; `POST /v1/auth/forgot-password`; `POST /v1/auth/reset-password`; `@Tag`, `@Operation`, logging
- **UserController** — CRUD + filter by department/role/project; `@Tag`, `@Operation`, logging
- **ProjectController** — CRUD + filter by status; `@Tag`, `@Operation`, logging
- **DepartmentController** — CRUD (including `updateDepartment`); `@Tag`, `@Operation`, logging
- **AuditLogController** — query by id/all/actor/department/project/role/action/date-range; `@Tag`, `@Operation`, logging
- **RoleController** — `getAllRoles`; `@Tag`, `@Operation`, logging

### Documentation
- **Swagger UI** — available at `/swagger-ui` (springdoc OpenAPI 3.0)
- **`docs/api-specs.md`** — full API reference for all 5 modules
- **`docs/postman_collection.json`** — Postman collection v2.1 with all endpoints (Auth, User, Project, AuditLog, Department, Role)

### Quality
- Validation annotations (`@NotBlank`, `@Email`, `@Size`, `@NotNull`, `@NotEmpty`) on all DTO `CreateRequest`/`UpdateRequest` records
- SLF4J logging on all controller endpoints and JWT authentication filter
- Interface-based DI (programming to interfaces, not implementations)
- `@NullMarked` applied to all controllers
- `@Transactional(readOnly = true)` on all service read methods — prevents `LazyInitializationException`
- Defensive null-safety in `UserPrincipal` and `UserServiceImpl.toResponse()`

### Security (JWT Authentication + RBAC)
- **CustomUserDetailsService** — loads users by email from DB, returns `UserPrincipal`
- **UserPrincipal** — `UserDetails` implementation with `ROLE_` authority derivation; exposes `getId()` and `getDepartmentId()` for SpEL self-restriction checks
- **PasswordConfig** — `BCryptPasswordEncoder` (strength 12)
- **JwtService** — generate/validate JWT tokens via jjwt 0.12.6
- **JwtAuthenticationFilter** — `OncePerRequestFilter` extracts `Bearer` token, validates, sets `SecurityContext`
- **SecurityConfig** — `SecurityFilterChain` (CSRF disabled, stateless sessions, permit `/v1/auth/**` + Swagger, all else authenticated), `AuthenticationManager` bean, `DaoAuthenticationProvider`, `@EnableMethodSecurity`
- **Role-Based Access Control** via `@PreAuthorize` on all controller endpoints:

  | Controller | ADMIN | MANAGER | EMPLOYEE |
  |---|---|---|---|
  | **User** | CRUD + list all | CRUD + same-dept users | Self only (by id) |
  | **Role** | getAllRoles | — | — |
  | **Department** | CRUD | Read | Read own only |
  | **Project** | CRUD | Update | Read (assigned only) |
  | **AuditLog** | CRUD | Read | — |

  Self-restriction expressed via SpEL: `#id == authentication.principal.id` (User), `#id == authentication.principal.departmentId` (Department), `#id in authentication.principal.user.projects.![id]` (Project).

### Exception Handling
- **GlobalExceptionHandler** (`@RestControllerAdvice`) — consistent JSON error responses for all mapped exceptions
- **Custom Exceptions** — `DuplicateResourceException`, `ResourceNotFoundException`, `InvalidResetTokenException`, `JwtAuthenticationException`
- **ErrorResponse DTO** — `msg`, `status`, `timestamp` fields
- **Handlers for**: `IllegalArgumentException` (400), `InvalidResetTokenException` (400), `ResourceNotFoundException` (404), `DuplicateResourceException` (409), `MethodArgumentNotValidException` (400 with field errors), `MethodArgumentTypeMismatchException` (400), `HttpMessageNotReadableException` (400), `HttpRequestMethodNotSupportedException` (405), `AccessDeniedException` (403), `BadCredentialsException` (401), and catch-all `Exception` (500)
- **RestAuthenticationEntryPoint** — JSON `401` response for Spring Security filter-level auth failures; reuses `authException.getMessage()` from thrown exceptions
- **JwtAuthenticationFilter** — invalid/expired tokens now throw `JwtAuthenticationException` → routed through `RestAuthenticationEntryPoint` instead of inline response writing
- **DepartmentServiceImpl** — duplicate name on update now throws `DuplicateResourceException` (was `IllegalArgumentException`)

### Password Reset Flow
- **`POST /v1/auth/forgot-password`** — generates a 15-minute UUID reset token, invalidates any prior tokens, and emails the token asynchronously (`@Async` via `@EnableAsync`); always returns `204` even for unknown emails to prevent user enumeration
- **`POST /v1/auth/reset-password`** — validates the token (exists, unused, unexpired), BCrypt-encodes the new password, marks the token used, and purges remaining tokens; invalid/expired/used tokens throw `InvalidResetTokenException` (400)
- **`EmailService`/`EmailServiceImpl`** — `sendMail(to, subject, htmlBody)` builds a `MimeMessage` and sends async; SMTP failures are caught and logged so the request still succeeds
- **`EmailService`** interface refactored away from returning `ResponseEntity` — clean `void` abstraction

### Automatic Audit Logging
- **`enums/AuditAction`** — `CREATE_USER, UPDATE_USER, DELETE_USER, CREATE_DEPARTMENT, UPDATE_DEPARTMENT, DELETE_DEPARTMENT, CREATE_PROJECT, UPDATE_PROJECT, DELETE_PROJECT, CREATE_ROLE, DELETE_ROLE, LOGIN, PASSWORD_RESET`
- **`AuditLog.record(action, actor, department, project, role, description)`** — system-generated audit entries; called inside every `@Transactional` write path so the log commits/rolls back with the operation
- **Actor resolution** — `SecurityUtils.currentUser()` reads the authenticated `UserPrincipal` from `SecurityContextHolder` (no controller/param changes)
- **Action targets are optional** — only the FK(s) relevant to the action are set; the acting user (`actor_id`) is always recorded
- **Wired into:** `UserServiceImpl` (create/update/delete user), `DepartmentServiceImpl` (add/update/remove), `ProjectServiceImpl` (create/update/remove), `RoleServiceImpl` (add/remove), `AuthenticationController.login` (`LOGIN`), `PasswordResetServiceImpl.resetPassword` (`PASSWORD_RESET`, actor = target user — no auth context needed)
- **Schema** — `employee_id` renamed to `actor_id`, `role_id` added; department/project/role now nullable; `action` stored as enum string
- **API** — public `POST /v1/audit-log` removed (logs are no longer manually creatable); read endpoints split into `GET /v1/audit-log/actor/{id}`, `/department/{id}`, `/project/{id}`, `/role/{id}`, `?action=`, and date-range

### Other
- **StatusConverter** — `String` → `Boolean` for project status query param
- **`UserRepository`** — added `findByDepartmentId`, `findByRoleId`, `findByProjects_Id`

## Not Yet Implemented
- Docker configuration
