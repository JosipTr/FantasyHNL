import 'package:fantasy_hnl/features/authentication/di/auth_di.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../bloc/login_cubit/login_cubit.dart';
import '../widgets/forms/login_form.dart';

class LoginPage extends StatelessWidget {
  static const String route = "/login_page";
  const LoginPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Login')),
      body: Padding(
        padding: const EdgeInsets.all(8),
        child: BlocProvider<LoginCubit>(
          create: (_) => authInjector(),
          child: const LoginForm(),
        ),
      ),
    );
  }
}
