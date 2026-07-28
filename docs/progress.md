# Progress Report

## Implemented

### Project Setup
- Spring Boot 4.1.0 / Java 26 project initialized
- Dependencies: Web, Security, Data JPA, PostgreSQL, Validation, Mail, JWT, Lombok, Swagger, DevTools, Testing

### DB Entities (7)
- **User** — id, name, email, password, active, timestamps; `@ManyToOne` Department, `@ManyToMany` Role & Projects
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

## Not Yet Implemented
- Services layer
- DTOs / Mappers
- REST Controllers
- Security (JWT, BCrypt, RBAC)
- Validation & Exception Handling
- API Documentation (Swagger)
- Docker configuration
