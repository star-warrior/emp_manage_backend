# API Specification — User Module

**Base URL:** `http://localhost:8080/v1/user`

**Content-Type:** `application/json`

---

## Endpoints

### 1. Create User

```
POST /v1/user
```

Creates a new user with department and role assignments.

**Request Body:**

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securePass123",
  "departmentId": 1,
  "roleId": 1
}
```

**Field Constraints:**

| Field        | Type        | Required | Constraints                        |
|-------------|-------------|----------|------------------------------------|
| name        | string      | Yes      | Not blank                          |
| email       | string      | Yes      | Valid email, not blank             |
| password    | string      | Yes      | Min 8 characters                   |
| departmentId| long        | Yes      | Must reference an existing department |
| roleId      | long        | Yes      | Must reference an existing role |

**Responses:**

| Status | Description |
|--------|-------------|
| `201 Created` | User created successfully |
| `400 Bad Request` | Validation error or duplicate email |
| `404 Not Found` | Department or role not found |

---

### 2. Update User

```
PATCH /v1/user/{id}
```

Partially updates user fields. Only provided fields are changed.

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| id        | long | User ID     |

**Request Body:**

```json
{
  "name": "Jane Doe",
  "email": "jane@example.com"
}
```

**Field Constraints:**

| Field        | Type        | Required | Constraints                        |
|-------------|-------------|----------|------------------------------------|
| name        | string      | No       | Max 100 characters                 |
| email       | string      | No       | Valid email                        |
| password    | string      | No       | Min 8 characters if provided       |
| departmentId| long        | No       | Must reference existing department |
| roleId      | long        | No       | Must reference existing role       |

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | User updated successfully |
| `400 Bad Request` | Validation error or duplicate email |
| `404 Not Found` | User, department, or role not found |

---

### 3. Delete User

```
DELETE /v1/user/{id}
```

Deletes a user by ID.

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| id        | long | User ID     |

**Responses:**

| Status | Description |
|--------|-------------|
| `204 No Content` | User deleted successfully |
| `404 Not Found`  | User not found |

---

### 4. Get User by ID

```
GET /v1/user/{id}
```

Retrieves a single user's details.

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| id        | long | User ID     |

**Response Body:**

```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "active": true,
  "departmentName": "Engineering",
  "roleName": "ADMIN",
  "createdAt": "2026-07-29T12:00:00"
}
```

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | User found |
| `404 Not Found` | User not found |

---

### 5. Get All Users

```
GET /v1/user/all
```

Retrieves all registered users.

**Response Body:**

```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "active": true,
    "departmentName": "Engineering",
    "roleName": "ADMIN",
    "createdAt": "2026-07-29T12:00:00"
  }
]
```

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | List of users retrieved |

---

### 6. Find Users by Department

```
GET /v1/user/department/{id}
```

Retrieves all users belonging to the specified department.

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| id        | long | Department ID |

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | Users retrieved successfully |
| `404 Not Found` | Department not found |

---

### 7. Find Users by Role

```
GET /v1/user/role/{id}
```

Retrieves all users assigned to the specified role.

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| id        | long | Role ID |

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | Users retrieved successfully |
| `404 Not Found` | Role not found |

---

### 8. Find Users by Project

```
GET /v1/user/project/{id}
```

Retrieves all users assigned to the specified project.

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| id        | long | Project ID |

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | Users retrieved successfully |
| `404 Not Found` | Project not found |

---

# Project Module

**Base URL:** `http://localhost:8080/v1/project`

---

## Endpoints

### 1. Create Project

```
POST /v1/project
```

Creates a new project.

**Request Body:**

```json
{
  "name": "New Project",
  "status": true,
  "description": "Project description"
}
```

**Field Constraints:**

| Field       | Type    | Required | Constraints                        |
|------------|---------|----------|------------------------------------|
| name       | string  | Yes      | Not blank                          |
| status     | boolean | Yes      | Active (true) / Inactive (false)   |
| description| string  | No       | Max 500 characters                 |

**Responses:**

| Status | Description |
|--------|-------------|
| `201 Created` | Project created successfully |
| `400 Bad Request` | Validation error or duplicate name |

---

### 2. Update Project

```
PATCH /v1/project/{id}
```

Partially updates project fields.

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| id        | long | Project ID  |

**Request Body:**

