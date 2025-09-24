package todoapi.todos.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import todoapi.todos.dto.TodoCreateRequest;
import todoapi.todos.dto.TodoResponse;
import todoapi.todos.service.TodoService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

	private final TodoService service;

	public TodoController(TodoService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<TodoResponse> create(
			@Valid @RequestBody TodoCreateRequest body) {
		TodoResponse created = service.create(body);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(created.getId())
				.toUri();

		return ResponseEntity.created(location).body(created);
	}

	@GetMapping
	public List<TodoResponse> list() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public TodoResponse getOne(@PathVariable Long id) {
		return service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Todo " + id + " not found"));
	}
}
