package com.example.suitcase;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyProfile extends AppCompatActivity {

    private Button my_lugs_btn,log_out_btn;
    private ImageButton edit;
    private TextView personal_name_TV,emailTV,PNTV;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth FBA = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setup();
        log_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfile.this, MainActivity.class);
                startActivity(intent);
            }
        });

        my_lugs_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfile.this, LuggageActivity.class);
                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this, editMyProfile.class);
                startActivity(intent);
            }
        });
    }

    private void fillText(){
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

@Override
    protected void onStart() {
    super.onStart();
    fillText();
}

    private void setup() {
        my_lugs_btn = (Button) findViewById(R.id.my_lugs_btn);
        log_out_btn = (Button) findViewById(R.id.log_out_btn);
        personal_name_TV = (TextView) findViewById(R.id.personal_name_TV);
        edit = (ImageButton) findViewById(R.id.editdetails);
        emailTV =(TextView) findViewById(R.id.personal_Email_TV);
        PNTV=(TextView) findViewById(R.id.personal_PN_TV);
    }
}



