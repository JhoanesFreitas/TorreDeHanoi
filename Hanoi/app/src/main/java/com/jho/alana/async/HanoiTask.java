package com.jho.alana.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by jhoanesfreitas on 26/08/16.
 */

public class HanoiTask extends AsyncTask<Void, Void, String>{

  private ProgressDialog mProgress;
  private Activity mActivity;

  public HanoiTask(Activity context){
    this.mActivity = context;
  }

  @Override protected void onPreExecute(){
    super.onPreExecute();
    mProgress = new ProgressDialog(mActivity);
    mProgress.setMessage("Resolvendo o problema...");
    mProgress.show();
  }

  @Override protected String doInBackground(Void... voids){
    return "Alguma coisa...";
  }


  @Override protected void onPostExecute(String string){
    mProgress.dismiss();
    Toast.makeText(mActivity, string, Toast.LENGTH_SHORT).show();
  }
}
