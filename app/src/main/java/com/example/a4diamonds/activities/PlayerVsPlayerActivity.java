package com.example.a4diamonds.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a4diamonds.FieldAdapter;
import com.example.a4diamonds.R;
import com.example.a4diamonds.engine.ChangeScoreCallback;
import com.example.a4diamonds.engine.Engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerVsPlayerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        final ExecutorService service = Executors.newCachedThreadPool();

        final Engine engine = new Engine(10);

        final FieldAdapter adapter = new FieldAdapter(this, engine.getField());

        final GridView gridView = findViewById(R.id.game_info);
        gridView.setSoundEffectsEnabled(false);
        final TextView scoreRed = findViewById(R.id.scoreBlue);
        final TextView scoreBlue = findViewById(R.id.scoreRed);


        final int[] nowStepIndex = {-1};

        this.changeActiveDiamond(Engine.RED_DIAMOND);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int x = position / 10;
                int y = position % 10;

                changeActiveDiamond((nowStepIndex[0] == 1 ? Engine.RED_DIAMOND : Engine.BLUE_DIAMOND));

                nowStepIndex[0] *= -1;

                service.submit(new Runnable() {
                    @Override
                    public void run() {
                        MediaPlayer player = MediaPlayer.create(PlayerVsPlayerActivity.this, R.raw.pick);
                        player.setVolume(100, 100);
                        player.start();
                    }
                });

                engine.setDiamond(x, y);
                adapter.updateResults(engine.getField());


                scoreRed.setText(String.format("%d", engine.getScoreRed().getScore()));
                scoreBlue.setText(String.format("%d", engine.getScoreBlue().getScore()));
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
                                MediaPlayer player = MediaPlayer.create(PlayerVsPlayerActivity.this, R.raw.score);
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