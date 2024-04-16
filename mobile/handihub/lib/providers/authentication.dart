import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:handihub/static/url.dart';
import 'package:http/http.dart' as http;
import 'package:http/http.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AuthenticationProvider extends ChangeNotifier {
  String? _token;
  String? _error;
  bool? _isSuccess;
  String? _id;
  Map<String, dynamic>? _body;

  String? get id => _id;
  String? get error => _error;
  String? get token => _token;
  bool? get isSuccess => _isSuccess;
  Map<String, dynamic>? get userInfoBody => _body;

  Future<void> sign(String username, String password) async {
    _error = null;
    _id = null;
    final response = await http
        .get(Uri.parse("$url/api/user?username=$username"));

    Map<String, dynamic> responseBody = json.decode(response.body);

    if (response.statusCode >= 400) {
      _error = responseBody["error"];
      return;
    }

    _id = responseBody["id"];
    getInfoById(_id!);
  }

  Future register(Map<String, String> data) async {
    _error = null;
    _token = null;
    _isSuccess = false;
    final response = await http.post(Uri.parse("$url/api/user"),
        body: json.encode(data), headers: {"Content-Type": "application/json"});

    Map<String, dynamic> responseBody =
        response.contentLength! > 0 ? json.decode(response.body) : {};
    if (response.statusCode > 400) {
      _error = responseBody["error"];
      return;
    }

    if (response.statusCode == 201) {
      _isSuccess = true;
      _id = responseBody["userId"];
      final prefs = await SharedPreferences.getInstance();
      prefs.setString("id", _id!);
    }
  }

  Future getInfoById(String id) async {
    _error = null;

    final response = await http.get(Uri.parse("$url/api/user/info/$id"));

    fetchUserInfo(response);
  }

  Future fetchUserInfo(Response response) async {
    Map<String, dynamic>? responseBody =
        response.contentLength! > 0 ? json.decode(response.body) : {};


    if (response.statusCode >= 400) {
      _error = responseBody!["error"];
      return;
    }

    

    _body = responseBody!;
    final prefs = await SharedPreferences.getInstance();

    if (_body != null) {
      String role = "customer";
      if (_body!["role"] == "ROLE_SELLER") {
        role = "seller";
      }

      prefs.setString("type", role);
      return;
    }

    _error = "Something went wrong";
  }

  Future resetPassword(String email) async {
    _isSuccess = false;
    _error = null;
    final response =
        await http.post(Uri.parse("$url/api/user/reset-password?email=$email"));

    Map<String, dynamic> responseBody =
        response.contentLength! > 0 ? json.decode(response.body) : {};

    if (response.statusCode > 400) {
      _error = responseBody["error"];
      return;
    }

    if (response.statusCode == 200) {
      _isSuccess = true;
    }
  }

  Future verify(String userId) async {
    _error = null;
    _isSuccess = false;
    final response =
        await http.post(Uri.parse("$url/api/user/verify-email/$userId"));

    Map<String, dynamic> responseBody =
        response.contentLength! > 0 ? json.decode(response.body) : {};

    if (response.statusCode >= 400) {
      _error = responseBody["error"] ?? "Something went wrong";
      return;
    }

    if (response.statusCode == 200) {
      _isSuccess = true;
      return;
    }
  }
}
