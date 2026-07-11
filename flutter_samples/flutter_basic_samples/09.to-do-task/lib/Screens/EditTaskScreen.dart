// lib/Screens/edit_task_screen.dart

import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';
import '../Models/TaskModel.dart';
import '../Providers/TaskProvider.dart';

class EditTaskScreen extends StatefulWidget {
  final TaskModel task; // Sirf edit ke liye, isliye 'required'

  const EditTaskScreen({Key? key, required this.task}) : super(key: key);

  @override
  _EditTaskScreenState createState() => _EditTaskScreenState();
}

class _EditTaskScreenState extends State<EditTaskScreen> {
  final _formKey = GlobalKey<FormState>();
  late TextEditingController _titleController;
  late TextEditingController _dateController;
  DateTime? _selectedDate;

  @override
  void initState() {
    super.initState();
    // Controllers ko purane task ke data se initialize karo
    _titleController = TextEditingController(text: widget.task.title);
    _dateController = TextEditingController(text: widget.task.dueDate);
    if (widget.task.dueDate.isNotEmpty) {
      _selectedDate = DateFormat('dd-MM-yyyy').parse(widget.task.dueDate);
    }
  }

  _pickDate() async {
    DateTime? date = await showDatePicker(
      context: context,
      initialDate: _selectedDate ?? DateTime.now(),
      firstDate: DateTime(2000),
      lastDate: DateTime(2101),
    );

    if (date != null) {
      setState(() {
        _selectedDate = date;
        _dateController.text = DateFormat('dd-MM-yyyy').format(date);
      });
    }
  }

  void _submitData() {
    if (_formKey.currentState!.validate()) {
      TaskModel updatedTask = TaskModel(
        id: widget.task.id,
        title: _titleController.text,
        dueDate: _dateController.text,
        isDone: widget.task.isDone, // Status wahi rahega
      );
      Provider.of<TaskProvider>(context, listen: false).editTask(updatedTask);
      Navigator.of(context).pop();
    }
  }

  @override
  void dispose() {
    _titleController.dispose();
    _dateController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Edit Task'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              TextFormField(
                controller: _titleController,
                decoration: InputDecoration(labelText: 'Task Title'),
                validator: (value) =>
                value!.isEmpty ? 'Please enter a title' : null,
              ),
              SizedBox(height: 20),
              TextFormField(
                controller: _dateController,
                decoration: InputDecoration(
                  labelText: 'Due Date',
                  suffixIcon: Icon(Icons.calendar_today),
                ),
                readOnly: true,
                onTap: _pickDate,
                validator: (value) =>
                value!.isEmpty ? 'Please select a due date' : null,
              ),
              SizedBox(height: 40),
              ElevatedButton(
                onPressed: _submitData,
                child: Text('Save Changes'),
                style: ElevatedButton.styleFrom(
                  padding: EdgeInsets.symmetric(vertical: 15),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}