package com.example.a4diamonds.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.a4diamonds.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class PlayerVsPlayerFirebaseActivity extends AppCompatActivity {
    final private String TAG = "firebaseAcitivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ListView lv = findViewById(R.id.games);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1);


        db.collection("games")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        adapter.clear();
                        for (DocumentSnapshot item : queryDocumentSnapshots.getDocuments()) {
                            Log.d(TAG, String.valueOf(item.getId()));
                            adapter.add(item.getId());
                        }
                    }
                });

        lv.setAdapter(adapter);
    }

}
