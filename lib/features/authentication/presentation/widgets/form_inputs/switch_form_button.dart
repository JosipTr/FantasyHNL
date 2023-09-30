import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../bloc/auth_bloc.dart';

class SwitchFormButton extends StatelessWidget {
  final AuthFilter authFilter;
  const SwitchFormButton({super.key, required this.authFilter});

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    return TextButton(
      key: const Key('loginForm_createAccount_flatButton'),
      onPressed: () => context.read<AuthFormCubit>().switchForm(authFilter),
      child: Text(
        authFilter == AuthFilter.login
            ? 'Already have an account?'
            : 'Create an account',
        style: TextStyle(color: theme.primaryColor),
      ),
    );
  }
}
