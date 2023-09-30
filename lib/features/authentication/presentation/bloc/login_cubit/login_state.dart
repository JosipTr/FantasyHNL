part of 'login_cubit.dart';

final class LoginState extends Equatable {
  final Email email;
  final Password password;
  final bool isValid;
  final Status status;
  final String errorMessage;

  const LoginState(
      {this.email = const Email.pure(),
      this.password = const Password.pure(),
      this.isValid = false,
      this.status = Status.initial,
      this.errorMessage = ''});

  @override
  List<Object> get props => [email, password, isValid, status, errorMessage];

  LoginState copyWith(
      {Email? email,
      Password? password,
      bool? isValid,
      Status? status,
      String? errorMessage}) {
    return LoginState(
        email: email ?? this.email,
        password: password ?? this.password,
        isValid: isValid ?? this.isValid,
        status: status ?? this.status,
        errorMessage: errorMessage ?? this.errorMessage);
  }
}
