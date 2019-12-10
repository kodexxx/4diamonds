package com.example.a4diamonds.activities;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a4diamonds.FieldAdapter;
import com.example.a4diamonds.R;
import com.example.a4diamonds.engine.ChangeScoreCallback;
import com.example.a4diamonds.engine.Engine;
import com.example.a4diamonds.entities.StepEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Nullable;

public class PlayerVsPlayerFirebaseGameActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        final String gameId = getIntent().getStringExtra("gameId");
        TextView gameIdView = findViewById(R.id.currentGameId);

        gameIdView.setText("ID: " + gameId);

        db = FirebaseFirestore.getInstance();

        final int color = getIntent().getIntExtra("color", 0);

        final ExecutorService service = Executors.newCachedThreadPool();

        final Engine engine = new Engine(10);

        final FieldAdapter adapter = new FieldAdapter(this, engine.getField());

        final GridView gridView = findViewById(R.id.game_info);
        gridView.setSoundEffectsEnabled(false);
        final TextView scoreRed = findViewById(R.id.scoreBlue);
        final TextView scoreBlue = findViewById(R.id.scoreRed);

        final Context self = this;


        final int[] nowStepIndex = {-1};
        this.changeActiveDiamond(Engine.RED_DIAMOND);

//        db.collection("games")
//                .document(gameId)
//                .collection("steps")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot item : task.getResult().getDocuments()) {
//                                int x = Integer.valueOf(String.valueOf(item.get("x")));
//                                int y = Integer.valueOf(String.valueOf(item.get("y")));
//                                int color = Integer.valueOf(String.valueOf(item.get("type")));
//                                engine.setDiamond(x, y, color);
//                                changeActiveDiamond(color == Engine.BLUE_DIAMOND ? Engine.RED_DIAMOND : Engine.BLUE_DIAMOND);
//                            }
//                            adapter.updateResults(engine.getField());
//                            scoreRed.setText(String.format("%d", engine.getScoreRed().getScore()));
//                            scoreBlue.setText(String.format("%d", engine.getScoreBlue().getScore()));
//                        }
//                    }
//                });

        db.collection("games")
                .document(gameId)
                .collection("steps")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        nowStepIndex[0] = -1;
                        for (DocumentSnapshot item : queryDocumentSnapshots.getDocuments()) {
                            int x = Integer.valueOf(String.valueOf(item.get("x")));
                            int y = Integer.valueOf(String.valueOf(item.get("y")));
                            int color = Integer.valueOf(String.valueOf(item.get("type")));
                            engine.setDiamond(x, y, color);
                            nowStepIndex[0] *= -1;

                            changeActiveDiamond(nowStepIndex[0] == -1 ? Engine.RED_DIAMOND : Engine.BLUE_DIAMOND);
                        }
                        adapter.updateResults(engine.getField());
                        scoreRed.setText(String.format("%d", engine.getScoreRed().getScore()));
                        scoreBlue.setText(String.format("%d", engine.getScoreBlue().getScore()));
                    }
                });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int x = position / 10;
                int y = position % 10;

                int nextColor = (nowStepIndex[0] == 1 ? Engine.RED_DIAMOND : Engine.BLUE_DIAMOND);

                if (nextColor == color) {
                    Toast.makeText(self, "Not your turn", Toast.LENGTH_SHORT).show();
                    return;
                }

                db.collection("games")
                        .document(gameId)
                        .collection("steps")
                        .add(new StepEntity(nextColor == Engine.BLUE_DIAMOND ? Engine.RED_DIAMOND : Engine.BLUE_DIAMOND, x, y))
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                System.out.println(task.isSuccessful());
                            }
                        });

//                changeActiveDiamond(nextColor);
//
//                nowStepIndex[0] *= -1;
//
//                service.submit(new Runnable() {
//                    @Override
//                    public void run() {
//                        MediaPlayer player = MediaPlayer.create(PlayerVsPlayerFirebaseGameActivity.this, R.raw.pick);
//                        player.setVolume(100, 100);
//                        player.start();
//                    }
//                });
//
//                engine.setDiamond(x, y);
//                adapter.updateResults(engine.getField());
//
//
//                scoreRed.setText(String.format("%d", engine.getScoreRed().getScore()));
//                scoreBlue.setText(String.format("%d", engine.getScoreBlue().getScore()));
            }
        });

        engine.onScoreChanged(new ChangeScoreCallback() {
            @Override
            public void changed(int type) {
                service.submit(new Runnable() {
                    @Override
                    public void run() {
                        service.submit(new Runnable() {
                            @Override
                            public void run() {
                                MediaPlayer player = MediaPlayer.create(PlayerVsPlayerFirebaseGameActivity.this, R.raw.score);
                                player.setVolume(100, 100);
                                player.start();
                            }
                        });
                    }
                });
            }
        });

        gridView.setNumColumns(10);

        gridView.setAdapter(adapter);
    }

    private void changeActiveDiamond(int type) {
        ImageView red = findViewById(R.id.blue_diamond_image);
        ImageView blue = findViewById(R.id.red_diamond_image);
        if (type == Engine.RED_DIAMOND) {
            red.setAlpha((float) 0.3);
            blue.setAlpha((float) 1);
        } else {
            red.setAlpha((float) 1);
            blue.setAlpha((float) 0.3);
        }
    }
}