```json
{
  "name": "Updated Project",
  "status": false
}
```

**Field Constraints:**

| Field       | Type    | Required | Constraints                        |
|------------|---------|----------|------------------------------------|
| name       | string  | No       | Max 100 characters                 |
| status     | boolean | No       | Active (true) / Inactive (false)   |
| description| string  | No       | Max 500 characters                 |

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | Project updated successfully |
| `400 Bad Request` | Invalid input |
| `404 Not Found` | Project not found |

---

### 3. Delete Project

```
DELETE /v1/project/{id}
```

Deletes a project by ID.

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| id        | long | Project ID  |

**Responses:**

| Status | Description |
|--------|-------------|
| `204 No Content` | Project deleted successfully |
| `404 Not Found`  | Project not found |

---

### 4. Get Project by ID

```
GET /v1/project/{id}
```

Retrieves a single project's details.

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| id        | long | Project ID  |

**Response Body:**

```json
{
  "id": 1,
  "name": "New Project",
  "status": true,
  "description": "Project description",
  "createdAt": "2026-07-29T12:00:00",
  "updatedAt": "2026-07-29T12:00:00"
}
```

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | Project found |
| `404 Not Found` | Project not found |

---

### 5. Get All Projects

```
GET /v1/project/all
```

Retrieves all projects.

**Response Body:**

```json
[
  {
    "id": 1,
    "name": "New Project",
    "status": true,
    "description": "Project description",
    "createdAt": "2026-07-29T12:00:00",
    "updatedAt": "2026-07-29T12:00:00"
  }
]
```

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | List of projects retrieved |

---

### 6. Find Projects by Status

```
GET /v1/project?status=active
```

Filters projects by status (`active` or `inactive`).

**Query Parameters:**

| Parameter | Type   | Required | Description                        |
|-----------|--------|----------|------------------------------------|
| status    | string | Yes      | `active` or `inactive`             |

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | Projects retrieved successfully |
| `400 Bad Request` | Invalid status value |

---

# Audit Log Module

**Base URL:** `http://localhost:8080/v1/audit-log`

---

## Endpoints

### 1. Create Audit Log

```
POST /v1/audit-log
```

Records an audit log entry for an employee action.

**Request Body:**

```json
{
  "employeeId": 1,
  "departmentId": 1,
  "projectId": 1,
  "action": "LOGIN",
  "description": "User logged into the system"
}
```

**Field Constraints:**

| Field        | Type   | Required | Constraints                     |
|-------------|--------|----------|----------------------------------|
| employeeId  | long   | Yes      | Must reference existing user     |
| departmentId| long   | Yes      | Must reference existing department |
| projectId   | long   | Yes      | Must reference existing project  |
| action      | string | Yes      | Not blank                        |
| description | string | No       | —                                |

**Responses:**

| Status | Description |
|--------|-------------|
| `201 Created` | Audit log created successfully |
| `400 Bad Request` | Invalid input |
| `404 Not Found` | Employee, department, or project not found |

---

### 2. Get Audit Log by ID

```
GET /v1/audit-log/{id}
```

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| id        | long | Audit log ID |

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | Audit log found |
| `404 Not Found` | Audit log not found |

---

### 3. Find Audit Logs by Employee

```
GET /v1/audit-log/employee/{employeeId}
```

**Path Parameters:**

| Parameter  | Type | Description |
|-----------|------|-------------|
| employeeId | long | Employee ID |

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | Audit logs retrieved |

---

### 4. Find Audit Logs by Department

```
GET /v1/audit-log/department/{departmentId}
```

**Path Parameters:**

| Parameter    | Type | Description |
|-------------|------|-------------|
| departmentId | long | Department ID |

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | Audit logs retrieved |

---

### 5. Find Audit Logs by Project

```
GET /v1/audit-log/project/{projectId}
```

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| projectId | long | Project ID |

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | Audit logs retrieved |

---

### 6. Find Audit Logs by Action

```
GET /v1/audit-log?action=LOGIN
```

**Query Parameters:**

| Parameter | Type   | Required | Description |
|-----------|--------|----------|-------------|
| action    | string | Yes      | Action type |

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | Audit logs retrieved |

---

### 7. Find Audit Logs by Date Range

```
GET /v1/audit-log?startDate=2026-01-01T00:00:00&endDate=2026-12-31T23:59:59
```

**Query Parameters:**

