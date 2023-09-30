import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../bloc/auth_form_cubit/auth_form_cubit.dart';

class RegisterForm extends StatelessWidget {
  const RegisterForm({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocListener<AuthFormCubit, AuthFormState>(
      listener: (context, state) {
        if (state.status == Status.failure) {
          ScaffoldMessenger.of(context)
            ..hideCurrentSnackBar()
            ..showSnackBar(
              SnackBar(
                content: Text(state.errorMessage ?? 'Authentication Failed'),
              ),
            );
        }
      },
      child: Align(
        alignment: const Alignment(0, -1 / 3),
        child: SingleChildScrollView(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              // Image.asset(
              //   'assets/bloc_logo_small.png',
              //   height: 120,
              // ),
              const SizedBox(height: 16),
              _EmailInput(),
              const SizedBox(height: 8),
              _PasswordInput(),
              const SizedBox(height: 8),
              _ConfirmPasswordInput(),
              const SizedBox(height: 8),
              _RegisterButton(),
              const SizedBox(height: 8),
              // _GoogleLoginButton(),
              const SizedBox(height: 4),
              _LoginButton(),
            ],
          ),
        ),
      ),
    );
  }
}

class _EmailInput extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<AuthFormCubit, AuthFormState>(
      buildWhen: (previous, current) => previous.email != current.email,
      builder: (context, state) {
        return TextField(
          key: const Key('registerForm_emailInput_textField'),
          onChanged: (email) =>
              context.read<AuthFormCubit>().emailChanged(email),
          keyboardType: TextInputType.emailAddress,
          decoration: InputDecoration(
              labelText: 'email',
              helperText: '',
              // errorText: state.isValid != true ? 'invalid email' : null,
              errorText: state.email.displayError(state.email.value)),
        );
      },
    );
  }
}

class _PasswordInput extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<AuthFormCubit, AuthFormState>(
      buildWhen: (previous, current) => previous.password != current.password,
      builder: (context, state) {
        return TextField(
          key: const Key('registerForm_passwordInput_textField'),
          onChanged: (password) =>
              context.read<AuthFormCubit>().passwordChanged(password),
          obscureText: true,
          decoration: InputDecoration(
            labelText: 'Password',
            helperText: 'Enter 8 characters',
            errorText: state.password.displayError(state.password.value),
          ),
        );
      },
    );
  }
}

class _ConfirmPasswordInput extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<AuthFormCubit, AuthFormState>(
      buildWhen: (previous, current) =>
          previous.confirmPassword != current.confirmPassword,
      builder: (context, state) {
        return TextField(
          key: const Key('registerForm_confirmPassword_textField'),
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

class _RegisterButton extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<AuthFormCubit, AuthFormState>(
      builder: (context, state) {
        return state.status == Status.inProgress
            ? const CircularProgressIndicator()
            : ElevatedButton(
                key: const Key('registerForm_continue_raisedButton'),
                style: ElevatedButton.styleFrom(
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(30),
                  ),
                  backgroundColor: const Color(0xFFFFD600),
                ),
                onPressed: state.isValid
                    ? () => context.read<AuthFormCubit>().register()
                    : null,
                child: const Text('REGISTER'),
              );
      },
    );
  }
}

class _LoginButton extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    return TextButton(
      key: const Key('registerForm_createAccount_flatButton'),
      onPressed: () =>
          context.read<AuthFormCubit>().switchForm(AuthFilter.login),
      child: Text(
        'CREATE ACCOUNT',
        style: TextStyle(color: theme.primaryColor),
      ),
    );
  }
}
