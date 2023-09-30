import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../bloc/auth_bloc.dart';

class PasswordInput extends StatelessWidget {
  const PasswordInput({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<AuthFormCubit, AuthFormState>(
      buildWhen: (previous, current) => previous.password != current.password,
      builder: (context, state) {
        return TextField(
          onChanged: (password) =>
              context.read<AuthFormCubit>().passwordChanged(password),
          obscureText: true,
          decoration: InputDecoration(
            labelText: 'Password',
            helperText: state.authFilter == AuthFilter.login
                ? ''
                : 'Enter 8 characters (number and letter)',
            errorText: state.authFilter == AuthFilter.login
                ? null
                : state.password.displayError(state.password.value),
          ),
        );
      },
    );
  }
}
