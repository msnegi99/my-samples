import 'package:api_integration/Seperate%20Category/seperate_cayegory.dart';
import 'package:flutter/material.dart';
import 'Dio/dio_home_screen.dart';
import 'Location AutoComplete/location_auto.dart';
import 'RestApi Getx/Screen/homescreen.dart';
import 'RestApi Getx/Services/get_controller.dart';
import 'Update/myhome_screen.dart';
import 'Update/update_display.dart';
import 'Upload Image/upload_image_screen.dart';
import 'Without Model/display_screen.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      //home: ProductCategoriesScreen(),
      //home: GetMethodWithoutModel(),
      //home: UploadImage(),
      //home: MyHomeScreen(),
      //home: HomePage(),
      //home: LocationAutoComplete(),
      home:DioHomePage(),

    );
  }
}
