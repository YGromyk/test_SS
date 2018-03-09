package com.fepeprog.test.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fepeprog.test.R;
import com.fepeprog.test.SignActivity;
import com.fepeprog.test.Validator;
import com.fepeprog.test.database.DBHandler;
import com.fepeprog.test.database.User;

/**
 * Created by fepeprog on 3/8/18.
 */

public class ProfileFragment extends Fragment {
    private TextView emailEditText;
    private EditText passwordEditText;
    private EditText ageEditText;
    private EditText phoneEditText;
    private EditText nameEditText;
    private TextView editName;
    private Button saveState;
    private TextInputLayout phoneTextInputLayout;

    private User currentUser;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout, container, false);
        final String email = getArguments().getString("email");
        currentUser = DBHandler.getUser(email);
        emailEditText = (TextView) view.findViewById(R.id.email_profile);
        passwordEditText = (EditText) view.findViewById(R.id.password_profile);
        ageEditText = (EditText) view.findViewById(R.id.age);
        phoneEditText = (EditText) view.findViewById(R.id.phone_number);
        nameEditText = (EditText) view.findViewById(R.id.name_profile);
        editName = (TextView) view.findViewById(R.id.edit_name);
        saveState = (Button) view.findViewById(R.id.save);

        phoneTextInputLayout = (TextInputLayout) view.findViewById(R.id.phone_text_input_layout);

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableFields(true);
                Toast.makeText(getActivity(), "You can edit information", Toast.LENGTH_SHORT).show();
            }
        });

        // set values
        nameEditText.setText(currentUser.getName());
        emailEditText.setText(currentUser.getEmail());
        passwordEditText.setText(currentUser.getPassword());
        ageEditText.setText("age: " + currentUser.getAge() + " years");
        phoneEditText.setText("phone: " + currentUser.getNumber());


        //check edit texts
        setAgeEditTextValidate();
        setNameEditTextValidate();
        setPasswordEditTextValidate();
        setPhoneEditTextValidate();


        saveState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Information!")
                        .setMessage("Save changed?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean flagChanged = false;
                                if (isPhoneValide()) {
                                    String number = phoneEditText.getText().toString();
                                    String[] parts = number.split(":");
                                    if (parts.length > 1)
                                        currentUser.setNumber(parts[1]);
                                    else
                                        currentUser.setNumber(number);
                                    flagChanged = true;
                                }
                                String name = nameEditText.getText().toString();
                                if (Validator.validateNameCyr(name) || Validator.validateNameLat(name)) {
                                    currentUser.setName(name);
                                    flagChanged = true;
                                }
                                if (Validator.validatePassword(passwordEditText.getText().toString())) {
                                    currentUser.setPassword(passwordEditText.getText().toString());
                                    flagChanged = true;
                                }
                                int age = 0;
                                try {
                                    age = Integer.valueOf(ageEditText.getText().toString());
                                } catch (NumberFormatException e) {
                                    Log.w(SignActivity.TAG, "error cast!");
                                }
                                if (age > 12 && age < 100) {
                                    currentUser.setAge(age);
                                    flagChanged = true;
                                }
                                if (flagChanged) {
                                    DBHandler.updateUser(currentUser);
                                    enableFields(false);
                                    Toast.makeText(getActivity(), "Changed saved!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        return view;
    }


    public void setPasswordEditTextValidate() {
        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String password = passwordEditText.getText().toString();
                if (hasFocus) {
                    passwordEditText.setText(password);
                }
            }
        });
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Validator.validatePassword(s.toString()) && !s.toString().isEmpty()) {
                    passwordEditText.setError("password is not valide!");
                } else {
                    currentUser.setPassword(s.toString());
                    passwordEditText.setError(null);
                }
            }
        });
    }

    public void setNameEditTextValidate() {

        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!(Validator.validateNameLat(s.toString()) || Validator.validateNameCyr(s.toString())) && !s.toString().isEmpty()) {
                    nameEditText.setError("name is not valide!");
                } else {
                    nameEditText.setError(null);
                    currentUser.setName(s.toString());
                }
            }
        });
    }

    public void setAgeEditTextValidate() {
        ageEditText.setText(String.valueOf(currentUser.getAge()));
        ageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int age = Integer.valueOf(s.toString());
                    if (age < 0)
                        ageEditText.setError("You can enter only unsigned numbers!");
                    if (age < 14 && age > 0)
                        ageEditText.setError("You are so little)))");
                    if (age > 100)
                        ageEditText.setError("So old(");
                    else {
                        currentUser.setAge(age);
                    }

                } catch (NumberFormatException e) {
                    ageEditText.setError("You can input only integer numbers!");
                }
            }
        });
    }

    public void setPhoneEditTextValidate() {
        phoneEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (currentUser.getNumber().equals("null")) {
                        phoneEditText.setText("");
                        return;
                    }
                    phoneEditText.setText((currentUser.getNumber() == null) ? "" : currentUser.getNumber());
                }
            }
        });
        phoneEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isPhoneValide()) {
                    phoneTextInputLayout.setError("Phone is not valide!\nFormat \'+(countrycode)+(number)\'");
                }
                if (DBHandler.userExistsPhone(s.toString()) && !s.toString().equals(currentUser.getNumber())) {
                    phoneTextInputLayout.setError("User with this number exists!");
                }
                if ((isPhoneValide() && !DBHandler.userExistsPhone(s.toString()))
                        || (DBHandler.userExistsPhone(s.toString()) && !s.toString().equals(currentUser.getNumber()))) {
                    phoneTextInputLayout.setError(null);
                    currentUser.setNumber(s.toString());
                }
            }
        });
    }

    public void enableFields(boolean enabled) {
        nameEditText.setEnabled(enabled);
        emailEditText.setEnabled(enabled);
        passwordEditText.setEnabled(enabled);
        ageEditText.setEnabled(enabled);
        phoneEditText.setEnabled(enabled);
    }

    public boolean isPhoneValide() {
        String number = phoneEditText.getText().toString();
        if (!number.isEmpty()) {
            String[] parts = number.split(":");
            if (parts[0].equals("phone") && Validator.validatePhone(parts[1])) {
                return true;
            }
            if (Validator.validatePhone(number))
                return true;
            else
                return false;
        }
        return false;

    }
}
