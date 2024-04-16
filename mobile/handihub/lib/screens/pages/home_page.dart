import 'dart:math';

import 'package:flutter/material.dart';
import 'package:handihub/providers/product_provider.dart';
import 'package:handihub/providers/shared_preferences.dart';
import 'package:handihub/screens/pages/product_info_page.dart';
import 'package:handihub/static/colors.dart';
import 'package:handihub/static/forms.dart';
import 'package:handihub/static/title.dart';
import 'package:handihub/static/url.dart';
import 'package:handihub/static/widgets.dart';
import 'package:provider/provider.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final searchController = TextEditingController();

  bool isLoading = false;

  List<dynamic>? data;
  String? userId;

  @override
  void didChangeDependencies() async {
    super.didChangeDependencies();
    userId = Provider.of<SharedPreferencesProvider>(context, listen: false).id!;
    setState(() {
      isLoading = true;
    });
    await Provider.of<ProductProvider>(context, listen: false)
        .fetchData(userId!)
        .then((value) {
      setState(() {
        data = Provider.of<ProductProvider>(context, listen: false).products;
        isLoading = false;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: SizedBox(
        height: height(context),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            sizedBox(38 / 812 * height(context)),
            title,
            sizedBox(25 / 812 * height(context)),
            SizedBox(
              width: 330 / 375 * width(context),
              height: 34,
              child: Row(
                children: [
                  Expanded(
                    flex: 288,
                    child: searchFormField(
                        searchController,
                        Icon(
                          Icons.search,
                          color: greyColor,
                        )),
                  ),
                  const Expanded(flex: 12, child: SizedBox()),
                  Expanded(
                    flex: 30,
                    child: Container(
                      decoration: const BoxDecoration(
                          color: Color.fromRGBO(254, 231, 206, 1),
                          shape: BoxShape.circle),
                      alignment: Alignment.center,
                      child: Icon(
                        Icons.arrow_forward_ios,
                        color: mainColor,
                        size: 20,
                      ),
                    ),
                  ),
                ],
              ),
            ),
            sizedBox(12.0),
            SizedBox(
              height: 210,
              child: Stack(
                alignment: Alignment.center,
                children: [
                  Container(
                    width: width(context) * 327 / 375,
                    height: 110,
                    padding: const EdgeInsets.all(19),
                    alignment: const Alignment(0.6, 0),
                    decoration: BoxDecoration(
                        color: mainColor,
                        borderRadius: BorderRadius.circular(10),
                        boxShadow: const [
                          BoxShadow(
                              offset: Offset(0, 6),
                              blurRadius: 10,
                              color: Color.fromRGBO(204, 204, 204, 1))
                        ]),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        text("Laganazavr", Colors.white),
                        const Text(
                          "The Biggest lagan\nin the werld",
                          style: TextStyle(color: Colors.white, fontSize: 14),
                        )
                      ],
                    ),
                  ),
                  Align(
                    alignment: const Alignment(-1, 0.0),
                    child: Transform.rotate(
                      angle: pi * 23.31 / 90,
                      child: Image.asset("assets/images/lagan.png",
                          height: 220, width: 220),
                    ),
                  ),
                ],
              ),
            ),
            SizedBox(
              width: width(context) * 327 / 375,
              height: height(context) * 0.5,
              child: isLoading
                  ? const Center(
                      child: CircularProgressIndicator(),
                    )
                  : GridView.builder(
                      gridDelegate:
                          const SliverGridDelegateWithFixedCrossAxisCount(
                              childAspectRatio: 0.6,
                              crossAxisSpacing: 10,
                              crossAxisCount: 2),
                      itemCount: data!.length,
                      itemBuilder: (context, index) {
                        Map<String, dynamic> product = data![index];
                        return InkWell(
                          onTap: () {
                            Navigator.of(context).pushNamed(
                                ProductInfoPage.routeName,
                                arguments: product["id"]);
                          },
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Container(
                                  height: 200,
                                  alignment: Alignment.center,
                                  decoration: BoxDecoration(
                                    color: const Color.fromRGBO(
                                        254, 219, 180, 0.65),
                                    borderRadius: BorderRadius.circular(38),
                                  ),
                                  child: product["contents"].isEmpty
                                      ? Container(
                                          height: 130,
                                          decoration: const BoxDecoration(
                                            color: Color.fromRGBO(
                                                254, 219, 180, 0.85),
                                            shape: BoxShape.circle,
                                          ),
                                          child: Image.asset(
                                              "assets/images/statue.png"),
                                        )
                                      : Image.network(
                                          "$url/api/product/content/${product["contents"][0]["id"]}",
                                          headers: {"X-User-Id": "$userId"},
                                          errorBuilder:
                                              (context, error, stackTrace) {
                                            return Container(
                                              height: 130,
                                              decoration: const BoxDecoration(
                                                color: Color.fromRGBO(
                                                    254, 219, 180, 0.85),
                                                shape: BoxShape.circle,
                                              ),
                                              child: Image.asset(
                                                  "assets/images/statue.png"),
                                            );
                                          },
                                        )),
                              Container(
                                padding: const EdgeInsets.all(0),
                                child: Row(
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceBetween,
                                  children: [
                                    Column(
                                      crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                      children: [
                                        Text(
                                          product["name"],
                                          maxLines: 2,
                                          style: const TextStyle(
                                              fontSize: 16,
                                              fontWeight: FontWeight.w500,
                                              color: Color.fromRGBO(
                                                  46, 64, 70, 1)),
                                        ),
                                        Text(
                                          "\$${product["price"]}",
                                          style: const TextStyle(
                                              color: Color.fromRGBO(
                                                  95, 113, 118, 1)),
                                        )
                                      ],
                                    ),
                                    CircleAvatar(
                                      radius: 14,
                                      backgroundColor: const Color.fromRGBO(
                                        254,
                                        219,
                                        180,
                                        0.85,
                                      ),
                                      child: Image.asset(
                                        "assets/images/Vectorcart.png",
                                        color: mainColor,
                                      ),
                                    )
                                  ],
                                ),
                              )
                            ],
                          ),
                        );
                      },
                    ),
            )
          ],
        ),
      ),
    );
  }
}
