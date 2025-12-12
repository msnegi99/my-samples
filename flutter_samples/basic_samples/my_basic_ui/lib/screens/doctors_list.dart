import 'package:flutter/material.dart';
import '../widgets/range_slider_view.dart';

class DoctorsListPage extends StatefulWidget {
  const DoctorsListPage({super.key, required this.title});

  final String title;

  @override
  State<DoctorsListPage> createState() => _DoctorsListPageState();
}

class _DoctorsListPageState extends State<DoctorsListPage> {

  RangeValues _values = const RangeValues(100, 600);

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        backgroundColor: Colors.white,
        foregroundColor: Colors.blue,
        title: new Text("Doctors List"),
      ),
      body: Container(
        height: MediaQuery.of(context).size.height,
        width: double.infinity,
        decoration: BoxDecoration(
          color: Colors.white,
          boxShadow: [
            BoxShadow(
              color: Colors.grey.withOpacity(0.5),
              spreadRadius: 3,
              blurRadius: 4,
              offset: Offset(0, 3), // changes position of shadow
            ),
          ],
          borderRadius: BorderRadius.circular(15),
        ),
        child: ListView(
          children: <Widget>[
            ListTile(title: Text("Item 1")),
            ListTile(title: Text("Item 2")),
            ListTile(title: Text("Item 3")),
            RangeSliderView(
              values: _values,
              onChangeRangeValues: (RangeValues values) {
                _values = values;
              },
            ),

          ],
        ),
      ),
    );
  }
}
