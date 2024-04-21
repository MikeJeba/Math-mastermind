package com.example.minigame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Result extends AppCompatActivity {

    TextView result;
    Button playAgain;
    Button exit;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result = findViewById(R.id.textViewResult);
        playAgain = findViewById(R.id.buttonPlayAgain);
        exit = findViewById(R.id.buttonExit);

        Intent intent = getIntent();
        score = intent.getIntExtra("score",0);
        String userScore = String.valueOf(score);
        result.setText("Your score: "+userScore);
        addQuoteToDB(userScore);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Result.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void addQuoteToDB(String userScore) {
        //create a hashmap
        HashMap<String, Object> quoteHashmap = new HashMap<>();
        quoteHashmap.put("userScore",userScore);

        //instantiate database connection
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference quotesRef = database.getReference("userScore");

        String key = quotesRef.push().getKey();
        quoteHashmap.put("key",key);

        quotesRef.child(key).setValue(quoteHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Result.this, "Added", Toast.LENGTH_SHORT).show();

            }
        });

    }
}