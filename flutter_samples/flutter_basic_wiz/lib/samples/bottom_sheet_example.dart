import 'package:flutter/material.dart';

class BottomSheetPage extends StatefulWidget {
  const BottomSheetPage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  _BottomSheetPageState createState() => _BottomSheetPageState();
}

class _BottomSheetPageState extends State<BottomSheetPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.black,
          foregroundColor: Colors.white,
          title: new Text(widget.title),
        ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            new ElevatedButton(
              child: Text(
                "Bottom Sheet",
                style: TextStyle(fontSize: 20),
              ),
              onPressed: () {
                _openBottomSheet(context);
              },
            )
          ],
        ),
      ),
    );
  }
}

void _openBottomSheet(context) {

  showModalBottomSheet(
    context: context,
    builder: (builder) {
      return new Container(
        padding: EdgeInsets.all(5.0),
        child: new Wrap(
          children: <Widget>[
            getListTile(Icons.more, Colors.black45, "More", context),
            getListTile(Icons.favorite, Colors.pink, "Favourites", context),
            getListTile(Icons.account_box, Colors.blue, "Profile", context),
            new Divider(
              thickness: 2.0,
              height: 10.0,
            ),
            getListTile(Icons.exit_to_app, null, "Logout", context),
          ],
        ),
      );
    },
  );
}

ListTile getListTile(icon, iconColor, titleText, context) {
  return new ListTile(
    leading: new Container(
      width: 4.0,
      child: Icon(
        icon,
        color: iconColor,
        size: 24.0,
      ),
    ),
    title: new Text(
      titleText,
      style: TextStyle(
        fontSize: 14.0,
        fontWeight: FontWeight.w700,
      ),
    ),
    onTap: () => Navigator.of(context).pop(),
  );
}

