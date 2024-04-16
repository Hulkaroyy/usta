import 'package:flutter/material.dart';
import 'package:handihub/providers/authentication.dart';
import 'package:handihub/screens/auth/login_screen.dart';
import 'package:handihub/screens/home_page.dart';
import 'package:handihub/static/buttons.dart';
import 'package:handihub/static/colors.dart';
import 'package:handihub/static/enums.dart';
import 'package:handihub/static/forms.dart';
import 'package:handihub/static/snack.dart';
import 'package:handihub/static/title.dart';
import 'package:handihub/static/widgets.dart';
import 'package:provider/provider.dart';
import 'package:shared_preferences/shared_preferences.dart';

class RegistrationScreenTwo extends StatefulWidget {
  static String routeName = "/registration-two";
  const RegistrationScreenTwo({super.key});

  @override
  State<RegistrationScreenTwo> createState() => _RegistrationScreenTwoState();
}

class _RegistrationScreenTwoState extends State<RegistrationScreenTwo> {
  UserType? userType;
  bool? obscure = false;
  bool? isLoading = false;

  final key = GlobalKey<FormState>();

  final fullNameController = TextEditingController();
  final userNameController = TextEditingController();
  final emailController = TextEditingController();
  final passwordController = TextEditingController();

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    userType = ModalRoute.of(context)!.settings.arguments as UserType;
  }

  @override
  void dispose() {
    super.dispose();
    fullNameController.dispose();
    userNameController.dispose();
    emailController.dispose();
    passwordController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: backgroundColor,
      body: SizedBox(
        width: width(context),
        height: height(context),
        child: SingleChildScrollView(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              sizedBox(65.0 / 812 * height(context)),
              title,
              sizedBox(50.0 / 812 * height(context)),
              SizedBox(
                width: 327 / 375 * width(context),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    text("Create your account"),
                    sizedBox(10.0 / 812 * height(context)),
                    Form(
                      key: key,
                      child: Column(
                        children: [
                          SizedBox(
                            height: 88.0 / 812 * height(context),
                            child: textFormField(
                                "Email", emailController, validate, null),
                          ),
                          SizedBox(
                            height: 88.0 / 812 * height(context),
                            child: textFormField("Full Name",
                                fullNameController, validate, null),
                          ),
                          SizedBox(
                            height: 88.0 / 812 * height(context),
                            child: textFormField(
                                "Username", userNameController, validate, null),
                          ),
                          Stack(
                            alignment: Alignment.center,
                            children: [
                              SizedBox(
                                height: 88.0 / 812 * height(context),
                                child: textFormField(
                                    "Password",
                                    passwordController,
                                    validatePassword,
                                    obscure),
                              ),
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
                  ],
                ),
              ),
              sizedBox(15.0),
              InkWell(
                onTap: isLoading!
                    ? null
                    : () {
                        if (!key.currentState!.validate()) {
                          return;
                        }
                        register();
                      },
                child: textButton(
                    isLoading! ? "Loading..." : "Sign Up", context, boxShadow),
              ),
              sizedBox(30.0 / 812 * height(context)),
              dynamicText("- Or sign up with -", 12.0, greyColor),
              sizedBox(10.0 / 812 * height(context)),
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
            ],
          ),
        ),
      ),
    );
  }

  register() async {
    var role = "CUSTOMER";
    if (userType == UserType.seller) {
      role = "SELLER";
    }
    Map<String, String> data = {
      "email": emailController.text.trim(),
      "password": passwordController.text.trim(),
      "fullName": fullNameController.text.trim(),
      "username": userNameController.text.trim(),
      "role": "ROLE_$role"
    };

    setState(() {
      isLoading = true;
    });

    await Provider.of<AuthenticationProvider>(context, listen: false)
        .register(data)
        .then((value) async {
      bool? isSuccess =
          Provider.of<AuthenticationProvider>(context, listen: false).isSuccess;
      String? id =
          Provider.of<AuthenticationProvider>(context, listen: false).id;
      String? error =
          Provider.of<AuthenticationProvider>(context, listen: false).error;
      if (isSuccess != null) {
        if (isSuccess) {
          showSnackBar(context, "We sent verification email, check your box",
              Colors.green);
          Navigator.of(context).pushNamed(HomePageScreen.routeName);
          final prefs = await SharedPreferences.getInstance();
          prefs.setString("type", role);
          prefs.setString("id", id!);
          return;
        }
      }

      if (error != null) {
        setState(() {
          isLoading = false;
        });
        showSnackBar(context, error);

        return;
      }
      setState(() {
        isLoading = false;
      });
      showSnackBar(context, "Server error, 500");
    });
  }
}
