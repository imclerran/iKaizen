package mclerrani.ikaizen;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ian on 1/21/2016.
 */
public class DataManager { /* SINGLETON */

    private static DataManager dm;
    private DatabaseHelper dbh;
    private static Context context;

    private ArrayList<Kaizen> kaizenList;
    private ArrayList<Countermeasure> countermeasureList;
    //private int nextListId;
    private int kParentId;

    private DataManager() {
        kaizenList          = new ArrayList<>();
        countermeasureList  = new ArrayList<>();
        dbh = new DatabaseHelper(context);
        new PopulateListsTask().execute();
    }

    // get the data manager
    // if it doesn't exist, instantiate it
    public static DataManager getInstance(Context context) {
        DataManager.context = context;
        if(null == dm) {
            dm = new DataManager();
        }
        return dm;
    }

    public void sortKaizenList() { Collections.sort(kaizenList); }


    public ArrayList<Kaizen> getKaizenList() {
        //Collections.sort(kaizenList);
        return kaizenList;
    }

    public ArrayList<Countermeasure> getCountermeasureList() {
        return countermeasureList;
    }

    public Kaizen getKaizen(int id) {
        if(id < 0)
            return null;

        int i = 0;
        int size = kaizenList.size();
        while(id != kaizenList.get(i).getItemID()) {
            i++;
            if(size <= i)
                return null;
        }
        return kaizenList.get(i);
    }

    //--------------------
    // kaizen db interface
    //--------------------
    public void insertKaizen(Kaizen k) {
        k.updateDateModified();
        new InsertKaizenTask().execute(k);
    }
    public void updateKaizen(Kaizen k) {
        k.updateDateModified();
        new UpdateKaizenTask().execute(k);
    }
    public void deleteKaizen(Kaizen k) {
        if(null != k.getSolution()) {
            deleteSolution(k.getSolution(), k);
        }
        while(0 < k.getImageFiles().size()) {
            deleteImageFile(k.getImageFiles().get(0), k);
        }
        new DeleteKaizenTask().execute(k);
    }

    //----------------------
    // imagefile db interface
    //----------------------
    public void insertImageFile(ImageFile f, Kaizen parent) {
        parent.updateDateModified();
        kParentId = parent.getItemID();
        new InsertImageFileTask().execute(f);
    }
    public void updateImageFile(ImageFile f, Kaizen parent) {
        parent.updateDateModified();
        kParentId = parent.getItemID();
        new UpdateImageFileTask().execute(f);
    }
    public void deleteImageFile(ImageFile f, Kaizen parent) {
        parent.updateDateModified();
        new DeleteImageFileTask().execute(f);
    }

    //----------------------
    // solution db interface
    //----------------------
    public void insertSolution(Solution s, Kaizen parent) {
        parent.updateDateModified();
        kParentId = parent.getItemID();
        new InsertSolutionTask().execute(s);
    }
    public void updateSolution(Solution s, Kaizen parent) {
        parent.updateDateModified();
        kParentId = parent.getItemID();
        new UpdateSolutionTask().execute(s);
    }
    public void deleteSolution(Solution s, Kaizen parent) {
        parent.updateDateModified();
        while(0 < s.getPossibleCounterMeasures().size()) {
            deleteCountermeasure(s.getPossibleCounterMeasures().get(0), parent);
        }
        new DeleteSolutionTask().execute(s);
    }

    //----------------------------
    // countermeasure db interface
    //----------------------------
    public void insertCountermeasure(Countermeasure cm, Kaizen parent) {
        parent.updateDateModified();
        kParentId = parent.getItemID();
        new InsertCountermeasureTask().execute(cm);
    }
    public void updateCountermeasure(Countermeasure cm, Kaizen parent) {
        parent.updateDateModified();
        kParentId = parent.getItemID();
        new UpdateCountermeasureTask().execute(cm);
    }
    public void deleteCountermeasure(Countermeasure cm, Kaizen parent) {
        parent.updateDateModified();
        new DeleteCountermeasureTask().execute(cm);
    }

