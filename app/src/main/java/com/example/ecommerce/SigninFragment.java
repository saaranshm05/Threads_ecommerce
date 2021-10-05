package com.example.ecommerce;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import static com.example.ecommerce.RegisterActivity.onResetPassFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SigninFragment extends Fragment {


    public SigninFragment() {
        // Required empty public constructor
    }
    private String pattern="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    public static boolean disableCloseBtn = false;

    private TextView dont_have_an_acc , forgotPassword;
    private FrameLayout parentframeLayout;
    private TextInputEditText email, pass;
    private Button signinBtn;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_signin, container, false);
        dont_have_an_acc=view.findViewById(R.id.sign_in_register_here);
        parentframeLayout=getActivity().findViewById(R.id.reg_frame_layout);

        forgotPassword=view.findViewById(R.id.sign_in_forgot_pass);

        email=view.findViewById(R.id.sign_in_email);
        pass=view.findViewById(R.id.sign_in_password);

        signinBtn=view.findViewById(R.id.update_pass_btn);

        progressBar=view.findViewById(R.id.sign_in_progressBar);

        firebaseAuth=FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dont_have_an_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignupFragment());
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResetPassFragment=true;
                setFragment(new ForgotPasswordFragment());
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailandPass();
            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentframeLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    private void checkInputs() {
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(pass.getText()) && pass.length()>=8){
                signinBtn.setEnabled(true);
            }else{
                signinBtn.setEnabled(false);
            }
        }else{
            signinBtn.setEnabled(false);
        }
    }

    private void checkEmailandPass() {
        if(email.getText().toString().matches(pattern)){
            if(pass.getText().length()>=8){

                progressBar.setVisibility(View.VISIBLE);
                signinBtn.setEnabled(false);

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString().trim(),pass.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    if(disableCloseBtn){
                                        disableCloseBtn=false;
                                    }else {
                                        startActivity(new Intent(getActivity(),MainActivity.class));
                                    }
                                    getActivity().finish();
                                }else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signinBtn.setEnabled(true);
                                    String error=task.getException().getMessage();
                                    Toast.makeText(getActivity(), error,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }else {
                pass.setError("Password must be of minimum 8 characters.");
            }
        }else {
            email.setError("Invalid Email.");
        }
    }


}
