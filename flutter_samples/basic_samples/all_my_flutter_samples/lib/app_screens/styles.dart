import 'package:flutter/material.dart';

TextStyle getCustomFontTextStyle() {
  // text style which defines a custom font
  return const TextStyle(
    // set color of text
      color: Colors.deepOrange,
      // set the font family as defined in pubspec.yaml
      fontFamily: 'Comforter',
      // set the font weight
      fontWeight: FontWeight.w400,
      // set the font size
      fontSize: 36.0);
}
