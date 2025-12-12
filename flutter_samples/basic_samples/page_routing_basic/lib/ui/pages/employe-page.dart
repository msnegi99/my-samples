import 'package:flutter/material.dart';
import 'package:page_routes/ui/data.dart';

class EmployeePage extends StatelessWidget {
  static const routeName = "/employe-page";
  @override
  Widget build(BuildContext context) {
    final Employee emp = ModalRoute.of(context)?.settings.arguments as Employee;
    return Scaffold(
      appBar: AppBar(),
      body: Column(
        children: [
          Text(emp.id),
          Text(emp.phone),
        ],
      ),
    );
  }
}
