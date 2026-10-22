import 'package:flutter/material.dart';
import 'package:page_routes/page-route.dart';
import 'package:page_routes/ui/pages/employe-page.dart';
import 'package:page_routes/ui/pages/home-page.dart';
import 'package:page_routes/ui/pages/profile-page.dart';
import 'package:page_routes/ui/pages/search-page.dart';
import 'package:page_routes/ui/pages/setting-page.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      initialRoute: MyHomePage.routeName,
      onGenerateRoute: MypageRoute.generateRoute,
      // routes: {
      //   MyHomePage.routeName: (context) => MyHomePage(),
      //   ProfilePage.routeName: (context) => ProfilePage(),
      //   EmployeePage.routeName: (context) => EmployeePage(),
      //   SearchPage.routeName: (context) => SearchPage(),
      //   SettingPage.routeName: (context) => SettingPage(),
      // },
      // initialRoute: MyHomePage.routeName,
      // home: MyHomePage(),
    );
  }
}
