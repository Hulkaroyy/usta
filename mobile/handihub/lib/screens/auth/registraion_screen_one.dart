import 'package:flutter/material.dart';
import 'package:handihub/screens/auth/registraion_screen_two.dart';
import 'package:handihub/static/buttons.dart';
import 'package:handihub/static/colors.dart';
import 'package:handihub/static/enums.dart';
import 'package:handihub/static/title.dart';
import 'package:handihub/static/widgets.dart';

class RegistrationScreenOne extends StatelessWidget {
  static String routeName = "/registration-one";
  const RegistrationScreenOne({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: backgroundColor,
      body: SizedBox(
        width: width(context),
        height: height(context),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            sizedBox(79.0),
            title,
            sizedBox(193.0),
            SizedBox(
              width: 327,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  textRegular('Choose your option', const Color.fromRGBO(95, 113, 118, 1)),
                  sizedBox(21.0),
                  InkWell(
                    onTap: () => Navigator.of(context).pushNamed(RegistrationScreenTwo.routeName, arguments: UserType.seller),
                    child: textButton("Seller",context, boxShadow)),
                  sizedBox(21.0),
                  InkWell(
                    onTap: ()=> Navigator.of(context).pushNamed(RegistrationScreenTwo.routeName, arguments: UserType.customer),
                    child: textButtonUnfilled("Customer",context))
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}