
import 'package:flutter/material.dart';
import 'package:flutter_basic_form/page_routes.dart';
import 'package:flutter_basic_form/home_page_design.dart';

void main() {
  runApp(const FormApp());
}

class FormApp extends StatelessWidget {
  const FormApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Page Architecture',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: MyHomePage(title: 'Home Page',),
      onGenerateRoute: MyRoutes.onGenerateRoutesMethod,
    );
  }
}
