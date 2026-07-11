import 'package:flutter/material.dart';

class ThirdScreen extends StatefulWidget {

  static const String routeName = '/third-screen';
  int number = 0;
  String name = '';
  double gpa = 0.0;

  @override
  State<ThirdScreen> createState() => _ThirdScreenState();
}

class _ThirdScreenState extends State<ThirdScreen> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Third Screen')),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[

          ],
        ),
      ),
    );
  }

}