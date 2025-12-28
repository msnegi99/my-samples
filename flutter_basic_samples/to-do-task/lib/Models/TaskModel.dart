// lib/Models/TaskModel.dart

class TaskModel {
  int? id;
  String title;
  String dueDate; // Naya field add kiya
  bool isDone;

  TaskModel({
    this.id,
    required this.title,
    required this.dueDate,
    this.isDone = false,
  });

  factory TaskModel.fromMap(Map<String, dynamic> map) {
    return TaskModel(
      id: map['id'],
      title: map['title'],
      dueDate: map['dueDate'], // Naya field add kiya
      isDone: map['isDone'] == 1,
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'title': title,
      'dueDate': dueDate, // Naya field add kiya
      'isDone': isDone ? 1 : 0,
    };
  }
}