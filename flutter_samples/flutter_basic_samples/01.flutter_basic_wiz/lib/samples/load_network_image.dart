import 'package:flutter/material.dart';

class NetworkImagePage extends StatefulWidget {
  const NetworkImagePage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  State<NetworkImagePage> createState() => _NetworkImagePageState(); // Creates the mutable state object
}

class _NetworkImagePageState extends State<NetworkImagePage> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.black,
          foregroundColor: Colors.white,
          title: new Text(widget.title),
        ),
        body: ListView(
            children:<Widget>[
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Image.network("https://images.pexels.com/photos/213780/pexels-photo-213780.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
              ),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Image.network("https://images.pexels.com/photos/2899097/pexels-photo-2899097.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
              ),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Image.network("https://images.pexels.com/photos/2820884/pexels-photo-2820884.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
              ),
            ]
        ),
    );
  }

}