    /**
     * An asynchronous task to allow querying the database in a second thread
     */
    private class PopulateListsTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            return dbh.populateLists(kaizenList);
        }
        @Override
        protected void onPostExecute(Boolean success) {
            if(success) {
                if(0 == kaizenList.size()) {
                    insertKaizen(Kaizen.getTestKaizen());
                }
                KaizenRecyclerActivity.getRecyclerAdapter().notifyDataSetChanged();
            }
            else {
                Toast.makeText(context, R.string.errGetDataStr, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * An asyncronous task to allow deleting a Kaizen from the db in a second thread
     */
    private class InsertKaizenTask extends AsyncTask<Kaizen, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Kaizen... params) {
            int id = dbh.insertKaizen(params[0]);
            params[0].setItemID(id);
            boolean success = id > 0;

            // if added to DB, add to list
            if(success)
                kaizenList.add(params[0]);

            return success;
        }
        @Override
        protected void onPostExecute(Boolean success) {
            if(success) {
                KaizenRecyclerActivity.getRecyclerAdapter().notifyDataSetChanged();
            }
            else {
                Toast.makeText(context, R.string.errGetDataStr, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class UpdateKaizenTask extends AsyncTask<Kaizen, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Kaizen... params) {
            int num = dbh.updateKaizen(params[0]);
            return num > 0;
        }
        @Override
        protected void onPostExecute(Boolean success) {
            if(success) {
                KaizenRecyclerActivity.getRecyclerAdapter().notifyDataSetChanged();
            }
            else {
                Toast.makeText(context, R.string.errGetDataStr, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class DeleteKaizenTask extends AsyncTask<Kaizen, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Kaizen... params) {
            int num = dbh.deleteKaizen(params[0]);
            return num > 0;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                KaizenRecyclerActivity.getRecyclerAdapter().notifyDataSetChanged();
            }
            else {
                Toast.makeText(context, R.string.errGetDataStr, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class InsertSolutionTask extends AsyncTask<Solution, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Solution... params) {
            int id = dbh.insertSolution(params[0], kParentId);
            params[0].setItemID(id);
            boolean success = id > 0;

            // if added to DB, add to list
            if(success)
                getKaizen(kParentId).setSolution(params[0]);

            return success;
        }
        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast.makeText(context, R.string.errGetDataStr, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class UpdateSolutionTask extends AsyncTask<Solution, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Solution... params) {
            int num = dbh.updateSolution(params[0], kParentId);
            return num > 0;
        }
        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast.makeText(context, R.string.errGetDataStr, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class DeleteSolutionTask extends AsyncTask<Solution, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Solution... params) {
            int num = dbh.deleteSolution(params[0]);
            return num > 0;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast.makeText(context, R.string.errGetDataStr, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class InsertImageFileTask extends AsyncTask<ImageFile, Void, Boolean> {
        @Override
        protected Boolean doInBackground(ImageFile... params) {
            int id = dbh.insertImageFile(params[0], kParentId);
            params[0].setItemID(id);
            boolean success = id > 0;

            // if added to DB, add to list
            if(success)
                getKaizen(kParentId).addImageFile(params[0]);

            return success;
        }
        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast.makeText(context, R.string.errGetDataStr, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class UpdateImageFileTask extends AsyncTask<ImageFile, Void, Boolean> {
        @Override
        protected Boolean doInBackground(ImageFile... params) {
            int num = dbh.updateImageFile(params[0], kParentId);
            return num > 0;
        }
        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast.makeText(context, R.string.errGetDataStr, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class DeleteImageFileTask extends AsyncTask<ImageFile, Void, Boolean> {
        @Override
        protected Boolean doInBackground(ImageFile... params) {
            int num = dbh.deleteImageFile(params[0]);
            return num > 0;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                ImageGalleryActivity.getRecyclerAdapter().notifyDataSetChanged();
            }
            else {
                Toast.makeText(context, R.string.errGetDataStr, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class InsertCountermeasureTask extends AsyncTask<Countermeasure, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Countermeasure... params) {
            int parentId = getKaizen(kParentId).getSolution().getItemID();

            int id = dbh.insertCountermeasure(params[0], parentId);
            params[0].setItemID(id);
            boolean success = id > 0;

            // if added to DB, add to list
            if(success)
                getKaizen(kParentId).getSolution().getPossibleCounterMeasures().add(params[0]);

            return success;
        }
        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                CountermeasureRecyclerAdapter recAdapter = CountermeasureListFragment.getRecyclerAdapter();
                if(null != recAdapter) {
                    recAdapter.notifyDataSetChanged();
                }
            }
            else {
                Toast.makeText(context, R.string.errGetDataStr, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class UpdateCountermeasureTask extends AsyncTask<Countermeasure, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Countermeasure... params) {
            int parentId = getKaizen(kParentId).getSolution().getItemID();

            int num = dbh.updateCountermeasure(params[0], parentId);
            return num > 0;
        }
        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                CountermeasureListFragment.getRecyclerAdapter().notifyDataSetChanged();
            }
            else {
                Toast.makeText(context, R.string.errGetDataStr, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class DeleteCountermeasureTask extends AsyncTask<Countermeasure, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Countermeasure... params) {
            int num = dbh.deleteCountermeasure(params[0]);
            return num > 0;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                CountermeasureListFragment.getRecyclerAdapter().notifyDataSetChanged();
            }
            else {
                Toast.makeText(context, R.string.errGetDataStr, Toast.LENGTH_LONG).show();
            }
        }
    }
}