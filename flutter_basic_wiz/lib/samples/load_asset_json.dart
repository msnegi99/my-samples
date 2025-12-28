import 'package:flutter/material.dart';
import 'dart:convert';

class AssetJSONPage extends StatefulWidget {
  const AssetJSONPage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  State<AssetJSONPage> createState() => _AssetJSONPageState(); // Creates the mutable state object
}

class _AssetJSONPageState extends State<AssetJSONPage> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.black,
        foregroundColor: Colors.white,
        title: new Text(widget.title),
      ),
      body: Container(
        child: Center(
          // Use future builder and DefaultAssetBundle to load the local JSON file
          child: FutureBuilder(
                future: DefaultAssetBundle
                        .of(context)
                        .loadString('assets/starwars_data.json'),
                builder: (context, snapshot)
                {
                    var new_data = json.decode(snapshot.data.toString());

                    return ListView.builder(
                            // Build the ListView
                            itemBuilder: (BuildContext context, int index) {
                              return Card(
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.stretch,
                                  children: <Widget>[
                                    Text("Name: " + new_data[index]['name']),
                                    Text("Height: " + new_data[index]['height']),
                                    Text("Mass: " + new_data[index]['mass']),
                                    Text("Hair Color: " + new_data[index]['hair_color']),
                                    Text("Skin Color: " + new_data[index]['skin_color']),
                                    Text("Eye Color: " + new_data[index]['eye_color']),
                                    Text("Birth Year: " + new_data[index]['birth_year']),
                                    Text("Gender: " + new_data[index]['gender'])
                                  ],
                                ),
                              );
                            },
                            itemCount: new_data == null ? 0 : new_data.length,
                          );
              }),
        ),
      ));
  }

}