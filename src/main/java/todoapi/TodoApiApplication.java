package todoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import todoapi.todos.data.Todo;
import todoapi.todos.data.TodoRepository;
import java.time.LocalDate;

@SpringBootApplication
public class TodoApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(TodoApiApplication.class, args);
	}

	@Bean
	CommandLineRunner seedTodos(TodoRepository repo) {
		return args -> {
			// Test seed
			if (repo.count() == 0) {
				Todo t = new Todo();
				t.setTitle("Buy milk");
				t.setDescription("2%, gallon");
				t.setDueDate(LocalDate.now().plusDays(3));
				t.setCompleted(false);
				repo.save(t);
			}
		};
	}
}