import 'package:flutter/material.dart';
import 'package:flutter_basic_form/screens/bottom_nav_sub_screen_2.dart';

class SubScreen1 extends StatefulWidget {
  static const String routeName = '/subscreen1-screen';
  int number = 0;
  String name = '';
  double gpa = 0.0;

  @override
  State<SubScreen1> createState() => _SubScreen1State();
}

class _SubScreen1State extends State<SubScreen1> {

  @override
  Widget build(BuildContext context) {

    Future<void> _navigateToSubScreen2() async {
      final result = await Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => SubScreen2()),
      );

      // Use the returned 'result' here
      if (result != null) {
        // Handle the returned data, e.g., update UI
        print('Data returned from SubScreen2: $result');
        setState(() {
          // Update a variable and rebuild the UI
          //_returnedValue = result;
          widget.name = result;
          //print(_returnedValue);
        });
      }
    }

    return Scaffold(
        appBar: AppBar(
          automaticallyImplyLeading: false, // This hides the back arrow
          actions: [
            IconButton(icon: Icon(Icons.arrow_back), onPressed: () { Navigator.of(context).pop(); }),
          ],
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              ElevatedButton(
                onPressed: () {
                  var navigateToFifthScreen = _navigateToSubScreen2();
                },
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.red.shade300,
                ),
                child: Text('enter sub screen 2 ${widget.name}'),
              ),
            ],
          ),
        ),
      );
  }
}
