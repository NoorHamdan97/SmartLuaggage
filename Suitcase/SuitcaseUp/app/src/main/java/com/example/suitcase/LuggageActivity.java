package com.example.suitcase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LuggageActivity extends AppCompatActivity {
    private Button Add;
    private ListView listView;
    private List<String> list;
    private ArrayAdapter arrayAdapter;
    private User u;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth FBA = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luggage);
        setup();
        final String userID = FBA.getCurrentUser().getEmail();
        DocumentReference User = db.collection("Users").document(userID);
        User.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot!= null && documentSnapshot.exists()) {
                    u = documentSnapshot.toObject(User.class);
                    list = u.getSuitcases();
                    arrayAdapter = new ArrayAdapter(LuggageActivity.this,android.R.layout.simple_list_item_1,list);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String scsn = list.get(position);
                            Intent intent = new Intent(LuggageActivity.this,SuitcaseProfileActivity.class);
                            intent.putExtra("SN",scsn);
                            intent.putExtra("User",u.getName());
                            startActivity(intent);
                        }
                    });
                }
            }
        });


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LuggageActivity.this, Add_SuitcaseActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setup() {
        Add = (Button) findViewById(R.id.add_btn);
        listView =(ListView)findViewById(R.id.suitcases_list);
        list = new ArrayList<String>();
    }
}
