import 'package:flutter/material.dart';
import 'package:handihub/static/colors.dart';

TextFormField textFormField(String label, TextEditingController controller,
        String? Function(String? val)? function, bool? obscure) =>
    TextFormField(
      obscureText: obscure ?? false,
      cursorColor: mainColor,
      controller: controller,
      decoration: InputDecoration(
        suffix: obscure == null
            ? null
            : obscure
                ? Image.asset(
                    "assets/images/eye-line.png",
                    color: greyColor,
                    height: 24,
                    width: 24,
                  )
                : Image.asset("assets/images/eye-slasheye.png",
                    color: greyColor, height: 24, width: 24),
        floatingLabelBehavior: FloatingLabelBehavior.never,
        label: Text(
          label,
          style: TextStyle(color: greyColor),
        ),
        errorStyle: const TextStyle(height: 0.7),
        border: OutlineInputBorder(
            borderRadius: const BorderRadius.all(Radius.circular(8)),
            borderSide:
                BorderSide(color: Colors.redAccent.shade400, width: 0.5)),
        errorBorder: OutlineInputBorder(
            borderRadius: const BorderRadius.all(Radius.circular(8)),
            borderSide:
                BorderSide(color: Colors.redAccent.shade400, width: 0.5)),
        enabledBorder: OutlineInputBorder(
            borderRadius: const BorderRadius.all(Radius.circular(8)),
            borderSide: BorderSide(color: greyColor, width: 0.5)),
        focusedBorder: OutlineInputBorder(
            borderRadius: const BorderRadius.all(Radius.circular(8)),
            borderSide: BorderSide(color: mainColor, width: 0.5)),
      ),
      validator: function,
    );

TextFormField searchFormField(textController, Widget? prefix) => TextFormField(
      controller: textController,
      cursorColor: greyColor,
      cursorHeight: 15,
      decoration: InputDecoration(
        filled: true,
        isDense: true,
        prefixIcon:  prefix,
        fillColor: Colors.white,
        border: OutlineInputBorder(
            borderRadius: const BorderRadius.all(Radius.circular(8)),
            borderSide:
                BorderSide(color: Colors.redAccent.shade400, width: 0.5)),
        errorBorder: OutlineInputBorder(
            borderRadius: const BorderRadius.all(Radius.circular(8)),
            borderSide:
                BorderSide(color: Colors.redAccent.shade400, width: 0.5)),
        enabledBorder: const OutlineInputBorder(
            borderRadius: BorderRadius.all(Radius.circular(8)),
            borderSide: BorderSide(color: Colors.white, width: 0.5)),
        focusedBorder: const OutlineInputBorder(
            borderRadius: BorderRadius.all(Radius.circular(8)),
            borderSide: BorderSide(color: Colors.white, width: 0.5)),
      ),
    );
