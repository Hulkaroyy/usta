import 'package:flutter/material.dart';
import 'package:handihub/providers/authentication.dart';
import 'package:handihub/providers/shared_preferences.dart';
import 'package:handihub/screens/home_page.dart';
import 'package:handihub/screens/splash_screen.dart';
import 'package:handihub/screens/starter_screen_one.dart';
import 'package:provider/provider.dart';

class InitPage extends StatefulWidget {
  static const routeName = "/init-page";
  const InitPage({super.key});

  @override
  State<InitPage> createState() => _InitPageState();
}

class _InitPageState extends State<InitPage> {
  bool? isLoading = true;
  String? id;

  @override
  void didChangeDependencies() async {
    super.didChangeDependencies();
    bool isInit = true;
    SharedPreferencesProvider sharedPreferencesProvider =
        Provider.of<SharedPreferencesProvider>(context, listen: false);

    if (isInit) {
      await sharedPreferencesProvider
          .fetchData()
          .then((value) => setState(() {
                id = sharedPreferencesProvider.id;
              }))
          .then((value) async {
        if (id != null) {
          await Provider.of<AuthenticationProvider>(context, listen: false)
              .getInfoById(id!);
        }
      });

      setState(() {
        isInit = false;
        isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return isLoading!
        ? const SplashScreen()
        : (id == null)
            ? const StarterScreenOne()
            : const HomePageScreen();
  }
}
