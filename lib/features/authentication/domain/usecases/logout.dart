import 'package:dartz/dartz.dart';
import 'package:fantasy_hnl/core/errors/failure.dart';
import 'package:fantasy_hnl/core/usecases/usecase.dart';
import 'package:fantasy_hnl/features/authentication/domain/repositories/auth_repository.dart';

import '../../../../core/errors/success.dart';

class Logout implements UseCase<Success, NoParams> {
  final AuthRepository _repository;

  const Logout(this._repository);
  @override
  Future<Either<Failure, Success>> call(NoParams params) async {
    return await _repository.logout();
  }
}
