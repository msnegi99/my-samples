import 'package:flutter/material.dart';

class ProgressBarPage extends StatefulWidget {
  const ProgressBarPage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  State<ProgressBarPage> createState() => _ProgressBarPageState(); // Creates the mutable state object
}

class _ProgressBarPageState extends State<ProgressBarPage> {

  bool visible = true ;

  loadProgress(){
    if(visible == true){
      setState(() {
        visible = false;
      });
    }
    else{
      setState(() {
        visible = true;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.black,
          foregroundColor: Colors.white,
          title: new Text(widget.title),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[

              Visibility(
                  maintainSize: true,
                  maintainAnimation: true,
                  maintainState: true,
                  visible: visible,
                  child: Container(
                      margin: EdgeInsets.only(top: 50, bottom: 30),
                      child: CircularProgressIndicator(),               //LinearProgressIndicator(),
                  )
              ),

              ElevatedButton(
                onPressed: loadProgress,
                child: Text('Show Hide Circular Progress'),
              ),

            ],
          ),
        ));
  }

}