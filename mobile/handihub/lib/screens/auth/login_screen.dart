import 'package:flutter/material.dart';
import 'package:handihub/providers/authentication.dart';
import 'package:handihub/screens/auth/registraion_screen_one.dart';
import 'package:handihub/screens/auth/reset_password.dart';
import 'package:handihub/screens/init_page.dart';
import 'package:handihub/static/buttons.dart';
import 'package:handihub/static/colors.dart';
import 'package:handihub/static/forms.dart';
import 'package:handihub/static/snack.dart';
import 'package:handihub/static/title.dart';
import 'package:handihub/static/widgets.dart';
import 'package:provider/provider.dart';
import 'package:shared_preferences/shared_preferences.dart';

class LoginScreen extends StatefulWidget {
  static String routeName = "/login";
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  bool? obscure = false;
  bool? isLoading = false;

  final key = GlobalKey<FormState>();

  final usernameController = TextEditingController();
  final passwordController = TextEditingController();

  TextFormField? password;

  @override
  void dispose() {
    super.dispose();
    usernameController.dispose();
    passwordController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: ()async => false,
      child: Scaffold(
        backgroundColor: backgroundColor,
        body: SizedBox(
          width: width(context),
          child: SingleChildScrollView(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                sizedBox(79.0 / 812 * height(context)),
                title,
                sizedBox(94.0 / 812 * height(context)),
                SizedBox(
                  width: 327 / 375 * width(context),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      text("Login to your account"),
                      sizedBox(18.0 / 812 * height(context)),
                      Form(
                        key: key,
                        child: Column(
                          children: [
                            SizedBox(
                              height: 91 / 812 * height(context),
                              child: textFormField(
                                  "Username", usernameController, validate, null),
                            ),
                            Stack(
                              alignment: Alignment.center,
                              children: [
                                SizedBox(
                                    height: 91 / 812 * height(context),
                                    child: textFormField(
                                        "Password",
                                        passwordController,
                                        validatePassword,
                                        obscure)),
                                Align(
                                  alignment: const Alignment(0.93, 0),
                                  child: InkWell(
                                    overlayColor: const MaterialStatePropertyAll(
                                        Colors.transparent),
                                    onTap: () {
                                      setState(() {
                                        obscure = !obscure!;
                                      });
                                    },
                                    child: Container(
                                      height: 24,
                                      width: 24,
                                      color: Colors.transparent,
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ],
                        ),
                      ),
                      InkWell(
                        onTap: () {
                          Navigator.of(context)
                              .pushNamed(ResetPassword.routeName);
                        },
                        child: textRegular("Forgot password",
                            const Color.fromRGBO(249, 194, 133, 1)),
                      )
                    ],
                  ),
                ),
                sizedBox(21.0 / 812 * height(context)),
                InkWell(
                  onTap: !isLoading!
                      ? () {
                          if (!key.currentState!.validate()) {
                            return;
                          }
      
                          login(usernameController.text, passwordController.text);
                        }
                      : null,
                  child: textButton(
                      isLoading! ? "Loading .." : "Sign In", context, boxShadow),
                ),
                sizedBox(60.0 / 812 * height(context)),
                dynamicText("- Or sign in with -", 12.0, greyColor),
                sizedBox(23.0 / 812 * height(context)),
                SizedBox(
                  width: 272 / 375 * width(context),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      oauth2Buttons("assets/images/google.png", context),
                      oauth2Buttons("assets/images/facebook.png", context),
                      oauth2Buttons("assets/images/twitter.png", context),
                    ],
                  ),
                ),
                sizedBox(50.0 / 812 * height(context)),
                InkWell(
                    onTap: () => Navigator.of(context)
                        .pushNamed(RegistrationScreenOne.routeName),
                    child: signUp)
              ],
            ),
          ),
        ),
      ),
    );
  }

  Future login(String username, String password) async {
    setState(() {
      isLoading = true;
    });

    await Provider.of<AuthenticationProvider>(context, listen: false)
        .sign(username, password)
        .then((value) async {
      String? error =
          Provider.of<AuthenticationProvider>(context, listen: false).error;
      String? id =
          Provider.of<AuthenticationProvider>(context, listen: false).id;

      if (error != null) {
        setState(() {
          isLoading = false;
        });
        showSnackBar(context, error);
        return;
      }

      if (id != null) {
        Navigator.of(context).pushNamed(InitPage.routeName);
        final prefs = await SharedPreferences.getInstance();
        prefs.setString("id", id);
      }
    });
  }
}

String? validatePassword(String? value) {
  if (value!.isEmpty) {
    return "* Required";
  }

  if (value.length < 4) {
    return "Password should be at least 6 characters";
  }

  return null;
}

String? validate(String? value) {
  if (value!.isEmpty) {
    return "* Required";
  }
  return null;
}
