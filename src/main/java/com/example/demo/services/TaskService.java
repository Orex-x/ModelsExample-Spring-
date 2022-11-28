package com.example.demo.services;

import com.example.demo.interfaces.TaskRepository;
import com.example.demo.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Iterable<Task> getAll(){
        return taskRepository.findAll();
    }

    public Optional<Task> get(Long id){
        return taskRepository.findById(id);
    }

    public Iterable<Task> getAllByTitle(String title){
        return taskRepository.getAllByTitle(title);
    }

    public Iterable<Task> getAllByTitleExists(String title){
        Iterable<Task> all  = getAll();
        List<Task> buffer = new ArrayList<>();
        for (var item : all) {
            try{
                if(item.getTitle().toLowerCase(Locale.ROOT)
                        .contains(title.toLowerCase(Locale.ROOT)))
                    buffer.add(item);
            }catch (Exception e){continue;}
        }

        return buffer;
    }

    public void add(Task task){
        taskRepository.save(task);
    }

    public void add(Long id, Task task){
        Task u = get(id).get();
        u.setDeadline(task.getDeadline());
        u.setEmployees(task.getEmployees());
        u.setTitle(task.getTitle());
        taskRepository.save(u);
    }

    public void delete(Long id){
        taskRepository.deleteById(id);
    }
}
