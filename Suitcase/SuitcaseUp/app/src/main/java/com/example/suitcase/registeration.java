package com.example.suitcase;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;

public class registeration extends AppCompatActivity {
    private EditText userName, userPassword1, userPassword2, userEmail, userPN;
    private Button registerbtn;
    private TextView userLogin1, userLogin2;
    private FirebaseAuth FBA = FirebaseAuth.getInstance();
    private User Cuser;
    FirebaseDatabase db1 = FirebaseDatabase.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference mDataBase = db1.getReference("Users");
    private ProgressDialog process;
    private static final String TAG = "RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        setupViews();
        process = new ProgressDialog(this);

        userLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registeration.this, MainActivity.class);
                startActivity(intent);
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process.setMessage("Wait A Moment");
                process.show();
                if (validate()) {
                    String Uname = userName.getText().toString();
                    String Uemail = userEmail.getText().toString().trim();
                    String Upassword = userPassword1.getText().toString();
                    String UPhN = PhoneNumberUtils.formatNumber(userPN.getText().toString());
//                    Cuser = new User(Uname,Uemail,Upassword,UPhN);
//                    reg(Uemail,Upassword);

                    try {
                        FBA.createUserWithEmailAndPassword(Uemail, Upassword);
                    } catch (Exception e) {
                        String reason = e.getMessage();
                        Toast.makeText(registeration.this, reason, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DocumentReference newUser = db.collection("Users").document(Uemail);
                    User usr = new User(Uname,Uemail,Upassword,UPhN,null);
                    newUser.set(usr).addOnCompleteListener(new OnCompleteListener<Void>(){
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                process.dismiss();
                                Toast.makeText(registeration.this,"Registration Complete",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(registeration.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                process.dismiss();
                                Exception exception = task.getException();
                                String reason = exception.getMessage();
                                Toast.makeText(registeration.this, reason, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    process.dismiss();
                    Toast.makeText(registeration.this, "try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupViews(){
        userName=(EditText)findViewById(R.id.userName);
        userEmail=(EditText)findViewById(R.id.userEmail);
        userPassword1=(EditText)findViewById(R.id.edUserPassword);
        userPassword2=(EditText)findViewById(R.id.Rpassword);
        userPN=(EditText)findViewById(R.id.PN);
        registerbtn=(Button)findViewById(R.id.bRegBut);
        userLogin1 = (TextView)findViewById(R.id.tvGoBack);
        userLogin2 = (TextView)findViewById(R.id.tvGoBack2);
    }

    private Boolean validate(){
        String name=userName.getText().toString();
        String password1=userPassword1.getText().toString();
        String password2=userPassword2.getText().toString();
        String email= userEmail.getText().toString();
        String PhN = PhoneNumberUtils.formatNumber(userPN.getText().toString());
        if (name.isEmpty() || password1.isEmpty() || email.isEmpty() || password2.isEmpty()||PhN.isEmpty()){
            Toast.makeText(this,"You Have To Fill All The Fields",Toast.LENGTH_SHORT).show();
            return false;
        }
        if ((password1.isEmpty()==false)&&(password2.isEmpty()==false)&&(password1.equals(password2))==false){
            Toast.makeText(this,"Your Password Is Incorrect",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void reg (String email,String password){
        FBA.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            FirebaseUser user = FBA.getCurrentUser();

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(registeration.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void updateUI(FirebaseUser curruser) {
        String keyId = mDataBase.push().getKey();
        mDataBase.child("keyId").setValue(Cuser);
        Toast.makeText(registeration.this,"Registration Complete",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(registeration.this, MainActivity.class);
        startActivity(intent);
    }
}