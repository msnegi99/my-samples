import 'package:flutter/material.dart';
import 'package:flutter_basic_form/screens/first_screen.dart';
import 'package:flutter_basic_form/screens/form_page_format.dart';
import 'package:flutter_basic_form/screens/simple_page_format.dart';
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

      case SimplePage.routeName:
        return MaterialPageRoute(builder: (context) => SimplePage(title: 'Simple Page',));

      case FormPageFormat.routeName:
        return MaterialPageRoute(builder: (context) => FormPageFormat(title: 'Form Page',));

    }

    return MaterialPageRoute(
      builder: (context) =>
          Scaffold(body: Center(child: Text("No route Defined"))),
    );
  }
}



