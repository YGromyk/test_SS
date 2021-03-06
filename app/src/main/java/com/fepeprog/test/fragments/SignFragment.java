package com.fepeprog.test.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fepeprog.test.R;

public class SignFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_fragment, container, false);
        Button signInOpen = (Button) view.findViewById(R.id.sign_in);
        Button signUpOpen = (Button) view.findViewById(R.id.sign_up);

        signInOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.sign_frame_container, new SignInFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        signUpOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.sign_frame_container, new SignUpFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;
    }


}


