package com.jho.alana.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jho.alana.hanoi.MainActivity;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static com.jho.alana.Constatns.FINISH_HANOI;
import static com.jho.alana.Constatns.INIT_PROGRESS;
import static com.jho.alana.Constatns.countChange;

/**
 * Created by jhoanesfreitas on 26/08/16.
 */

public class HanoiTask extends AsyncTask<Void, Void, String>{

  private Stack ori;
  private Stack dest;
  private Stack aux;
  
  private ProgressDialog mProgress;
  private MainActivity mActivity;
  private int qntDiscs;
  private View view;

  private LinearLayout layoutTowerA;
  private LinearLayout layoutTowerB;
  private LinearLayout layoutTowerC;

  Queue queue = new LinkedList();

  public HanoiTask(MainActivity context, int qntDiscs, View view){
    this.mActivity = context;
    this.qntDiscs = qntDiscs;
    this.view = view;
  }

  public HanoiTask(MainActivity context, int qntDiscs, Stack ori, Stack dest, Stack aux){
    this.mActivity = context;
    this.qntDiscs = qntDiscs;
    this.ori = ori;
    this.dest = dest;
    this.aux = aux;
  }

  @Override protected void onPreExecute(){
    ori.push(view);
    super.onPreExecute();
    mProgress = new ProgressDialog(mActivity);
    mProgress.setMessage(INIT_PROGRESS);
    mProgress.show();
  }

  @Override protected String doInBackground(Void... voids){
    hanoi(qntDiscs, layoutTowerA, layoutTowerB, layoutTowerC);
    return FINISH_HANOI;
  }


  @Override protected void onPostExecute(String string){
    mProgress.dismiss();
    Toast.makeText(mActivity, string, Toast.LENGTH_SHORT).show();

    mActivity.hanoi(queue);
    mActivity.setQntDiscsIncrement(null);
    //HanoiUITask hanoiUITask = new HanoiUITask(queue);
    //hanoiUITask.setLayouts(layoutTowerA, layoutTowerB, layoutTowerC);
    //hanoiUITask.execute();
  }

  private void hanoi(int n, LinearLayout ori, LinearLayout dest, LinearLayout aux){

    if(n == 1){
      System.out.println("Mover disco " + n + " de " +
          ori + " para " + dest);
      onAddQueue(n, ori, dest);
      countChange++;
    }else{
      hanoi(n - 1, ori, aux, dest);
      System.out.println("Mover disco " + n + " de " +
              ori + " para " + dest);
      countChange++;
      onAddQueue(n, ori, dest);
      hanoi(n - 1, aux, dest, ori);
      //onAddQueue(n, ori, dest);
    }
  }

  private void onAddQueue(int n, LinearLayout ori, LinearLayout dest){
    queue.add(n);
    queue.add(ori);
    queue.add(dest);
  }

  public void setLayouts(LinearLayout layoutTowerA, LinearLayout layoutTowerB, LinearLayout layoutTowerC){
    this.layoutTowerA = layoutTowerA;
    this.layoutTowerB = layoutTowerB;
    this.layoutTowerC = layoutTowerC;
  }

  /*private void hanoi(int n, Stack ori, Stack dest, Stack aux){

    /*if(view == 1){
      dest.push(ori.pop());
      onAddQueue(n, ori, dest);
    }*/
    /*hanoi(n - 1, ori, aux, dest);
    dest.push(ori.peek());
    ori.pop();
    onAddQueue(n, ori, dest);
    hanoi(n - 1, aux, dest, ori);

  }*/

}
