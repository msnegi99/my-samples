import 'package:flutter/material.dart';

class LoadImagePage extends StatefulWidget {
  const LoadImagePage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  State<LoadImagePage> createState() => _LoadImagePageState(); // Creates the mutable state object
}

class _LoadImagePageState extends State<LoadImagePage> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.black,
        foregroundColor: Colors.white,
        title: new Text(widget.title),
      ),
      body: Container(
          width: 200,
          height: 200,
          child: Center(
            child: Text("Hello World!",style: TextStyle(color: Colors.white),),
          ),
          decoration: BoxDecoration(
                        image: DecorationImage(image: AssetImage('assets/social/facebook.png'),fit: BoxFit.cover)
                      ),
        )
    );
  }

}