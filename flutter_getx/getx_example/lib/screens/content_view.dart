import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';

class ContentView extends StatefulWidget {
  const ContentView({Key? key}) : super(key: key);

  @override
  State<ContentView> createState() => _ContentViewState();
}

class _ContentViewState extends State<ContentView> {
  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        final shouldPop = await showDialog(
            context: context,
            builder: (context) {
              return AlertDialog(
                title: const Text("Do you want to go back"),
                actions: [
                  TextButton(
                      onPressed: () {
                        //Navigator.pop(context, false);
                        Get.back(result: false);
                      },
                      child: const Text(
                        "NO",
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 14,
                        ),
                      )),
                  TextButton(
                      onPressed: () {
                        //Navigator.pop(context, true);
                        Get.back(result: true);
                      },
                      child: const Text(
                        "Yes",
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 14,
                        ),
                      )),
                ],
              );
            });
        return shouldPop;
      },
      child: Scaffold(
        appBar: AppBar(centerTitle: true, title: const Text("Content View")),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: const [
              Text(
                "This is content view",
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.w400),
              )
            ],
          ),
        ),
      ),
    );
  }
}
