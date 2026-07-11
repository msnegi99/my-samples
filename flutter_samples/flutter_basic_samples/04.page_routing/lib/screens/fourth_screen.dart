import 'package:flutter/material.dart';
import 'package:page_routing/screens/fifth_screen.dart';

class FourthScreen extends StatefulWidget {
  static const String routeName = '/fourth-screen';
  int number = 0;
  String name = '';
  double gpa = 0.0;

  @override
  State<FourthScreen> createState() => _FourthScreenState();
}

class _FourthScreenState extends State<FourthScreen> {

  @override
  Widget build(BuildContext context) {

    Future<void> _navigateToFifthScreen() async {
      final result = await Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => FifthScreen()),
      );

      // Use the returned 'result' here
      if (result != null) {
        // Handle the returned data, e.g., update UI
        print('Data returned from FifthScreen: $result');
        setState(() {
          // Update a variable and rebuild the UI
          //_returnedValue = result;
          widget.name = result;
          //print(_returnedValue);
        });
      }
    }

    return WillPopScope(
      onWillPop: () async {
        return true;
      },
      child: Scaffold(
        appBar: AppBar(title: Text('Fourth Screen ${widget.name}')),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              ElevatedButton(
                onPressed: () {
                  var navigateToFifthScreen = _navigateToFifthScreen();
                },
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.red.shade300,
                ),
                child: Text('enter red page Android ${widget.name}'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
