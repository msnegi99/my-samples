import 'package:flutter/material.dart';
import '../Models/TaskModel.dart';
import '../Services/DataBaseHelper.dart';
import 'dart:collection';

class TaskProvider extends ChangeNotifier {
  List<TaskModel> _tasks = [];
  final DatabaseHelper _dbHelper = DatabaseHelper();

  // Getter for all tasks
  UnmodifiableListView<TaskModel> get allTasks => UnmodifiableListView(_tasks);

  // Getter sirf pending tasks ke liye
  List<TaskModel> get pendingTasks => _tasks.where((task) => !task.isDone).toList();

  // Getter sirf completed tasks ke liye
  List<TaskModel> get completedTasks => _tasks.where((task) => task.isDone).toList();

  TaskProvider() {
    fetchTasks();
  }

  Future<void> fetchTasks() async {
    _tasks = await _dbHelper.getTasks();
    notifyListeners();
  }

  // Add Task ab dueDate bhi lega
  Future<void> addTask(String title, String dueDate) async {
    if (title.isNotEmpty) {
      TaskModel newTask = TaskModel(title: title, dueDate: dueDate);
      await _dbHelper.insertTask(newTask);
      fetchTasks();
    }
  }

  // Naya function: Task edit karne ke liye
  Future<void> editTask(TaskModel task) async {
    await _dbHelper.updateTask(task);
    fetchTasks();
  }

  Future<void> updateTaskStatus(TaskModel task) async {
    task.isDone = !task.isDone;
    await _dbHelper.updateTask(task);
    fetchTasks();
  }

  Future<void> deleteTask(int id) async {
    await _dbHelper.deleteTask(id);
    fetchTasks();
  }
}