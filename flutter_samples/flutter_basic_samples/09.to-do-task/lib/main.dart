// lib/main.dart

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart'; // Provider ko import karein

// Apne file paths ke hisab se inko adjust kar lein
import 'Providers/TaskProvider.dart';
import 'Screens/HomeScreen.dart';

void main() {
  // WidgetsFlutterBinding.ensureInitialized() zaroori hai jab app start hotay hi koi async operation ho
  // Hamara Provider constructor mein fetchTasks() (async) call kar raha hai
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    // ChangeNotifierProvider widget hamare TaskProvider ko poori app ke liye available kar deta hai
    return ChangeNotifierProvider(
      create: (context) => TaskProvider(), // Yahan Provider ko initialize kiya
      child: MaterialApp(
        debugShowCheckedModeBanner: false,
        title: 'To Do Task Application',
        theme: ThemeData(
            colorScheme: ColorScheme.fromSeed(seedColor: CupertinoColors.activeGreen),
            appBarTheme: AppBarTheme( // Thori si styling
              backgroundColor: CupertinoColors.activeGreen,
              foregroundColor: Colors.white,
              titleTextStyle: TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
            )
        ),
        home: const HomeScreen(), // Ab HomeScreen Provider ko access kar sakti hai
      ),
    );
  }
}