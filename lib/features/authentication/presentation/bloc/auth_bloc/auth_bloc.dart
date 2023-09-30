import 'dart:developer';

import 'package:equatable/equatable.dart';
import 'package:bloc/bloc.dart';
import 'package:fantasy_hnl/core/usecases/usecase.dart';

import '../../../domain/usecases/auth_state_changes.dart';
import '../../../domain/usecases/logout.dart';

part 'auth_event.dart';
part 'auth_state.dart';

class AuthBloc extends Bloc<AuthEvent, AuthState> {
  final AuthStateChanges _authStateChanges;
  final Logout _logout;

  AuthBloc(this._authStateChanges, this._logout) : super(const AuthInitial()) {
    on<AuthStarted>(_onStarted);
    on<AuthStateChanged>(_onStateChanged);
    on<AuthLogoutRequired>(_onLogoutRequired);
  }

  Future<void> _onStarted(AuthStarted event, Emitter<AuthState> emit) async {
    emit(const AuthInitial());
    final stream = _authStateChanges();
    await emit.forEach(stream,
        onData: (user) {
          log(user.toString());
          if (user == null) {
            return const AuthFailure();
          } else {
            return const AuthSuccess();
          }
        },
        onError: (_, __) => const AuthFailure());
  }

  Future<void> _onStateChanged(
      AuthStateChanged event, Emitter<AuthState> emit) async {
    final stream = _authStateChanges();
    await emit.forEach(stream,
        onData: (user) {
          if (user == null) {
            return const AuthFailure();
          } else {
            return const AuthSuccess();
          }
        },
        onError: (_, __) => const AuthFailure());
  }

  Future<void> _onLogoutRequired(
      AuthLogoutRequired event, Emitter<AuthState> emit) async {
    await _logout(const NoParams());
  }
}
