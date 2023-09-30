import 'package:fantasy_hnl/features/authentication/di/auth_di.dart';
import 'package:fantasy_hnl/features/authentication/presentation/bloc/auth_form_cubit/auth_form_cubit.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../widgets/forms/login_form.dart';

class AuthPage extends StatelessWidget {
  static const String route = "/login_page";
  const AuthPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Login')),
      body: Padding(
        padding: const EdgeInsets.all(8),
        child: BlocProvider<AuthFormCubit>(
          create: (_) => authInjector(),
          child: const LoginForm(),
        ),
      ),
    );
  }
}
