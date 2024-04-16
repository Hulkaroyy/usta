import 'package:flutter/material.dart';
import 'package:handihub/static/colors.dart';
import 'package:handihub/static/widgets.dart';

Container textButton(String text, BuildContext context,
        [BoxShadow boxShadow = const BoxShadow()]) =>
    Container(
      height: 55 / 812 * height(context),
      width: 327 / 375 * width(context),
      decoration: BoxDecoration(
          color: mainColor,
          borderRadius: BorderRadius.circular(5),
          boxShadow: [boxShadow]),
      alignment: Alignment.center,
      child: Text(
        text,
        style: const TextStyle(
          fontSize: 20,
          fontFamily: 'Inter',
          fontWeight: FontWeight.w600,
          color: Colors.white,
        ),
      ),
    );

Container textButtonUnfilled(String text, BuildContext context) => Container(
      height: 55 / 812 * height(context),
      width: 327 / 375 * width(context),
      decoration: BoxDecoration(
          color: Colors.transparent,
          borderRadius: BorderRadius.circular(5),
          border: Border.all(color: mainColor, width: 1)),
      alignment: Alignment.center,
      child: Text(
        text,
        style: TextStyle(
          fontSize: 20,
          fontFamily: 'Inter',
          fontWeight: FontWeight.w600,
          color: mainColor,
        ),
      ),
    );

Container roundedButtons(String name, BuildContext context, [Color color = const Color.fromRGBO(255, 135, 2, 1)]) => Container(
      width: width(context) * 150 / 375,
      height: height(context) * 56 / 812,
      decoration: BoxDecoration(
          color: color,
          borderRadius: BorderRadius.circular(40),
          boxShadow: [boxShadow]),
      alignment: Alignment.center,
      child: Text(
        name,
        style: const TextStyle(
          color: Colors.white,
          fontWeight: FontWeight.w600,
          fontSize: 20,
        ),
      ),
    );

Container roundedButtonsOutlined(String name, BuildContext context) =>
    Container(
      width: width(context) * 150 / 375,
      height: height(context) * 56 / 812,
      decoration: BoxDecoration(
        color: Colors.transparent,
        border: Border.all(color: greyColor),
        borderRadius: BorderRadius.circular(40),
      ),
      alignment: Alignment.center,
      child: Text(
        name,
        style: TextStyle(
          color: greyColor,
          fontWeight: FontWeight.w600,
          fontSize: 20,
        ),
      ),
    );

Container oauth2Buttons(String assetName, BuildContext context) => Container(
      width: 82 / 812 * height(context),
      height: 44 / 375 * width(context),
      alignment: Alignment.center,
      decoration: BoxDecoration(
          color: backgroundColor,
          borderRadius: BorderRadius.circular(5),
          boxShadow: const [
            BoxShadow(
                offset: Offset(2, 2),
                blurRadius: 15,
                color: Color.fromRGBO(204, 204, 204, 0.65))
          ]),
      child: Image.asset(assetName),
    );

Container smallTextButton(String name, BuildContext context) => Container(
      width: 86 / 812 * height(context),
      height: 39 / 375 * width(context),
      alignment: Alignment.center,
      decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(5), color: mainColor),
      child: Text(
        name,
        style: const TextStyle(
            fontWeight: FontWeight.w500, fontSize: 16, color: Colors.white),
      ),
    );

Container smallButtonTwo(IconData iconData) => Container(
  padding: const EdgeInsets.all(5),
  decoration: BoxDecoration(
      borderRadius: BorderRadius.circular(5),
      color: backgroundColor,
      boxShadow: [boxShadow]),
  child: Icon(
    iconData,
    color: mainColor,
  ),
);
