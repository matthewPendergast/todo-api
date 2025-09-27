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
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
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

	@PatchMapping("/{id}")
	public TodoResponse patch(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
		return service.update(id, updates);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

	@PostMapping("/{id}/complete")
	public TodoResponse complete(@PathVariable Long id) {
		return service.markComplete(id);
	}

	@PostMapping("/{id}/incomplete")
	public TodoResponse incomplete(@PathVariable Long id) {
		return service.markIncomplete(id);
	}

	@GetMapping
	public List<TodoResponse> list(@RequestParam(value = "completed", required = false) Boolean completed) {
		return service.findAll(completed);
	}

	@GetMapping("/{id}")
	public TodoResponse getOne(@PathVariable Long id) {
		return service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "To-do " + id + " not found"));
	}
}
