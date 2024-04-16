import 'package:flutter/material.dart';

Widget sizedBox(value) => SizedBox(height: value);

Text text(text, [Color color = Colors.black]) => Text(
      text,
      style: TextStyle(
        color: color,
        fontSize: 20,
        fontWeight: FontWeight.w700,
      ),
      textAlign: TextAlign.center,
    );

double width(BuildContext context) => MediaQuery.of(context).size.width;
double height(BuildContext context) => MediaQuery.of(context).size.height;

Text textRegular(String text, Color color) => Text(text,
    style: TextStyle(color: color, fontSize: 16, fontWeight: FontWeight.w600));

Text dynamicText(String text, fontsize, color) =>
    Text(text, style: TextStyle(fontSize: fontsize, color: color));

BoxShadow boxShadow = const BoxShadow(
    offset: Offset(4, 4), blurRadius: 10, color: Color.fromRGBO(0, 0, 0, 0.25));

Text warningText(text, color) => Text(
      text,
      style: TextStyle(
          fontFamily: "Montserrat",
          color: color,
          fontSize: 16.0,
          fontWeight: FontWeight.bold),
    );
