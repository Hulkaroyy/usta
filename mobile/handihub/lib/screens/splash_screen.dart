import 'package:flutter/material.dart';
import 'package:handihub/static/colors.dart';

class SplashScreen extends StatelessWidget {
  const SplashScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: mainColor,
      body: const Center(
        child: Text("Usta.", style: TextStyle(color: Colors.white, fontWeight: FontWeight.w700, fontSize: 40),),
      ),
    );
  }
}
