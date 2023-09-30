import 'package:dartz/dartz.dart';
import 'package:fantasy_hnl/core/errors/exception.dart';
import 'package:fantasy_hnl/core/errors/failure.dart';
import 'package:fantasy_hnl/core/errors/success.dart';
import 'package:fantasy_hnl/features/authentication/data/datasources/remote_datasource.dart';
import 'package:fantasy_hnl/features/authentication/domain/entities/user.dart';
import 'package:fantasy_hnl/features/authentication/domain/repositories/auth_repository.dart';
import 'package:fantasy_hnl/features/authentication/util/parameters/auth_params.dart';

class AuthRepositoryImpl implements AuthRepository {
  final RemoteDataSource _remoteDataSource;

  const AuthRepositoryImpl(this._remoteDataSource);

  @override
  Stream<User?> authStateChanges() {
    return _remoteDataSource.authStateChanges();
  }

  @override
  Future<Either<Failure, Success>> loginWithEmailAndPassword(
      AuthParams params) async {
    try {
      await _remoteDataSource.loginWithEmailAndPassword(params);
      return Right(LoginSuccess('Login successful'));
    } on LoginException catch (error) {
      return Left(AuthFailure(error.message));
    }
  }

  @override
  Future<Either<Failure, Success>> register(AuthParams params) async {
    try {
      await _remoteDataSource.register(params);
      return Right(LoginSuccess('Registration successful'));
    } on LoginException catch (error) {
      return Left(AuthFailure(error.message));
    }
  }

  @override
  Future<Either<Failure, Success>> logout() async {
    try {
      await _remoteDataSource.logout();
      return Right(LoginSuccess('Logout successful'));
    } on LoginException catch (error) {
      return Left(AuthFailure(error.message));
    }
  }
}
