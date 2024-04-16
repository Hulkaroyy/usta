import 'package:flutter/material.dart';
import 'package:handihub/static/enums.dart';
import 'package:shared_preferences/shared_preferences.dart';

class SharedPreferencesProvider extends ChangeNotifier {
  String? _id;
  String? get id => _id;
  UserType? _type;
  UserType? get type => _type;

  Future fetchData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    _id = prefs.getString("id");
    _type = prefs.getString("type") == null
        ? null
        : prefs.getString("type") == "CUSTOMER"
            ? UserType.customer
            : UserType.seller;
  }
}
