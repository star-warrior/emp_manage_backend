# Progress Report

## Implemented

### Project Setup
- Spring Boot 4.1.0 / Java 26 project initialized
- Dependencies: Web, Security, Data JPA, PostgreSQL, Validation, Mail, JWT, Lombok, Swagger, DevTools, Testing

### DB Entities (7)
- **User** — id, name, email, password, active, timestamps; `@ManyToOne` Department, `@ManyToOne` Role, `@ManyToMany` Projects
- **Role** — id, role_name (enum: Employee, HR, Admin, PM)
- **Department** — id, department_name
- **Projects** — id, name, description, status, timestamps
- **AuditLog** — id, employee, department, project, action, description, createdAt
- **PasswordResetToken** — id, token (UUID), expiresAt, used, createdAt

### Repository
- **UserRepository** — `findByEmail`, `findByActive`
- **RoleRepository** — `findByRoleName`
- **DepartmentRepository** — `findByDepartmentName`
- **ProjectsRepository** — `findByName`, `findByStatus`
- **AuditLogRepository** — `findByEmployeeId`, `findByDepartmentId`, `findByProjectId`, `findByAction`, `findByCreatedAtBetween`
- **PasswordResetTokenRepository** — `findByToken`, `findByEmployeeIdAndUsedFalse`, `deleteByEmployeeId`

### Services Layer (Interfaces + Implementations)
- **RoleService** — `addRole`, `removeRole`, `getRoleById`, `getRoleByName`, `getAllRoles`
- **DepartmentService** — `addDepartment`, `getDepartmentById`, `getAllDepartments`, `removeDepartmentById`
- **UserService** — `createUser`, `updateUser`, `removeUser`, `getUserById`, `getAllUsers`, `getUsersByDepartment`, `getUsersByRole`, `getUsersByProject`
- **ProjectService** — `createProject`, `updateProject`, `removeProject`, `getProjectById`, `getAllProjects`, `getProjectsByStatus`
- **AuditLogService** — `createAuditLog`, `getAuditLogById`, `getAuditLogsByEmployee`, `getAuditLogsByDepartment`, `getAuditLogsByProject`, `getAuditLogsByAction`, `getAuditLogsByDateRange`

### DTOs (`dto/`)
- **RoleDto** — `CreateRequest`, `Response`
- **DepartmentDto** — `CreateRequest`, `Response` (with validation)
- **UserDto** — `CreateRequest`, `UpdateRequest`, `Response` (with validation)
- **ProjectDto** — `CreateRequest`, `UpdateRequest`, `Response` (with validation)
- **AuditLogDto** — `CreateRequest`, `Response` (with validation)

### Controllers (`controller/`)
- **UserController** — CRUD + filter by department/role/project; `@Tag`, `@Operation`, logging
- **ProjectController** — CRUD + filter by status; `@Tag`, `@Operation`, logging
- **DepartmentController** — CRUD; `@Tag`, `@Operation`, logging
- **AuditLogController** — create + query by id/employee/department/project/action/date-range; `@Tag`, `@Operation`, logging
- **RoleController** — `getAllRoles`; `@Tag`, `@Operation`, logging

### Documentation
- **Swagger UI** — available at `/swagger-ui` (springdoc OpenAPI 3.0)
- **`docs/api-specs.md`** — full API reference for all 5 modules
- **`docs/postman_collection.json`** — Postman collection v2.1 with all endpoints (User, Project, AuditLog, Department, Role)

### Quality
- Validation annotations (`@NotBlank`, `@Email`, `@Size`, `@NotNull`, `@NotEmpty`) on all DTO `CreateRequest`/`UpdateRequest` records
- SLF4J logging on all controller endpoints
- Interface-based DI (programming to interfaces, not implementations)
- `@NullMarked` applied to all controllers

### Other
- **StatusConverter** — `String` → `Boolean` for project status query param
- **`UserRepository`** — added `findByDepartmentId`, `findByRoleId`, `findByProjects_Id`

## Not Yet Implemented
- Security (JWT, BCrypt, RBAC)
- Global exception handler (`@ControllerAdvice`)
- Docker configuration
