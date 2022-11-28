package com.example.demo.interfaces;

import com.example.demo.models.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    Iterable<Task> getAllByTitle(String title);
}
