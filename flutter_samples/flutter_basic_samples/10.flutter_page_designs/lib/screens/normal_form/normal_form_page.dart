import 'package:flutter/material.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:get/get_instance/src/extension_instance.dart';
import 'package:get/get_state_manager/src/rx_flutter/rx_obx_widget.dart';
import 'package:intl_phone_field/intl_phone_field.dart';
import 'choicechip_controller.dart';
import 'controller.dart';

class FormPageFormat extends StatefulWidget {
  const FormPageFormat({super.key, required this.title});

  final String title;

  @override
  State<FormPageFormat> createState() => _FormPageFormatState();
}

class _FormPageFormatState extends State<FormPageFormat> {
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
    if (_formKey.currentState!.validate()
        && _agree
        &&  _gender!=null
        && profileController.gender.value.isNotEmpty
        && choiceChipController.selectedChoiceList.isNotEmpty) {
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

  final ProfileController profileController = Get.put(ProfileController());
  final ChoiceChipController choiceChipController = Get.put(ChoiceChipController());


  @override
  Widget build(BuildContext context) {
    return PopScope(
        canPop: true, //- false to disable pop back
        onPopInvoked: (didPop) {
          if (didPop) {
            return; // If the pop was handled by another PopScope, do nothing.
          }
          if (Navigator.of(context).canPop()) {
            Navigator.of(context).pop();
          }
        },
        child: Scaffold(
            appBar: new AppBar(
              backgroundColor: Colors.blue,
              foregroundColor: Colors.white,
              title: new Text(widget.title),
              actions: [
                IconButton(icon: Icon(Icons.search),onPressed: () {},),
                IconButton(icon: Icon(Icons.add),onPressed: () {},),
              ],
            ),
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
                          prefixIcon: Icon(Icons.person),
                        ),
                        validator: (value){
                          if (value!.isEmpty) {
                            return 'Please enter your Name';
                          } else {
                            return null;
                          }
                        },
                      ),
                      const SizedBox(height: 16),

                      // Mobile Number Field
                      IntlPhoneField(
                        focusNode: FocusNode(),
                        // controller: profileController.mobileNumberTextEditingController,
                        decoration: InputDecoration(
                          labelText: 'Phone Number',
                          border: OutlineInputBorder(
                            borderSide: BorderSide(),
                          ),
                        ),
                        languageCode: "en",
                        initialCountryCode: 'IN',
                        onChanged: (phone) {
                          profileController.mobileNumberTextEditingController.text = phone.completeNumber;
                          debugPrint(profileController.mobileNumberTextEditingController.text);
                        },
                        onCountryChanged: (country) {
                          debugPrint('Country changed to: ${country.name}');
                        },
                          validator: (phone) { // The validator receives the PhoneNumber object
                            if (phone == null || phone.number.isEmpty) {
                              return 'Please enter your phone number'; // Return an error string
                            }
                            // The package handles most international validation internally
                            // but you can add custom logic here if needed.
                            return null; // Return null if valid
                          }
                      ),
                      const SizedBox(height: 16),

                      // Email
                      TextFormField(
                        decoration: const InputDecoration(
                          labelText: "Email",
                          border: OutlineInputBorder(),
                          prefixIcon: Icon(Icons.email),
                        ),
                        keyboardType: TextInputType.emailAddress,
                        validator: (value) => value!.isEmpty ? "Enter your email" : null,
                      ),
                      const SizedBox(height: 16),

                      // Password
                      TextFormField(
                        decoration: const InputDecoration(
                          labelText: "Password",
                          border: OutlineInputBorder(),
                        ),
                        obscureText: true,
                        validator: (value) => value!.length < 6 ? "Password must be 6+ chars" : null,
                      ),
                      const SizedBox(height: 16),

                      // Date Picker
                      TextFormField(
                        controller: _dateController,
                        readOnly: true,
                        decoration: const InputDecoration(
                          labelText: "Date of Birth",
                          border: OutlineInputBorder(),
                          prefixIcon: Icon(Icons.calendar_month),
                        ),
                        onTap: _pickDate,
                        validator: (value) => value!.isEmpty ? "Enter date of birth" : null,
                      ),
                      const SizedBox(height: 16),

                      // Gender (Radio Buttons)
                      const Text("Gender:"),
                      Row(
                        children: [
                          Radio<String>(
                            value: "Male",
                            groupValue: _gender,
                            onChanged: (value) {
                              setState(() => _gender = value);
                            },
                          ),
                          const Text("Male"),
                          Radio<String>(
                            value: "Female",
                            groupValue: _gender,
                            onChanged: (value) {
                              setState(() => _gender = value);
                            },
                          ),
                          const Text("Female"),
                        ],
                      ),
                      const SizedBox(height: 16),

                      // Gender Selection
                      Obx(() => DropdownMenu(
                          expandedInsets: EdgeInsets.zero,
                          leadingIcon: Icon(Icons.male_outlined),
                          label: Text('Select Gender'),
                          dropdownMenuEntries: profileController.genderItems
                              .map((e) => DropdownMenuEntry(value: e, label: e),
                          ).toList(),
                          onSelected: (value) {
                            if (value!.isNotEmpty) {
                              profileController.gender.value = value;
                            }
                          },
                        ),
                      ),

                      SizedBox(height: 20),

                      // Dropdown
                      DropdownButtonFormField<String>(
                        value: _dropdownValue,
                        items: ["India", "USA", "UK", "Canada"]
                            .map((e) =>
                            DropdownMenuItem(value: e, child: Text(e)))
                            .toList(),
                        onChanged: (value) {
                          setState(() => _dropdownValue = value!);
                        },
                        decoration: const InputDecoration(
                          labelText: "Country",
                          border: OutlineInputBorder(),
                        ),
                      ),
                      const SizedBox(height: 16),

                      Row(
                        children: [
                          Expanded(
                              child: Text(
                                'Select Hobbies',
                                style: TextStyle(fontSize: 16.0),
                              ))
                        ],
                      ),
                      SizedBox(height: 10),

                      // Hobbies selection
                      Obx(() => Wrap(
                          spacing: 10,
                          children: choiceChipController.choiceList
                              .map(
                                (e) => ChoiceChip(
                              label: Text(e),
                              selectedColor: Colors.green,
                              selected:
                              choiceChipController.selectedChoiceList.contains(e),
                              onSelected: (value) {
                                if (value) {
                                  choiceChipController.selectedChoiceList.add(e);
                                  debugPrint('$e = added into selectedChoiceList.');
                                } else {
                                  choiceChipController.selectedChoiceList.removeWhere((element) => element == e);
                                  debugPrint('$e = removed from selectedChoiceList.');
                                }
                              },
                            ),
                          )
                              .toList(),
                        ),
                      ),

                      SizedBox(height: 20),

                      // Switch
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          const Text("Enable Notifications"),
                          Switch(
                            value: _switchValue,
                            onChanged: (value) {
                              setState(() => _switchValue = value);
                            },
                          ),
                        ],
                      ),
                      const SizedBox(height: 16),

