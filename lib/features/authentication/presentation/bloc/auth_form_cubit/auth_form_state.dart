part of 'auth_form_cubit.dart';

enum Status {
  initial,
  inProgress,
  failure,
  success,
}

enum AuthFilter {
  login,
  register,
}

final class AuthFormState extends Equatable {
  final Email email;
  final Password password;
  final ConfirmPassword? confirmPassword;
  final bool isValid;
  final Status status;
  final String? errorMessage;
  final AuthFilter authFilter;

  const AuthFormState({
    this.email = const Email.pure(),
    this.password = const Password.pure(),
    this.confirmPassword = const ConfirmPassword.pure(),
    this.isValid = false,
    this.status = Status.initial,
    this.errorMessage = '',
    this.authFilter = AuthFilter.login,
  });

  @override
  List<Object?> get props => [
        email,
        password,
        confirmPassword,
        isValid,
        status,
        errorMessage,
        authFilter
      ];

  AuthFormState copyWith({
    Email? email,
    Password? password,
    ConfirmPassword? confirmPassword,
    bool? isValid,
    Status? status,
    String? errorMessage,
    AuthFilter? authFilter,
  }) {
    return AuthFormState(
      email: email ?? this.email,
      password: password ?? this.password,
      confirmPassword: confirmPassword ?? this.confirmPassword,
      isValid: isValid ?? this.isValid,
      status: status ?? this.status,
      errorMessage: errorMessage ?? this.errorMessage,
      authFilter: authFilter ?? this.authFilter,
    );
  }
}
