part of 'auth_bloc.dart';

sealed class AuthEvent {
  const AuthEvent();
}

final class AuthStarted extends AuthEvent {
  const AuthStarted();
}

final class AuthStateChanged extends AuthEvent {
  const AuthStateChanged();
}

final class AuthLogoutRequired extends AuthEvent {
  const AuthLogoutRequired();
}