                      // Slider
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text("Age: ${_sliderValue.toInt()}"),
                          Slider(
                            value: _sliderValue,
                            min: 0,
                            max: 100,
                            divisions: 100,
                            label: _sliderValue.toInt().toString(),
                            onChanged: (value) {
                              setState(() => _sliderValue = value);
                            },
                          ),
                        ],
                      ),
                      const SizedBox(height: 16),

                      Row(
                        children: [
                          Expanded(
                              child: Text(
                                'Enter Description',
                                style: TextStyle(fontSize: 16.0),
                              ))
                        ],
                      ),
                      SizedBox(height: 10),
                      // Multiline text input
                      TextFormField(
                        decoration: InputDecoration(
                          border: OutlineInputBorder(
                            borderSide: BorderSide(),
                          ),
                          prefixIcon: Icon(Icons.description),
                        ),
                        maxLines: 4,
                        keyboardType: TextInputType.multiline,
                        validator: (value) => value!.isEmpty ? "Enter description" : null,
                      ),

                      SizedBox(height: 60),

                      // Checkbox
                      CheckboxListTile(
                        value: _agree,
                        onChanged: (value) {
                          setState(() => _agree = value!);
                        },
                        title: const Text("I agree to the terms & conditions"),
                      ),
                      const SizedBox(height: 24),

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
            ),
        );
    }
}
