package com.example.questionnaire2;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/* Inclusion des packages n�cessaires*/
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.view.Gravity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import net.sqlcipher.database.SQLiteDatabase;

public class Accueil extends AppCompatActivity {
	// D�claration des variables  
	public EditText editNom = null;
	public Button btnOK = null;
	public TextView txtMessage = null;
	private DBHandler dbHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SQLiteDatabase.loadLibs(this);
		setContentView(R.layout.activity_accueil);

		/* S�lection des vues de l'activit� */
		editNom = (EditText)findViewById(R.id.editNom);
		btnOK = (Button)findViewById(R.id.boutonOK);
		txtMessage = (TextView)findViewById(R.id.message);

		/*Ecouteur sur le bouton OK*/
		btnOK.setOnClickListener(EcouteurBouton);

		/* Création objet DBHandler et attribution du contexte*/
		dbHandler = new DBHandler(Accueil.this);

		// below line is to add on click listener for our add course button.
		btnOK.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// below line is to get data from all edit text fields.
				String userName = editNom.getText().toString();

				// validating if the text fields are empty or not.
				if (userName.isEmpty()) {
					Toast.makeText(Accueil.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
					return;
				}

				// on below line we are calling a method to add new
				// course to sqlite data and pass all our values to it.
				dbHandler.addNewParticipant(userName, 0);

				// after adding the data we are displaying a toast message.
				Toast.makeText(Accueil.this, "Participant ajouté.", Toast.LENGTH_SHORT).show();
				editNom.setText("");
			}
		});
	}
	
	private OnClickListener EcouteurBouton = new OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	txtMessage.setText("");
	    	if (editNom.getText().length()>0) {
	    		/*String message = getString(R.string.debutMessage) + " " + editNom.getText() + "\n" + getString(R.string.finMessage);
		    	//String message = "Bienvenue, " + editNom.getText() + "\nCommencez le Quizz !";
				txtMessage.setText(message);*/
	    		Intent question1 = new Intent(Accueil.this, Question1.class);
	    		// On rajoute un extra
                question1.putExtra("NOM", editNom.getText().toString());
                question1.putExtra("SCORE", "0");
                question1.putStringArrayListExtra("REPONSES", new ArrayList<String>());
	            startActivity(question1);
	    	}
	    	else {
		    	AlertDialog.Builder boite = new AlertDialog.Builder(Accueil.this);
	    		boite.setTitle("Information");
	    		boite.setMessage("Vous n'avez pas saisi votre nom !");
	    		boite.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						editNom.requestFocus();
					}
	    		});
				boite.show();
	    	}
	    }
	};

	private OnClickListener EcouteurBoutonToast = new OnClickListener() {
	    @Override
	    public void onClick(View v) {
			String message = "Bienvenue, " + editNom.getText() + "\nCommencez le Quizz !";
			Toast msg = Toast.makeText(Accueil.this, message, Toast.LENGTH_LONG);
			msg.setGravity(Gravity.CENTER, 0, 0);
			msg.show();
	    }
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accueil, menu);
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
