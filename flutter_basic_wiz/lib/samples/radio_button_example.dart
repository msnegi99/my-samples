import 'package:flutter/material.dart';

class RadioButtonPage extends StatefulWidget {
  const RadioButtonPage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  State<RadioButtonPage> createState() => _RadioButtonPageState(); // Creates the mutable state object
}

class _RadioButtonPageState extends State<RadioButtonPage> {

  int _selectedValue = 1;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.black,
          foregroundColor: Colors.white,
          title: new Text(widget.title),
        ),
        body: ListView(
          children: <Widget>[

            RadioListTile(
              title: Text('Option 1'),
              subtitle: Text('Subtitle for Option 1'),
              value: 1,
              groupValue: _selectedValue,
              onChanged: (value) {
                setState(() {
                  _selectedValue = value!;
                });
              },
            ),

            RadioListTile(
              title: Text('Option 2'),
              subtitle: Text('Subtitle for Option 2'),
              value: 2,
              groupValue: _selectedValue,
              onChanged: (value) {
                setState(() {
                  _selectedValue = value!;
                });
              },
            ),

            RadioListTile(
              title: Text('Option 3'),
              subtitle: Text('Subtitle for Option 3'),
              value: 3,
              groupValue: _selectedValue,
              onChanged: (value) {
                setState(() {
                  _selectedValue = value!;
                });
              },
            ),

          ],
        ),
    );
  }

}