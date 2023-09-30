part of 'register_cubit.dart';

final class RegisterState extends Equatable {
  final Email email;
  final Password password;
  final ConfirmPassword confirmPassword;
  final bool isValid;
  final Status status;
  final String errorMessage;

  const RegisterState(
      {this.email = const Email.pure(),
      this.password = const Password.pure(),
      this.confirmPassword = const ConfirmPassword.pure(),
      this.isValid = false,
      this.status = Status.initial,
      this.errorMessage = ''});

  @override
  List<Object> get props =>
      [email, password, confirmPassword, isValid, status, errorMessage];

  RegisterState copyWith(
      {Email? email,
      Password? password,
      ConfirmPassword? confirmPassword,
      bool? isValid,
      Status? status,
      String? errorMessage}) {
    return RegisterState(
        email: email ?? this.email,
        password: password ?? this.password,
        confirmPassword: confirmPassword ?? this.confirmPassword,
        isValid: isValid ?? this.isValid,
        status: status ?? this.status,
        errorMessage: errorMessage ?? this.errorMessage);
  }
}
