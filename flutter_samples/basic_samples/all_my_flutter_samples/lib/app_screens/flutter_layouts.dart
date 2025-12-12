import 'package:flutter/material.dart';

class FlutterLayoutsPage extends StatefulWidget {
  const FlutterLayoutsPage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<FlutterLayoutsPage> createState() => FlutterLayoutsPageState();
}

class FlutterLayoutsPageState extends State<FlutterLayoutsPage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Image.asset('resources/images/pic1.png',height: 50,width: 50,),
                Image.asset('resources/images/pic2.png',height: 50,width: 50,),
                Image.asset('resources/images/pic3.png',height: 50,width: 50,),
              ],
            ),
            Column(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Image.asset('resources/images/pic1.png',height: 50,width: 50,),
                Image.asset('resources/images/pic2.png',height: 50,width: 50,),
                Image.asset('resources/images/pic3.png',height: 50,width: 50,),
              ],
            ),
            Row(crossAxisAlignment: CrossAxisAlignment.center, children: [
              Expanded(
                child: Image.asset('resources/images/pic1.png',height: 50,width: 50,),
              ),
              Expanded(
                child: Image.asset('resources/images/pic2.png',height: 50,width: 50,),
              ),
              Expanded(
                child: Image.asset('resources/images/pic3.png',height: 50,width: 50,),
              ),
            ]),
            Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Expanded(
                  child: Image.asset('resources/images/pic1.png',height: 50,width: 50,),
                ),
                Expanded(
                  flex: 2,
                  child: Image.asset('resources/images/pic2.png',height: 50,width: 50,),
                ),
                Expanded(
                  child: Image.asset('resources/images/pic3.png',height: 50,width: 50,),
                ),
              ],
            ),
            Row(
              mainAxisSize: MainAxisSize.min,
              children: [
                Icon(Icons.star, color: Colors.green[500]),
                Icon(Icons.star, color: Colors.green[500]),
                Icon(Icons.star, color: Colors.green[500]),
                const Icon(Icons.star, color: Colors.black),
                const Icon(Icons.star, color: Colors.black),
              ],
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
