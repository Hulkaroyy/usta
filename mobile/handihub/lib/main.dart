import 'package:flutter/material.dart';
import 'package:handihub/providers/authentication.dart';
import 'package:handihub/providers/product_provider.dart';
import 'package:handihub/providers/shared_preferences.dart';
import 'package:handihub/screens/auth/login_screen.dart';
import 'package:handihub/screens/auth/registraion_screen_one.dart';
import 'package:handihub/screens/auth/registraion_screen_two.dart';
import 'package:handihub/screens/auth/reset_password.dart';
import 'package:handihub/screens/home_page.dart';
import 'package:handihub/screens/init_page.dart';
import 'package:handihub/screens/pages/create_post_upload.dart';
import 'package:handihub/screens/pages/product_info_page.dart';

import 'package:handihub/screens/starter_screen_two.dart';
import 'package:provider/provider.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (c) => AuthenticationProvider()),
        ChangeNotifierProvider(create: (c) => SharedPreferencesProvider()),
        ChangeNotifierProvider(create: (c) => ProductProvider())
      ],
      child: MaterialApp(
        title: 'Flutter Demo',
        theme: ThemeData(
          fontFamily: 'Inter',
          useMaterial3: true,
        ),
        home: const InitPage(),
        routes: {
          StarterScreenTwo.routeName: (context) => const StarterScreenTwo(),
          RegistrationScreenOne.routeName: (context) =>
              const RegistrationScreenOne(),
          LoginScreen.routeName: (context) => const LoginScreen(),
          RegistrationScreenTwo.routeName: (context) =>
              const RegistrationScreenTwo(),
          HomePageScreen.routeName: (context) => const HomePageScreen(),
          ResetPassword.routeName: (context) => const ResetPassword(),
          InitPage.routeName: (context) => const InitPage(),
          CreatePostUploadPhotos.routeName: (context) =>
              const CreatePostUploadPhotos(),
          ProductInfoPage.routeName: (context) => const ProductInfoPage(),
        },
      ),
    );
  }
}
