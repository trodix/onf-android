package com.example.aurelienthazet.bubuche;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreationInterventionActivity extends AppCompatActivity {

    private Button btAccueilCreerIntervention;
    @SuppressLint("WrongViewCast")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_intervention);

        btAccueilCreerIntervention = (Button) findViewById(R.id.btAccueilCreerIntervention);
        btAccueilCreerIntervention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(CreationInterventionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
