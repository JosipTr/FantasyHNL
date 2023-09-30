class ConfirmPassword {
  final String value;
  const ConfirmPassword.pure({this.value = ''});

  const ConfirmPassword.dirty(this.value);

  static final _passwordRegExp =
      RegExp(r'^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$');

  bool validate(String? value) {
    return _passwordRegExp.hasMatch(value ?? '') ? true : false;
  }

  String? displayError(String value) {
    if (value.isEmpty) {
      return null;
    }
    if (this.value != value) {
      return 'Passwords do not match';
    }
    return null;
  }
}
