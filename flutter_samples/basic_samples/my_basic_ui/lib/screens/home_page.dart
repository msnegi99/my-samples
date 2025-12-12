import 'package:flutter/material.dart';
import '../widgets/BottomNavigation.dart';
import '../widgets/buttons.dart';
import '../widgets/card_design.dart';
import '../widgets/search_bar.dart';
import '../widgets/text_field.dart';
import 'doctors_list.dart';
import 'filter_screen.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;
  RangeValues _values = const RangeValues(100, 600);
  double distValue = 50.0;

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: Center(
        child: SafeArea(
          child: Stack(
            children: <Widget>[
              Container(
                color: Colors.white,
                padding: const EdgeInsets.all(10),
                height: MediaQuery.of(context).size.height,
                width: MediaQuery.of(context).size.width,
                child: Column(
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        TextWidget(
                          "Hello",
                          17,
                          Colors.black.withOpacity(.7),
                          FontWeight.bold,
                        ),
                        const Icon(Icons.phonelink_ring),
                      ],
                    ),
                    const SizedBox(height: 10),
                    searchBar(),
                    const SizedBox(height: 10),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        category("assets/images/capsule.png", "Drug", 5),
                        category("assets/images/virus.png", "Virus", 10),
                        category("assets/images/heart.png", "Physo", 10),
                        category("assets/images/app.png", "Other", 12),
                      ],
                    ),
                    const SizedBox(height: 10),
                    Container(
                      alignment: Alignment(0, 0),
                      color: Colors.green,
                      child: Center(
                        child: CircleAvatar(
                          backgroundColor: Colors.grey,
                          radius: 150,
                          child: Center(
                            child: Image(
                              fit: BoxFit.fill,
                              image: AssetImage('assets/images/p1.png'),
                            ),
                          ),
                        ),
                      ),
                    ),
                    const SizedBox(height: 10),
                    Column(
                      children: [
                        TextWidget(
                          "Hello",
                          25,
                          Colors.black.withOpacity(.7),
                          FontWeight.bold,
                        ),
                        const SizedBox(height: 10),
                        card(context),
                        const SizedBox(height: 10),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.end,
                          children: [
                            InkWell(
                                onTap: () async {
                                  setState(() {});
                                  await Future.delayed(const Duration(milliseconds: 500));
                                  await Navigator.push(context, MaterialPageRoute(
                                    builder: (context) {
                                      return DoctorsListPage(title: 'List of Doctors',);
                                    },
                                  ));

                                  setState(() {
                                  });
                                },
                                child: Padding(
                                  padding: const EdgeInsets.only(right: 10.0), // Adjust the value as needed
                                  child: TextWidget(
                                    "See all",
                                    15,
                                    Colors.blue.shade600.withOpacity(.8),
                                    FontWeight.bold,
                                    letterSpace: 0,
                                  ),
                                )
                            ),
                            InkWell(
                                onTap: () async {
                                  setState(() {});
                                  await Future.delayed(const Duration(milliseconds: 500));
                                  Navigator.push<dynamic>(
                                    context,
                                    MaterialPageRoute<dynamic>(
                                      builder: (BuildContext context) => FiltersScreen(),
                                      fullscreenDialog: true,
                                    ),
                                  );

                                  setState(() {
                                  });
                                },
                                child: Padding(
                                  padding: const EdgeInsets.only(right: 10.0), // Adjust the value as needed
                                  child: TextWidget(
                                    "Filter Screen",
                                    15,
                                    Colors.blue.shade600.withOpacity(.8),
                                    FontWeight.bold,
                                    letterSpace: 0,
                                  ),
                                )
                            ),
                          ]
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
      bottomNavigationBar: BottomNavigationText(),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
