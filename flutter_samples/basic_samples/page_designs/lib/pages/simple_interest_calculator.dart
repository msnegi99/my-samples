import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import '../consts.dart';

class Calculator extends StatefulWidget {
  const Calculator({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<StatefulWidget> createState() {
    return _CalculatorState();
  }
}

class _CalculatorState extends State<Calculator> {

  var _formKey = GlobalKey<FormState>();

  var _currencies = ['Rupees', 'Dollar', 'Pounds', 'Others'];
  var _currentItemSelected = '';
  final double _minimumPadding = 10.0;

  TextEditingController principalController = TextEditingController();
  TextEditingController roiController = TextEditingController();
  TextEditingController termController = TextEditingController();

  var _displayResult = "";

  @override
  void initState() {
    _currentItemSelected = _currencies[0];
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: IndigoBlue,
        foregroundColor: Colors.white,
        title: Text("Simple calculator"),
      ),
      body: Form(
        key: _formKey,
        child: Padding(
            padding: EdgeInsets.only(
              top: _minimumPadding,
              bottom: _minimumPadding,
              left: _minimumPadding,
              right: _minimumPadding,
            ),
            child: ListView(
              children: [
                getImageAsset(),
                Padding(
                  padding: EdgeInsets.only(
                    top: _minimumPadding,
                    bottom: _minimumPadding,
                    left: _minimumPadding,
                    right: _minimumPadding,
                  ),
                  child: Expanded(
                    child: TextFormField(
                      keyboardType: TextInputType.number,
                      controller: principalController,
                      validator: (value){
                        if(value!.isEmpty){
                          return 'Please enter principal amount';
                        }else if(double.tryParse(value) == null){
                          return 'Not an valid number';
                        }
                      },
                      decoration: InputDecoration(
                        labelText: 'Principal',
                        hintText: 'Enter Principal e.g. 12000',
                        errorStyle: TextStyle(
                          color: Colors.blue,
                          fontSize: 15.0,
                        ),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(5.0),
                        ),
                      ),
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(
                    top: _minimumPadding,
                    bottom: _minimumPadding,
                    left: _minimumPadding,
                    right: _minimumPadding,
                  ),
                  child: Expanded(
                    child: TextFormField(
                      keyboardType: TextInputType.number,
                      controller: roiController,
                      validator: (value){
                        if(value!.isEmpty){
                          return 'Please enter rate of Interest';
                        }else if(double.tryParse(value) == null){
                          return 'Not an valid number';
                        }
                      },
                      decoration: InputDecoration(
                        labelText: 'Rate of Interest',
                        hintText: 'Enter rate of Interest',
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(5.0),
                        ),
                      ),
                    ),
                  ),
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: <Widget>[
                    Expanded(
                      child: Padding(
                        padding: EdgeInsets.only(
                          top: _minimumPadding,
                          bottom: _minimumPadding,
                          left: _minimumPadding,
                          right: _minimumPadding,
                        ),
                        child: TextFormField(
                          keyboardType: TextInputType.number,
                          controller: termController,
                          validator: (value){
                            if(value!.isEmpty){
                              return 'Please enter term in year';
                            }else if(double.tryParse(value) == null){
                              return 'Not an valid number';
                            }
                          },
                          decoration: InputDecoration(
                            labelText: 'Term in year',
                            hintText: 'Enter term in year',
                            border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(5.0),
                            ),
                          ),
                        ),
                      ),
                    ),
                    Expanded(
                      child: Padding(
                        padding: EdgeInsets.only(
                          top: _minimumPadding,
                          bottom: _minimumPadding,
                          left: _minimumPadding,
                          right: _minimumPadding,
                        ),
                        child: DropdownButton<String>(
                          items: _currencies.map((String dropDownStringItem) {
                            return DropdownMenuItem<String>(
                              value: dropDownStringItem,
                              child: Text(dropDownStringItem),
                            );
                          }).toList(),
                          value: _currentItemSelected,
                          onChanged: (String? value) {
                            setState(() {
                              this._currentItemSelected = value!;
                            });
                          },
                        ),
                      ),
                    ),
                  ],
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: <Widget>[
                    Expanded(
                      child: ElevatedButton(
                        child: Text(
                          "Calculate",
                          style: TextStyle(fontSize: 20.0, color: Colors.white),
                        ),
                        style: ElevatedButton.styleFrom(
                          backgroundColor: Colors.blue,
                          // Background color of the button
                          foregroundColor: Colors.white,
                          // Text color of the button
                          elevation: 5,
                          // Shadow elevation
                          padding: EdgeInsets.symmetric(
                            horizontal: 10.0,
                            vertical: 10.0,
                          ),
                          // Padding
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(
                              10,
                            ), // Rounded corners
                          ),
                        ),
                        onPressed: () {
                          setState(() {
                            if(_formKey.currentState!.validate()){
                            _displayResult = _calculateTotalInterest();
                            }
                          });
                        },
                      ),
                    ),
                    Expanded(
                      child: ElevatedButton(
                        child: Text(
                          "Reset",
                          style: TextStyle(fontSize: 20.0, color: Colors.white),
                        ),
                        style: ElevatedButton.styleFrom(
                          backgroundColor: Colors.blue,
                          // Background color of the button
                          foregroundColor: Colors.white,
                          // Text color of the button
                          elevation: 5,
                          // Shadow elevation
                          padding: EdgeInsets.symmetric(
                            horizontal: 10.0,
                            vertical: 10.0,
                          ),
                          // Padding
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(
                              10,
                            ), // Rounded corners
                          ),
                        ),
                        onPressed: () {
                          _reset();
                        },
                      ),
                    ),
                  ],
                ),
                Expanded(
                  child: Padding(
                    padding: EdgeInsets.only(
                      top: _minimumPadding,
                      bottom: _minimumPadding,
                      left: _minimumPadding,
                      right: _minimumPadding,
                    ),
                    child: Text(
                      _displayResult,
                      style: TextStyle(fontSize: 30.0, fontWeight: FontWeight.bold),
                    ),
                  ),
                ),
              ],
            ),
        ),
      ),
    );
  }

  Widget getImageAsset() {
    AssetImage assetImage = AssetImage('resources/images/pic1.png');
    Image image = Image(image: assetImage, width: 125.0, height: 125.0);

    return Container(
      child: image,
      margin: EdgeInsets.all(_minimumPadding * 10),
    );
  }

  String _calculateTotalInterest() {
    double principal = double.parse(principalController.text);
    double roi = double.parse(roiController.text);
    double term = double.parse(termController.text);
    double totalInterest = principal * roi * term / 100;
    String result =
        "After $term years, your investment will be worth ${principal+totalInterest}";
    return result;
  }

  void _reset(){
    principalController.text = "";
    roiController.text = "";
    termController.text = "";
    _displayResult = "";
    setState(() {
      this._currentItemSelected = _currencies[0];
    });
  }
}

/*
Column  --> ListView( children:<Widget> [

])


Scaffold --> Container --> ListView()/Column/Row --> Expanded --> Padding --> Widget/TextField*/
