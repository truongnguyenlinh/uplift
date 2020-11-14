package com.example.uplift;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GetFrequencyActivity extends AppCompatActivity {

    private Button btnNext;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private Spinner spinnerFrequency;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_frequency);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),SignInActivity.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        spinnerFrequency = findViewById(R.id.spinnerFrequency);
        btnNext = findViewById(R.id.btnNext);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.frequency, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerFrequency.setAdapter(adapter);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreferences();
                Intent intent = new Intent(GetFrequencyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setPreferences(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String frequencyString = spinnerFrequency.getSelectedItem().toString();

        UserPreference userPreference = new UserPreference(name, FrequencyToInt(frequencyString));
        databaseReference.child(user.getUid()).setValue(userPreference);
    }

    private int FrequencyToInt(String frequency) {
        int frequencyMinutes = 0;
        if (frequency.equals("Every Hour")) {
            frequencyMinutes = 60;
        } else if (frequency.equals("Every 2 Hours")) {
            frequencyMinutes = 120;
        } else if (frequency.equals("Every 4 Hours")) {
            frequencyMinutes = 240;
        } else if (frequency.equals("Every 8 Hours")) {
            frequencyMinutes = 480;
        } else if (frequency.equals("Once a Day")) {
            frequencyMinutes = 1440;
        }
        return frequencyMinutes;
    }
}