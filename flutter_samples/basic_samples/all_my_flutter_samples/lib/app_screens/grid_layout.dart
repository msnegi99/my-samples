import 'package:flutter/material.dart';
import 'dart:ui' as ui;

class GridLayoutPage extends StatefulWidget {
  const GridLayoutPage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<GridLayoutPage> createState() => GridLayoutPageState();
}

class GridLayoutPageState extends State<GridLayoutPage> {
  @override
  Widget build(BuildContext context) {
    final ui.Size logicalSize = MediaQuery.of(context).size;
    final double _height = logicalSize.height;
    return new Scaffold(
        appBar: new AppBar(backgroundColor: new Color(0xFF26C6DA)),
        body: new SingleChildScrollView(
          child: new Column(
            //mainAxisAlignment: MainAxisAlignment.spaceBetween,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              new Container(
                  height: 300.0,
                  child: new Column(
                    children: <Widget>[
                      new TextField(
                        decoration: const InputDecoration(
                          labelText: "Description",
                        ),
                      ),
                      new TextField(
                        decoration: const InputDecoration(
                          labelText: "Description",
                        ),
                      ),
                      new TextField(
                        decoration: const InputDecoration(
                          labelText: "Description",
                        ),
                      ),
                    ],
                  )
              ),
              new Container(
                padding: new EdgeInsets.only(top: (_height - 450.0)),
                margin: new EdgeInsets.only(bottom: 16.0),
                child: new FloatingActionButton(
                    backgroundColor: new Color(0xFFE57373),
                    child: new Icon(Icons.check),
                    onPressed: (){}
                ),
              )
            ],
          ),
        )
    );
  }
}