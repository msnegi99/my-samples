import 'package:flutter/material.dart';

class GridViewManualPage extends StatefulWidget {
  const GridViewManualPage({super.key,required this.title,});
  final String title;

  @override
  State<GridViewManualPage> createState() => _GridViewManualPageState();
}

class _GridViewManualPageState extends State<GridViewManualPage> {

  GestureDetector getStructuredGridCell(name, image) {
    // Wrap the child under GestureDetector to setup a on click action
    return GestureDetector(
      onTap: () {
        print("onTap called.$name");
      },
      child: Card(
          elevation: 1.5,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            mainAxisSize: MainAxisSize.min,
            verticalDirection: VerticalDirection.down,
            children: <Widget>[
              Image(image: AssetImage('assets/' + image)),
              Center(
                child: Text(name),
              )
            ],
          )),
    );
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.black,
        foregroundColor: Colors.white,
        title: Text(widget.title),
      ),
      body: GridView.count(
        primary: true,
        padding: const EdgeInsets.all(1.0),
        crossAxisCount: 2,
        childAspectRatio: 0.85,
        mainAxisSpacing: 1.0,
        crossAxisSpacing: 1.0,
        children: <Widget>[
          getStructuredGridCell("Facebook", "social/facebook.png"),
          getStructuredGridCell("Twitter", "social/twitter.png"),
          getStructuredGridCell("Instagram", "social/instagram.png"),
          getStructuredGridCell("Linkedin", "social/linkedin.png"),
          getStructuredGridCell("Google Plus", "social/google_plus.png"),
          getStructuredGridCell("Launcher Icon", "img/ic_launcher.png"),
        ],
      ),
    );
  }
}
