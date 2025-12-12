import 'package:flutter/material.dart';

class SimplePage extends StatefulWidget {
  static const String routeName = '/simple-page';

  const SimplePage({super.key, required this.title});
  final String title;

  @override
  State<SimplePage> createState() => _SimplePageState();
}

class _SimplePageState extends State<SimplePage> {
  final _formKey = GlobalKey<FormState>();

  // Controllers
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _dateController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return PopScope(
        canPop: true,    //- false to disable pop back
        onPopInvoked: (didPop) {
          if (didPop) {
            return; 	// If the pop was handled by another PopScope, do nothing.
          }
          if (Navigator.of(context).canPop()) {
            Navigator.of(context).pop();
          }
        },
        child: Scaffold(
          appBar: new AppBar(
            backgroundColor: Colors.blue,
            foregroundColor: Colors.white,
            title: new Text(widget.title),
            actions: [
              IconButton(
                icon: Icon(Icons.search),
                onPressed: () {},
              ),
              IconButton(
                icon: Icon(Icons.add),
                onPressed: () {},
              ),
            ],
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(16),
            child: Form(
              key: _formKey,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [

                  Container(
                    child: Text(
                      'Screen 1',
                      style: TextStyle(
                          color: Colors.white,
                          fontSize: 20
                      ),
                    ),
                    margin: EdgeInsets.all(16),
                  ),
                  ElevatedButton(
                    onPressed: () {
                      //Navigator.pushNamed(context, FormPage.routeName);
                    },
                    child: Text('Go to next screen'),
                  ),

                ],
              ),
            ),
          ),
        )
    );
  }
}
