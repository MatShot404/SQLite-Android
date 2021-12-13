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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Question3 extends AppCompatActivity {
	// D�claration des variables 
	public ListView lstProposition = null;
	public TextView txtMessage = null;
	public Button btnVerifier = null;
	public Button btnSuivante = null;
	public String nom ="";
	public int score;
	public String message;
	public Intent i ;
	public ArrayList<String> lesReponses;
	public String[] lesContinents = {"Afrique", "Am�rique", "Asie", "Europe", "Oc�anie"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question3);
		
		/* S�lection des vues de l'activit� */
		lstProposition = (ListView)findViewById(R.id.listProposition);
		btnVerifier = (Button)findViewById(R.id.boutonVerif);
		btnSuivante = (Button)findViewById(R.id.boutonSuiv);
		txtMessage = (TextView)findViewById(R.id.message);
		
		/*Remplissage de la ListView */
		lstProposition.setAdapter(new ArrayAdapter<String>(this, R.layout.row, lesContinents));
		
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
					Toast.makeText(Question3.this, Integer.toString(score), Toast.LENGTH_SHORT).show();
					Intent question4 = new Intent(Question3.this, Question4.class);
					question4.putExtra("NOM", nom );
					question4.putExtra("SCORE", Integer.toString(score) );
					question4.putStringArrayListExtra("REPONSES", lesReponses);
					startActivity(question4);
					break;	
		    	}
	      }
	};
	
	private boolean VerifReponse()
	{
		boolean bonneRep = false;
		lesReponses = i.getStringArrayListExtra("REPONSES");
		score = Integer.parseInt(i.getStringExtra("SCORE"));
		if (lesContinents[lstProposition.getCheckedItemPosition()].equals("Am�rique")) {
			message = "Bonne r�ponse " + nom.toUpperCase();
			
			score++;
			if (lesReponses.size()==2)
				lesReponses.add(2, "Bonne r�ponse");
			else
				lesReponses.set(2, "Bonne r�ponse");
			bonneRep = true;
		}
		else {
			message = "Mauvaise r�ponse " + nom.toUpperCase() + "\nLe continent est l'Am�rique";
			if (lesReponses.size()==2)
				lesReponses.add(2, "Mauvaise r�ponse");
			else
				lesReponses.set(2, "Mauvaise r�ponse");
		}
		return bonneRep;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question3, menu);
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
