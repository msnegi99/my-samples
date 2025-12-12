import 'package:flutter/material.dart';
import 'package:flutter_basic_form/screens/bottom_nav_sub_screen_1.dart';

class BottomPage1 extends StatefulWidget {
  static const String routeName = '/bottom-page-1';

  const BottomPage1({super.key});

  @override
  State<BottomPage1> createState() => _BottomPage1State();
}

class _BottomPage1State extends State<BottomPage1> {
  final _formKey = GlobalKey<FormState>();

  // Controllers
  final TextEditingController _nameController = TextEditingController();

  // Submit form
  void _submitForm() {
    Navigator.of(context)
        .push<int>(
      MaterialPageRoute(
        builder: (Redcontext) => SubScreen1(),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              //---------page content
              // Name
              TextFormField(
                controller: _nameController,
                decoration: const InputDecoration(
                  labelText: "Name",
                  border: OutlineInputBorder(),
                ),
                validator: (value) => value!.isEmpty ? "Enter your name" : null,
              ),
              const SizedBox(height: 16),

              // Submit Button
              Center(
                child: ElevatedButton(
                  onPressed: _submitForm,
                  child: const Text("Submit"),
                ),
              ),

              //---- Page content ends
            ],
          ),
        ),
      ),
    );
  }
}
