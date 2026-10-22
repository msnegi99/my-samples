import 'package:flutter/material.dart';

class FontPage extends StatefulWidget {
  const FontPage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  State<FontPage> createState() => _FontPageState(); // Creates the mutable state object
}

class _FontPageState extends State<FontPage> {

  TextStyle getCustomFontTextStyle() {
    return const TextStyle(
        color: Colors.blueAccent,
        fontFamily: 'Pacifico',
        fontWeight: FontWeight.w400,
        fontSize: 36.0);
  }


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
            child: Text("The quick brown fox jumps over the lazy dog",
                textAlign: TextAlign.center,
                style: getCustomFontTextStyle()),
          ),
        )
    );
  }

}