package com.fepeprog.test.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fepeprog.test.R;
import com.fepeprog.test.Validator;
import com.fepeprog.test.database.DBHandler;

/**
 * Created by fepeprog on 3/7/18.
 */

public class SignInFragment extends Fragment {
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignIn;
    private TextView textViewCreateProfile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signin_layout, container, false);
        textInputLayoutEmail = (TextInputLayout) view.findViewById(R.id.text_input_layout_email);
        textInputLayoutPassword = (TextInputLayout) view.findViewById(R.id.text_input_layout_password);
        editTextEmail = (EditText) view.findViewById(R.id.edit_text_email);
        editTextPassword = (EditText) view.findViewById(R.id.edit_text_password);
        buttonSignIn = (Button) view.findViewById(R.id.sign_in);
        textViewCreateProfile = (TextView) view.findViewById(R.id.sign_in_textView);

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Validator.validateEmail(s.toString()) && !s.toString().isEmpty()) {
                    textInputLayoutEmail.setError("Email isn't valide! Try again!");
                } else
                    textInputLayoutEmail.setError(null);
            }
        });
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Validator.validatePassword(s.toString()) && !s.toString().isEmpty()) {
                    textInputLayoutPassword.setError("Password is not valide! It must contain uppercase & small case" +
                            "letters and number");
                } else
                    textInputLayoutPassword.setError(null);
            }
        });

        textViewCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.sign_frame_container, new SignUpFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                if (DBHandler.signingIn(email, password)) {
                    ProfileFragment profileFragment = new ProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);
                    profileFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.sign_frame_container, profileFragment)
                            .addToBackStack(null)
                            .commit();
                }
                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(getActivity(), "Fields is empty!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "User not exists!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


}
