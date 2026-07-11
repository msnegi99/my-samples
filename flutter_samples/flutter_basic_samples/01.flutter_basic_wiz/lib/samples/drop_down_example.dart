import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

class DropDownPage extends StatefulWidget {
  const DropDownPage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  State<DropDownPage> createState() => _DropDownPageState(); // Creates the mutable state object
}

class _DropDownPageState extends State<DropDownPage> {
  List _fruits = ["Apple", "Banana", "Pineapple", "Mango", "Grapes"];
  late List<DropdownMenuItem<String>> _dropDownMenuItems;
  late String _selectedFruit;

  @override
  void initState() {
    _dropDownMenuItems = buildAndGetDropDownMenuItems(_fruits);
    _selectedFruit = _dropDownMenuItems[0].value!;
    super.initState();
  }

  List<DropdownMenuItem<String>> buildAndGetDropDownMenuItems(List fruits) {
    List<DropdownMenuItem<String>> items = [];
    for (String fruit in fruits) {
      items.add(DropdownMenuItem(value: fruit, child: Text(fruit)));
    }
    return items;
  }

  void changedDropDownItem(String selectedFruit) {
    setState(() {
      _selectedFruit = selectedFruit;
    });
  }

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
                Text("Please choose a fruit: "),
                DropdownButton(
                  value: _selectedFruit,
                  items: _dropDownMenuItems,
                  onChanged: (value) {
                    setState(() {
                      _selectedFruit = value.toString();
                      Fluttertoast.showToast(
                        timeInSecForIosWeb: 3,
                        msg: 'You Selected ${value.toString()}', // Message to display in the toast
                        backgroundColor: Colors.green, // Background color of the toast
                      );
                    });
                  },
                )
              ],
            )),
      ),
    );
  }

}