import 'package:flutter/material.dart';
import 'package:page_routes/ui/data.dart';

class ProfilePage extends StatelessWidget {
  static const routeName = "/profile-page";
  @override
  Widget build(BuildContext context) {
    final Person ps = ModalRoute.of(context)?.settings.arguments as Person;
    return Scaffold(
      appBar: AppBar(
        title: Text("Profile Page"),
      ),
      body: Column(
        children: [
          Text(ps.name),
          Text(ps.location),
        ],
      ),
    );
  }
}
