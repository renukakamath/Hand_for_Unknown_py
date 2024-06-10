package com.example.hfu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hfu.Moregames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private TextView instructionTextView;
    private TextView scoreTextView,Gamecon;
    private List<String> colors;
    private Random random;
    private int currentColorIndex;
    private int correctButtonIndex;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        instructionTextView = findViewById(R.id.instructionTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        Gamecon = findViewById(R.id.Gamecon);

        colors = new ArrayList<>(Arrays.asList(
                "#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF",
                "#00FFFF", "#FFA500", "#800080", "#FFC0CB", "#008000",
                "#FF4500", "#800000", "#FFFF99", "#DC143C", "#FF1493",
                "#000080", "#FFD700", "#228B22", "#FF6347", "#4B0082",
                "#FF8C00", "#2E8B57", "#FF69B4", "#0000CD", "#D2691E",
                "#8B008B", "#00CED1", "#BA55D3", "#FFFFE0", "#4169E1",
                "#B22222", "#FFB6C1", "#7CFC00", "#FFFF66", "#9400D3",
                "#00BFFF", "#6B8E23", "#FF00FF", "#F08080", "#4682B4",
                "#DAA520", "#FF7F50", "#FFDAB9", "#1E90FF", "#9932CC",
                "#FF4500", "#48D1CC", "#ADFF2F", "#FF69B4", "#FFD700",
                "#FF8C00", "#00BFFF", "#008B8B", "#FF00FF", "#FF6347",
                "#40E0D0", "#9ACD32", "#BA55D3", "#800080", "#FF0000",
                "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF",
                "#FFA500", "#800080", "#FFC0CB", "#008000", "#FF4500",
                "#800000", "#FFFF99", "#DC143C", "#FF1493", "#000080",
                "#FFD700", "#228B22", "#FF6347", "#4B0082", "#FF8C00",
                "#2E8B57", "#FF69B4", "#0000CD", "#D2691E", "#8B008B",
                "#00CED1", "#BA55D3", "#FFFFE0", "#4169E1", "#B22222",
                "#FFB6C1", "#7CFC00", "#FFFF66", "#9400D3", "#00BFFF",
                "#6B8E23", "#FF00FF", "#F08080", "#4682B4", "#DAA520",
                "#FF7F50", "#FFDAB9", "#1E90FF", "#9932CC", "#FF4500",
                "#48D1CC", "#ADFF2F", "#FF69B4", "#FFD700", "#FF8C00",
                "#00BFFF", "#008B8B", "#FF00FF", "#FF6347", "#40E0D0",
                "#9ACD32", "#BA55D3", "#800080", "#FF0000", "#00FF00"
        ));

        random = new Random();
        score = 0;

        showQuestion();
    }

    private void showQuestion() {
        currentColorIndex = random.nextInt(colors.size());
        String currentColor = colors.get(currentColorIndex);
        int lighterColor = lightenColor(currentColor);

        instructionTextView.setText("Select the Darker shade  from this ");
        scoreTextView.setText("Score: " + score);

        Button[] buttons = {
                findViewById(R.id.color1Button),
                findViewById(R.id.color2Button),
                findViewById(R.id.color3Button),
                findViewById(R.id.color4Button),
                findViewById(R.id.color6Button),
                findViewById(R.id.color8Button),
                findViewById(R.id.color9Button),
                findViewById(R.id.color10Button),
                findViewById(R.id.color11Button),
                findViewById(R.id.color12Button),
                findViewById(R.id.color13Button),
                findViewById(R.id.color14Button),
                findViewById(R.id.color15Button),
                findViewById(R.id.color16Button),
                findViewById(R.id.color17Button),
                findViewById(R.id.color18Button)
        };

        correctButtonIndex = random.nextInt(buttons.length);

        for (int i = 0; i < buttons.length; i++) {
            Button button = buttons[i];
            if (i == correctButtonIndex) {
                button.setBackgroundColor(lighterColor);
                button.setTag(true);
            } else {
                button.setBackgroundColor(Color.parseColor(currentColor));
                button.setTag(false);
            }
            button.setOnClickListener(this);
        }
    }

    private int lightenColor(String colorName) {
        int color = Color.parseColor(colorName);
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.90f; // Reduce brightness by 20%
        return Color.HSVToColor(hsv);
    }

    @Override
    public void onClick(View v) {
        boolean isCorrect = (boolean) v.getTag();
        if (isCorrect) {
            score++;
            Gamecon.setText("Correct Answer...");

        } else {
            score = 0;
            final CharSequence[] items = {"Start Again", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
            builder.setTitle("Game Over......!");
            builder.setItems(items, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Start Again"))
                    {
                        startActivity(new Intent(getApplicationContext(),MainActivity2.class));

                    }  else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }
        showQuestion();
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit  :")
                .setMessage("Are you sure you want to exit..?")
                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        // TODO Auto-generated method stub
                        startActivity(new Intent(getApplicationContext(), Moregames.class));
                        finish();
                    }
                }).setNegativeButton("No",null).show();
    }


}
