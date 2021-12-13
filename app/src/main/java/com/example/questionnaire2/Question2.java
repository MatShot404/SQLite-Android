package com.example.questionnaire2;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Question2 extends AppCompatActivity {
	// D�claration des variables 
	public Spinner lstProposition = null;
	public TextView txtMessage = null;
	public Button btnVerifier = null;
	public Button btnSuivante = null;
	public String nom ="";
	public int score;
	public String message;
	public Intent i ;
	public ArrayList<String> lesReponses;
	public String[] lesVilles = {"Bogota", "Buenos Aires", "Caracas", "Santiago"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question2);
		
		/* S�lection des vues de l'activit� */
		lstProposition = (Spinner)findViewById(R.id.listProposition);
		btnVerifier = (Button)findViewById(R.id.boutonVerif);
		btnSuivante = (Button)findViewById(R.id.boutonSuiv);
		txtMessage = (TextView)findViewById(R.id.message);
		
		/*Remplissage du Spinner */
		lstProposition.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lesVilles));
		
		// R�cup�ration du nom du joueur
	    i = getIntent();
	    nom = i.getStringExtra("NOM");
	    
	    /*Ecouteurs sur les boutons de l'activit�*/
		btnVerifier.setOnClickListener(EcouteurBouton);
		btnSuivante.setOnClickListener(EcouteurBouton);
	}

	private OnClickListener EcouteurBouton = new OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	boolean BR;
	    	switch(v.getId()) {
				case R.id.boutonVerif :
					txtMessage.setText("");
					BR = VerifReponse();
					txtMessage.setText(message);
					break;
				case R.id.boutonSuiv :
					BR = VerifReponse();
					Toast.makeText(Question2.this, Integer.toString(score), Toast.LENGTH_SHORT).show();
					Intent question3 = new Intent(Question2.this, Question3.class);
					question3.putExtra("NOM", nom );
					question3.putExtra("SCORE", Integer.toString(score) );
					question3.putStringArrayListExtra("REPONSES", lesReponses);
					startActivity(question3);
					break;	
		    	}
	      }
	};
	
	private boolean VerifReponse()
	{
		boolean bonneRep = false;
		lesReponses = i.getStringArrayListExtra("REPONSES");
		score = Integer.parseInt(i.getStringExtra("SCORE"));
		if (lstProposition.getSelectedItem().toString().equals("Buenos Aires")) {
			message = "Bonne r�ponse " + nom.toUpperCase();
			
			score++;
			if (lesReponses.size()==1)
				lesReponses.add(1, "Bonne r�ponse");
			else
				lesReponses.set(1, "Bonne r�ponse");
			bonneRep = true;
		}
		else {
			message = "Mauvaise r�ponse " + nom.toUpperCase() + "\nLa capitale est Buenos Aires";
			if (lesReponses.size()==1)
				lesReponses.add(1, "Mauvaise r�ponse");
			else
				lesReponses.set(1, "Mauvaise r�ponse");
		}
		return bonneRep;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question2, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
