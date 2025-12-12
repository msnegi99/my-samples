import 'package:flutter/material.dart';
import 'package:page_routing/page_routes.dart';
import 'package:page_routing/screens/content_view.dart';
import 'package:page_routing/screens/first_screen.dart';
import 'package:page_routing/screens/fourth_screen.dart';
import 'package:page_routing/screens/red_page.dart';
import 'package:page_routing/screens/second_screen.dart';
import 'package:page_routing/screens/third_screen.dart';
import 'modals/first_model.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
      ),
      //home: const MyHomePage(title: 'Flutter Demo Home Page'),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
      onGenerateRoute: MyRoutes.onGenerateRoutesMethod,
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  void navigateToScreen(BuildContext context, String screenName) {
    switch (screenName) {
      case "HomeScreen":
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(builder: (context) => ThirdScreen()),
        );
        break;
      case "SecondScreen":
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(builder: (context) => SecondScreen()),
        );
        break;
      default:
        print("Screen not found");
    }
  }

  @override
  Widget build(BuildContext context) {
    return PopScope(
      canPop: false,
      onPopInvoked: (didPop) {
        if (didPop) {
          return; // If the pop was handled by another PopScope, do nothing.
        }
        if (!Navigator.of(context).canPop()) {
          // This means we are on the home screen (root route)
          // You can show an exit confirmation dialog here, or simply exit the app.
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
            }
          });
        } else {
          // We are not on the home screen, so perform regular pop
          Navigator.of(context).pop();
        }
      },
      child: Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            InkWell(
              onTap: () {
                Navigator.pushNamed(
                  context,
                  FirstScreen.routeName,
                  arguments: FirstModel(number: 1, name: "Nour", gpa: 3),
                );
              },
              splashColor: Colors.blue,
              child: Container(
                padding: EdgeInsets.all(20.0),
                color: Colors.grey[200],
                child: Text('First Screen'),
              ),
            ),
            ElevatedButton(
              onPressed: () {
                /* Navigator.push(context, route); */
                Navigator.of(context)
                    .push<int>(
                  MaterialPageRoute(builder: (Redcontext) => RedPage()),
                )
                    .then((int? value) => debugPrint('incoming number $value'));
              },
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.red.shade300,
              ),
              child: Text('enter red page Android'),
            ),
            ElevatedButton(
              onPressed: () {
                /* Navigator.push(context, route); */
                Navigator.of(context)
                    .push<int>(
                  MaterialPageRoute(
                    builder: (Redcontext) => SecondScreen(),
                  ),
                )
                    .then((int? value) => debugPrint('incoming number $value'));
              },
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.red.shade300,
              ),
              child: Text('enter red page Android'),
            ),
            TextButton(
              style: ButtonStyle(
                backgroundColor: MaterialStateProperty.all<Color>(Colors.blue),
              ),
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const ContentView()),
                );
              },
              child: const Text(
                "Go to contentView",
                style: TextStyle(
                  fontWeight: FontWeight.w500,
                  fontSize: 14,
                  color: Colors.black,
                ),
              ),
            ),
            ElevatedButton(
              onPressed: () {
                /* Navigator.push(context, route); */
                Navigator.of(context)
                    .push<int>(
                  MaterialPageRoute(
                    builder: (Redcontext) => FourthScreen(),
                  ),
                )
                    .then((int? value) => debugPrint('incoming number $value'));
              },
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.red.shade300,
              ),
              child: Text('enter red page Android'),
            ),
          ],
        ),
      ),
    ),
    );
  }
}
