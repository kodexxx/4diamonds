package com.example.a4diamonds.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a4diamonds.GameMenu;
import com.example.a4diamonds.R;
import com.example.a4diamonds.engine.Engine;
import com.example.a4diamonds.services.FirebaseGameManager;
import com.example.a4diamonds.utils.OnGameCreatedCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

//        ListView lv = findViewById(R.id.games);


        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        Button createGameButton = findViewById(R.id.create_game_button);

        Button connectToGame = findViewById(R.id.connectToGame);
        final EditText gameId = findViewById(R.id.gameId);
        final Context s  = this;
        connectToGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameId.getText().toString().length() < 1) {
                    Toast.makeText(s, "Enter gameId", Toast.LENGTH_SHORT).show();
                    return;
                }
                db.collection("games").document(gameId.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Intent intentMain = new Intent(PlayerVsPlayerFirebaseActivity.this,
                                        PlayerVsPlayerFirebaseGameActivity.class);

                                intentMain.putExtra("gameId", gameId.getText().toString());
                                intentMain.putExtra("color", Engine.BLUE_DIAMOND);
                                PlayerVsPlayerFirebaseActivity.this.startActivity(intentMain);
                            } else {
                                Toast.makeText(s, "Game not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseGameManager.createGame(new OnGameCreatedCallback() {
                    @Override
                    public void onGameCreated(String gameId) {
                        Intent intentMain = new Intent(PlayerVsPlayerFirebaseActivity.this,
                                PlayerVsPlayerFirebaseGameActivity.class);

                        intentMain.putExtra("gameId", gameId);
                        intentMain.putExtra("color", Engine.RED_DIAMOND);
                        PlayerVsPlayerFirebaseActivity.this.startActivity(intentMain);
                    }
                });
            }
        });


        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1);

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String item = (String) adapterView.getItemAtPosition(i);
//                System.out.println(item);
//            }
//        });

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

//        lv.setAdapter(adapter);
    }

}
