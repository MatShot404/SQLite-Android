package com.example.questionnaire2;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class Question1 extends AppCompatActivity {
	// D�claration des variables 
	public RadioGroup grpReponse = null;
	public TextView txtMessage = null;
	public Button btnVerifier = null;
	public Button btnSuivante = null;
	public String nom = "";
	public int score;
	public String message;
	public Intent i ;
	public ArrayList<String> lesReponses;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question1);
		
		/* S�lection des vues de l'activit� */
		grpReponse = (RadioGroup)findViewById(R.id.grpReponse);
		btnVerifier = (Button)findViewById(R.id.boutonVerif);
		btnSuivante = (Button)findViewById(R.id.boutonSuiv);
		txtMessage = (TextView)findViewById(R.id.message);
		
		// On r�cup�re l'intent qui a lanc� cette activit�
	    i = getIntent();
	    // Puis on r�cup�re le nom saisi dans l'autre activit�
	    nom = i.getStringExtra("NOM");
	    
		/*Ecouteurs sur les boutons de l'activit�*/
		btnVerifier.setOnClickListener(EcouteurBouton);
		btnSuivante.setOnClickListener(EcouteurBouton);
	}

	private OnClickListener EcouteurBouton = new OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	//String message = "";
	    	// quel bouton a �t� cliqu� ?
	    	boolean BR;
	    	switch(v.getId()) {
				case R.id.boutonVerif :
					
					//if (grpReponse.getCheckedRadioButtonId() == R.id.rdbFaux) {
					//	message = "Bonne r�ponse " + nom.toUpperCase() + "\nLa capitale est Tokyo";
					//	score++;
					//}
					//else {
					//	message = "Mauvaise r�ponse " + nom.toUpperCase() + "\nLa capitale est Tokyo";
					//}
					txtMessage.setText("");
					BR = VerifReponse();
					txtMessage.setText(message);
					break;
				case R.id.boutonSuiv :
					BR = VerifReponse();
					Toast.makeText(Question1.this, Integer.toString(score), Toast.LENGTH_SHORT).show();
					Intent question2 = new Intent(Question1.this, Question2.class);
					question2.putExtra("NOM", nom );
					question2.putExtra("SCORE", Integer.toString(score) );
					question2.putStringArrayListExtra("REPONSES", lesReponses);
					startActivity(question2);
					break;	
		    	}
	      }
	};
	private boolean VerifReponse()
	{
		boolean bonneRep = false;
		lesReponses = i.getStringArrayListExtra("REPONSES");
		score = Integer.parseInt(i.getStringExtra("SCORE"));
		if (grpReponse.getCheckedRadioButtonId() == R.id.rdbFaux) {
			message = "Bonne r�ponse " + nom.toUpperCase() + "\nLa capitale est Tokyo";
			
			score++;
			if (lesReponses.size()==0)
				lesReponses.add(0, "Bonne r�ponse");
			else
				lesReponses.set(0, "Bonne r�ponse");
			bonneRep = true;
		}
		else {
			message = "Mauvaise r�ponse " + nom.toUpperCase() + "\nLa capitale est Tokyo";
			if (lesReponses.size()==0)
				lesReponses.add(0, "Mauvaise r�ponse");
			else
				lesReponses.set(0, "Mauvaise r�ponse");
		}
		return bonneRep;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question1, menu);
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
