package mclerrani.ikaizen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ian on 2/18/2016.
 *
 * @author Ian McLerran
 * @version 2/19/2016
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "kaizendb";
    public static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     *
     * @param db - a SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;

        // Create the Kaizen table
        sql =   "CREATE TABLE Kaizen(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "owner TEXT, dept TEXT, dateCreated INTEGER, " +
                "dateModified INTEGER, problemStatement TEXT, " +
                "overProductionWaste TEXT, transportationWaste TEXT, " +
                "motionWaste TEXT, waitingWaste TEXT, " +
                "processingWaste TEXT, inventoryWaste TEXT, " +
                "defectsWaste TEXT, rootCauses TEXT, totalWaste INTEGER" +
                ")";
        db.execSQL(sql);

        // Create the ImageFiles table
        sql =   "CREATE TABLE ImageFiles(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "kaizenId INTEGER FOREIGN KEY NOT NULL, " +
                "imagePath TEXT" +
                ")";
        db.execSQL(sql);

        // Create the Solution table
        sql =   "CREATE TABLE Solution(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "kaizenId FOREIGN KEY NOT NULL, " +
                "todaysFix TEXT, cutMuri INTEGER, threeXBetter INTEGER, " +
                "truePull INTEGER, estimatedSavings REAL, " +
                "dateSolved INTEGER, dateSolutionUpdated INTEGER, " +
                "signedOffBy TEXT, solvedEmote INTEGER" +
                ")";
        db.execSQL(sql);

        // Create the Countermeasure table
        sql =   "CREATE TABLE Countermeasure(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "solutionId INTEGER FOREIGN KEY NOT NULL, " +
                "dateModified INTEGER, preventativeAction TEXT, " +
                "cutMuri INTEGER, threeXBetter INTEGER, " +
                "truePull INTEGER, costToImplement REAL, " +
                "implemented INTEGER, dateWalkedOn TEXT" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
