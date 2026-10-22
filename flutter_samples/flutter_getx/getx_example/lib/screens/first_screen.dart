import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';

class FirstScreen extends StatefulWidget {

  static const String routeName = '/first-screen';
  int number;
  String name;
  double gpa;

  FirstScreen({super.key, required this.number, required this.name, required this.gpa});

  @override
  State<FirstScreen> createState() => _FirstScreenState();
}

class _FirstScreenState extends State<FirstScreen> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('First Screen'),
        leading: IconButton(
          icon: Icon(Icons.arrow_back),
          onPressed: () {
            // Custom back button logic
            print('Custom back button pressed!');
            //Navigator.of(context).pop(); // Or navigate to a specific route
            Get.back();
          },
        ),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
              Text("My Name is ${widget.name} ,  gpa is :${widget.gpa} and   number is :${widget.number} "),

          ],
        ),
      ),
    );
  }

}