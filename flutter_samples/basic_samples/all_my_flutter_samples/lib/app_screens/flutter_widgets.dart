import 'package:flutter/material.dart';
import './styles.dart' as utils;

class FlutterWidgetsPage extends StatefulWidget {
  const FlutterWidgetsPage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<FlutterWidgetsPage> createState() => FlutterWidgetsPageState();
}

class FlutterWidgetsPageState extends State<FlutterWidgetsPage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Expanded(
              child: Text(
                "Container and Fonts",
                textDirection: TextDirection.ltr,
                textAlign: TextAlign.center,
                style: TextStyle(
                    decoration: TextDecoration.none,
                    fontSize: 40.0,
                    color: Colors.deepOrange,
                    fontFamily: 'Times New Roman'),
              ),
            ),
            Expanded(
              child: Text(
                "Container and Fonts",
                textDirection: TextDirection.ltr,
                textAlign: TextAlign.center,
                style: TextStyle(
                    decoration: TextDecoration.none,
                    fontSize: 40.0,
                    color: Colors.deepOrange,
                    fontFamily: 'Times New Roman'),
              ),
            ),
            Expanded(
              child: Text(
                "Container and Fonts",
                textDirection: TextDirection.ltr,
                textAlign: TextAlign.center,
                style: TextStyle(
                    decoration: TextDecoration.none,
                    fontSize: 40.0,
                    color: Colors.deepOrange,
                    fontFamily: 'Times New Roman'),
              ),
            ),
            FlightBookButton(),
            _buildCard(),
            _buildStack(),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}

class FlightBookButton extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(top: 30.0),
      width: 250.0,
      height: 50.0,
      child: ElevatedButton(
          child: Text(
            "Book Your Flight",
            style: TextStyle(fontSize: 20.0, color: Colors.white,),
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

Widget _buildCard() {
  return SizedBox(
    height: 210,
    child: Card(
      child: Column(
        children: [
          ListTile(
            title: const Text(
              '1625 Main Street',
              style: TextStyle(fontWeight: FontWeight.w500),
            ),
            subtitle: const Text('My City, CA 99984'),
            leading: Icon(
              Icons.restaurant_menu,
              color: Colors.blue[500],
            ),
          ),
          const Divider(),
          ListTile(
            title: const Text(
              '(408) 555-1212',
              style: TextStyle(fontWeight: FontWeight.w500),
            ),
            leading: Icon(
              Icons.contact_phone,
              color: Colors.blue[500],
            ),
          ),
          ListTile(
            title: const Text('costa@example.com'),
            leading: Icon(
              Icons.contact_mail,
              color: Colors.blue[500],
            ),
          ),
        ],
      ),
    ),
  );
}

Widget _buildStack() {
  return Stack(
    alignment: const Alignment(0.6, 0.6),
    children: [
      const CircleAvatar(
        backgroundImage: AssetImage('resources/images/pic1.png'),
        radius: 100,
      ),
      Container(
        decoration: const BoxDecoration(
          color: Colors.black45,
        ),
        child: const Text(
          'Mia B',
          style: TextStyle(
            fontSize: 20,
            fontWeight: FontWeight.bold,
            color: Colors.white,
          ),
        ),
      ),
    ],
  );
}
