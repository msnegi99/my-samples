import 'package:flutter/material.dart';

class SecondScreen extends StatefulWidget {
  static const String routeName = '/second-screen';
  int number = 0;
  String name = '';
  double gpa = 0.0;

  @override
  State<SecondScreen> createState() => _SecondScreenState();
}

class _SecondScreenState extends State<SecondScreen> {

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
        child: Scaffold(
      appBar: AppBar(title: const Text('Second Screen')),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[

          ],
        ),
      ),
    ),onWillPop: _reqPop);
  }

  Future<bool> _reqPop() {

      showDialog(
          context: context,
          builder: (context) {
            return AlertDialog(
              title: const Text("Discard changes?"),
              content: const Text("When exiting the information will be lost"),
              actions: [
                ElevatedButton(
                    onPressed: () {
                      Navigator.pop(context);
                    },
                    child: const Text("Cancel")),
                ElevatedButton(
                    onPressed: () {
                      Navigator.pop(context);
                      Navigator.pop(context);
                    },
                    child: const Text("Confirm")),
              ],
            );
          });
      return Future.value(false);

  }
}