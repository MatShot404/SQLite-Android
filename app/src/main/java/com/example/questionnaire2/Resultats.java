package com.example.questionnaire2;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Toast;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Resultats extends AppCompatActivity {
	// D�claration des variables 
	public TextView txtTitre = null;
	public TextView txtScore = null;
	public CheckBox chkVoir = null;
	public ListView lstReponses = null;
	public String nom ="";
	public int score;
	public ArrayList<String> lesReponses;
	public ArrayList<HashMap<String, String>> contenuListe = new ArrayList<HashMap<String, String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resultats);
		
		/* S�lection des vues de l'activit� */
		txtTitre = (TextView)findViewById(R.id.titre);
		txtScore = (TextView)findViewById(R.id.score);
		chkVoir = (CheckBox)findViewById(R.id.voirReponses);
		lstReponses = (ListView)findViewById(R.id.listResponses);
		// R�cup�ration du nom du joueur
	    Intent i = getIntent();
	    nom = i.getStringExtra("NOM");
	    score = Integer.parseInt(i.getStringExtra("SCORE"));
	    lesReponses = i.getStringArrayListExtra("REPONSES");
	    Toast.makeText(Resultats.this, Integer.toString(score), Toast.LENGTH_SHORT).show();
	    /* Remplissage des zones */
		txtTitre.setText(nom.toUpperCase() + ", vous avez termin� le quizz");
		String message="";
		if (score==0) {
			message = "Score : Aucune bonne r�ponse";
		}
		else {
			message = "Score : " + score + " bonnes r�ponses";
		}
		txtScore.setText(message);
		
		/*Remplissage lstReponses*/
	    int j=1;
	    for (String el : lesReponses) {
	        HashMap<String, String> item = new HashMap<String, String>();
	        item.put("question", "Question " + Integer.toString(j));
	        item.put("reponse", lesReponses.get(j-1));
	        contenuListe.add(item);
	        j++;
	    }
	    lstReponses.setAdapter(new SimpleAdapter(this, contenuListe, R.layout.layoutlignelstreponses,
	    		               new String[] {"question", "reponse"}, new int[] {R.id.question, R.id.reponse}));
	    lstReponses.setVisibility(View.INVISIBLE);
		
		// Ecouteur CheckBox
		chkVoir.setOnClickListener(EcouteurChk);
		
	}
	
	private OnClickListener EcouteurChk = new OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	 if (chkVoir.isChecked()) {
	    		 lstReponses.setVisibility(View.VISIBLE);
	    	 }
	    	 else
	    	 {
	    		 lstReponses.setVisibility(View.INVISIBLE);
	    	 }
	      }
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.resultats, menu);
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
