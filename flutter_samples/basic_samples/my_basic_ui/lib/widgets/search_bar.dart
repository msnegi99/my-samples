import 'package:flutter/material.dart';

Widget searchBar() {

  return Container(
    height: 40,
    width: double.infinity,
    decoration: BoxDecoration(
      color: Colors.white,
      boxShadow: [
        BoxShadow(
          color: Colors.grey.withOpacity(0.5),
          spreadRadius: 3,
          blurRadius: 4,
          offset: Offset(0, 3), // changes position of shadow
        ),
      ],
      borderRadius: BorderRadius.circular(15),
    ),
    child: TextFormField(
      decoration: InputDecoration(
        border: InputBorder.none,
        prefixIcon: Icon(
          Icons.search_sharp,
          size: 30,
          color: Colors.black.withOpacity(.5),
        ),
        hintText: "   Search",
      ),
    ),
  );
}
