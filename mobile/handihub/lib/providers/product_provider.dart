import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:handihub/static/url.dart';
import 'package:http/http.dart' as http;

class ProductProvider extends ChangeNotifier {
  Map<String, dynamic>? _singleData;
  Map<String, dynamic>? get singleData => _singleData;

  List<dynamic>? _products;
  List<dynamic>? get products => _products;

  String? _error;
  String? get error => _error;

  Future create(Map<String, String> data, String userId) async {
    _products = null;
    _error = null;

    final response = await http.post(Uri.parse("$url/api/product"),
        headers: getHeaders(userId), body: json.encode(data));

    Map<String, dynamic> responseBody =
        response.contentLength! > 0 ? json.decode(response.body) : {};

    if (response.statusCode >= 400) {
      _error = responseBody["error"];
      return;
    }

    _singleData = responseBody;
  }

  Future fetchData(String userId) async {
    _error = null;
    final response = await http.get(Uri.parse("$url/api/product"));
    dynamic responseBody =
        response.contentLength! > 0 ? json.decode(response.body) : {};

    if (response.statusCode >= 400) {
      _error = responseBody["error"];
      return;
    }

    _products = responseBody;
  }

  Map<String, String>? getHeaders(String userId) {
    return {"X-User-Id": userId, "Content-Type": "application/json"};
  }

  Future fetchDataByID(String productId, String userId) async {
    _error = null;
    _singleData = null;
    final response = await http.get(Uri.parse("$url/api/product/$productId"));
    dynamic responseBody =
        response.contentLength! > 0 ? json.decode(response.body) : {};

    if (response.statusCode >= 400) {
      _error = responseBody["error"];
      return;
    }

    _singleData = responseBody;
  }
}
