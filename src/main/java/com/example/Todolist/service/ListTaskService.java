package com.example.Todolist.service;

import com.example.Todolist.domain.TaskList;
import com.example.Todolist.domain.Task;
import com.example.Todolist.domain.list;
import com.example.Todolist.repository.ListRepository;
import com.example.Todolist.repository.ListTaskRepository;
import com.example.Todolist.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ListTaskService{
    private final ListRepository listRepository;
    private final TaskRepository taskRepository;
     private final  ListTaskRepository listTaskRepository;

    public ListTaskService(ListRepository listRepository, TaskRepository taskRepository, ListTaskRepository listTaskRepository) {
        this.listRepository = listRepository;
        this.taskRepository = taskRepository;
        this.listTaskRepository = listTaskRepository;
    }

    public TaskList addTaskInList(TaskList taskList) {
        Optional<list> listt =listRepository.getByName(taskList.getList().getName());
        Optional<Task> task = taskRepository.getByDescription(taskList.getTask().getDescription());

        TaskList taskList1 = new TaskList();
        if(listt.isPresent()&&task.isPresent()){
            taskList1.setList(listt.get());
            taskList1.setTask(task.get());

            return listTaskRepository.save(taskList1);
        }
        throw   new RuntimeException("Task Cannot be added into list");
    }

//    public ListTask addTaskInList(Long listId, Long taskId) {
//        Optional<list> listt =listRepository.findById(listId);
//        Optional<Task> task = taskRepository.findById(taskId);
//        if(listt.isPresent()&&task.isPresent()){
//            ListTask listTask = new ListTask();
//            listTask.setList(listt.get());
//            listTask.setTask(task.get());
//
//            return listTaskRepository.save(listTask);
//        }
//        throw  new RuntimeException("Task Cannot be added into list");
//    }


        public void deleteTaskFromList(Long listId, Long taskId) {
            Optional<list> listt =listRepository.findById(listId);
            Optional<Task> task = taskRepository.findById(taskId);
            if(listt.isPresent()&&task.isPresent()){
                listTaskRepository.deleteByListIdAndTaskId(listt.get().getId(),task.get().getId());

            }
            throw  new RuntimeException("Task Cannot be added into list");

    }

    public List<TaskList> getTasksByListName(String listName) {
        Optional<list> l = listRepository.getByName(listName);

        if(l.isPresent()){
            return  listTaskRepository.findByListId(l.get().getId());
        }
        throw  new RuntimeException("List is not found");
    }
}
