import 'package:flutter/material.dart';

void showSnackBar(BuildContext context, String text,
    [Color? color = Colors.red]) {
  SnackBar snackBar = SnackBar(
    content: Text(text),
    backgroundColor: color,
  );
  ScaffoldMessenger.of(context).showSnackBar(snackBar);
}
