import 'package:flutter/material.dart';
import 'package:page_routing/screens/first_screen.dart';
import 'package:page_routing/screens/fourth_screen.dart';
import 'package:page_routing/screens/second_screen.dart';
import 'package:page_routing/screens/third_screen.dart';

import 'modals/first_model.dart';

class MyRoutes {
  static Route<dynamic> onGenerateRoutesMethod(RouteSettings settings) {
    switch (settings.name) {
      case FirstScreen.routeName:
        return MaterialPageRoute(builder: (context) {
          final arg = settings.arguments as FirstModel;
          return FirstScreen(
            name: arg.name,
            gpa: arg.gpa,
            number: arg.number,
          );
        });

      case SecondScreen.routeName:
        return MaterialPageRoute(builder: (context) => SecondScreen());

      case ThirdScreen.routeName:
        return MaterialPageRoute(builder: (context) => ThirdScreen());

      case FourthScreen.routeName:
        return MaterialPageRoute(builder: (context) => FourthScreen());

    }

    return MaterialPageRoute(
      builder: (context) =>
          Scaffold(body: Center(child: Text("No route Defined"))),
    );
  }
}

