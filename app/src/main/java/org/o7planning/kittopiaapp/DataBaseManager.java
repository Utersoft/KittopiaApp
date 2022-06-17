package org.o7planning.kittopiaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataBaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Joueurs.db";
    private static final int DATABASE_VERSION = 1;


    public DataBaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSqlClient = "create table if not exists Client(" +
                "ID_Client integer primary key autoincrement," +
                "Pseudo text not null," +
                "Mpd text not null," +
                "D_Naissance text not null," +
                "Email text not null," +
                "D_Creation TEXT not null" +
                ");";

        String strSqlChats = "create table if not exists Chats(" +
                "ID_Chat integer primary key autoincrement, " +
                "ID_Client integer not null, " +
                "Nom text not null, " +
                "Valeur_Actu numeric, " +
                "foreign key (ID_Client)  references Client(ID_Client) " +
                ");";

        String strSqlValeurIni = "create table if not exists Valeur_Ini(" +
                "ID_ValeurIni integer primary key autoincrement," +
                "ID_Chat integer not null references Chats(ID_Chat)," +
                "Prix_euro numeric," +
                "Prix_bitcoin integer," +
                "Prix_etherium numeric" +
                ");";

        String strSqlEmplacement = "create table if not exists Emplacement(" +
                "ID_Emplacement integer primary key autoincrement," +
                "Libelle_Emplacement text not null" +
                ");";

        String strSqlCara = "create table if not exists Caracteristique(" +
                "ID_Cara integer primary key autoincrement," +
                "Libelle_Cara text not null," +
                "Type_Cara text not null," +
                "Quantite integer," +
                "Chance numeric" +
                ");";

        String strSqlPossedeCara = "create table if not exists Possede_Cara(" +
                "ID_Chat integer not null, " +
                "ID_Cara integer not null, " +
                "foreign key (ID_Chat)  references Chats(ID_Chat), " +
                "foreign key (ID_Cara) references Caracteristique(ID_Cara), " +
                "primary key (ID_Chat, ID_Cara));";

        String strSqlObjet = "create table if not exists Objet(" +
                "ID_Objet integer primary key autoincrement, " +
                "Libelle_Objet text not null," +
                "Type text not null, " +
                " Description text not null, " +
                "Categorie text" +
                ");";

        String strSqlPorte = "create table if not exists Porte(" +
                "ID_Chat integer not null," +
                "ID_Emplacement integer not null," +
                "ID_Objet integer not null, " +
                "foreign key (ID_Chat)  references Chats(ID_Chat), " +
                "foreign key (ID_Emplacement) references Emplacement(ID_Emplacement), " +
                "foreign key (ID_Objet) references Objet(ID_Objet), " +
                "primary key (ID_Chat, ID_Emplacement, ID_Objet));";

        String strSqlInventaire = "create table if not exists Inventaire(" +
                "ID_Client integer not null," +
                "ID_Objet integer not null," +
                "Quantite integer not null, " +
                "foreign key (ID_Client)  references Client(ID_Client), " +
                "foreign key (ID_Objet) references Objet(ID_Objet), " +
                "primary key (ID_Client, ID_Objet));";

        String strSqlTache = "create table if not exists Tache(" +
                "ID_Tache integer primary key autoincrement," +
                "Libelle_Tache text not null" +
                ");";

        String strSqlStatut = "create table if not exists Statut(" +
                "ID_Tache integer not null," +
                "ID_Chat integer not null," +
                "Effectuee numeric not null, " +
                "foreign key (ID_Chat)  references Chats(ID_Chat), " +
                "foreign key (ID_Tache) references Tache(ID_Tache), " +
                "primary key (ID_Tache, ID_Chat));";

        String strSqlImage = "create table if not exists Image (" +
                "ID_Image integer primary key autoincrement," +
                "ID_Chat integer not null references Chats(ID_Chat)," +
                "Chemin_Image text not null" +
                ");";

        db.execSQL(strSqlClient);
        db.execSQL(strSqlChats);
        db.execSQL(strSqlValeurIni);
        db.execSQL(strSqlEmplacement);
        db.execSQL(strSqlCara);
        db.execSQL(strSqlPossedeCara);
        db.execSQL(strSqlObjet);
        db.execSQL(strSqlPorte);
        db.execSQL(strSqlInventaire);
        db.execSQL(strSqlTache);
        db.execSQL(strSqlStatut);
        db.execSQL(strSqlImage);

        insertObject(db);
        insertPlacement(db);
        insertCharacteristics(db);
        insertTask(db);


        Log.i("DATABASE", "onCreate invoked");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void insertClient(String pseudo, String mdp, String d_naissance, String email){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());


        String strSql = "insert into Client values (null, '"
                + pseudo + "', '" + mdp + "', '" + d_naissance + "', '" + email + "', '" + date
                + "');";

        this.getWritableDatabase().execSQL(strSql);
        Log.i("DATABASE", "insertClient invoked");
    }

    public void insertObject(SQLiteDatabase db){
        String strSql = "Insert into Objet values\n" +
                "\t(null, 'Croquettes', 'Nourriture', 'Croquettes standard pour chatton.', null),\n" +
                "\t(null, 'Lait', 'Nourriture', 'Lait standard pour chatton.', null),\n" +
                "\t(null, 'Biberon', 'Cle', 'Biberon pour donner du lait aux chattons.', null),\n" +
                "\t(null, 'Bol de Croquette', 'Cle', 'Permet de poser les croquettes pour les chattons.', null),\n" +
                "\t(null, 'Cat''shing', 'Argent', 'Monnaie de Catopia.', null),\n" +
                "\t(null, 'Catagochi', 'Argent', 'Nombre de NFT différent posséder.', null),\n" +
                "\t(null, 'Chatpeau', 'Accessoire', 'Chapeau en forme de chat pour chatton.', 'Tete'),\n" +
                "\t(null, 'CatBody', 'Accessoire', 'Body avec un chat dessus pour chatton.', 'Corps'),\n" +
                "\t(null, 'Collier Ron Ron', 'Accessoire', 'Collier spécialement fait pour les chattons.', 'Cou');";

        db.execSQL(strSql);
        Log.i("DATABASE", "insertObject invoked");
    }

    public void insertPlacement(SQLiteDatabase db){
        String strSql = "Insert into Emplacement values\n" +
                "\t(null, 'Tete'),\n" +
                "\t(null, 'Cou'),\n" +
                "\t(null, 'Corps');";

        db.execSQL(strSql);
        Log.i("DATABASE", "insertPlacement invoked");
    }

    public void insertTask(SQLiteDatabase db){
        String strSql = "Insert into Tache values\n" +
                "\t(null, 'Brossage'),\n" +
                "\t(null, 'Caresses'),\n" +
                "\t(null, 'Nourriture'),\n" +
                "\t(null, 'Manucure'),\n" +
                "\t(null, 'Bain'),\n" +
                "\t(null, 'Jeu');";

        db.execSQL(strSql);
        Log.i("DATABASE", "insertTask invoked");
    }

    public void insertCharacteristics(SQLiteDatabase db){
        String strSql = "Insert into Caracteristique values\n" +
                "\t(null, 'Gris', 'Couleur', null, 17),\n" +
                "\t(null, 'Blanc', 'Couleur', null, 14),\n" +
                "\t(null, 'Noir', 'Couleur', null, 17),\n" +
                "\t(null, 'Roux', 'Couleur', null, 12),\n" +
                "\t(null, 'Orange', 'Couleur', null, 12),\n" +
                "\t(null, 'Brun', 'Couleur', null, 14),\n" +
                "\t(null, 'Violet', 'Couleur', 1000, 6),\n" +
                "\t(null, 'Bleu', 'Couleur', 1000, 6),\n" +
                "\t(null, 'Rainbow', 'Couleur', 4, 1),\n" +
                "\t(null, 'Ocean', 'Couleur', 4, 1),\n" +
                "\t(null, 'Unicolore', 'Patron', null, 30),\n" +
                "\t(null, 'Bicolore', 'Patron', null, 15),\n" +
                "\t(null, 'Tricolore', 'Patron', 10, 6),\n" +
                "\t(null, 'Tabby', 'Patron', null, 10),\n" +
                "\t(null, 'Colourpoint', 'Patron', null, 18),\n" +
                "\t(null, 'Mink', 'Patron', null, 12),\n" +
                "\t(null, 'Sepia', 'Patron', null, 9),\n" +
                "\t(null, 'Main Coon', 'Race', null, 8),\n" +
                "\t(null, 'Persan', 'Race', null, 6),\n" +
                "\t(null, 'Bengal', 'Race', 1000, 4),\n" +
                "\t(null, 'Siamois', 'Race', 1000, 4),\n" +
                "\t(null, 'Lynx', 'Race', 300, 2),\n" +
                "\t(null, 'Sphynx', 'Race', 1000, 4),\n" +
                "\t(null, 'Munchkin', 'Race', 1000, 4),\n" +
                "\t(null, 'Européen', 'Race', null, 50),\n" +
                "\t(null, 'Chartreux', 'Race', 1000, 4),\n" +
                "\t(null, 'Cymric', 'Race', 300, 2.4),\n" +
                "\t(null, 'Kurilian Bobtail', 'Race', 300, 2.49),\n" +
                "\t(null, 'Ashera', 'Race', 50, 1),\n" +
                "\t(null, 'Savannah', 'Race', 500, 3),\n" +
                "\t(null, 'Tonkinois', 'Race', 50, 1),\n" +
                "\t(null, 'Anatolie', 'Race', 500, 3),\n" +
                "\t(null, 'Lionceau', 'Race', 2, 0.05),\n" +
                "\t(null, 'Trigreau', 'Race', 2, 0.05),\n" +
                "\t(null, 'Khao Manee', 'Race', 1, 0.01);";

        db.execSQL(strSql);
        Log.i("DATABASE", "insertCharacteristics invoked");
    }

    public void insertCats(int playerID, String name, float currentValue){
        String strSql = "insert into Chats values(null, "
                + playerID + ", '"
                + name + "', "
                + currentValue + ");" ;

        this.getWritableDatabase().execSQL(strSql);
        Log.i("DATABASE", "insertCats invoked");
    }

    public void insertImage(int catID, String imageURL){
        String strSql = "insert into Image values(null, "
                + catID + ", '"
                + imageURL + "');";

        this.getWritableDatabase().execSQL(strSql);
        Log.i("DATABASE", "insertImage invoked");
    }

    public void insertInventory(int playerID, int objectID, int quantity){
        String strSql = "insert into Inventaire values("
                + playerID + ", "
                + objectID + ", "
                + quantity + ");";
        this.getWritableDatabase().execSQL(strSql);
        Log.i("DATABASE", "insertInventory invoked");
    }

    public void insertWear(int catID, int placementID, int objectID){
        String strSql = "insert into Porte values("
                + catID + ", "
                + placementID + ", "
                + objectID + ");";
        this.getWritableDatabase().execSQL(strSql);
        Log.i("DATABASE", "insertWear invoked");
    }

    public void insertStatus(int taskID, int catID, boolean done){
        String strSql = "insert into Tache values("
                + taskID + ", "
                + catID + ", "
                + done + ");";

        this.getWritableDatabase().execSQL(strSql);
        Log.i("DATABASE", "insertStatus invoked");
    }

    public void insertStartPrice(int catID, float euroPrice, int bitcoinPrice, float etheriumPrice){
        String strSql = "insert into Valeur_Ini(null, "
                + catID + ", "
                + euroPrice + ", "
                + bitcoinPrice + ", "
                + etheriumPrice + ");";
        this.getWritableDatabase().execSQL(strSql);
        Log.i("DATABASE", "insertStartPrice invoked");
    }

    public void insertHasCharacteristics(int catID, int characteristicsID){
        String strSql = "insert into Caracteristique values("
                + catID + ", "
                + characteristicsID + ");";
        this.getWritableDatabase().execSQL(strSql);
        Log.i("DATABASE", "insertHasCharacteristics invoked");
    }
}