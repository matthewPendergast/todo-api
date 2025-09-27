package todoapi.todos.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import todoapi.todos.data.Todo;
import todoapi.todos.data.TodoRepository;
import todoapi.todos.dto.TodoCreateRequest;
import todoapi.todos.dto.TodoResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class TodoService {

	private final TodoRepository repo;

	public TodoService(TodoRepository repo) {
		this.repo = repo;
	}

	@Transactional
	public TodoResponse create(TodoCreateRequest req) {
		Todo t = new Todo();
		t.setTitle(req.getTitle());
		t.setDescription(req.getDescription());
		t.setDueDate(req.getDueDate());
		t.setCompleted(Boolean.TRUE.equals(req.getCompleted())); // defaults false if omitted

		Todo saved = repo.save(t);
		return toResponse(saved);
	}

	@Transactional
	public TodoResponse update(Long id, Map<String, Object> updates) {
		var todo = repo.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "To-do " + id + " not found"));

		if (updates.containsKey("title")) {
			Object v = updates.get("title");
			if (v == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title cannot be null");
			}
			if (!(v instanceof String s)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title must be a string");
			}
			String t = s.trim();
			if (t.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title must not be blank");
			}
			if (t.length() > 200) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title must be <= 200 characters");
			}
			todo.setTitle(t);
		}

		if (updates.containsKey("description")) {
			Object v = updates.get("description");
			if (v != null && !(v instanceof String _s)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "description must be a string or null");
			}
			String desc = (String) v;
			if (desc != null && desc.length() > 2000) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "description must be <= 2000 characters");
			}
			todo.setDescription(desc);
		}

		if (updates.containsKey("dueDate")) {
			Object v = updates.get("dueDate");
			if (v == null) {
				todo.setDueDate(null);
			} else if (v instanceof String s) {
				try {
					todo.setDueDate(LocalDate.parse(s));
				} catch (DateTimeParseException e) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"dueDate must be an ISO date string");
				}
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "dueDate must be a string or null");
			}
		}

		if (updates.containsKey("completed")) {
			Object v = updates.get("completed");
			if (!(v instanceof Boolean b)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "completed must be true or false");
			}
			todo.setCompleted(b);
		}

		var saved = repo.save(todo);
		return toResponse(saved);
	}

	@Transactional
	public TodoResponse markComplete(Long id) {
		return setCompleted(id, true);
	}

	@Transactional
	public TodoResponse markIncomplete(Long id) {
		return setCompleted(id, false);
	}

	@Transactional
	public void delete(Long id) {
		if (!repo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "To-do " + id + " not found");
		}
		repo.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<TodoResponse> findAll(Boolean completed) {
		var todos = (completed == null)
				? repo.findAll()
				: repo.findByCompleted(completed);
		return todos.stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public List<TodoResponse> findAll() {
		return repo.findAll().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public Optional<TodoResponse> findById(Long id) {
		return repo.findById(id).map(this::toResponse);
	}

	private TodoResponse setCompleted(Long id, boolean value) {
		var todo = repo.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "To-do " + id + " not found"));
		todo.setCompleted(value);
		var saved = repo.save(todo);
		return toResponse(saved);
	}

	private TodoResponse toResponse(Todo t) {
		TodoResponse r = new TodoResponse();
		r.setId(t.getId());
		r.setTitle(t.getTitle());
		r.setDescription(t.getDescription());
		r.setDueDate(t.getDueDate());
		r.setCompleted(t.isCompleted());
		r.setCreatedAt(t.getCreatedAt());
		r.setUpdatedAt(t.getUpdatedAt());
		return r;
	}

}
