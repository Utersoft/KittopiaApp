package org.o7planning.kittopiaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText pseudoText;
    private EditText passwordText;
    private Button acceptButton;
    private DataBaseManager dbManager;
    private String pseudo;
    private String mdp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pseudoText = (EditText) findViewById(R.id.pseudo);
        passwordText = (EditText) findViewById(R.id.password);
        acceptButton = (Button) findViewById(R.id.loginButton);

        pseudo = MyApplication.getPseudo();
        mdp = MyApplication.getMdp();

        if(pseudo != "" && mdp != ""){
            pseudoText.setText(pseudo);
            passwordText.setText(mdp);
        }

        dbManager = new DataBaseManager( this );
        /*dbManager.insertCats(1, "Félix", (float)143.5);
        dbManager.insertCats(1, "Figaro", (float)1300);
        dbManager.insertCats(1, "Cléo", (float)56);
        dbManager.insertCats(1, "Channel", (float)3981.23);
        dbManager.insertCats(1, "Topaze", (float)1830);*/



        //dbManager.insertClient("Utersoft", "1234", "1993-11-04", "s.wilhelm@ludus-academie.com");

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(pseudoText.getText().toString())){
                    pseudoText.setError("Veullez entrez un pseudo.");
                }else if (TextUtils.isEmpty(passwordText.getText().toString())) {
                    passwordText.setError("Veullez entrez un mot de passe..");
                }else{
                    String strSql1 = "select Pseudo from Client where Pseudo like '"+ pseudoText.getText().toString() + "';";
                    String strSql2 = "select Mpd from Client where Pseudo like '" + pseudoText.getText().toString() + "';";

                    Cursor cursor1 = dbManager.getReadableDatabase().rawQuery(strSql1, null);
                    Cursor cursor2 = dbManager.getReadableDatabase().rawQuery(strSql2, null);

                    //cursor1.moveToFirst();
                    //while(!curs)
                    if(cursor1.getCount() > 0 && cursor2.getCount() > 0){
                        cursor1.moveToFirst();
                        cursor2.moveToFirst();
                        MyApplication.setPseudo(cursor1.getString(0));
                        MyApplication.setMdp(cursor2.getString(0));
                        startActivity(new Intent(MainActivity.this, AccueilActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(), "Le pseudo ou le mot de passe est incorrect.", Toast.LENGTH_SHORT).show();
                    }

                    cursor1.close();
                    cursor2.close();
                }
            }
        });


    }
}