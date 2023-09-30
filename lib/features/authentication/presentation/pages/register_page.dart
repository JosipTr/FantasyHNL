import 'package:fantasy_hnl/features/authentication/di/auth_di.dart';
import 'package:fantasy_hnl/features/authentication/presentation/bloc/register_cubit/register_cubit.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../widgets/forms/register_form.dart';

class RegisterPage extends StatelessWidget {
  static const String route = "/register_page";

  const RegisterPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Login')),
      body: Padding(
        padding: const EdgeInsets.all(8),
        child: BlocProvider<RegisterCubit>(
          create: (_) => authInjector(),
          child: const RegisterForm(),
        ),
      ),
    );
  }
}
