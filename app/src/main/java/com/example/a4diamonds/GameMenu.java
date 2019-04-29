package com.example.a4diamonds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.a4diamonds.activities.PlayerVsAIActivity;
import com.example.a4diamonds.activities.PlayerVsPlayerActivity;
import com.example.a4diamonds.activities.PlayerVsPlayerFirebaseActivity;

public class GameMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        Button startAi = findViewById(R.id.start_ai);
        Button startPvP = findViewById(R.id.start_pvsp);
        Button startFirebase = findViewById(R.id.start_firebase);

        startAi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMain = new Intent(GameMenu.this,
                        PlayerVsAIActivity.class);
                GameMenu.this.startActivity(intentMain);
            }
        });

        startPvP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMain = new Intent(GameMenu.this,
                        PlayerVsPlayerActivity.class);
                GameMenu.this.startActivity(intentMain);
            }
        });

        startFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMain = new Intent(GameMenu.this,
                        PlayerVsPlayerFirebaseActivity.class);
                GameMenu.this.startActivity(intentMain);
            }
        });
    }
}
