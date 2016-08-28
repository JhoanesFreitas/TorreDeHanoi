package com.jho.alana.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static com.jho.alana.Constatns.FINISH_HANOI;
import static com.jho.alana.Constatns.INIT_PROGRESS;

/**
 * Created by jhoanesfreitas on 26/08/16.
 */

public class HanoiTask extends AsyncTask<Void, Void, String>{

  private ProgressDialog mProgress;
  private Activity mActivity;
  private int qntDiscs;

  Stack stack = new Stack();

  Queue queue = new LinkedList();

  public HanoiTask(Activity context, int qntDiscs){
    this.mActivity = context;
    this.qntDiscs = qntDiscs;
  }

  @Override protected void onPreExecute(){
    super.onPreExecute();
    mProgress = new ProgressDialog(mActivity);
    mProgress.setMessage(INIT_PROGRESS);
    mProgress.show();
  }

  @Override protected String doInBackground(Void... voids){
    hanoi(qntDiscs, 'A', 'B', 'C');
    return FINISH_HANOI;
  }


  @Override protected void onPostExecute(String string){
    mProgress.dismiss();
    Toast.makeText(mActivity, string, Toast.LENGTH_SHORT).show();
  }

  private void hanoi(int n, char ori, char dest, char aux){

    if(n == 1){
      System.out.println("Mover disco " + n + " de " +
          ori + " para " + dest);
      onAddQueue(n, ori, dest);
    }else{
      hanoi(n - 1, ori, aux, dest);
      System.out.println("Mover disco " + n + " de " +
          ori + " para " + dest);
      onAddQueue(n, ori, dest);
      hanoi(n - 1, aux, dest, ori);
    }
  }

  private void onAddQueue(int n, char ori, char dest){
    queue.add(n);
    queue.add(ori);
    queue.add(dest);
  }
}
