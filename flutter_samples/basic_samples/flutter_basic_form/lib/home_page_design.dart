import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_basic_form/screens/bottom_nav_page_1.dart';
import 'package:flutter_basic_form/screens/bottom_nav_page_2.dart';
import 'package:flutter_basic_form/screens/bottom_nav_page_3.dart';
import 'package:flutter_basic_form/screens/form_page_format.dart';
import 'package:flutter_basic_form/screens/simple_page_format.dart';import 'BottomNavigation.dart';

import 'modals/first_model.dart';
import 'nav_drawer_widget.dart';
import 'screens/first_screen.dart';

class MyHomePage extends StatefulWidget {
  MyHomePage({super.key, required this.title});

  String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _selectedIndex = 0;

  List<GlobalKey<NavigatorState>> _navigatorKeys = [
    GlobalKey<NavigatorState>(),
    GlobalKey<NavigatorState>(),
    GlobalKey<NavigatorState>()
  ];


  @override
  void initState() {
    super.initState();
    _selectedIndex = 0;
    setTitle('Page 1');
  }

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

  void setTitle(String title){
    widget.title = title;
  }

  Map<String, WidgetBuilder> _routeBuilders(BuildContext context, int index) {
    return {
      '/': (context) {
        return [
          BottomPage1(),
          BottomPage2(),
          BottomPage3(),
        ].elementAt(index);
      },
    };
  }

  Widget _buildOffstageNavigator(int index) {
    var routeBuilders = _routeBuilders(context, index);

    return Offstage(
      offstage: _selectedIndex != index,
      child: Navigator(
        key: _navigatorKeys[index],
        onGenerateRoute: (routeSettings) {
          return MaterialPageRoute(
            builder: (context) => routeBuilders[routeSettings.name]!(context),
          );
        },
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return PopScope(
      canPop: false,
      onPopInvoked: (didPop) async {
        if (didPop) return; // If the system already handled the pop, do nothing

        final NavigatorState? currentNavigator = _navigatorKeys[_selectedIndex].currentState;

        if (currentNavigator != null && currentNavigator.canPop()) {
          currentNavigator.pop();
        } else if (_selectedIndex != 0) {
          setState(() {
            _selectedIndex = 0;
          });
        } else {
          if (!Navigator.of(context).canPop()) {
            // This means we are on the home screen (root route)
            // You can show an exit confirmation dialog here, or simply exit the app.
            exitDialog(context);
          } else {
            // We are not on the home screen, so perform regular pop
            Navigator.of(context).pop();
          }
        }
      },
      child: Scaffold(
        drawer: NavigationDrawerWidget(),
        appBar: AppBar(
          backgroundColor: Colors.blue,
          foregroundColor: Colors.white,
          title: new Text(widget.title),
          actions: [
            IconButton(icon: Icon(Icons.search), onPressed: () {}),
            IconButton(icon: Icon(Icons.add), onPressed: () {}),
          ],
        ),
        bottomNavigationBar: BottomNavigationBar(
          items:const <BottomNavigationBarItem>[
            BottomNavigationBarItem(icon: Icon(Icons.home),label: 'Home',),
            BottomNavigationBarItem(icon: Icon(Icons.search),label: 'Search',),
            BottomNavigationBarItem(icon: Icon(Icons.person),label: 'Profile',),
          ],
          currentIndex: _selectedIndex,
          selectedItemColor: Colors.amberAccent,
          backgroundColor: Colors.blue,
          unselectedItemColor: Colors.white,
          showSelectedLabels: false,
          showUnselectedLabels: false,
          onTap: (index) {
            setState(() {
              _selectedIndex = index;
              switch (index) {
                case 0:
                  setTitle("Page 1");
                  break;
                case 1:
                  setTitle("Page 2");
                  break;
                case 2:
                  setTitle("Page 3");
                  break;
              }
            });
          },
        ),
        body: Stack(
          children: [
            _buildOffstageNavigator(0),
            _buildOffstageNavigator(1),
            _buildOffstageNavigator(2),
          ],
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: (){},
          tooltip: 'Increment',
          child: const Icon(Icons.add),
        ),
      ),
    );
  }
}

