package com.example.suitcase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class editMyProfile extends AppCompatActivity {


    private ImageView save;
    private TextView personal_name_TV,emailTV,PNTV;
    private ProgressDialog process;
    private FirebaseAuth FBA = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setup();


        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                process.setMessage("Saving Changes");
                process.show();
                if (validate()){
                    final String Uname=personal_name_TV.getText().toString();
                    final String UPhN = PhoneNumberUtils.formatNumber(PNTV.getText().toString());
                    String userID = FBA.getCurrentUser().getEmail();
                    final DocumentReference User = db.collection("Users").document(userID);
                    User.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot!= null && documentSnapshot.exists()) {
                                User u = documentSnapshot.toObject(User.class);
                                u.setName(Uname);
                                u.setPhoneNumber(UPhN);
                                User.set(u);
                            }
                        }
                    });
                }
                else{
                    process.dismiss();
                    return;
                }
                process.dismiss();
                Toast.makeText(editMyProfile.this,"Saved",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(editMyProfile.this, MyProfile.class);
                startActivity(intent);
                return;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        String userID = FBA.getCurrentUser().getEmail();
        DocumentReference User = db.collection("Users").document(userID);
        User.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot!= null && documentSnapshot.exists()) {
                    User u = documentSnapshot.toObject(User.class);
                    personal_name_TV.setText( u.getName());
                    emailTV.setText( u.getEmail());
                    PNTV.setText( u.getPhoneNumber());
                }
            }
        });
    }

    private void setup() {

        personal_name_TV = (TextView) findViewById(R.id.personal_name_TV);
        save=(ImageView) findViewById(R.id.save);
        emailTV =(TextView) findViewById(R.id.personal_Email_TV);
        PNTV=(TextView) findViewById(R.id.personal_PN_TV);
        process = new ProgressDialog(this);
    }

    private Boolean validate(){
        String name=personal_name_TV.getText().toString();
        String PhN = PhoneNumberUtils.formatNumber(PNTV.getText().toString());
        if (name.isEmpty() ||PhN.isEmpty()){
            Toast.makeText(this,"You Have To Fill All The Fields",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}



