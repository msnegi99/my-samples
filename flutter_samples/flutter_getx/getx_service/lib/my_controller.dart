import 'package:get/get.dart';

//GetxController is removed by OS When not in use
//GetxService remains alive throughout the app, even when not in use
//GetxService removes only when app exits or when Get.reset is called

class MyController extends GetxService {

  var count = 0.obs;

  incrementCounter(){
    count++;
  }
}