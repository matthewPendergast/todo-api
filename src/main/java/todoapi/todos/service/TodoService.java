package todoapi.todos.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todoapi.todos.data.Todo;
import todoapi.todos.data.TodoRepository;
import todoapi.todos.dto.TodoCreateRequest;
import todoapi.todos.dto.TodoResponse;
import java.util.List;
import java.util.Optional;

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

	@Transactional(readOnly = true)
	public List<TodoResponse> findAll() {
		return repo.findAll().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public Optional<TodoResponse> findById(Long id) {
		return repo.findById(id).map(this::toResponse);
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