| Parameter | Type          | Required | Description |
|-----------|---------------|----------|-------------|
| startDate | LocalDateTime | Yes      | Start of range |
| endDate   | LocalDateTime | Yes      | End of range |

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | Audit logs retrieved |

---

## Common Response Schema — `AuditLogDto.Response`

| Field          | Type           | Description                |
|---------------|----------------|----------------------------|
| id            | long           | Audit log ID               |
| employeeId    | long           | Employee ID                |
| employeeName  | string         | Employee name              |
| departmentId  | long           | Department ID              |
| departmentName| string         | Department name            |
| projectId     | long           | Project ID                 |
| projectName   | string         | Project name               |
| action        | string         | Action type                |
| description   | string         | Action description         |
| createdAt     | LocalDateTime  | Creation timestamp         |

---

# Department Module

**Base URL:** `http://localhost:8080/v1/department`

---

## Endpoints

### 1. Create Department

```
POST /v1/department
```

Creates a new department.

**Request Body:**

```json
{
  "name": "Engineering"
}
```

**Field Constraints:**

| Field | Type   | Required | Constraints |
|-------|--------|----------|-------------|
| name  | string | Yes      | Not blank   |

**Responses:**

| Status | Description |
|--------|-------------|
| `201 Created` | Department created successfully |
| `400 Bad Request` | Validation error or duplicate name |

---

### 2. Get Department by ID

```
GET /v1/department/{id}
```

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| id        | long | Department ID |

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | Department found |
| `404 Not Found` | Department not found |

---

### 3. Get All Departments

```
GET /v1/department/all
```

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | List of departments retrieved |

---

### 4. Delete Department

```
DELETE /v1/department/{id}
```

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| id        | long | Department ID |

**Responses:**

| Status | Description |
|--------|-------------|
| `204 No Content` | Department deleted successfully |
| `404 Not Found` | Department not found |

---

## Common Response Schema — `DepartmentDto.Response`

| Field | Type   | Description      |
|-------|--------|------------------|
| id    | long   | Department ID    |
| name  | string | Department name  |

---

# Role Module

**Base URL:** `http://localhost:8080/v1/role`

---

## Endpoints

### 1. Get All Roles

```
GET /v1/role/all
```

Retrieves all available roles.

**Response Body:**

```json
[
  {
    "id": 1,
    "roleName": "ADMIN"
  },
  {
    "id": 2,
    "roleName": "MANAGER"
  },
  {
    "id": 3,
    "roleName": "EMPLOYEE"
  }
]
```

**Responses:**

| Status | Description |
|--------|-------------|
| `200 OK` | List of roles retrieved |

---

## Common Response Schema — `RoleDto.Response`

| Field    | Type   | Description |
|---------|--------|-------------|
| id      | long   | Role ID     |
| roleName| string | Role name   |

---

## Common Response Schema — `ProjectDto.Response`

| Field       | Type           | Description                |
|------------|----------------|----------------------------|
| id         | long           | Project ID                 |
| name       | string         | Project name               |
| status     | boolean        | Active (true) / Inactive   |
| description| string         | Project description        |
| createdAt  | LocalDateTime  | Creation timestamp         |
| updatedAt  | LocalDateTime  | Last update timestamp      |

---

## Common Response Schema — `UserDto.Response`

| Field          | Type           | Description                |
|---------------|----------------|----------------------------|
| id            | long           | User ID                    |
| name          | string         | Full name                  |
| email         | string         | Email address              |
| active        | boolean        | Account active status      |
| departmentName| string         | Assigned department name   |
| roleName      | string         | Assigned role name         |
| createdAt     | LocalDateTime  | Account creation timestamp |

---

## Error Handling

The API uses standard HTTP status codes. All error responses follow the format:

```json
{
  "error": "Error description",
  "status": 400,
  "path": "/v1/user",
  "timestamp": "2026-07-29T12:00:00"
}
```

**Common Status Codes:**

| Code | Meaning |
|------|---------|
| 400  | Bad Request — validation error or business rule violation |
| 404  | Resource not found |
| 500  | Internal server error |

---

## Swagger UI

The API is documented with OpenAPI 3.0 via Springdoc. Access the interactive UI at:

```
http://localhost:8080/swagger-ui
```

OpenAPI JSON spec available at:

```
http://localhost:8080/api-docs
```
