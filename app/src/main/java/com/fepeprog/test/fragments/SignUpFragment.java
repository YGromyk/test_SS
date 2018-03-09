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
import com.fepeprog.test.database.User;

/**
 * Created by fepeprog on 3/8/18.
 */

public class SignUpFragment extends Fragment {
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private Button buttonSignUp;
    private TextView textViewSignIn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_layout, container, false);
        textInputLayoutEmail = (TextInputLayout) view.findViewById(R.id.text_input_layout_email);
        textInputLayoutPassword = (TextInputLayout) view.findViewById(R.id.text_input_layout_password);
        textInputLayoutName = (TextInputLayout) view.findViewById(R.id.text_input_layout_name);

        editTextEmail = (EditText) view.findViewById(R.id.edit_text_email);
        editTextPassword = (EditText) view.findViewById(R.id.edit_text_password);
        editTextName = (EditText) view.findViewById(R.id.edit_text_name);

        buttonSignUp = (Button) view.findViewById(R.id.sign_up);
        textViewSignIn = (TextView) view.findViewById(R.id.sign_in_textView);

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
                    buttonSignUp.setEnabled(false);
                } else {
                    textInputLayoutEmail.setError(null);
                    buttonSignUp.setEnabled(true);
                }
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
                    buttonSignUp.setEnabled(false);

                } else {
                    textInputLayoutPassword.setError(null);
                    buttonSignUp.setEnabled(true);
                }
            }
        });

        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!(Validator.validateNameLat(s.toString()) || Validator.validateNameCyr(s.toString())) && !s.toString().isEmpty()) {
                    textInputLayoutName.setError("Name isn't valide!");
                    buttonSignUp.setEnabled(false);
                } else {
                    textInputLayoutName.setError(null);
                    buttonSignUp.setEnabled(true);
                }
            }
        });

        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.sign_frame_container, new SignInFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFields()) {
                    String password = editTextPassword.getText().toString();
                    String email = editTextEmail.getText().toString();
                    String name = editTextName.getText().toString();
                    User user = new User(email, name, password);
                    if (!DBHandler.userExistsEmail(email)) {
                        DBHandler.createUser(user);
                        Toast.makeText(getActivity(), "User created!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "User with this email exists!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Fields is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private boolean checkFields(){
        String password = editTextPassword.getText().toString();
        String email= editTextEmail.getText().toString();
        String name = editTextName.getText().toString();

        boolean checkPassword = !password.isEmpty() && Validator.validatePassword(password);
        boolean checkEmail = !email.isEmpty() && Validator.validateEmail(email);
        boolean checkName = !name.isEmpty() && (!Validator.validateNameLat(name)
                                                || !Validator.validateNameCyr(name));

        return checkPassword && checkEmail && checkName;
    }
}
