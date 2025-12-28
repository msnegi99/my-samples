import 'package:flutter/material.dart';
import 'package:page_routes/ui/pages/employe-page.dart';
import 'package:page_routes/ui/pages/home-page.dart';
import 'package:page_routes/ui/pages/profile-page.dart';
import 'package:page_routes/ui/pages/search-page.dart';
import 'package:page_routes/ui/pages/setting-page.dart';

class MypageRoute {
  static Route<dynamic> generateRoute(RouteSettings settings) {
    switch (settings.name) {
      case MyHomePage.routeName:
        return MaterialPageRoute(builder: (context) => MyHomePage());
        break;
      case ProfilePage.routeName:
        return MaterialPageRoute(builder: (context) => ProfilePage());
        break;
      case SearchPage.routeName:
        return MaterialPageRoute(builder: (context) => SearchPage());
        break;
      case EmployeePage.routeName:
        return MaterialPageRoute(builder: (context) => EmployeePage());
        break;
      case SettingPage.routeName:
        return MaterialPageRoute(builder: (context) => SettingPage());
        break;
      default:
        return _errorRoute();
    }
  }

  static Route<dynamic> _errorRoute() {
    return MaterialPageRoute(builder: (context) {
      return Scaffold(
        appBar: AppBar(
          title: Text('ERROR'),
          centerTitle: true,
        ),
        body: Center(
          child: Text('Page not found'),
        ),
      );
    });

  }
}
