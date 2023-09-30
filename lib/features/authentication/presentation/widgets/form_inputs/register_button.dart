import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../bloc/auth_bloc.dart';

class RegisterButton extends StatelessWidget {
  const RegisterButton({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<AuthFormCubit, AuthFormState>(
      builder: (context, state) {
        return state.status == Status.inProgress
            ? const CircularProgressIndicator()
            : ElevatedButton(
                style: ElevatedButton.styleFrom(
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(30),
                  ),
                  backgroundColor: const Color(0xFFFFD600),
                ),
                onPressed: state.isValid &&
                        state.email.value.isNotEmpty &&
                        state.password.value.isNotEmpty &&
                        state.confirmPassword!.value.isNotEmpty
                    ? () => context.read<AuthFormCubit>().register()
                    : null,
                child: const Text('Register'),
              );
      },
    );
  }
}
