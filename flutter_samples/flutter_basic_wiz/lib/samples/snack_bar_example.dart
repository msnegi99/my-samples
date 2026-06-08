import 'package:flutter/material.dart';

class SnackBarPage extends StatefulWidget {
  const SnackBarPage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  State<SnackBarPage> createState() => _SnackBarPageState(); // Creates the mutable state object
}

class _SnackBarPageState extends State<SnackBarPage> {

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

                    ElevatedButton(
                    child: Text('Show SnackBar'),
                    onPressed: () {
                        ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(
                              content: Text('Hello! I am 2nd SnackBar :)'),
                              duration: Duration(seconds: 2),
                              action: SnackBarAction(label: 'Hit me action', onPressed: (){}),
                            )
                        );
                    }),

              ],
            )),
      ),
    );
  }

}