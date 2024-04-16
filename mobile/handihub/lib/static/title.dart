import 'package:flutter/material.dart';
import 'package:handihub/static/colors.dart';

RichText title = RichText(
    text: TextSpan(
        text: "Usta",
        style: const TextStyle(
            fontFamily: 'Jomolhari',
            fontSize: 40,
            fontWeight: FontWeight.w400,
            color: Colors.black),
        children: [TextSpan(text: '.', style: TextStyle(color: mainColor))]));

Text resetPasswordInfo = const Text(
      "Enter the email address associated with your Usta account, and click 'Continue' below to have password reset instructions emailed to you",
      style:  TextStyle(
        fontWeight: FontWeight.w500,
        fontSize: 14,
      ),
    );

RichText resetPasswordInfoExtra = RichText(
    text: TextSpan(
        text: "*",
        style: TextStyle(
            fontFamily: 'Inter',
            fontSize: 40,
            fontWeight: FontWeight.w400,
            color: mainColor),
        children: const [
      TextSpan(
          text: ' Indicates required fields',
          style: TextStyle(color: Colors.black))
    ]));

RichText signUp = RichText(
    text: TextSpan(
        text: "Don't have an account?",
        style: TextStyle(
            fontFamily: 'Montserrat',
            fontSize: 12,
            fontWeight: FontWeight.w500,
            color: greyColor),
        children: const [
      TextSpan(
        text: 'Sign Up',
        style: TextStyle(
            color: Color.fromRGBO(60, 90, 154, 1),
            decoration: TextDecoration.underline),
      )
    ]));
