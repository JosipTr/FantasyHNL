import 'package:fantasy_hnl/features/authentication/domain/usecases/auth_state_changes.dart';
import 'package:fantasy_hnl/features/authentication/domain/usecases/login_email_password.dart';
import 'package:fantasy_hnl/features/authentication/domain/usecases/logout.dart';
import 'package:fantasy_hnl/features/authentication/domain/usecases/register.dart';
import 'package:fantasy_hnl/features/authentication/presentation/bloc/auth_form_cubit/auth_form_cubit.dart';

import 'package:firebase_auth/firebase_auth.dart';
import 'package:get_it/get_it.dart';
import '../data/datasources/remote_datasource.dart';
import '../data/repositories/auth_repository_impl.dart';
import '../domain/repositories/auth_repository.dart';
import '../presentation/bloc/auth_bloc/auth_bloc.dart';

final authInjector = GetIt.instance;

Future<void> initializeAuthDependencies() async {
  authInjector.registerLazySingleton(() => FirebaseAuth.instance);

  //ConnectivityPlus
  // authInjector.registerLazySingleton(() => Connectivity());

  //Repositories
  authInjector.registerLazySingleton<AuthRepository>(
      () => AuthRepositoryImpl(authInjector()));

  //Datasources
  authInjector.registerLazySingleton<RemoteDataSource>(
      () => FirebaseAuthentication(authInjector()));

  //UseCases
  authInjector.registerLazySingleton(() => Logout(authInjector()));
  authInjector.registerLazySingleton(() => AuthStateChanges(authInjector()));
  authInjector.registerLazySingleton(() => Register(authInjector()));
  authInjector
      .registerLazySingleton(() => LoginWithEmailAndPassword(authInjector()));

  //Bloc
  authInjector.registerFactory(() => AuthBloc(authInjector(), authInjector()));

  // //Cubit
  authInjector
      .registerFactory(() => AuthFormCubit(authInjector(), authInjector()));
  //Core
  // authInjector.registerLazySingleton<NetworkInfo>(
  //     () => NetworkInfoImpl(authInjector()));
}
