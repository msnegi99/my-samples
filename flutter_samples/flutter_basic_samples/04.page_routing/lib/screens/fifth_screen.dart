import 'package:flutter/material.dart';

class FifthScreen extends StatefulWidget {

  static const String routeName = '/fifth-screen';
  int number = 0;
  String name = '';
  double gpa = 0.0;

  @override
  State<FifthScreen> createState() => _FifthScreenState();
}

class _FifthScreenState extends State<FifthScreen> {

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
        onWillPop: () async {
          return true;
        },
        child:Scaffold(
          appBar: AppBar(title: const Text('Fifth Screen')),
          body: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                TextButton(
                    onPressed: () {
                      Navigator.pop(context, "true");   //return value true to fourth screen
                    },
                    child: const Text(
                      "Yes",
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 14,
                      ),
                    )),
              ],
            ),
          ),
        ));
  }

}