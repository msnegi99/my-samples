import 'package:flutter/material.dart';

class ExpandidPage extends StatefulWidget {
  const ExpandidPage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  State<ExpandidPage> createState() => _ExpandidPageState(); // Creates the mutable state object
}

class _ExpandidPageState extends State<ExpandidPage> {

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

                Text("When we create any child of a row or column we provide the size of the widget according to the screen size but sometimes when we provide more size of child as compared to screen size we get a warning and our widget goes out of the screen for resolving this we put a child of a row or column in an expanded widget so that the child occupies only the available space along the main axis."),
                Expanded(
                  child: Container(
                    color: Colors.green,
                    padding: const EdgeInsets.all(14),
                    child: Image.asset('assets/images/city.jpg'),
                  ),
                ),

              ],
            )),
      ),
    );
  }

}