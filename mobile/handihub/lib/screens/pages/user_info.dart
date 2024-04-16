import 'package:flutter/material.dart';
import 'package:handihub/providers/authentication.dart';
import 'package:handihub/providers/shared_preferences.dart';
import 'package:handihub/screens/auth/login_screen.dart';
import 'package:handihub/static/buttons.dart';
import 'package:handihub/static/colors.dart';
import 'package:handihub/static/snack.dart';
import 'package:handihub/static/widgets.dart';
import 'package:provider/provider.dart';
import 'package:shared_preferences/shared_preferences.dart';

class UserInfoPage extends StatefulWidget {
  const UserInfoPage({super.key});

  @override
  State<UserInfoPage> createState() => _UserInfoPageState();
}

class _UserInfoPageState extends State<UserInfoPage> {
  Map<String, dynamic>? data;

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    data = Provider.of<AuthenticationProvider>(context, listen: false)
        .userInfoBody;
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        sizedBox(43 / 812 * height(context)),
        SizedBox(
          width: 327 / 375 * width(context),
          height: 40 / 812 * height(context),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              InkWell(
                  onTap: ()async{
                    Navigator.of(context).pushNamed(LoginScreen.routeName);
                    final prefs= await SharedPreferences.getInstance();
                    prefs.remove("type");
                    prefs.remove("token");
                    prefs.remove("id");
                  }, child: smallButtonTwo(Icons.login_outlined)),
              InkWell(
                  onTap: () async {
                    await Provider.of<AuthenticationProvider>(context,
                            listen: false)
                        .getInfoById(Provider.of<SharedPreferencesProvider>(
                                context,
                                listen: false)
                            .id!)
                        .then((value) {
                      setState(() {
                        data = Provider.of<AuthenticationProvider>(context,
                                listen: false)
                            .userInfoBody;
                      });
                    });
                  },
                  child: smallButtonTwo(Icons.refresh))
            ],
          ),
        ),
        sizedBox(40 / 812 * height(context)),
        Container(
          width: width(context) * 150 / 375,
          height: width(context) * 150 / 375,
          decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(width(context) * 75 / 375)),
          child: data!["imageUrl"] == null
              ? Image.asset(
                  "assets/images/user.png",
                  height: 150,
                  width: 150,
                )
              : Image.network(
                  data!["imageUrl"],
                  errorBuilder: (context, error, stackTrace) {
                    return Image.asset("assets/images/user.png");
                  },
                ),
        ),
        sizedBox(19 / 812 * height(context)),
        Text(
          data!["fullName"],
          style: const TextStyle(
              color: Colors.black, fontSize: 20, fontWeight: FontWeight.w600),
        ),
        if (data!["phoneNumber"] == null) ...{
          sizedBox(19 / 812 * height(context)),
          InkWell(
            onTap: () {},
            child: textRegular("Add Phone Number", mainColor),
          )
        },
        if (data!["phoneNumber"] != null) ...{
          sizedBox(19 / 812 * height(context)),
          textRegular(data!["phoneNumber"], mainColor),
        },
        sizedBox(19 / 812 * height(context)),
        if (!data!["emailVerified"]) ...{
          InkWell(
            onTap: () async {
              await Provider.of<AuthenticationProvider>(context, listen: false)
                  .verify(Provider.of<SharedPreferencesProvider>(context,
                          listen: false)
                      .id!)
                  .then(
                    (value) => showSnackBar(
                        context,
                        "We sent verification email, you shoul sign in agan",
                        Colors.green),
                  );
            },
            child: warningText(
                "Email is not Verified, Verify?", Colors.grey.shade700),
          )
        },
        sizedBox(19 / 812 * height(context)),
        SizedBox(
          width: width(context) * 327 / 375,
          child: Divider(
            height: 1.0,
            color: greyColor,
          ),
        ),
        SizedBox(
          width: width(context) * 327 / 375,
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              dynamicText("Email", 20.0, greyColor),
              dynamicText(data!["email"], 16.0, mainColor)
            ],
          ),
        ),
        sizedBox(12 / 812 * height(context)),
        SizedBox(
          width: width(context) * 327 / 375,
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            crossAxisAlignment: CrossAxisAlignment.end,
            children: [
              dynamicText("username", 20.0, greyColor),
              dynamicText(data!["username"], 16.0, mainColor)
            ],
          ),
        ),
        if (data!["role"] == "ROLE_SELLER") ...{
          sizedBox(30 / 812 * height(context)),
          if (data!["licenseUrl"] == null) ...{
            warningText(
              "License is not found, Upload",
              Colors.red,
            )
          },
          if (data!["licenseUrl"] != null) ...{
            smallButton("See License", context)
          },
          if (data!["sellerType"] == null) ...{
            sizedBox(30 / 812 * height(context)),
            InkWell(
                onTap: () {},
                child: warningText("Choose your profession", Colors.red))
          } else ...{
            sizedBox(30 / 812 * height(context)),
            warningText(data!["sellerType"], mainColor)
          }
        },
      ],
    );
  }

  Container smallButton(text, BuildContext context) => Container(
        width: width(context) * 80 / 375,
        height: height(context) * 140 / 812,
        alignment: Alignment.center,
        decoration: BoxDecoration(color: mainColor),
        child: Text(
          text,
          style: const TextStyle(color: Colors.white),
        ),
      );
}
