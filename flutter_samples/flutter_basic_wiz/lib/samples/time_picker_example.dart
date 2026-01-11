import 'package:flutter/material.dart';

class TimePickerPage extends StatefulWidget {
  const TimePickerPage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  State<TimePickerPage> createState() => _TimePickerPageState(); // Creates the mutable state object
}

class _TimePickerPageState extends State<TimePickerPage> {
  TimeOfDay _time = TimeOfDay.now();
  late TimeOfDay? picked;

  Future<Null> selectTime(BuildContext context) async {
    picked = await showTimePicker(context: context,initialTime: _time,);
    setState(() {
      _time = picked!;
      print(picked);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.black,
          foregroundColor: Colors.white,
          title: new Text(widget.title),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
                IconButton(iconSize: 80,
                           icon: Icon(Icons.alarm,size: 80,),
                           onPressed: () {
                              selectTime(context);
                           },
                ),
                SizedBox(height: 60,),
                Text('$_time', style: TextStyle(fontSize: 40)),
            ],
          ),
        ),
    );
  }

}