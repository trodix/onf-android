package com.example.aurelienthazet.bubuche;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import bdd.InterventionDAO;
import metier.Intervention;

public class CreationInterventionActivity extends AppCompatActivity {

    private Button btAccueilCreerIntervention;

    private TextView tvCreationInterventionIdArbre;
    private EditText ptTypeIntervention;
    private EditText ptDateCreationIntervention;
    private EditText ptHeureCreationIntervention;
    private EditText mtObsCreationIntervention;


    //Valeur saisies
    int idArbre;
    String typeIntervention;
    String dateCreationIntervention;
    String heureCreationIntervention;
    String observations;

    @SuppressLint("WrongViewCast")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_intervention);

        /*btAccueilCreerIntervention = (Button) findViewById(R.id.btAccueilCreerIntervention);
        btAccueilCreerIntervention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(CreationInterventionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });*/
    }

    public void gererCreation(){
        idArbre = Integer.parseInt(getIntent().getStringExtra("selected-item"));

        ptTypeIntervention = (EditText) findViewById(R.id.ptTypeIntervention);
        ptDateCreationIntervention = (EditText) findViewById(R.id.ptDateCreationIntervention);
        ptHeureCreationIntervention = (EditText) findViewById(R.id.ptHeureCreationIntervention);
        mtObsCreationIntervention = (EditText) findViewById(R.id.mtObsCreationIntervention);


        //METTRE CA DANS UN ONCLICK LISTENER
        typeIntervention = ptTypeIntervention.getText().toString();
        dateCreationIntervention = ptDateCreationIntervention.getText().toString();
        heureCreationIntervention = ptHeureCreationIntervention.getText().toString();
        observations = mtObsCreationIntervention.getText().toString();

        Intervention uneIntervention = new Intervention(idArbre,dateCreationIntervention, heureCreationIntervention, observations);

        InterventionDAO uneInterventionDAO = new InterventionDAO(getApplicationContext());

        uneInterventionDAO.create(uneIntervention);
    }
}
