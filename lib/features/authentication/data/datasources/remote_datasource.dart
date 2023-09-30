import 'dart:developer';

import 'package:fantasy_hnl/core/errors/exception.dart';
import 'package:firebase_auth/firebase_auth.dart' hide User;

import '../../util/parameters/auth_params.dart';
import '../models/user_model.dart';

abstract interface class RemoteDataSource {
  Stream<UserModel?> authStateChanges();
  Future<void> loginWithEmailAndPassword(AuthParams params);

  Future<void> register(AuthParams params);
  Future<void> logout();
}

class FirebaseAuthentication implements RemoteDataSource {
  final FirebaseAuth _firebaseAuth;

  FirebaseAuthentication(this._firebaseAuth);

  @override
  Stream<UserModel?> authStateChanges() {
    return _firebaseAuth.authStateChanges().map((user) {
      if (user == null) {
        return null;
      }
      return UserModel(username: user.displayName!, email: user.email!);
    });
  }

  @override
  Future<void> loginWithEmailAndPassword(AuthParams params) async {
    try {
      await _firebaseAuth.signInWithEmailAndPassword(
          email: params.email, password: params.password);
    } catch (error) {
      log(error.toString());
      throw const LoginException();
    }
  }

  @override
  Future<void> register(AuthParams params) async {
    try {
      await _firebaseAuth.createUserWithEmailAndPassword(
          email: params.email, password: params.password);
    } catch (error) {
      log(error.toString());
      throw const LoginException();
    }
  }

  @override
  Future<void> logout() async {
    try {
      await _firebaseAuth.signOut();
    } catch (error) {
      log(error.toString());
      throw const LoginException();
    }
  }
}
