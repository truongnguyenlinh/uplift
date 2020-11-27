package com.example.uplift;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class GetNameActivity extends AppCompatActivity {

    private Button btnNext;
    private FirebaseAuth firebaseAuth;
    private EditText editTextName;

    public GetNameActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_name);
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),SignInActivity.class));
        }

        editTextName = findViewById(R.id.nameEditText);
        btnNext = findViewById(R.id.btnNext);

        editTextName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        enterName();
                        return true;
                    }
                }
                return false;
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterName();
            }
        });

    }

    public void enterName() {
        String name = editTextName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "You must enter a name.", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(GetNameActivity.this, GetFrequencyActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }

}