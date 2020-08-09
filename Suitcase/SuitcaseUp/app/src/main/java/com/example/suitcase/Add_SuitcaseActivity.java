package com.example.suitcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Add_SuitcaseActivity extends AppCompatActivity {
    private static final String TAG = "tag" ;
    private ImageView Add1,Add2;
    private TextView SN1,SN2,SName;
    private ProgressDialog process;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth FBA = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__suitcase);
        process = new ProgressDialog(this);
        setup();

        Add2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                process.setMessage("Authentication In Process");
                process.show();
                final boolean[] res = {true};
                final String SerialN2 = SN2.getText().toString();
                final String userID = FBA.getCurrentUser().getEmail();
                final DocumentReference User1 = db.collection("Users").document(userID);
                User1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    public void onSuccess(final DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot!= null && documentSnapshot.exists()) {
                            User u = documentSnapshot.toObject(User.class);
                            List<String> array = u.getSuitcases();
                            if (array == null){
                                array = new ArrayList<String>(7);
                            }
                            ///check if SN exists
                            final DocumentReference docIdRef = db.collection("Suitcases").document(SerialN2);
                            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Log.d(TAG, "Document exists!");
                                            Suitcase s = document.toObject(Suitcase.class);
                                            User u = documentSnapshot.toObject(User.class);
                                            if (s.isSC_InUse()) {
                                                List<String> array = u.getSuitcases();
                                                List<String> arr = s.getSC_Users();
                                                if (array == null) {
                                                    array = new ArrayList<String>(7);
                                                }
                                                if (arr == null) {
                                                    arr = new ArrayList<String>(7);
                                                }
                                                for (int j = 0; j < array.size(); j++) {
                                                    if (array.get(j).equals(SerialN2)) {
                                                        Toast.makeText(Add_SuitcaseActivity.this, "Suitcase Already Exists", Toast.LENGTH_SHORT).show();
                                                        process.dismiss();
                                                        res[0] = false;
                                                        Intent intent = new Intent(Add_SuitcaseActivity.this, LuggageActivity.class);
                                                        startActivity(intent);
                                                        break;
                                                    }
                                                }
                                                if (res[0]) {
                                                    array.add(SerialN2);
                                                    u.setSuitcases(array);
                                                    int n = u.getSuitcaseNum()+1;
                                                    u.setSuitcaseNum(n);
                                                    User1.set(u);
                                                    arr.add(u.getEmail());
                                                    int num = s.getSC_UsersNum()+1;
                                                    s.setSC_UsersNum(num);
                                                    s.setSC_Users(arr);
                                                    docIdRef.set(s);
                                                    Toast.makeText(Add_SuitcaseActivity.this, "Suitcase Added", Toast.LENGTH_SHORT).show();
                                                    process.dismiss();
                                                    Intent intent = new Intent(Add_SuitcaseActivity.this, LuggageActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
                                            else{
                                                Toast.makeText(Add_SuitcaseActivity.this, "Not in Use", Toast.LENGTH_SHORT).show();
                                                process.dismiss();
                                            }
                                        }
                                        else {
                                            Log.d(TAG, "Document does not exist!");
                                            Toast.makeText(Add_SuitcaseActivity.this, "No Such Serial Number", Toast.LENGTH_SHORT).show();
                                            process.dismiss();
                                        }
                                    }
                                    else {
                                        Log.d(TAG, "Failed with: ", task.getException());
                                        Toast.makeText(Add_SuitcaseActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        process.dismiss();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        Add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process.setMessage("Authentication In Process");
                process.show();
                final boolean[] res = {true};
                final String SerialN = SN1.getText().toString();
                final String Name = SName.getText().toString();
                final String userID = FBA.getCurrentUser().getEmail();
                final DocumentReference User1 = db.collection("Users").document(userID);
                User1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    public void onSuccess(final DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot!= null && documentSnapshot.exists()) {
                            User u = documentSnapshot.toObject(User.class);
                            List<String> array = u.getSuitcases();
                            if (array == null){
                                array = new ArrayList<String>(7);
                            }
                            ///check if SN exists
                            final DocumentReference docIdRef = db.collection("Suitcases").document(SerialN);
                            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Log.d(TAG, "Document exists!");
                                            Suitcase s = document.toObject(Suitcase.class);
                                            User u = documentSnapshot.toObject(User.class);
                                            if (!s.isSC_InUse()) {
                                                List<String> array = u.getSuitcases();
                                                List<String> arr = s.getSC_Users();
                                                if (array == null) {
                                                    array = new ArrayList<String>(7);
                                                }
                                                if (arr == null) {
                                                    arr = new ArrayList<String>(7);
                                                }
                                                for (int j = 0; j < array.size(); j++) {
                                                    if (array.get(j).equals(SerialN)) {
                                                        Toast.makeText(Add_SuitcaseActivity.this, "Suitcase Already Exists", Toast.LENGTH_SHORT).show();
                                                        process.dismiss();
                                                        res[0] = false;
                                                        Intent intent = new Intent(Add_SuitcaseActivity.this, LuggageActivity.class);
                                                        startActivity(intent);
                                                        break;
                                                    }
                                                }
                                                if (res[0]) {
                                                    array.add(SerialN);
                                                    u.setSuitcases(array);
                                                    int n = u.getSuitcaseNum()+1;
                                                    u.setSuitcaseNum(n);
                                                    User1.set(u);
                                                    arr.add(u.getEmail());
                                                    int num = s.getSC_UsersNum()+1;
                                                    s.setSC_InUse(true);
                                                    s.setSC_Users(arr);
                                                    s.setSC_UsersNum(num);
                                                    s.setSC_Name(Name);
                                                    docIdRef.set(s);
                                                    Toast.makeText(Add_SuitcaseActivity.this, "Suitcase Added", Toast.LENGTH_SHORT).show();
                                                    process.dismiss();
                                                    Intent intent = new Intent(Add_SuitcaseActivity.this, LuggageActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
                                            else{
                                                Toast.makeText(Add_SuitcaseActivity.this, "Already in Use", Toast.LENGTH_SHORT).show();
                                                process.dismiss();
                                            }
                                        }
                                        else {
                                            Log.d(TAG, "Document does not exist!");
                                            Toast.makeText(Add_SuitcaseActivity.this, "No Such Serial Number", Toast.LENGTH_SHORT).show();
                                            process.dismiss();
                                        }
                                    }
                                    else {
                                        Log.d(TAG, "Failed with: ", task.getException());
                                        Toast.makeText(Add_SuitcaseActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        process.dismiss();
                                    }
                                }
                            });
                        }
                    }
                });
            }
       });
    }

    private void setup() {
        Add1 = (ImageView) findViewById(R.id.registerSC);
        Add2= (ImageView) findViewById(R.id.registerSC2);
        SN1=  (TextView) findViewById(R.id.SN);
        SName =(TextView) findViewById(R.id.SCName);
        SN2=  (TextView) findViewById(R.id.SN2);
    }
}
