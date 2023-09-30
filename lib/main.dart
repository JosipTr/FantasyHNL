// ignore_for_file: curly_braces_in_flow_control_structures

import 'package:fantasy_hnl/features/authentication/presentation/bloc/auth_bloc/auth_bloc.dart';
import 'package:fantasy_hnl/features/authentication/presentation/pages/auth_page.dart';
import 'package:fantasy_hnl/firebase_options.dart';
import 'package:fantasy_hnl/features/home/presentation/pages/home_page.dart';
import 'package:fantasy_hnl/splash_screen.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'features/authentication/di/auth_di.dart';
import 'features/authentication/presentation/bloc/auth_form_cubit/auth_form_cubit.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
  await initializeAuthDependencies();
  runApp(const FantasyApp());
}

class FantasyApp extends StatelessWidget {
  const FantasyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocProvider<AuthBloc>(
      create: (context) => authInjector()..add(const AuthStarted()),
      child: const FantasyAppPage(),
    );
  }
}

class FantasyAppPage extends StatelessWidget {
  const FantasyAppPage({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: BlocBuilder<AuthBloc, AuthState>(
        builder: (context, state) {
          if (state is AuthSuccess) return const HomePage();

          if (state is AuthFailure)
            return BlocProvider<AuthFormCubit>(
              create: (_) => authInjector(),
              child: const AuthPage(),
            );

          return const SplashScreen();
        },
      ),
    );
  }
}
