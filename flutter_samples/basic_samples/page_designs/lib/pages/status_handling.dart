
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../consts.dart';

class FavoriteCity extends StatefulWidget{
  const FavoriteCity({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<StatefulWidget> createState() {
      return _FavoriteCityState();
  }
}

class _FavoriteCityState extends State<FavoriteCity>{
  String cityName = "";
  var _currencies = ['Rupees','Dollar','Pounds','Others'];
  var _currentItemSelected = 'Rupees';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: IndigoBlue,
        foregroundColor: Colors.white,
        title: Text("Stateful Widget"),
      ),
      body: Container(
        child: Column(
          children: [
            TextField(
              decoration: InputDecoration(
                hintText: "Enter your favorite city",
              ),
              onChanged: (text){
                setState(() {
                  cityName = text;
                });
              },
            ),
            Text("City Name: $cityName"),
            DropdownButton<String>(
              items: _currencies.map((String dropDownStringItem){
                return DropdownMenuItem<String>(
                  value: dropDownStringItem,
                  child: Text(dropDownStringItem),
                );
              }).toList(),
              value: _currentItemSelected,
              onChanged: (String? value) {
                setState(() {
                  this._currentItemSelected = value!;
                });
              },
            )
          ],
        ),
      )
    );
  }
}