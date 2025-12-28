import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:get/get_navigation/src/root/get_material_app.dart';
import 'package:getx_example/screens/content_view.dart';
import 'package:getx_example/screens/first_screen.dart';
import 'package:getx_example/screens/fourth_screen.dart';
import 'package:getx_example/screens/red_page.dart';
import 'package:getx_example/screens/second_screen.dart';
import 'package:getx_example/screens/third_screen.dart';
import 'modals/first_model.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.red),
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
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
        /*Navigator.pushReplacement(
          context,
          MaterialPageRoute(builder: (context) => ThirdScreen()),
        );*/
        Get.to(() => ThirdScreen());
        break;
      case "FirstScreen":
        break;
      case "SecondScreen":
        /*Navigator.pushReplacement(
          context,
          MaterialPageRoute(builder: (context) => SecondScreen()),
        );*/
        Get.to(() => SecondScreen(),
          duration: Duration(seconds: 3),
          transition: Transition.zoom,
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
                  onPressed: (){
                    //Navigator.of(context).pop(false);  // Don't exit
                    Get.back(result: false);
                  },
                  child: const Text('No'),
                ),
                TextButton(
                  onPressed: () {
                      //Navigator.of(context).pop(true); // Exitexit(0)
                      Get.back(result: true);
                  },
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
        } else {
          // We are not on the home screen, so perform regular pop
          //Navigator.of(context).pop();
          Get.back();
        }
      },
      child: Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.red,
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            InkWell(
              onTap: () {
                /*Navigator.pushNamed(
                  context,
                  FirstScreen.routeName,
                  arguments: FirstModel(number: 1, name: "Nour", gpa: 3),
                );*/
                Get.to(() => FirstModel(number: 1, name: "Nour", gpa: 3),);
              },
              splashColor: Colors.blue,
              child: Container(
                padding: EdgeInsets.all(20.0),
                color: Colors.grey[200],
                child: Text('First Screen'),
              ),
            ),
            ElevatedButton(
              onPressed: () async {
                /* Navigator.push(context, route); */
                /*Navigator.of(context)
                    .push<int>(
                  MaterialPageRoute(builder: (Redcontext) => RedPage()),
                )
                .then((int? value) => debugPrint('incoming number $value'));*/
                var a = await Get.to(() => RedPage());
                print("returned value : " + a);
              },
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.red.shade300,
              ),
              child: Text('enter red page Android'),
            ),
            ElevatedButton(
              onPressed: () async {
                /* Navigator.push(context, route); */
                /*Navigator.of(context).push<int>(MaterialPageRoute(
                    builder: (Redcontext) => SecondScreen(),
                ),)
                .then((int? value) => debugPrint('incoming number $value'));*/
                var a = await Get.to(() => SecondScreen());
                print("returned value : " + a);

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
                /*Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const ContentView()),
                );*/
                Get.to(() => ContentView());
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
              onPressed: () async {
                /* Navigator.push(context, route); */
                /*Navigator.of(context).push<int>(
                  MaterialPageRoute(builder: (Redcontext) => FourthScreen(),
                  ),
                )
                .then((int? value) => debugPrint('incoming number $value'));*/
                var a = await Get.to(() => FourthScreen());
                print("returned value : " + a);
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
