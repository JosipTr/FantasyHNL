abstract class Failure {
  final String message;
  const Failure(this.message);
}

class AuthFailure extends Failure {
  AuthFailure(super.message);
}
