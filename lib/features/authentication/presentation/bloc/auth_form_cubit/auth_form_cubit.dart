import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:fantasy_hnl/features/authentication/domain/usecases/login_email_password.dart';
import 'package:fantasy_hnl/features/authentication/domain/usecases/register.dart';
import 'package:fantasy_hnl/features/authentication/util/parameters/auth_params.dart';

import '../../../util/enums/enum.dart';
import '../../models/confirm_password.dart';
import '../../models/email.dart';
import '../../models/password.dart';

part 'auth_form_state.dart';

class AuthFormCubit extends Cubit<AuthFormState> {
  final LoginWithEmailAndPassword _loginWithEmailAndPassword;
  final Register _register;

  AuthFormCubit(this._loginWithEmailAndPassword, this._register)
      : super(const AuthFormState());

  void emailChanged(String value) {
    final email = Email.dirty(value);
    emit(state.copyWith(email: email, isValid: email.validate(value)));
  }

  void passwordChanged(String value) {
    final password = Password.dirty(value);
    emit(state.copyWith(password: password, isValid: password.validate(value)));
  }

  void confirmPasswordChanged(String value) {
    final password = ConfirmPassword.dirty(value);
    emit(state.copyWith(
        confirmPassword: password, isValid: password.validate(value)));
  }

  Future<void> loginWithCredentials() async {
    if (!state.isValid) return;
    emit(state.copyWith(status: Status.inProgress));
    final either = await _loginWithEmailAndPassword(
      AuthParams(email: state.email.value, password: state.password.value),
    );
    either.fold(
      (failure) => emit(
        state.copyWith(errorMessage: failure.message, status: Status.failure),
      ),
      (success) => state.copyWith(status: Status.success),
    );
  }

  Future<void> register() async {
    if (!state.isValid) return;
    emit(state.copyWith(status: Status.inProgress));
    final either = await _register(
      AuthParams(email: state.email.value, password: state.password.value),
    );
    either.fold(
      (failure) => emit(
        state.copyWith(errorMessage: failure.message, status: Status.failure),
      ),
      (success) => state.copyWith(status: Status.success),
    );
  }
}
