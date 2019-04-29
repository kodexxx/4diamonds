package com.example.a4diamonds.services;

import android.support.annotation.NonNull;

import com.example.a4diamonds.entities.GameInfoEntity;
import com.example.a4diamonds.utils.StringGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseGameManager {
    FirebaseFirestore db;
    StringGenerator stringGenerator;

    FirebaseGameManager() {
        db = FirebaseFirestore.getInstance();
        stringGenerator = new StringGenerator();
    }

    List<GameInfoEntity> getAllGames() {
        db.collection("games")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        task.getResult().getDocuments();
                    }
                });

        return new ArrayList<>();

    }

    String createGame() {
        return stringGenerator.generateGameId();
    }
}
