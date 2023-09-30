import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../bloc/auth_bloc.dart';

class EmailInput extends StatelessWidget {
  const EmailInput({super.key});
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<AuthFormCubit, AuthFormState>(
      buildWhen: (previous, current) => previous.email != current.email,
      builder: (context, state) {
        return TextField(
          onChanged: (email) =>
              context.read<AuthFormCubit>().emailChanged(email),
          keyboardType: TextInputType.emailAddress,
          decoration: InputDecoration(
              labelText: 'Email',
              helperText: '',
              errorText: state.authFilter == AuthFilter.login
                  ? null
                  : state.email.displayError(state.email.value)),
        );
      },
    );
  }
}
