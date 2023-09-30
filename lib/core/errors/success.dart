abstract class Success {
  final String message;
  const Success(this.message);
}

class LoginSuccess extends Success {
  LoginSuccess(super.message);
}
