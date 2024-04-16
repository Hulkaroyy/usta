import 'package:flutter/material.dart';
import 'package:handihub/screens/auth/login_screen.dart';
import 'package:handihub/static/buttons.dart';
import 'package:handihub/static/colors.dart';
import 'package:handihub/static/title.dart';
import 'package:handihub/static/widgets.dart';

class StarterScreenTwo extends StatelessWidget {
  static String routeName = "/starter-screen-two";
  const StarterScreenTwo({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: backgroundColor,
      body: SizedBox(
        width: width(context),
        height: height(context),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            sizedBox(24.0/812*height(context)),
            title,
            sizedBox(77.0/812*height(context)),
            Image.asset("assets/images/starter_two.png", height: 347/812*height(context), width: 341/375*width(context)),
            sizedBox(9.0/812*height(context)),
            text("Get your \ndeliveries fast"),
            sizedBox(140.0/812*height(context)),
            InkWell(
              onTap:()=> Navigator.of(context).pushNamed(LoginScreen.routeName),
              child: textButton("Continue",context))
          ],
        ),
      ),
    );
  }
}