String url = "http://64.23.155.56:8090";

String tokenUrl = "http://10.0.2.2:9080/realms/dev/protocol/openid-connect/token";

Map<String, String> body(username, password) => {
  "username": username,
  "password": password,
  "client_id": "microservice",
  "grant_type": "password",
  "client_secret": "microservice",
  "scope": "openid"
};