package com.example.suitcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SuitcaseProfileActivity extends AppCompatActivity {
    private Switch Vacuum;
    private TextView scname,scsn,sccu;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suitcase_profile);
        db = FirebaseFirestore.getInstance();
        setup();
        final String SN =getIntent().getStringExtra("SN");
        final String User = getIntent().getStringExtra("User");
        DocumentReference SC = db.collection("Suitcases").document(SN);
        SC.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot!= null && documentSnapshot.exists()) {
                    Suitcase s = documentSnapshot.toObject(Suitcase.class);
                    boolean SCswitch = s.isSC_Switch();
                    Vacuum.setChecked(SCswitch);
                    scname.setText(s.getSC_Name());
                    scsn.setText(s.getSC_SN());
                    sccu.setText(User);
                }
            }
        });

        Vacuum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DocumentReference docRef = db.collection("Suitcases").document(SN);
                if (isChecked == true) {
                    docRef.update("sc_Switch",true).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SuitcaseProfileActivity.this,"vacuum on",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(SuitcaseProfileActivity.this,"operation can not be completed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    docRef.update("sc_Switch",false).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SuitcaseProfileActivity.this,"vacuum off",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(SuitcaseProfileActivity.this,"operation can not be completed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    void setup(){
        Vacuum = (Switch)findViewById(R.id.sw_vacuum);
        scname = (TextView)findViewById(R.id.textVname);
        scsn = (TextView)findViewById(R.id.textVsn);
        sccu = (TextView)findViewById(R.id.textVusr);
    }
}
