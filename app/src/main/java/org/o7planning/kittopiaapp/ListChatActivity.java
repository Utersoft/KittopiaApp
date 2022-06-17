package org.o7planning.kittopiaapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ListChatActivity extends AppCompatActivity {

    private DataBaseManager db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_cat);

        //db = new DataBaseManager(this);

        //String strSql = "select * from Chats where Chats.ID_Client like Client.ID_Client;";

        //Cursor cursor = db.getReadableDatabase().rawQuery(strSql, null);


    }
}
