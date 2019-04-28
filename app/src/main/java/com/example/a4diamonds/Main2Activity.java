package com.example.a4diamonds;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.a4diamonds.engine.AI;
import com.example.a4diamonds.engine.ChangeScoreCallback;
import com.example.a4diamonds.engine.Engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final ExecutorService service = Executors.newCachedThreadPool();

        final Engine engine = new Engine(10);
        final AI ai = new AI();

        final FieldAdapter adapter = new FieldAdapter(this, engine.getField());

        final GridView gridView = findViewById(R.id.game_info);
        gridView.setSoundEffectsEnabled(false);
        final TextView scoreRed = findViewById(R.id.scoreBlue);
        final TextView scoreBlue = findViewById(R.id.scoreRed);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int x = position / 10;
                int y = position % 10;

                service.submit(new Runnable() {
                    @Override
                    public void run() {
                        MediaPlayer player = MediaPlayer.create(Main2Activity.this, R.raw.pick);
                        player.setVolume(100, 100);
                        player.start();
                    }
                });

                engine.setDiamond(x, y);
                adapter.updateResults(engine.getField());
                gridView.setEnabled(false);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Pair<Integer, Integer> aiStep = ai.getStep(engine.getField());

                        if (aiStep != null) {
                            engine.setDiamond(aiStep.first, aiStep.second);
                            adapter.updateResults(engine.getField());
                        }
                        gridView.setEnabled(true);

                    }
                });


                scoreRed.setText(String.format("red: %d", engine.getScoreRed().getScore()));
                scoreBlue.setText(String.format("blue: %d", engine.getScoreBlue().getScore()));
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
                                MediaPlayer player = MediaPlayer.create(Main2Activity.this, R.raw.score);
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
}
