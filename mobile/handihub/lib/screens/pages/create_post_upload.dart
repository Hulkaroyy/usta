import 'package:flutter/material.dart';
import 'package:handihub/providers/product_provider.dart';
import 'package:handihub/providers/shared_preferences.dart';
import 'package:handihub/static/colors.dart';
import 'package:handihub/static/title.dart';
import 'package:handihub/static/widgets.dart';
import 'package:provider/provider.dart';

class CreatePostUploadPhotos extends StatefulWidget {
  static String routeName = "/create-post";
  const CreatePostUploadPhotos({super.key});

  @override
  State<CreatePostUploadPhotos> createState() => _CreatePostUploadPhotosState();
}

class _CreatePostUploadPhotosState extends State<CreatePostUploadPhotos> {
  bool isInit = true;
  String? productId;
  String? userId;
  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    if (isInit) {
      userId =
          Provider.of<SharedPreferencesProvider>(context, listen: false).id;
      productId = Provider.of<ProductProvider>(context).singleData!["id"];
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: backgroundColor,
        body: SingleChildScrollView(
          child: SizedBox(
            height: height(context),
            width: width(context),
            child: Column(children: [
              sizedBox(79 / 812 * height(context)),
              title,
              sizedBox(79 / 812 * height(context)),
              InkWell(
                onTap: () {
                  
                },
                child: Container(
                  width: 120 / 375 * height(context),
                  height: 60,
                  alignment: Alignment.center,
                  decoration: BoxDecoration(
                      color: mainColor,
                      borderRadius: BorderRadius.circular(15),
                      boxShadow: [boxShadow]),
                  child: const Text("Upload photos",
                      style: TextStyle(color: Colors.white, fontSize: 20)),
                ),
              ),
            ]),
          ),
        ));
  }
}
