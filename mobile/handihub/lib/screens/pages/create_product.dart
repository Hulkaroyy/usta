import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:handihub/providers/product_provider.dart';
import 'package:handihub/providers/shared_preferences.dart';
import 'package:handihub/screens/auth/login_screen.dart';
import 'package:handihub/screens/pages/create_post_upload.dart';
import 'package:handihub/static/buttons.dart';
import 'package:handihub/static/forms.dart';
import 'package:handihub/static/snack.dart';
import 'package:handihub/static/title.dart';
import 'package:handihub/static/widgets.dart';
import 'package:provider/provider.dart';

class CreateProductPage extends StatefulWidget {
  const CreateProductPage({super.key});

  @override
  State<CreateProductPage> createState() => _CreateProductPageState();
}

class _CreateProductPageState extends State<CreateProductPage> {
  final key = GlobalKey<FormState>();

  final nameController = TextEditingController();
  final priceContoller = TextEditingController();
  final descriptionController = TextEditingController();

  String? userId;
  bool isLoading = false;

  Map<String, Map<String, dynamic>> types = {
    "Pottery": {
      "value": "POTTERY",
      "enabled": false,
    },
    "Sculptue": {
      "value": "SCULPTURE",
      "enabled": false,
    },
    "Carperbaggin": {
      "value": "CARPETBAGGING",
      "enabled": true,
    },
    "EMBROIDERY": {
      "value": "EMBROIDERY",
      "enabled": false,
    }
  };

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    userId = Provider.of<SharedPreferencesProvider>(context, listen: false).id;
  }

  @override
  void dispose() {
    super.dispose();
    nameController.dispose();
    priceContoller.dispose();
    descriptionController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: SizedBox(
          height: height(context),
          child: Column(
            children: [
              sizedBox(79 / 812 * height(context)),
              title,
              sizedBox(83 / 812 * height(context)),
              Container(
                  width: width(context) * 307 / 375,
                  alignment: Alignment.centerLeft,
                  child: text("Choose product type")),
              sizedBox(22 / 812 * height(context)),
              Wrap(
                children: types.keys
                    .map((e) => InkWell(
                          onTap: () {
                            for (var element in types.keys) {
                              types[element]!["enabled"] = false;
                            }
                            setState(() {
                              types[e]!["enabled"] = !types[e]!["enabled"];
                            });
                          },
                          child: smallOutlinedButtons(e, types[e]!["enabled"]),
                        ))
                    .toList(),
              ),
              sizedBox(20.0),
              SizedBox(
                  width: width(context) * 327 / 375,
                  child: Form(
                    key: key,
                    child: Column(
                      children: [
                        textFormField(
                            "Product name", nameController, validate, null),
                        sizedBox(12.0),
                        textFormField("Product price", priceContoller, (val) {
                          if (val!.isEmpty) {
                            return "* Required";
                          }
                          if ((val is Double || val is int)) {
                            return "Please enter valid value";
                          }
                          return null;
                        }, null),
                        sizedBox(12.0),
                        textFormField("Description", descriptionController,
                            (val) => null, null),
                        sizedBox(20.0),
                        InkWell(
                            onTap: isLoading ? null : create,
                            child: roundedButtons(isLoading ? "Loading .." : "Next", context,
                                const Color.fromRGBO(127, 85, 85, 1)))
                      ],
                    ),
                  ))
            ],
          )),
    );
  }

  create() async {
    if (!key.currentState!.validate()) {
      return;
    }

    String type =
        types.keys.firstWhere((element) => types[element]!["enabled"]);
        
    Map<String, String> data = {
      "name": nameController.text,
      "price": priceContoller.text,
      "description": descriptionController.text,
      "category": types[type]!["value"]
    };

    setState(() {
      isLoading = true;
    });

    ProductProvider productProvider =
        Provider.of<ProductProvider>(context, listen: false);

    await productProvider.create(data, userId!).then((value) {
      if (productProvider.error != null) {
        showSnackBar(context, productProvider.error!);
        setState(() {
          isLoading = true;
        });
        return;
      }
      setState(() {
        isLoading = true;
      });
      Navigator.of(context).pushNamed(CreatePostUploadPhotos.routeName);
    });
  }

  Container smallOutlinedButtons(String text, bool enabled) => Container(
        height: 40,
        margin: const EdgeInsets.all(5),
        padding: const EdgeInsets.all(10),
        decoration: BoxDecoration(
            color: enabled
                ? const Color.fromRGBO(127, 85, 85, 1)
                : Colors.transparent,
            borderRadius: BorderRadius.circular(10),
            border: Border.all(
                color: const Color.fromRGBO(127, 85, 85, 1), width: 0.5)),
        child: Text(
          text,
          style: TextStyle(
              fontFamily: "Montserrat",
              fontSize: 15,
              fontWeight: FontWeight.w500,
              color: enabled
                  ? Colors.white
                  : const Color.fromRGBO(127, 85, 85, 1)),
        ),
      );
}
