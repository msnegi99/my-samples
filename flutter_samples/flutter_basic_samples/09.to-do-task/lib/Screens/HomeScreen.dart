// lib/Screens/HomeScreen.dart

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../Providers/TaskProvider.dart';
import 'AddTaskScreen.dart';
import 'EditTaskScreen.dart';
 // EditTaskScreen ko import karein

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 2,
      child: Scaffold(
        appBar: AppBar(
          title: Text("To-Do List"),
          centerTitle: true,
          bottom: TabBar(
            indicatorColor: Colors.white,
            indicatorWeight: 3,
            tabs: [
              Tab(icon: Icon(Icons.pending_actions,color: Colors.white,), text: "Pending",),
              Tab(icon: Icon(Icons.check_circle,color: Colors.white,), text: "Completed"),
            ],
          ),
        ),
        body: TabBarView(
          children: [
            _buildTaskList(context, isCompletedTab: false),
            _buildTaskList(context, isCompletedTab: true),
          ],
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            // Ab ye AddTaskScreen kholega
            Navigator.of(context).push(MaterialPageRoute(
              builder: (context) => AddTaskScreen(),
            ));
          },
          backgroundColor: CupertinoColors.activeGreen,
          child: Icon(Icons.add, color: Colors.white),
          tooltip: 'Add Task',
        ),
      ),
    );
  }

  Widget _buildTaskList(BuildContext context, {required bool isCompletedTab}) {
    return Consumer<TaskProvider>(
      builder: (context, taskProvider, child) {
        final tasks = isCompletedTab
            ? taskProvider.completedTasks
            : taskProvider.pendingTasks;

        if (tasks.isEmpty) {
          return Center(
            child: Text(
              "No ${isCompletedTab ? 'completed' : 'pending'} tasks.",
              style: TextStyle(fontSize: 18, color: Colors.grey),
            ),
          );
        }

        return ListView.builder(
          padding: EdgeInsets.only(bottom: 80), // FAB ke liye space
          itemCount: tasks.length,
          itemBuilder: (context, index) {
            final task = tasks[index];
            return Card(
              elevation: 3,
              margin: EdgeInsets.symmetric(horizontal: 10, vertical: 5),
              child: ListTile(
                leading: Checkbox(
                  value: task.isDone,
                  onChanged: (value) {
                    taskProvider.updateTaskStatus(task);
                  },
                ),
                title: Text(
                  task.title,
                  style: TextStyle(
                    fontSize: 17,
                    decoration: task.isDone
                        ? TextDecoration.lineThrough
                        : TextDecoration.none,
                  ),
                ),
                subtitle: Text("Due: ${task.dueDate}"),
                trailing: Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    // Edit Button ab EditTaskScreen kholega
                    IconButton(
                      icon: Icon(Icons.edit, color: Theme.of(context).primaryColor),
                      onPressed: () {
                        Navigator.of(context).push(MaterialPageRoute(
                          builder: (context) => EditTaskScreen(task: task),
                        ));
                      },
                    ),
                    IconButton(
                      icon: Icon(Icons.delete, color: Colors.redAccent),
                      onPressed: () {
                        // Confirmation Dialog
                        showDialog(
                            context: context,
                            builder: (ctx) => AlertDialog(
                              title: Text("Are you sure?"),
                              content: Text("Do you want to delete this task?"),
                              actions: [
                                TextButton(child: Text("No"), onPressed: () => Navigator.of(ctx).pop()),
                                TextButton(child: Text("Yes"), onPressed: (){
                                  taskProvider.deleteTask(task.id!);
                                  Navigator.of(ctx).pop();
                                }),
                              ],
                            ));
                      },
                    ),
                  ],
                ),
              ),
            );
          },
        );
      },
    );
  }
}