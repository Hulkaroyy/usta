import 'package:flutter/material.dart';
import 'package:handihub/providers/product_provider.dart';
import 'package:handihub/providers/shared_preferences.dart';
import 'package:handihub/static/colors.dart';
import 'package:handihub/static/forms.dart';
import 'package:handihub/static/url.dart';
import 'package:handihub/static/widgets.dart';
import 'package:provider/provider.dart';

class ProductInfoPage extends StatefulWidget {
  static String routeName = "/product-info";
  const ProductInfoPage({super.key});

  @override
  State<ProductInfoPage> createState() => _ProductInfoPageState();
}

class _ProductInfoPageState extends State<ProductInfoPage> {
  String? id;
  bool isLoading = true;
  bool isInit = true;
  Map<String, dynamic>? data;
  String? userId;

  final reviewController = TextEditingController();

  @override
  void didChangeDependencies() async {
    super.didChangeDependencies();
    if (isInit) {
      final productId = ModalRoute.of(context)!.settings.arguments as String;
      userId =
          Provider.of<SharedPreferencesProvider>(context, listen: false).id!;
      await Provider.of<ProductProvider>(context, listen: false)
          .fetchDataByID(productId, userId!)
          .then((value) {
        setState(() {
          data =
              Provider.of<ProductProvider>(context, listen: false).singleData;
        });
      });
    }
    setState(() {
      isInit = false;
      isLoading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: backgroundColor,
      body: isLoading
          ? Center(
              child: CircularProgressIndicator(color: mainColor),
            )
          : SizedBox(
              width: width(context),
              child: Column(
                children: [
                  Container(
                    height: 525,
                    decoration: const BoxDecoration(
                        color: Color.fromRGBO(254, 231, 206, 1),
                        borderRadius: BorderRadius.only(
                            bottomLeft: Radius.circular(32),
                            bottomRight: Radius.circular(32)),
                        boxShadow: [
                          BoxShadow(
                              offset: Offset(5, 10),
                              blurRadius: 18,
                              color: Color.fromRGBO(204, 204, 204, 0.48))
                        ]),
                    child: data!["contents"].isEmpty
                        ? Container(
                            height: 130,
                            decoration: const BoxDecoration(
                              color: Color.fromRGBO(254, 219, 180, 0.85),
                              shape: BoxShape.circle,
                            ),
                            child: Image.asset(
                              "assets/images/statue.png",
                              height: 120,
                            ),
                          )
                        : Image.network(
                            "$url/api/product/content/${data!["contents"][0]["id"]}",
                            headers: {"X-User-Id": "$userId"},
                            errorBuilder: (context, error, stackTrace) {
                              return Container(
                                height: 130,
                                decoration: const BoxDecoration(
                                  color: Color.fromRGBO(254, 219, 180, 0.85),
                                  shape: BoxShape.circle,
                                ),
                                child: Image.asset(
                                  "assets/images/statue.png",
                                  height: 120,
                                ),
                              );
                            },
                          ),
                  ),
                  sizedBox(20.0),
                  SizedBox(
                    width: 327,
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        text(data!["name"], const Color.fromRGBO(0, 0, 0, 1)),
                        textRegular("\$${data!["price"]}", greyColor)
                      ],
                    ),
                  ),
                  sizedBox(20.0),
                  SizedBox(
                    width: width(context) * 327 / 375,
                    child: Text(
                      data!["description"],
                      style: TextStyle(color: greyColor, fontSize: 14),
                    ),
                  ),
                  sizedBox(20.0),
                  SizedBox(
                    width: width(context) * 327 / 375,
                    child: const Row(
                      children: [
                        Text(
                          "Review",
                          style: TextStyle(
                              decoration: TextDecoration.underline,
                              fontSize: 14),
                        )
                      ],
                    ),
                  ),
                  sizedBox(10.0),
                  SizedBox(
                      width: 327 / 375 * width(context),
                      height: 34,
                      child: Row(
                        children: [
                          Expanded(
                            child: Container(
                              alignment: Alignment.center,
                              decoration: BoxDecoration(
                                  shape: BoxShape.circle,
                                  color: Colors.grey.shade200),
                              child: const Icon(Icons.person),
                            ),
                          ),
                          const SizedBox(width: 10),
                          Expanded(
                              flex: 9,
                              child: searchFormField(reviewController, null)),
                        ],
                      )),
                  sizedBox(50.0),
                  SizedBox(
                    width: width(context) * 327 / 375,
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.end,
                      children: [
                        Container(
                          height: 34,
                          width: 106,
                          alignment: Alignment.center,
                          decoration: BoxDecoration(
                              color: mainColor,
                              borderRadius: BorderRadius.circular(15)),
                          child: const Text(
                            "Buy now",
                            style: TextStyle(
                                color: Colors.white,
                                fontFamily: 'Montserrat',
                                fontSize: 14),
                          ),
                        ),
                      ],
                    ),
                  )
                ],
              ),
            ),
    );
  }
}
