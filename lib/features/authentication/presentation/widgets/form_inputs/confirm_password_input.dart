import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../bloc/auth_bloc.dart';

class ConfirmPasswordInput extends StatelessWidget {
  const ConfirmPasswordInput({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<AuthFormCubit, AuthFormState>(
      buildWhen: (previous, current) =>
          previous.confirmPassword != current.confirmPassword,
      builder: (context, state) {
        return TextField(
          onChanged: (password) =>
              context.read<AuthFormCubit>().confirmPasswordChanged(password),
          obscureText: true,
          decoration: InputDecoration(
            labelText: 'Confirm password:',
            helperText: '',
            errorText:
                state.confirmPassword!.displayError(state.password.value),
          ),
        );
      },
    );
  }
}
