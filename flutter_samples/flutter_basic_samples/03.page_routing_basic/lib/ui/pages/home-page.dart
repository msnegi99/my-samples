import 'package:flutter/material.dart';
import 'package:page_routes/ui/data.dart';
import 'package:page_routes/ui/pages/employe-page.dart';
import 'package:page_routes/ui/pages/profile-page.dart';
import 'package:page_routes/ui/pages/search-page.dart';
import 'package:page_routes/ui/pages/setting-page.dart';

class MyHomePage extends StatelessWidget {
  static const routeName = "/home";
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            IconButton(
              onPressed: () {
                Navigator.of(context).pushNamed(SearchPage.routeName);
              },
              icon: Icon(
                Icons.search,
                size: 50,
                color: Colors.blue,
              ),
            ),
            SizedBox(
              height: 20,
            ),
            TextButton(
              onPressed: () {
                Navigator.of(context).pushNamed(ProfilePage.routeName,
                    arguments: Person(
                      name: "mg mg",
                      location: "yangon",
                    ));
              },
              child: Text(
                "Profile Page",
                style: TextStyle(
                  fontWeight: FontWeight.bold,
                  fontSize: 30,
                  color: Colors.amber[900],
                ),
              ),
            ),
            SizedBox(
              height: 20,
            ),
            GestureDetector(
              onTap: () {
                Navigator.of(context).pushNamed(SettingPage.routeName);
              },
              child: Text(
                "Setting Page",
                style: TextStyle(
                  fontWeight: FontWeight.bold,
                  fontSize: 30,
                  color: Colors.amber[900],
                ),
              ),
            ),
            SizedBox(height: 20),
            GestureDetector(
              onTap: () {
                Navigator.of(context).pushNamed(
                  EmployeePage.routeName,
                  arguments: Employee(id: "001", phone: "002"),
                );
              },
              child: Text(
                "Employe Page",
                style: TextStyle(
                  fontWeight: FontWeight.bold,
                  fontSize: 30,
                  color: Colors.amber[900],
                ),
              ),
            )
          ],
        ),
      ),
    );
  }
}
