import 'package:dartz/dartz.dart';
import 'package:fantasy_hnl/core/errors/failure.dart';
import 'package:fantasy_hnl/core/usecases/usecase.dart';
import 'package:fantasy_hnl/features/authentication/domain/repositories/auth_repository.dart';
import 'package:fantasy_hnl/features/authentication/util/parameters/auth_params.dart';

import '../../../../core/errors/success.dart';

class Register implements UseCase<Success, AuthParams> {
  final AuthRepository _repository;

  const Register(this._repository);
  @override
  Future<Either<Failure, Success>> call(AuthParams params) {
    return _repository.register(params);
  }
}
