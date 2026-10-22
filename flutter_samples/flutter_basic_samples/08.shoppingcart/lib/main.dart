import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:shoppingcart/db_provider.dart';
import 'package:shoppingcart/productList.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (context) {
        return DbProvider();
      },
      child: MaterialApp(
        debugShowCheckedModeBanner: false,
        home: Productlist(),
      ),
    );
  }
}
