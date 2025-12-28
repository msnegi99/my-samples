import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:email_validator/email_validator.dart';
import 'package:fluttertoast/fluttertoast.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key, required this.title});
  final String title;

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final _formKey = GlobalKey<FormState>();
  bool _passwordVisible = false;

  // Controllers
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();

  String? validateEmail(String? value) {
    if (value == null || value.isEmpty) return 'Input required';

    if (!EmailValidator.validate(value)) {
      return 'Please enter a valid email!';
    }
    return null;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        decoration: BoxDecoration(
          image: DecorationImage(
            image: AssetImage("assets/images/city.jpg"),
            fit: BoxFit.fill,
          ),
        ),
        child: Form(
          key: _formKey,
          child: Stack(
            children: [
              Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  Container(
                    margin: EdgeInsets.all(20),
                    padding: EdgeInsets.all(20),
                    alignment: Alignment.center,
                    decoration: BoxDecoration(
                      color: Colors.white,
                      border: Border.all(
                        color: Colors.grey, // Set border color
                        width: 3.0,
                      ),
                      // Set border width
                      borderRadius: BorderRadius.all(Radius.circular(10.0)),
                      // Set rounded corner radius
                      boxShadow: [
                        BoxShadow(
                          blurRadius: 10,
                          color: Colors.black,
                          offset: Offset(1, 3),
                        ),
                      ], // Make rounded corner of border
                    ),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: <Widget>[
                        Image.asset("assets/social/facebook.png"),

                        const SizedBox(height: 10),

                        Text(
                          'Welcome, please log in',
                          style: TextStyle(
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                            color: Colors.grey,
                          ),
                        ),

                        const SizedBox(height: 10),

                        TextFormField(
                          controller: _nameController,
                          maxLength: 50,
                          inputFormatters: [
                            FilteringTextInputFormatter.deny(
                              RegExp(
                                r'(\u00a9|\u00ae|[\u2000-\u3300]|\ud83c[\ud000-\udfff]|\ud83d[\ud000-\udfff]|\ud83e[\ud000-\udfff])',
                              ),
                            ),
                          ],
                          decoration: const InputDecoration(
                            prefixIcon: Icon(Icons.person),
                            labelText: 'User Name',
                            border: OutlineInputBorder(),
                            // Default border when not focused
                            enabledBorder: OutlineInputBorder(
                              borderSide: BorderSide(
                                color: Colors.grey, // Default border color
                                width: 1.0,
                              ),
                            ),
                            // Border when focused
                            focusedBorder: OutlineInputBorder(
                              borderSide: BorderSide(
                                color: Colors.green, // Focused border color
                                width: 2.0,
                              ),
                            ),
                          ),
                          validator: (value) => validateEmail(value),
                        ),

                        const SizedBox(height: 10),

                        TextFormField(
                          controller: _passwordController,
                          maxLength: 50,
                          decoration: InputDecoration(
                            prefixIcon: const Icon(Icons.lock),
                            suffixIcon: IconButton(
                              icon: Icon(
                                _passwordVisible
                                    ? Icons.visibility
                                    : Icons.visibility_off,
                              ),
                              onPressed: () {
                                setState(() {
                                  _passwordVisible = !_passwordVisible;
                                });
                              },
                            ),
                            labelText: 'Password',
                            border: const OutlineInputBorder(),
                            // Default border when not focused
                            enabledBorder: OutlineInputBorder(
                              borderSide: BorderSide(
                                color: Colors.grey, // Default border color
                                width: 1.0,
                              ),
                            ),
                            // Border when focused
                            focusedBorder: OutlineInputBorder(
                              borderSide: BorderSide(
                                color: Colors.green, // Focused border color
                                width: 2.0,
                              ),
                            ),
                          ),
                          obscureText: !_passwordVisible,
                          validator: (value) => value!.length < 6
                              ? "Password must be 6+ chars"
                              : null,
                        ),

                        const SizedBox(height: 10),

                        SizedBox(
                          width: double.infinity,
                          child: ElevatedButton(
                            onPressed: () async {
                              if (_formKey.currentState!.validate()) {
                                //--call service here
                                Fluttertoast.showToast(
                                  timeInSecForIosWeb: 3,
                                  msg: 'Form validated properly.', // Message to display in the toast
                                  backgroundColor: Colors.green, // Background color of the toast
                                );
                              } else {
                                ScaffoldMessenger.of(context).showSnackBar(
                                  const SnackBar(content: Text("Please complete the form properly.")),
                                );
                              }
                            },
                            style: ElevatedButton.styleFrom(
                              fixedSize: const Size(100, 50),
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
                        ),

                        const SizedBox(height: 10),

                        InkWell(
                          onTap: () {
                            //Navigator.of(context).push(MaterialPageRoute(builder: (context) => ForgotPage(title: 'Forgot Password',)));
                          },
                          child: Text(
                            "Forgot Password?",
                            style: TextStyle(fontSize: 16, color: Colors.grey),
                          ),
                        ),

                        const SizedBox(height: 10),
                      ],
                    ),
                  ),
                ],
              ),
              Positioned(
                left: MediaQuery.of(context).size.width - 60.0,
                top: 60.0,
                child: InkWell(
                  onTap: () {
                    //Navigator.of(context).push(MaterialPageRoute(builder: (context) => AssistPage(title: 'Call For Assistance',)));
                  },
                  child: Image.asset('assets/img/ic_help_34.png'),
                ),
              ),
              Align(
                alignment: Alignment.bottomCenter,
                child: Container(
                  padding: EdgeInsets.all(16.0),
                  margin: EdgeInsets.only(top: 20.0),
                  child: Text(
                    'ver 1.0.0',
                    style: TextStyle(color: Colors.white, fontSize: 20),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
