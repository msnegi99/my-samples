import 'package:flutter/material.dart';

class BottomPage2 extends StatefulWidget {
  static const String routeName = '/bottom-page-1';

  const BottomPage2({super.key});

  @override
  State<BottomPage2> createState() => _BottomPage2State();
}

class _BottomPage2State extends State<BottomPage2> {
  final _formKey = GlobalKey<FormState>();

  // Controllers
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _dateController = TextEditingController();

  // State variables
  String? _gender;
  bool _agree = false;
  bool _switchValue = false;
  double _sliderValue = 20;
  String _dropdownValue = 'India';

  // Submit form
  void _submitForm() {
    if (_formKey.currentState!.validate() && _agree) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text("Form Submitted! Hello ${_nameController.text}"),
        ),
      );
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Please complete the form properly.")),
      );
    }
  }

  // Date Picker
  Future<void> _pickDate() async {
    DateTime? pickedDate = await showDatePicker(
      context: context,
      initialDate: DateTime(2000),
      firstDate: DateTime(1900),
      lastDate: DateTime.now(),
    );

    if (pickedDate != null) {
      setState(() {
        _dateController.text =
        "${pickedDate.day}/${pickedDate.month}/${pickedDate.year}";
      });
    }
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
              Text('This is Search Screen')

              //---- Page content ends
            ],
          ),
        ),
      ),
    );
  }
}
