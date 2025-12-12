import 'package:flutter/material.dart';
import 'dart:ui' as ui;
import '../consts.dart';
import '../views/simple_round_button.dart';
import '../views/simple_round_icon_button.dart';

class GridLayoutPage extends StatefulWidget {
  const GridLayoutPage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<GridLayoutPage> createState() => GridLayoutPageState();
}

class GridLayoutPageState extends State<GridLayoutPage> {
  @override
  Widget build(BuildContext context) {
    final ui.Size logicalSize = MediaQuery.of(context).size;
    final double _height = logicalSize.height;
    return new Scaffold(
      appBar: new AppBar(
        backgroundColor: IndigoBlue,
        foregroundColor: Colors.white,
        title: new Text("Grid Layout"),
      ),
      body: new SingleChildScrollView(
        padding: new EdgeInsets.all(20.0),
        child: new Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    Image.asset(
                      'resources/images/pic1.png',
                      height: 50,
                      width: 50,
                    ),
                    Image.asset(
                      'resources/images/pic2.png',
                      height: 50,
                      width: 50,
                    ),
                    Image.asset(
                      'resources/images/pic3.png',
                      height: 50,
                      width: 50,
                    ),
                  ],
                ),
                Column(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    Image.asset(
                      'resources/images/pic1.png',
                      height: 50,
                      width: 50,
                    ),
                    Image.asset(
                      'resources/images/pic2.png',
                      height: 50,
                      width: 50,
                    ),
                    Image.asset(
                      'resources/images/pic3.png',
                      height: 50,
                      width: 50,
                    ),
                  ],
                ),
                Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Expanded(
                      child: Image.asset(
                        'resources/images/pic1.png',
                        height: 50,
                        width: 50,
                      ),
                    ),
                    Expanded(
                      child: Image.asset(
                        'resources/images/pic2.png',
                        height: 50,
                        width: 50,
                      ),
                    ),
                    Expanded(
                      child: Image.asset(
                        'resources/images/pic3.png',
                        height: 50,
                        width: 50,
                      ),
                    ),
                  ],
                ),
                Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    Icon(Icons.star, color: Colors.green[500]),
                    Icon(Icons.star, color: Colors.green[500]),
                    Icon(Icons.star, color: Colors.green[500]),
                    const Icon(Icons.star, color: Colors.black),
                    const Icon(Icons.star, color: Colors.black),

                  ],
                ),
                FlightBookButton(),
              ],
            ),
            new Container(
              padding: new EdgeInsets.only(top: (_height - 650.0)),
              margin: new EdgeInsets.only(bottom: 16.0),
              child: new FloatingActionButton(
                backgroundColor: new Color(0xFFE57373),
                child: new Icon(Icons.check),
                onPressed: () {},
              ),
            ),
            SimpleRoundButton(
              backgroundColor: Colors.redAccent,
              buttonText: Text(
                "LOGIN",
                style: TextStyle(color: Colors.white),
              ),
            ),
            SimpleRoundIconButton(
              backgroundColor: Colors.orangeAccent,
              buttonText: Text(
                "SEND EMAIL",
                style: TextStyle(color: Colors.white),
              ),
              icon: Icon(Icons.email),
            ),
          ],
        ),
      ),
    );
  }
}

class FlightBookButton extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(top: 30.0),
      color: IndigoLightBlue,
      width: 250.0,
      height: 50.0,
      child: ElevatedButton(
          child: Text(
            "Book Your Flight",
            style: TextStyle(fontSize: 20.0, color: Colors.white),
          ),
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.blue, // Background color of the button
            foregroundColor: Colors.white, // Text color of the button
            elevation: 5, // Shadow elevation
            padding: EdgeInsets.symmetric(horizontal: 20, vertical: 15), // Padding
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(10), // Rounded corners
            ),),
          onPressed: () {
            bookFlight(context);
          }),
    );
  }
}

void bookFlight(BuildContext context) {
  var alertDialog = AlertDialog(
    title: Text("Flight Booked Successfully"),
    content: Text("Have a pleasant flight"),
  );

  showDialog(
      context: context,
      builder: (BuildContext context) {
        return alertDialog;
      });
}