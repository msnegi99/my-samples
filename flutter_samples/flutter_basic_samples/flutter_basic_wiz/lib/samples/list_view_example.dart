import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

class ListViewWidgetPage extends StatefulWidget {
  const ListViewWidgetPage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  State<ListViewWidgetPage> createState() => _ListViewWidgetPageState(); // Creates the mutable state object
}

class _ListViewWidgetPageState extends State<ListViewWidgetPage> {
  List<String> fruits = ['Mango','Orange','Banana'];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.black,
        foregroundColor: Colors.white,
        title: new Text(widget.title),
      ),
      body: ListView.builder(
        itemCount: fruits.length,
        itemBuilder: (context, index) {
          return ListTile(
            title: Text(fruits[index]),
            onTap: (){
              Fluttertoast.showToast(
                timeInSecForIosWeb: 3,
                msg: 'You Selected ${fruits[index]}', // Message to display in the toast
                backgroundColor: Colors.green, // Background color of the toast
              );
            },
          );
        },
      ),
    );
  }

}