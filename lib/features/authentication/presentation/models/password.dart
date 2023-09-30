class Password {
  final String value;
  const Password.pure({this.value = ''});

  const Password.dirty(this.value);

  static final _passwordRegExp =
      RegExp(r'^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$');

  bool validate(String? value) {
    return _passwordRegExp.hasMatch(value ?? '') ? true : false;
  }

  String? displayError(String value) {
    if (value.isEmpty) {
      return null;
    }
    if (!validate(value)) {
      return 'Invalid password';
    }
    return null;
  }
}
