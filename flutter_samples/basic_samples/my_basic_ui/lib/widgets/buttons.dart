
import 'package:flutter/material.dart';
import 'package:shine_tech_app/widgets/text_field.dart';

Widget category(String asset, String txt, double padding) {
  return Column(
    children: [
      InkWell(
        child: Card(
          shape:
          RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
          child: Container(
            padding: EdgeInsets.all(padding),
            height: 50,
            width: 50,
            decoration: BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.circular(10),
            ),
            child: Center(
              child: Image(
                image: AssetImage(asset),
              ),
            ),
          ),
        ),
      ),
      const SizedBox(
        height: 5,
      ),
      TextWidget(
        txt,
        16,
        Colors.black,
        FontWeight.bold,
        letterSpace: 1,
      ),
    ],
  );
}
