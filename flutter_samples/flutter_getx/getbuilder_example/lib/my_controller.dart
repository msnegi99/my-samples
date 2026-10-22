import 'package:get/get.dart';

class MyController extends GetxController {

  var count = 0;

  incrementCounter(List<String> lst){
    count++;
    update(lst);
  }

  @override
  void onInit() {
    //
  }

  @override
  void onClose() {
    //
  }

  @override
  void onReady() {
    //
  }
}