package com.example.knowledgeknockout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputName;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        inputName = findViewById(R.id.input_name);
        startButton = findViewById(R.id.start_button);

        startButton.setOnClickListener(view -> {
            String name = inputName.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
            } else {
                // Start GameActivity and pass the user's name
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("userName", name);
                startActivity(intent);
            }
        });
    }
}
