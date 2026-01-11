import 'package:flutter/material.dart';
import 'dart:convert';

class AssistPage extends StatefulWidget {
  const AssistPage({super.key, required this.title});
  final String title;

  @override
  State<AssistPage> createState() => _AssistPageState();
}

class _AssistPageState extends State<AssistPage> {

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.black,
        foregroundColor: Colors.white,
        title: new Text(widget.title),
      ),
      body: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        decoration: BoxDecoration(
          image: DecorationImage(
            image: AssetImage("assets/images/city.jpg"),  //background image
            fit: BoxFit.cover,
          ),
        ),
        child: SingleChildScrollView(
          child: Stack(
            children: [
              Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[

                  Container(
                    height: MediaQuery.of(context).size.height - 200,
                    margin: EdgeInsets.all(10),
                    padding: EdgeInsets.all(10),
                    alignment: Alignment.center,
                    decoration: BoxDecoration(
                        color: Colors.white,
                        border: Border.all(
                            color: Colors.grey, 			// Set border color
                            width: 3.0),   				// Set border width
                        borderRadius: BorderRadius.all(Radius.circular(10.0)), 	// Set rounded corner radius
                        boxShadow: [BoxShadow(blurRadius: 10,color: Colors.black,offset: Offset(1,3))] // Make rounded corner of border
                    ),
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
                                child: Padding(
                                  padding: const EdgeInsets.all(16.0), // Adds 16 pixels of space on all sides
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
                                ),
                              );
                            },
                            itemCount: new_data == null ? 0 : new_data.length,
                          );
                        })
                  ),
                  Padding(
                      padding: EdgeInsets.only(bottom: 10.0, top: 10.0, left: 10.0, right: 10.0),  // Adds 16 logical pixels of padding on all sides
                      child: SizedBox(
                        width: double.infinity,
                        child: ElevatedButton(
                          onPressed: () {
                            print('Submit');
                          },
                          style: ElevatedButton.styleFrom(
                            backgroundColor: Colors.lightGreen,
                            foregroundColor: Colors.white,
                            elevation: 5,
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(6),
                            ),
                          ),
                          child: Text(
                            'Submit',
                            style: TextStyle(
                              color: Colors.white,
                              fontSize: 20,
                            ),
                          ),
                        ),
                      )
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
