import 'package:flutter/material.dart';

class SubScreen2 extends StatefulWidget {
  static const String routeName = '/sub-screen-2';
  int number = 0;
  String name = '';
  double gpa = 0.0;

  @override
  State<SubScreen2> createState() => _SubScreen2State();
}

class _SubScreen2State extends State<SubScreen2> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false, // This hides the back arrow
        actions: [IconButton(icon: Icon(Icons.arrow_back), onPressed: () { Navigator.of(context).pop(); })],
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            TextButton(
              onPressed: () {
                Navigator.pop(
                  context,
                  "true",
                ); //return value true to fourth screen
              },
              child: const Text(
                "Yes",
                style: TextStyle(fontWeight: FontWeight.bold, fontSize: 14),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
