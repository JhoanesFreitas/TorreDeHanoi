package com.jho.alana.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;

import com.jho.alana.Constatns;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by jhoanesfreitas on 28/08/16.
 */

public class HanoiUITask extends AsyncTask<Void, Void, String>{

  private LinearLayout layoutTowerA;
  private LinearLayout layoutTowerB;
  private Queue queue;
  private View mTowerView;

  public HanoiUITask(Queue queue){
    this.queue = queue;
  }

  @Override protected void onPreExecute(){
    super.onPreExecute();
  }

  @Override protected String doInBackground(Void... voids){
    hanoi();
    return Constatns.FINISH_HANOI;
  }

  @Override protected void onPostExecute(String s){
    super.onPostExecute(s);
  }

  private void hanoi(){
    Integer n;

    while(!queue.isEmpty()){
      n = (Integer) queue.remove();
      layoutTowerA = (LinearLayout) queue.remove();
      layoutTowerB = (LinearLayout) queue.remove();

      mTowerView = layoutTowerA.findViewById(n);

      if(mTowerView != null){
        layoutTowerA.removeView(mTowerView);
        layoutTowerB.addView(mTowerView);
      }

    }
  }

  public void setLayouts(LinearLayout layoutTowerA, LinearLayout layoutTowerB, LinearLayout layoutTowerC){
    this.layoutTowerA = layoutTowerA;
    this.layoutTowerB = layoutTowerB;
    //this.layoutTowerC = layoutTowerC;
  }

}
