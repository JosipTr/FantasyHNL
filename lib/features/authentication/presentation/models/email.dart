class Email {
  final String value;

  const Email.pure({this.value = ''});

  /// {@macro email}
  const Email.dirty(this.value);

  static final RegExp _emailRegExp = RegExp(
    r'^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$',
  );

  bool validate(String? value) {
    return _emailRegExp.hasMatch(value ?? '') ? true : false;
  }

  String? displayError(String value) {
    if (value.isEmpty) {
      return null;
    }
    if (!validate(value)) {
      return 'Invalid email';
    }
    return null;
  }
}
