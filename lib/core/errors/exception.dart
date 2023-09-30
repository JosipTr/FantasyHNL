class LoginException implements Exception {
  final String message;
  const LoginException({this.message = "Invalid email or password"});
}
