import 'package:flutter/material.dart';
import 'package:handihub/providers/authentication.dart';
import 'package:handihub/providers/shared_preferences.dart';
import 'package:handihub/screens/pages/create_product.dart';
import 'package:handihub/screens/pages/home_page.dart';
import 'package:handihub/screens/pages/user_info.dart';
import 'package:handihub/static/colors.dart';
import 'package:handihub/static/enums.dart';
import 'package:handihub/static/widgets.dart';
import 'package:provider/provider.dart';

class HomePageScreen extends StatefulWidget {
  static String routeName = "/home-page";
  const HomePageScreen({super.key});

  @override
  State<HomePageScreen> createState() => _HomePageScreenState();
}

enum Pages { userInfo, homePage, createPost, products }

class _HomePageScreenState extends State<HomePageScreen> {
  String? token;
  UserType? userType;
  String? id;
  Pages? page = Pages.userInfo;
  Map<String, dynamic>? data;
  bool isInit = true;

  @override
  void didChangeDependencies() async {
    super.didChangeDependencies();
    if (isInit) {
      SharedPreferencesProvider shProvider =
          Provider.of<SharedPreferencesProvider>(context, listen: false);

      data = Provider.of<AuthenticationProvider>(context, listen: false)
          .userInfoBody;
      id = shProvider.id;
      userType = shProvider.type;
      setState(() {
        isInit = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: backgroundColor,
      body: SizedBox(
          width: width(context),
          height: height(context),
          child: Stack(
            alignment: Alignment.center,
            children: [
              if (page == Pages.userInfo) ...{
                const UserInfoPage(),
              },
              if (page == Pages.homePage) ...{const HomePage()},
              if (page == Pages.createPost) ...{const CreateProductPage()},
              Align(
                alignment: const Alignment(0, 0.9),
                child: Container(
                  padding: const EdgeInsets.all(3),
                  width: width(context) * 265 / 375,
                  height: height(context) * 72 / 812,
                  decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(40),
                      boxShadow: const [
                        BoxShadow(
                            offset: Offset(0, 4),
                            color: Color.fromRGBO(0, 0, 0, 0.1),
                            blurRadius: 20)
                      ]),
                  child: Row(
                    children: [
                      InkWell(
                        overlayColor:
                            const MaterialStatePropertyAll(Colors.transparent),
                        onTap: () {
                          setState(() {
                            page = Pages.homePage;
                          });
                        },
                        child: Container(
                            alignment: Alignment.center,
                            width: 66,
                            child: Image.asset(
                              "assets/images/home-3-fill.png",
                              color: page == Pages.homePage
                                  ? mainColor
                                  : const Color.fromRGBO(158, 159, 186, 1),
                            )),
                      ),
                      InkWell(
                        overlayColor:
                            const MaterialStatePropertyAll(Colors.transparent),
                        onTap: () {
                          setState(() {
                            page = Pages.createPost;
                          });
                        },
                        child: Container(
                            alignment: Alignment.center,
                            width: 66,
                            child: Image.asset(
                              "assets/images/Vectoradd.png",
                              color: page == Pages.createPost
                                  ? mainColor
                                  : const Color.fromRGBO(158, 159, 186, 1),
                            )),
                      ),
                      InkWell(
                        overlayColor:
                            const MaterialStatePropertyAll(Colors.transparent),
                        onTap: () {
                          setState(() {
                            page = Pages.products;
                          });
                        },
                        child: Container(
                            alignment: Alignment.center,
                            width: 66,
                            child: Image.asset(
                              "assets/images/Vectorcart.png",
                              color: page == Pages.products
                                  ? mainColor
                                  : const Color.fromRGBO(158, 159, 186, 1),
                            )),
                      ),
                      InkWell(
                        overlayColor:
                            const MaterialStatePropertyAll(Colors.transparent),
                        onTap: () {
                          setState(() {
                            page = Pages.userInfo;
                          });
                        },
                        child: Container(
                          alignment: Alignment.center,
                          width: 66,
                          child: Image.asset(
                            "assets/images/user-fill.png",
                            color: page == Pages.userInfo
                                ? mainColor
                                : const Color.fromRGBO(158, 159, 186, 1),
                          ),
                        ),
                      )
                    ],
                  ),
                ),
              )
            ],
          )),
    );
  }
}
