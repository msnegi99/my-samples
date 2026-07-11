import 'dart:math';

import 'package:flutter/material.dart';

class AlertDialogPage extends StatefulWidget {
  const AlertDialogPage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  State<AlertDialogPage> createState() => _AlertDialogPageState(); // Creates the mutable state object
}

class _AlertDialogPageState extends State<AlertDialogPage> {

  Future<bool> _AlertPop() {
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text("Discard changes?"),
          content: const Text("When exiting the information will be lost"),
          actions: [
            ElevatedButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: const Text("Cancel"),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.pop(context);
                Navigator.pop(context);
              },
              child: const Text("Confirm"),
            ),
          ],
        );
      },
    );
    return Future.value(false);
  }

  Future<void> _SimplePop(BuildContext context) async {
    final String? result = await showDialog<String>(
      context: context,
      builder: (BuildContext context) {
        return SimpleDialog(
          title: const Text('Select an Option'), // Optional title
          children: <Widget>[
            SimpleDialogOption(
              onPressed: () {
                // Dismiss the dialog and return the value 'Flutter'
                Navigator.pop(context, 'Flutter');
              },
              child: const Text('Flutter'),
            ),
            SimpleDialogOption(
              onPressed: () {
                // Dismiss the dialog and return the value 'React Native'
                Navigator.pop(context, 'React Native');
              },
              child: const Text('React Native'),
            ),
          ],
        );
      },
    );

    if (result != null) {
      print('User selected: $result');
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('You selected: $result')),
      );
    } else {
      print('Dialog dismissed (cancelled)');
    }
  }

  Future<bool> _showPop() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return Expanded(
          child: AlertDialog(
            title: Text('Welcome'),
            content: Text('Example of show dialog'),
            actions: [
              TextButton(
                onPressed: () {},
                child: Text('CANCEL'),
              ),
              TextButton(
                onPressed: () {},
                child: Text('ACCEPT'),
              ),
            ],
          ),
        );
      },
    );
    return Future.value(false);
  }

  @override
  Widget build(BuildContext ctx) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.black,
        foregroundColor: Colors.white,
        title: new Text(widget.title),
      ),
      body: Container(
        child: Center(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[

                ElevatedButton(
                    child: Text('Alert Dialog'),
                    onPressed: () {
                      _AlertPop();
                    }),

                const SizedBox(height: 10),

                ElevatedButton(
                    child: Text('Simple Dialog'),
                    onPressed: () {
                      _SimplePop(context);
                    }),

                const SizedBox(height: 10),

                ElevatedButton(
                    child: Text('show Dialog'),
                    onPressed: () {
                      _showPop();
                    }),

              ],
            )),
      ),
    );
  }

}