import '../entities/user.dart';
import '../repositories/auth_repository.dart';

class AuthStateChanges {
  final AuthRepository _repository;

  const AuthStateChanges(this._repository);

  Stream<User?> call() {
    return _repository.authStateChanges();
  }
}
