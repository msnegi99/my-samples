import 'package:flutter/widgets.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:shoppingcart/cartmodel.dart';
import 'package:shoppingcart/db_helper.dart';

class DbProvider with ChangeNotifier {
  int _counter = 0;
  double _totalPrice = 0.0;

  int get counter => _counter;
  double get totalPrice => _totalPrice;

  late Future<List<Cartmodel>> _dataList;
  Future<List<Cartmodel>> get dataList => _dataList;

  DbHelper dbHelper = DbHelper();

  DbProvider() {
    _loadPrefs();
  }

  Future<void> _loadPrefs() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    _counter = preferences.getInt("counter") ?? 0;
    _totalPrice = preferences.getDouble("totalPrice") ?? 0.0;
    notifyListeners();
  }

  Future<void> _savePrefs() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    await preferences.setInt("counter", _counter);
    await preferences.setDouble("totalPrice", _totalPrice);
  }

  void addCounter() {
    _counter++;
    _savePrefs();
    notifyListeners();
  }

  void removeCounter() {
    if (_counter > 0) {
      _counter--;
      _savePrefs();
      notifyListeners();
    }
  }

  void addTotalPrice(double totalprc) {
    _totalPrice += totalprc;
    _savePrefs();
    notifyListeners();
  }

  void removeTotalPrice(double totalprc) {
    if (_totalPrice > 0.0) {
      _totalPrice -= totalprc;
      if (_totalPrice < 0) _totalPrice = 0;
      _savePrefs();
      notifyListeners();
    }
  }

  Future<List<Cartmodel>> getDataList() async {
    _dataList = dbHelper.getData();
    return dataList;
  }
}
