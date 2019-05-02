package com.example.a4diamonds.services;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.a4diamonds.entities.GameInfoEntity;
import com.example.a4diamonds.entities.StepEntity;
import com.example.a4diamonds.utils.OnGameCreatedCallback;
import com.example.a4diamonds.utils.StringGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class FirebaseGameManager {
    final private static String TAG = "FirebaseGameManager";
    final private static String GAME_COLLECTION = "games";
    private FirebaseFirestore db;
    private StringGenerator stringGenerator;

    public FirebaseGameManager() {
        db = FirebaseFirestore.getInstance();
        stringGenerator = new StringGenerator();
    }

    public void connectToGame(String gameId) {
        db.collection(GAME_COLLECTION).document(gameId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && Objects.requireNonNull(task.getResult()).exists()) {
                    System.out.println(task.getResult());
                }
            }
        });
    }

    public String createGame(final OnGameCreatedCallback onGameCreatedCallback) {
        final String gameId = stringGenerator.generateGameId();
        db
                .collection(GAME_COLLECTION)
                .document(gameId)
                .set(new GameInfoEntity(false, true, new Date(), new ArrayList<StepEntity>()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        onGameCreatedCallback.onGameCreated(gameId);
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                });
        return gameId;
    }
}
