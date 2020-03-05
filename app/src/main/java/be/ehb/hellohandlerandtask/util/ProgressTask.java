package be.ehb.hellohandlerandtask.util;

import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

public class ProgressTask extends AsyncTask<ProgressBar,Integer, Void> {

    private WeakReference<ProgressBar> pbReference;
    private WeakReference<Button> btnReference;

    public ProgressTask(ProgressBar pb, Button btnStartTask) {
        pbReference = new WeakReference<>(pb);
        btnReference = new WeakReference<>(btnStartTask);
    }
    @Override
    //als we iets van werk moeten voorbereiden
    protected void onPreExecute() {
        super.onPreExecute();
        //1, voorbereidend werk alvorens taak begint bv  iets initialiseren
        btnReference.get().setEnabled(false);
    }

    @Override
    protected Void doInBackground(ProgressBar... niks) {
        //2, voert uit op de achtergrond
        for (int i = 0 ; i <= 100; i++){
            try {
                Thread.sleep(100);

            } catch (InterruptedException e){
                e.printStackTrace();
            }
            publishProgress( i);
        }

        //return naar on PostExecute, komt binnen als parameter
        return null;
    }

    

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //3, tussentijdse updates tijdens het uitvoeren
        pbReference.get().setProgress(values[0]);
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //4, klaar met uitvoer , laatste aanpassingen aan UI
        //opkuis van geheugen
        btnReference.get().setEnabled(true);
        pbReference = null;
        btnReference = null;
    }
}
