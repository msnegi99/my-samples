import 'package:flutter/material.dart';
import 'package:page_designs/consts.dart';
import 'package:page_designs/pages/buttons.dart';
import 'package:page_designs/pages/grid_layout.dart';
import 'package:page_designs/pages/simple_interest_calculator.dart';
import 'package:page_designs/pages/status_handling.dart';

class NavigationDrawerWidget extends StatelessWidget {
  final padding = EdgeInsets.symmetric(horizontal: 20);
  @override
  Widget build (BuildContext context) {
    final name = "John Potter";
    final email = "johnpotter@wiz.com";
    final imageUrl = "https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg";
    return Drawer(
      child: Material(
        color: IndigoBlue,
        child: ListView(
          children: <Widget>[
            buildHeader(
                name: name,
                email: email,
                imageUrl: imageUrl,
        ),
            const SizedBox(height: 16,),
            buildMenuItem(
              text: 'Grid Layout',
              icon: Icons.people_alt_outlined,
              onClick: () => selectedItem(context, 0),
            ),
            const SizedBox(height: 16,),
            buildMenuItem(
              text: 'Simple Interest Calculator',
              icon: Icons.favorite_border,
              onClick: () => selectedItem(context, 1),
            ),
            const SizedBox(height: 16,),
            buildMenuItem(
              text: 'Stateful Widgets',
              icon: Icons.group_work_sharp,
              onClick: () => selectedItem(context, 2),
            ),
            const SizedBox(height: 16,),
            buildMenuItem(
              text: 'Button Example',
              icon: Icons.group_work_sharp,
              onClick: () => selectedItem(context, 3),
            ),
          ],
        ),
      ),
    );
  }
}

Widget buildMenuItem({
  required String text,
  required IconData icon,
  VoidCallback? onClick,
}) {
  final color = White;
  final hoverColor = AccentBlue;

  return ListTile(
    leading: Icon(icon, color: color,),
    title: Text(text, style: TextStyle(color: color),),
    onTap: onClick,
    hoverColor: hoverColor,
  );
}

Widget buildHeader({
  required String name,
  required String email,
  required String imageUrl,
}) => InkWell(
  child: Container(
    padding: EdgeInsets.symmetric(vertical: 40),
    child: Row(
      children: [
        CircleAvatar(radius: 30, backgroundImage: NetworkImage(imageUrl),),
        SizedBox(width: 20),
        Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              name,
              style: TextStyle(fontSize: 20, color: Colors.white),
            ),
            const SizedBox(height: 4),
            Text(
              email,
              style: TextStyle(fontSize: 14, color: Colors.white),
            ),
          ],
        ),
        Spacer(),
        CircleAvatar(
          radius: 24,
          backgroundColor: AccentBlue,
          child: Icon(Icons.add),
        ),
      ],
  ),
  ),
);

void selectedItem(BuildContext context, int index){
  Navigator.of(context).pop();

  switch (index) {
    case 0:
      Navigator.of(context).push(MaterialPageRoute(
        builder: (context) => GridLayoutPage(title: 'Grid Layout Page',),
      )); //MaterialPageRoute
      break;
    case 1:
      Navigator.of(context).push(MaterialPageRoute(
        builder: (context) => const Calculator(title: 'Simple Interest Calculator',),
      ));
      break;
    case 2:
      Navigator.of(context).push(MaterialPageRoute(
        builder: (context) => const FavoriteCity(title: 'Stateful Widgets Page',),
      ));
      break;
    case 3:
      Navigator.of(context).push(MaterialPageRoute(
        builder: (context) => const ButtonExample(title: 'Button Example',),
      ));
      break;
  }
}