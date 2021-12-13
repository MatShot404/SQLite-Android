package com.example.questionnaire2;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
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

public class Question4 extends AppCompatActivity {
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
	public String[] lesCouleurs = {"Blanc", "Bleu", "Jaune", "Noir", "Rouge", "Vert"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question4);
		
		/* S�lection des vues de l'activit� */
		lstProposition = (ListView)findViewById(R.id.listProposition);
		btnVerifier = (Button)findViewById(R.id.boutonVerif);
		btnSuivante = (Button)findViewById(R.id.boutonSuiv);
		txtMessage = (TextView)findViewById(R.id.message);
		
		/*Remplissage de la ListView */
		lstProposition.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, lesCouleurs));
		
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
					Toast.makeText(Question4.this, Integer.toString(score), Toast.LENGTH_SHORT).show();
					Intent resultat = new Intent(Question4.this, Resultats.class);
					resultat.putExtra("NOM", nom );
					resultat.putExtra("SCORE", Integer.toString(score) );
					resultat.putStringArrayListExtra("REPONSES", lesReponses);
					startActivity(resultat);
					break;	
		    	}
	      }
	};
	
	private boolean VerifReponse()
	{
		boolean bonneRep = false;
		lesReponses = i.getStringArrayListExtra("REPONSES");
		score = Integer.parseInt(i.getStringExtra("SCORE"));
		String choix = "";
    	SparseBooleanArray lesChoix = lstProposition.getCheckedItemPositions();
    	for(int i=0;i<lesChoix.size();i++)
        {
           	if (lesChoix.valueAt(i)) {
           		choix = choix + Integer.toString(lesChoix.keyAt(i));
           	}
        }
    	if (choix.equals("235")) {
			message = "Bonne r�ponse " + nom.toUpperCase();
			
			score++;
			if (lesReponses.size()==3)
				lesReponses.add(3, "Bonne r�ponse");
			else
				lesReponses.set(3, "Bonne r�ponse");
			bonneRep = true;
		}
		else {
			message = "Mauvaise r�ponse " + nom.toUpperCase() + "\nLe drapeau est Jaune-Noir-Vert";
			if (lesReponses.size()==3)
				lesReponses.add(3, "Mauvaise r�ponse");
			else
				lesReponses.set(3, "Mauvaise r�ponse");
		}
		return bonneRep;
			
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question4, menu);
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
