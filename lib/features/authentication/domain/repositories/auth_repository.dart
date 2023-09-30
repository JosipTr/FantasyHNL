import 'package:dartz/dartz.dart';

import '../../../../core/errors/failure.dart';
import '../../../../core/errors/success.dart';
import '../../util/parameters/auth_params.dart';
import '../entities/user.dart';

abstract interface class AuthRepository {
  Stream<User?> authStateChanges();
  Future<Either<Failure, Success>> loginWithEmailAndPassword(AuthParams params);
  Future<Either<Failure, Success>> register(AuthParams params);

  Future<Either<Failure, Success>> logout();
}
