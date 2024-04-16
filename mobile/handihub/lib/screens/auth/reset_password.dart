import 'package:flutter/material.dart';
import 'package:handihub/providers/authentication.dart';
import 'package:handihub/screens/auth/login_screen.dart';
import 'package:handihub/static/buttons.dart';
import 'package:handihub/static/colors.dart';
import 'package:handihub/static/forms.dart';
import 'package:handihub/static/snack.dart';
import 'package:handihub/static/title.dart';
import 'package:handihub/static/widgets.dart';
import 'package:provider/provider.dart';

class ResetPassword extends StatefulWidget {
  static String routeName = "/reset-password";
  const ResetPassword({super.key});

  @override
  State<ResetPassword> createState() => _ResetPasswordState();
}

class _ResetPasswordState extends State<ResetPassword> {
  final emailController = TextEditingController();

  @override
  void dispose() {
    super.dispose();
    emailController.dispose();
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
              sizedBox(79 / 812 * height(context)),
              title,
              sizedBox(140 / 812 * height(context)),
              SizedBox(
                width: 327 / 375 * width(context),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    text("Password reset"),
                    sizedBox(28 / 812 * height(context)),
                    resetPasswordInfo,
                    sizedBox(28 / 812 * height(context)),
                    textFormField("Email", emailController, validate, null),
                  ],
                ),
              ),
              sizedBox(30 / 812 * height(context)),
              SizedBox(
                width: 310/375*width(context),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    InkWell(
                        onTap: ()=> Navigator.of(context).pop(),
                        child: roundedButtonsOutlined("Cancel", context)),
                    InkWell(
                        onTap: resetPassword,
                        child: roundedButtons("Continue", context)),
                  ],
                ),
              )
            ],
          ),
        ),
      ),
    );
  }

  Future resetPassword() async {
    if (emailController.text.isEmpty) {
      showSnackBar(context, "Please enter your email");
      return;
    }

    await Provider.of<AuthenticationProvider>(context, listen: false)
        .resetPassword(emailController.text.toString())
        .then((value) {
      String? error =
          Provider.of<AuthenticationProvider>(context, listen: false).error;
      bool? isSuccess =
          Provider.of<AuthenticationProvider>(context, listen: false).isSuccess;

      if (error != null) {
        showSnackBar(context, error);
        return;
      }

      if (isSuccess != null) {
        if (isSuccess) {
          showSnackBar(
              context,
              "We sent link for resetting password to your email",
              Colors.green);
        }
      }
    });
  }
}
