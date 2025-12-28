import 'package:flutter/material.dart';

class ClipRRectPage extends StatefulWidget {
  const ClipRRectPage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  State<ClipRRectPage> createState() => _ClipRRectPageState(); // Creates the mutable state object
}

class _ClipRRectPageState extends State<ClipRRectPage> {

  @override
  Widget build(BuildContext context) {
    return  Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.black,
        foregroundColor: Colors.white,
        title: Text(widget.title),
      ),
      body: Container(
        child: Center(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[

                ClipRRect(
                  borderRadius: BorderRadius.circular(10), // Rounded corners with radius 10
                  child: Image.network('https://picsum.photos/250?image=9'), // Image from the network
                ),

              ],
            )),
      ),
    );
  }

}