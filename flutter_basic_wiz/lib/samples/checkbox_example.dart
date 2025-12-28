import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

class CheckBoxPage extends StatefulWidget {
  const CheckBoxPage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  State<CheckBoxPage> createState() => _CheckBoxPageState(); // Creates the mutable state object
}

class _CheckBoxPageState extends State<CheckBoxPage> {

  bool? value = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.black,
        foregroundColor: Colors.white,
        title: new Text(widget.title),
      ),
      body: Center(
        child: Card(
          child: Padding(
            padding: const EdgeInsets.all(15.0),
            child: SizedBox(
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height,
              child: Column(
                children: [
                  Text(
                    'Algorithms',
                    style: TextStyle(color: Colors.greenAccent[400],fontSize: 30),
                  ),
                  const SizedBox(height: 10),
                  Row(
                    children: <Widget>[
                      const SizedBox(width: 10),
                      const Text(
                        'Checkbox example: ',
                        style: TextStyle(fontSize: 17.0),
                      ),
                      const SizedBox(width: 10),
                      Checkbox(
                        tristate: true, // Example with tristate
                        value: value,
                        onChanged: (bool? newValue) {
                          setState(() {
                            value = newValue;
                          });
                        },
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }

}