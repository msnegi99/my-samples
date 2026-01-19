import 'package:flutter/material.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';

class StaggeredGridPage extends StatefulWidget {
  const StaggeredGridPage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  State<StaggeredGridPage> createState() => _StaggeredGridPageState(); // Creates the mutable state object
}

class _StaggeredGridPageState extends State<StaggeredGridPage> {

  List<StaggeredGridTile> _cardTile = [
    StaggeredGridTile.count(
      crossAxisCellCount: 1,
      mainAxisCellCount: 2,
      child: BackGroundTile(backgroundColor: Colors.red, icondata: Icons.home),
    ),
    StaggeredGridTile.count(
      crossAxisCellCount: 1,
      mainAxisCellCount: 1,
      child:
      BackGroundTile(backgroundColor: Colors.orange, icondata: Icons.ac_unit),
    ),
    StaggeredGridTile.count(
      crossAxisCellCount: 1,
      mainAxisCellCount: 2,
      child:
      BackGroundTile(backgroundColor: Colors.pink, icondata: Icons.landscape),
    ),
    StaggeredGridTile.count(
      crossAxisCellCount: 1,
      mainAxisCellCount: 1,
      child:
      BackGroundTile(backgroundColor: Colors.green, icondata: Icons.portrait),
    ),
    StaggeredGridTile.count(
      crossAxisCellCount: 4,
      mainAxisCellCount: 2,
      child: BackGroundTile(
          backgroundColor: Colors.deepPurpleAccent, icondata: Icons.music_note),
    ),
    StaggeredGridTile.count(
      crossAxisCellCount: 4,
      mainAxisCellCount: 2,
      child: BackGroundTile(
          backgroundColor: Colors.blue, icondata: Icons.access_alarms),
    ),
    StaggeredGridTile.count(
      crossAxisCellCount: 4,
      mainAxisCellCount: 2,
      child: BackGroundTile(
          backgroundColor: Colors.indigo, icondata: Icons.satellite_outlined),
    ),
    StaggeredGridTile.count(
      crossAxisCellCount: 4,
      mainAxisCellCount: 2,
      child: BackGroundTile(
          backgroundColor: Colors.deepPurpleAccent, icondata: Icons.music_note),
    ),
    StaggeredGridTile.count(
      crossAxisCellCount: 4,
      mainAxisCellCount: 2,
      child: BackGroundTile(
          backgroundColor: Colors.deepPurpleAccent, icondata: Icons.music_note),
    ),
  ];

  @override
  Widget build(BuildContext context) {
    return  Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.black,
        foregroundColor: Colors.white,
        title: Text(widget.title),
      ),
      body: Container(
            child: SingleChildScrollView(
                   child: StaggeredGrid.count(
                              crossAxisCount: 2,
                              mainAxisSpacing: 4.0,
                              crossAxisSpacing: 4.0,
                              children: _cardTile,
                            ),
                  ),
            ),
    );
  }

}

class BackGroundTile extends StatelessWidget {
  final Color backgroundColor;
  final IconData icondata;

  BackGroundTile({required this.backgroundColor, required this.icondata});

  @override
  Widget build(BuildContext context) {
    return Card(
      color: backgroundColor,
      child: Icon(icondata, color: Colors.white),
    );
  }
}