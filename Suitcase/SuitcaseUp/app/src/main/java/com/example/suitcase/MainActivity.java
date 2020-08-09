package com.example.suitcase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private EditText Email,UserPass;
    private Button Login;
    private TextView Register;
    private FirebaseAuth FBauth;
    private ProgressDialog process;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email = (EditText)findViewById(R.id.et_email);
        UserPass = (EditText)findViewById(R.id.et_user_pass);
        Login = (Button)findViewById(R.id.btn_Login);
        Register = (TextView)findViewById(R.id.tv_register);
        FBauth = FirebaseAuth.getInstance();
        process = new ProgressDialog(this);




        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Email.getText().toString(), UserPass.getText().toString());

            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,  registeration.class);
                startActivity(intent);
            }
        });

    }

    private void validate(String userName, String userPassword) {
        if (userName.isEmpty() || userPassword.isEmpty()){
            Toast.makeText(this,"You Have To Fill All The Fields",Toast.LENGTH_SHORT).show();
            return;
        }
        process.setMessage("Authentication In Process");
        process.show();
        FBauth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                    process.dismiss();

                    Intent intent = new Intent(MainActivity.this, MyProfile.class);
                    startActivity(intent);
                }
                else{
                    process.dismiss();
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
