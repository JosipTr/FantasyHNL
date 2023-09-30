import 'package:fantasy_hnl/features/authentication/di/auth_di.dart';
import 'package:fantasy_hnl/features/authentication/presentation/bloc/auth_form_cubit/auth_form_cubit.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../widgets/auth_widget.dart';

class AuthPage extends StatelessWidget {
  const AuthPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Login')),
      body: Padding(
        padding: const EdgeInsets.all(8),
        child: BlocProvider<AuthFormCubit>(
          create: (_) => authInjector(),
          child: BlocBuilder<AuthFormCubit, AuthFormState>(
            builder: (context, state) {
              return AnimatedSwitcher(
                transitionBuilder: (child, animation) {
                  return FadeTransition(
                    opacity: CurvedAnimation(
                        parent: animation, curve: Curves.easeIn),
                    child: child,
                  );
                },
                duration: const Duration(milliseconds: 550),
                child: state.authFilter == AuthFilter.login
                    ? const LoginForm()
                    : const RegisterForm(),
              );
            },
          ),
        ),
      ),
    );
  }
}
