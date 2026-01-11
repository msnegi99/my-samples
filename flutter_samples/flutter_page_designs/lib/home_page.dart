import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_page_designs/screens/dynamic_form/dynamic_form_screen.dart';
import 'package:flutter_page_designs/screens/image_picker.dart';
import 'package:flutter_page_designs/screens/normal_form/normal_form_page.dart';
import 'package:flutter_page_designs/screens/login.dart';
import 'package:flutter_page_designs/screens/page_structure_example.dart';
import 'package:flutter_page_designs/screens/calculator.dart';


class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  void exitDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Exit App?'),
        content: const Text('Do you want to exit the application?'),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(false), // Don't exit
            child: const Text('No'),
          ),
          TextButton(
            onPressed: () => Navigator.of(context).pop(true), // Exitexit(0)
            child: const Text('Yes'),
          ),
        ],
      ),
    ).then((value) {
      if (value == true) {
        // Exit the app (e.g., SystemNavigator.pop() for Android)
        // Note: SystemNavigator.pop() is for Android; iOS handles app exit differently.
        SystemNavigator.pop();
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: <Widget>[
        Container(
          decoration: BoxDecoration(
            image: DecorationImage(
              image: AssetImage('assets/images/city.jpg'),
              fit: BoxFit.cover,
            ),
          ),
        ),
        PopScope(
          canPop: false,
          onPopInvoked: (didPop) async {
            if (didPop)
              return; // If the system already handled the pop, do nothing
            if (!Navigator.of(context).canPop()) {
              // This means we are on the home screen (root route)
              // You can show an exit confirmation dialog here, or simply exit the app.
              exitDialog(context);
            } else {
              // We are not on the home screen, so perform regular pop
              Navigator.of(context).pop();
            }
          },
          child: Scaffold(
            appBar: AppBar(
              backgroundColor: Colors.black,
              foregroundColor: Colors.white,
              title: new Text(widget.title),
            ),
            body: ListView(
              shrinkWrap: true,
              children: <Widget>[

                new ListTile(
                  title: Text('Page Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) => AssistPage(title: 'Page Example'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('Login Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) => LoginPage(title: 'Login Example'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('Normal Form Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) => FormPageFormat(title: 'Normal Form Example'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('Calculator Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) => Calculator(title: 'Calculator Example'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('Dynamic Form Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) => DynamicForm(title: 'Dynamic Form Example'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('Image Picker Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) => ImagePickerPage(title: 'Image Picker Example'),
                      ),
                    );
                  },
                ),


              ],
            ),
          ),
        ),
      ],
    );
  }
}
