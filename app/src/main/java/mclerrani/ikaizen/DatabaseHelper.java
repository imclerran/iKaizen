package mclerrani.ikaizen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.joda.time.DateTime;

import java.util.ArrayList;

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
     * @param db - a SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;

        // Create the Kaizen table
        sql = "CREATE TABLE Kaizen(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "owner TEXT, dept TEXT, dateCreated INTEGER, " +
                "dateModified INTEGER, problemStatement TEXT, " +
                "overProductionWaste TEXT, transportationWaste TEXT, " +
                "motionWaste TEXT, waitingWaste TEXT, " +
                "processingWaste TEXT, inventoryWaste TEXT, " +
                "defectsWaste TEXT, rootCauses TEXT, totalWaste INTEGER" +
                ")";
        db.execSQL(sql);

        // Create the ImageFile table
        sql = "CREATE TABLE ImageFile(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "imagePath TEXT, " +
                "kaizenId INTEGER, FOREIGN KEY(kaizenId) REFERENCES Kaizen(_id) " +
                ")";
        db.execSQL(sql);

        // Create the Solution table
        sql = "CREATE TABLE Solution(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "todaysFix TEXT, cutMuri INTEGER, threeXBetter INTEGER, " +
                "truePull INTEGER, estimatedSavings REAL, " +
                "dateSolved INTEGER, dateSolutionUpdated INTEGER, " +
                "signedOffBy TEXT, solvedEmote INTEGER, " +
                "kaizenId INTEGER, FOREIGN KEY(kaizenId) REFERENCES Kaizen(_id)" +
                ")";
        db.execSQL(sql);

        // Create the Countermeasure table
        sql = "CREATE TABLE Countermeasure(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "dateModified INTEGER, preventativeAction TEXT, " +
                "cutMuri INTEGER, threeXBetter INTEGER, " +
                "truePull INTEGER, costToImplement REAL, " +
                "implemented INTEGER, dateWalkedOn TEXT, " +
                "solutionId INTEGER, FOREIGN KEY(solutionId) REFERENCES Solution(_id)" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public synchronized int insertKaizen(Kaizen k) {
        SQLiteDatabase db = null;
        try {
            int id;
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("owner", k.getOwner());
            values.put("dept", k.getDept());
            values.put("dateCreated", k.getDateCreated().getMillis());
            values.put("dateModified", k.getDateModified().getMillis());
            values.put("problemStatement", k.getProblemStatement());
            values.put("overProductionWaste", k.getOverProductionWaste());
            values.put("transportationWaste", k.getTransportationWaste());
            values.put("motionWaste", k.getMotionWaste());
            values.put("waitingWaste", k.getWaitingWaste());
            values.put("processingWaste", k.getProcessingWaste());
            values.put("inventoryWaste", k.getInventoryWaste());
            values.put("defectsWaste", k.getDefectsWaste());
            values.put("rootCauses", k.getRootCauses());
            values.put("totalWaste", k.getTotalWaste());

            id = (int) db.insert("Kaizen", null, values);
            return id;
        } catch (Exception e) {
            return -1;
        } finally {
            if (null != db && db.isOpen()) {
                db.close();
            }
        }
    }

    public synchronized int updateKaizen(Kaizen k) {
        SQLiteDatabase db = null;
        try {
            int num;
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("owner", k.getOwner());
            values.put("dept", k.getDept());
            values.put("dateCreated", k.getDateCreated().getMillis());
            values.put("dateModified", k.getDateModified().getMillis());
            values.put("problemStatement", k.getProblemStatement());
            values.put("overProductionWaste", k.getOverProductionWaste());
            values.put("transportationWaste", k.getTransportationWaste());
            values.put("motionWaste", k.getMotionWaste());
            values.put("waitingWaste", k.getWaitingWaste());
            values.put("processingWaste", k.getProcessingWaste());
            values.put("inventoryWaste", k.getInventoryWaste());
            values.put("defectsWaste", k.getDefectsWaste());
            values.put("rootCauses", k.getRootCauses());
            values.put("totalWaste", k.getTotalWaste());

            num = (int) db.update("Kaizen", values, "_id = ?",
                    new String[]{String.valueOf(k.getItemID())});
            return num;
        } catch (Exception e) {
            return 0;
        } finally {
            if (null != db && db.isOpen()) {
                db.close();
            }
        }
    }

    public synchronized int deleteKaizen(Kaizen k) {
        SQLiteDatabase db = null;
        try {
            int num;
            db = this.getWritableDatabase();
            num = db.delete("Kaizen", "_id = ?",
                    new String[]{String.valueOf(k.getItemID())});
            return num;
        } catch (Exception e) {
            return 0;
        } finally {
            if (null != db && db.isOpen()) {
                db.close();
            }
        }
    }

    public synchronized int insertSolution(Solution s, int parentId) {
        SQLiteDatabase db = null;
        try {
            int id;
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("kaizenId", parentId);
            values.put("todaysFix", s.getTodaysFix());
            values.put("cutMuri", s.isCutMuri());
            values.put("threeXBetter", s.isThreeXBetter());
            values.put("truePull", s.isTruePull());
            values.put("estimatedSavings", s.getEstimatedSavings());
            if(null != s.getDateSolved())
                values.put("dateSolved", s.getDateSolved().getMillis());
            else
                values.putNull("dateSolved");
            if(null != s.getDateSolutionUpdated())
                values.put("dateSolutionUpdated", s.getDateSolutionUpdated().getMillis());
            else
                values.putNull("dateSolutionUpdated");
            values.put("signedOffBy", s.getSignedOffBy());
            values.put("solvedEmote", s.getSolvedEmote());

            id = (int) db.insert("Solution", null, values);
            return id;
        } catch (Exception e) {
            return -1;
        } finally {
            if (null != db && db.isOpen()) {
                db.close();
            }
        }
    }

    public synchronized int updateSolution(Solution s, int parentId) {
        SQLiteDatabase db = null;
        try {
            int num;
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("kaizenId", parentId);
            values.put("todaysFix", s.getTodaysFix());
            values.put("cutMuri", s.isCutMuri());
            values.put("threeXBetter", s.isThreeXBetter());
            values.put("truePull", s.isTruePull());
            values.put("estimatedSavings", s.getEstimatedSavings());
            if(null != s.getDateSolved())
                values.put("dateSolved", s.getDateSolved().getMillis());
            else
                values.putNull("dateSolved");
            if(null != s.getDateSolutionUpdated())
                values.put("dateSolutionUpdated", s.getDateSolutionUpdated().getMillis());
            else
                values.putNull("dateSolutionUpdated");
            values.put("signedOffBy", s.getSignedOffBy());
            values.put("solvedEmote", s.getSolvedEmote());

            num = (int) db.update("Solution", values, "_id = ?",
                    new String[]{String.valueOf(s.getItemID())});
            return num;
        } catch (Exception e) {
            return 0;
        } finally {
            if (null != db && db.isOpen()) {
                db.close();
            }
        }
    }

    public synchronized int deleteSolution(Solution s) {
        SQLiteDatabase db = null;
        try {
            int num;
            db = this.getWritableDatabase();
            num = db.delete("Solution", "_id = ?",
                    new String[]{String.valueOf(s.getItemID())});
            return num;
        } catch (Exception e) {
            return 0;
        } finally {
            if (null != db && db.isOpen()) {
                db.close();
            }
        }
    }

    public synchronized int insertImageFile(ImageFile f, int parentId) {
        SQLiteDatabase db = null;
        try {
            int id;
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("imagePath", f.getPath());
            values.put("kaizenId", parentId);

            id = (int) db.insert("ImageFile", null, values);
            return id;
        } catch (Exception e) {
            return -1;
        } finally {
            if (null != db && db.isOpen()) {
                db.close();
            }
        }
    }

    public synchronized int updateImageFile(ImageFile f, int parentId) {
        SQLiteDatabase db = null;
        try {
            int num;
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("kaizenId", parentId);
            values.put("imagePath", f.getPath());

            num = db.update("Solution", values, "_id = ?",
                    new String[]{String.valueOf(f.getItemID())});
            return num;
        } catch (Exception e) {
            return 0;
        } finally {
            if (null != db && db.isOpen()) {
                db.close();
            }
        }
    }

    public synchronized int deleteImageFile(ImageFile f) {
        SQLiteDatabase db = null;
        try {
            int num;
            db = this.getWritableDatabase();
            num = db.delete("ImageFile", "_id = ?",
                    new String[]{String.valueOf(f.getItemID())});
            return num;
        } catch (Exception e) {
            return 0;
        } finally {
            if (null != db && db.isOpen()) {
                db.close();
            }
        }
    }

    public synchronized int insertCountermeasure(Countermeasure cm, int parentId) {
        SQLiteDatabase db = null;
        try {
            int id;
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("solutionId", parentId);
            values.put("dateModified", cm.getDateModified().getMillis());
            values.put("preventativeAction", cm.getPreventativeAction());
            values.put("cutMuri", cm.isCutMuri());
            values.put("threeXBetter", cm.isThreeXBetter());
            values.put("truePull", cm.isTruePull());
            values.put("costToImplement", cm.getCostToImplement());
            values.put("implemented", cm.isImplemented());
            values.put("dateWalkedOn", cm.getDateWalkedOn());

            id = (int) db.insert("Countermeasure", null, values);
            return id;
        } catch (Exception e) {
            return -1;
        } finally {
            if (null != db && db.isOpen()) {
                db.close();
            }
        }
    }

    public synchronized int updateCountermeasure(Countermeasure cm, int parentId) {
        SQLiteDatabase db = null;
        try {
            int num;
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("solutionId", parentId);
            values.put("dateModified", cm.getDateModified().getMillis());
            values.put("preventativeAction", cm.getPreventativeAction());
            values.put("cutMuri", cm.isCutMuri());
            values.put("threeXBetter", cm.isThreeXBetter());
            values.put("truePull", cm.isTruePull());
            values.put("costToImplement", cm.getCostToImplement());
            values.put("implemented", cm.isImplemented());
            values.put("dateWalkedOn", cm.getDateWalkedOn());

            num = (int) db.update("Countermeasure", values, "_id = ?",
                    new String[]{String.valueOf(cm.getItemID())});
            return num;
        } catch (Exception e) {
            return 0;
        } finally {
            if (null != db && db.isOpen()) {
                db.close();
            }
        }
    }

    public synchronized int deleteCountermeasure(Countermeasure cm) {
        SQLiteDatabase db = null;
        try {
            int num;
            db = this.getWritableDatabase();
            // TODO: find better fix. VERY BAD SOLUTION!
            ContentValues values = new ContentValues();
            values.putNull("solutionId");
            db.update("Countermeasure", values, "_id = ?",
                    new String[]{String.valueOf(cm.getItemID())});
            // End very bad solution

            num = db.delete("Countermeasure", "_id = ?",
                    new String[]{String.valueOf(cm.getItemID())});
            return num;
        } catch (Exception e) {
            return 0;
        } finally {
            if (null != db && db.isOpen()) {
                db.close();
            }
        }
    }

    public synchronized boolean populateLists(ArrayList<Kaizen> kaizenList) {
        SQLiteDatabase db = null;
        Cursor kResults = null;
        Cursor sResults = null;
        Cursor iResults = null;
        Cursor cmResults = null;

        try {
            db = this.getReadableDatabase();
            kResults = db.rawQuery("SELECT * FROM Kaizen", null);
            if(kResults.moveToFirst()) {
                do {
                    Kaizen k = createKaizen(kResults);
                    kaizenList.add(k);

                    iResults = db.rawQuery("SELECT * FROM ImageFile WHERE kaizenId = ?",
                            new String[]{String.valueOf(k.getItemID())});
                    if(iResults.moveToFirst()) {
                        do {
                            ImageFile i = createImageFile(iResults);
                            k.addImageFile(i);
                        } while (iResults.moveToNext());
                    }

                    sResults = db.rawQuery("SELECT * FROM Solution WHERE kaizenId = ?",
                            new String[]{String.valueOf(k.getItemID())});
                    if(sResults.moveToFirst()) {
                        Solution s = createSolution(sResults);
                        k.setSolution(s);

                        cmResults = db.rawQuery("SELECT * FROM Countermeasure WHERE solutionId = ?",
                                new String[]{String.valueOf(s.getItemID())});
                        if(cmResults.moveToFirst()) {
                            do {
                                Countermeasure cm = createCountermeasure(cmResults);
                                s.getPossibleCounterMeasures().add(cm);
                            } while (cmResults.moveToNext());
                        }
                    }

                } while (kResults.moveToNext());
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if(null != kResults && !kResults.isClosed())
                kResults.close();
            if(null != db && db.isOpen())
                db.close();
        }
    }

    public Kaizen createKaizen(Cursor results) {
        int id = (int) results.getLong(results.getColumnIndex("_id"));
        String owner = results.getString(results.getColumnIndex("owner"));
        String dept = results.getString(results.getColumnIndex("dept"));
        DateTime dateCreated = new DateTime(results.getLong(results.getColumnIndex("dateCreated")));
        DateTime dateModified = new DateTime(results.getLong(results.getColumnIndex("dateModified")));
        String problemStatement = results.getString(results.getColumnIndex("problemStatement"));
        String overProductionWaste = results.getString(results.getColumnIndex("overProductionWaste"));
        String transportationWaste = results.getString(results.getColumnIndex("transportationWaste"));
        String motionWaste = results.getString(results.getColumnIndex("motionWaste"));
        String waitingWaste = results.getString(results.getColumnIndex("waitingWaste"));
        String processingWaste = results.getString(results.getColumnIndex("processingWaste"));
        String inventoryWaste = results.getString(results.getColumnIndex("inventoryWaste"));
        String defectsWaste = results.getString(results.getColumnIndex("defectsWaste"));
        String rootCauses = results.getString(results.getColumnIndex("rootCauses"));
        int totalWaste = results.getInt(results.getColumnIndex("totalWaste"));

        Kaizen k = new Kaizen(owner, dept, dateCreated, dateModified, problemStatement, overProductionWaste, transportationWaste, motionWaste,
                waitingWaste, processingWaste, inventoryWaste, defectsWaste, rootCauses, totalWaste);
        k.setItemID(id);

        return k;
    }

    public Solution createSolution(Cursor results) {
        int id = (int) results.getLong(results.getColumnIndex("_id"));
        String todaysFix = results.getString(results.getColumnIndex("todaysFix"));
        boolean cutMuri = results.getInt(results.getColumnIndex("cutMuri")) > 0;
        boolean threeXBetter = results.getInt(results.getColumnIndex("threeXBetter")) > 0;
        boolean truePull = results.getInt(results.getColumnIndex("truePull")) > 0;
        float estimatedSavings = results.getFloat(results.getColumnIndex("estimatedSavings"));
        long dateSolvedEpoch = results.getLong(results.getColumnIndex("dateSolved"));
        DateTime dateSolved;
        if(0 != dateSolvedEpoch) dateSolved = new DateTime();
        else dateSolved = null;
        DateTime dateSolutionUpdated = new DateTime(results.getLong(results.getColumnIndex("dateSolutionUpdated")));
        String signedOffBy = results.getString(results.getColumnIndex("signedOffBy"));
        int solvedEmote = results.getInt(results.getColumnIndex("solvedEmote"));

        Solution s = new Solution(todaysFix, cutMuri, threeXBetter, truePull, estimatedSavings,
                dateSolved, dateSolutionUpdated, signedOffBy, solvedEmote);
        s.setItemID(id);

        return s;
    }

    private ImageFile createImageFile(Cursor results) {
        int id = (int) results.getLong(results.getColumnIndex("_id"));
        String imagePath = results.getString(results.getColumnIndex("imagePath"));

        ImageFile f = new ImageFile(imagePath);
        f.setItemID(id);

        return f;
    }

    public Countermeasure createCountermeasure(Cursor results) {
        int id = (int) results.getLong(results.getColumnIndex("_id"));
        DateTime dateModified = new DateTime(results.getLong(results.getColumnIndex("dateModified")));
        String preventativeAction = results.getString(results.getColumnIndex("preventativeAction"));
        boolean cutMuri = results.getInt(results.getColumnIndex("cutMuri")) > 0;
        boolean threeXBetter = results.getInt(results.getColumnIndex("threeXBetter")) > 0;
        boolean truePull = results.getInt(results.getColumnIndex("truePull")) > 0;
        float costToImplement = results.getFloat(results.getColumnIndex("costToImplement"));
        boolean implemented = results.getInt(results.getColumnIndex("implemented")) > 0;
        String dateWalkedOn = results.getString(results.getColumnIndex("dateWalkedOn"));

        Countermeasure cm = new Countermeasure(dateModified, preventativeAction, cutMuri, threeXBetter, truePull,
                costToImplement, implemented, dateWalkedOn);
        cm.setItemID(id);
        return cm;
    }
}
