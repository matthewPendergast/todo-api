# To-Do List API

This is a simple to-do list project I worked on to learn how to build a basic API using Java + Spring Boot.

## Documentation

<details>
	<summary>
		<b>GET</b>
		<code>/api/todos</code>
		Returns a list of all current to-do items
	</summary>

#### Query Parameters

- `completed` (boolean, optional) — filter by completion status

#### Example Response Body

```json
[
  {
    "id": 1,
    "title": "Buy milk",
    "description": "2% gallon",
    "dueDate": "2025-10-01",
    "completed": false,
    "createdAt": "2025-09-24T17:04:08.845416Z",
    "updatedAt": "2025-09-24T17:04:08.845416Z"
  }
]
```

---

</details>

<details>
	<summary>
		<b>GET</b>
		<code>/api/todos{id}</code>
		Returns the specified to-do item
	</summary>

#### Parameters

- `id` (integer, required) — ID number of the to-do item

#### Example Response Body

```json
[
  {
    "id": 1,
    "title": "Buy milk",
    "description": "2% gallon",
    "dueDate": "2025-10-01",
    "completed": false,
    "createdAt": "2025-09-24T17:04:08.845416Z",
    "updatedAt": "2025-09-24T17:04:08.845416Z"
  }
]
```

---

</details>

<details>
	<summary>
		<b>POST</b>
		<code>/api/todos</code>
		Creates a new to-do item
	</summary>

#### Example Request Body

```json
{
  "title": "Buy milk",
  "description": "2% gallon",
  "dueDate": "2025-10-01"
}
```

#### Example Response Body

```json
{
  "id": 1,
  "title": "Buy milk",
  "description": "2% gallon",
  "dueDate": "2025-10-01",
  "completed": false,
  "createdAt": "2025-09-24T17:04:08.845416Z",
  "updatedAt": "2025-09-24T17:04:08.845416Z"
}
```

---

</details>

<details>
	<summary>
		<b>PATCH</b>
		<code>/api/todos/{id}</code>
		Updates specified to-do item's fields
	</summary>

#### Parameters

- `id` (integer, required) — ID number of the to-do item

#### Example Request Body

```json
{
  "title": "Buy milk and bread",
  "description": null,
  "dueDate": "2025-10-02",
  "completed": true
}
```

---

</details>

<details>
	<summary>
		<b>POST</b>
		<code>/api/todos/{id}/complete</code>
		Marks specified to-do items as complete
	</summary>

#### Parameters

- `id` (integer, required) — ID number of the to-do item

---

</details>

<details>
	<summary>
		<b>POST</b>
		<code>/api/todos/{id}/incomplete</code>
		Marks specified to-do items as incomplete
	</summary>

#### Parameters

- `id` (integer, required) — ID number of the to-do item

---

</details>

<details>
	<summary>
		<b>DELETE</b>
		<code>/api/todos/{id}</code>
		Deletes the specific to-do item
	</summary>

#### Parameters

- `id` (integer, required) — ID number of the to-do item

---

</details>

## Project Structure

```bash
todo-api/
├── pom.xml
├── readme.md
└── src/
    └── main/
        ├── java/
        │   └── todoapi/
        │       ├── TodoApiApplication.java
        │       ├── health/
        │       │   └── HealthController.java
        │       └── todos/
        │           ├── data/
        │           │   ├── Todo.java
        │           │   └── TodoRepository.java
        │           ├── dto/
        │           │   ├── TodoCreateRequest.java
        │           │   └── TodoResponse.java
        │           ├── service/
        │           │   └── TodoService.java
        │           └── web/
        │               └── TodoController.java
        └── resources/
            └── application.properties
```
