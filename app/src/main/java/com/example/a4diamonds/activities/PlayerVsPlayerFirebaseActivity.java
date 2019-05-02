package com.example.a4diamonds.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.a4diamonds.R;
import com.example.a4diamonds.services.FirebaseGameManager;
import com.example.a4diamonds.utils.OnGameCreatedCallback;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class PlayerVsPlayerFirebaseActivity extends AppCompatActivity {
    final private String TAG = "firebaseAcitivity";

    FirebaseGameManager firebaseGameManager = new FirebaseGameManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ListView lv = findViewById(R.id.games);


        FirebaseFirestore db = FirebaseFirestore.getInstance();


        Button createGameButton = findViewById(R.id.create_game_button);

        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseGameManager.createGame(new OnGameCreatedCallback() {
                    @Override
                    public void onGameCreated(String gameId) {
                        System.out.println(gameId);
                    }
                });
            }
        });


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
