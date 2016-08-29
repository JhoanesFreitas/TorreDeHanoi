package com.jho.alana.hanoi;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.jho.alana.Constatns;
import com.jho.alana.async.HanoiTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener{

  private Stack stackA;
  private Stack stackB;
  private Stack stackC;
  private Stack stackAux;

  private Spinner spinner;
  private Spinner spinnerTime;
  private LinearLayout layoutTowerA;
  private LinearLayout layoutTowerB;
  private LinearLayout layoutTowerC;

  private View newTower;

  private Integer qntDiscsIncrement;

  private Double timeExecuteSpinner;
  private List<Integer> qtdDiscs;
  private List<Double> timeExecute;
  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View view){

        if(qntDiscsIncrement == null){
          Snackbar.make(view, "Talvez você tenha esquecido de gerar a torre!", Snackbar.LENGTH_LONG)
              .setAction("Action", null).show();
          return;
        }

        HanoiTask hanoi = new HanoiTask(MainActivity.this, qntDiscsIncrement, stackA, stackB, stackC);
        Log.d("discs", qntDiscsIncrement + "");
        hanoi.setLayouts(layoutTowerA, layoutTowerB, layoutTowerC);
        hanoi.execute();
      }
    });

    spinner = (Spinner) findViewById(R.id.qtdDiscs);
    spinnerTime = (Spinner) findViewById(R.id.qtdDiscs2);

    spinner.setOnItemSelectedListener(this);
    spinnerTime.setOnItemSelectedListener(this);

    qtdDiscs = new ArrayList<Integer>();

    qtdDiscs.add(4);
    qtdDiscs.add(8);
    qtdDiscs.add(16);
    qtdDiscs.add(32);
    qtdDiscs.add(64);

    ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, qtdDiscs);

    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    spinner.setAdapter(dataAdapter);

    timeExecute = new ArrayList<Double>();
    timeExecute.add(0.3);
    timeExecute.add(0.5);
    timeExecute.add(1.0);
    timeExecute.add(2.0);

    ArrayAdapter<Double> dataAdapterTime = new ArrayAdapter<Double>(this, android.R.layout.simple_spinner_item, timeExecute);
    dataAdapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    spinnerTime.setAdapter(dataAdapterTime);
  }

  public void onClick(View view){
    layoutTowerA = (LinearLayout) findViewById(R.id.layoutDiscs);
    layoutTowerB = (LinearLayout) findViewById(R.id.layoutDiscs2);
    layoutTowerC = (LinearLayout) findViewById(R.id.layoutDiscs3);

    layoutTowerA.setRotation(180);
    layoutTowerB.setRotation(180);
    layoutTowerC.setRotation(180);
    stackAux = new Stack();

    startStacks();

    layoutTowerA.removeAllViews();
    layoutTowerB.removeAllViews();
    layoutTowerC.removeAllViews();

    Random random = new Random(10);
    qntDiscsIncrement =  qtdDiscs.get(spinner.getSelectedItemPosition());
    timeExecuteSpinner = timeExecute.get(spinnerTime.getSelectedItemPosition());
    LinearLayout.LayoutParams layoutParams;

    int var = qntDiscsIncrement;

    for(int i = 0; i < qntDiscsIncrement; i++){
      newTower = new View(this);
      newTower.setBackgroundColor(Color.rgb(random.nextInt(), random.nextInt(), random.nextInt()));
      newTower.setId(var--);

      if(qntDiscsIncrement <= 16){
        layoutParams = new LinearLayout.LayoutParams(
            200 - i * 7, 15
        );
      }
      else if(qntDiscsIncrement == 32){
        layoutParams = new LinearLayout.LayoutParams(
            200 - i * 7, 9
        );
      }
      else {
        layoutParams = new LinearLayout.LayoutParams(
            200 - i * 4, 5
        );
      }

      if(i == 0){
        addTam(layoutParams);
      }

      layoutParams.gravity = Gravity.CENTER;
      layoutTowerA.addView(newTower, layoutParams);
      stackA.push(newTower);
    }
    //addOnStackA();
  }

  private void addOnStackA(){
    while(!stackAux.isEmpty())
      stackA.push(stackAux.pop());
  }

  //Posição dos discos quanto à base
  private void addTam(LinearLayout.LayoutParams params){
    params.topMargin = 0;
   /* if(qntDiscsIncrement == 4)
      params.topMargin = 0;
    else if(qntDiscsIncrement == 8)
      params.topMargin = 0;
    else if(qntDiscsIncrement == 16)
      params.topMargin = 0;
    else if(qntDiscsIncrement == 32)
      params.topMargin = 225;
    else
      params.topMargin = 230;*/
  }

  private void startStacks(){
    stackA = new Stack();
    stackB = new Stack();
    stackC = new Stack();
  }

  /////////////////////////

  public void hanoi(final Queue queue){

    new CountDownTimer((long) (timeExecuteSpinner * 1000), 1000) {

      @Override
      public void onTick(long millisUntilFinished) {

      }

      @Override
      public void onFinish() {

        View mTowerView;
        Integer n;
        //while (!queue.isEmpty()) {
          n = (Integer) queue.remove();
          layoutTowerA = (LinearLayout) queue.remove();
          layoutTowerB = (LinearLayout) queue.remove();

          mTowerView = layoutTowerA.findViewById(n);

          if (mTowerView != null) {
            layoutTowerA.removeView(mTowerView);
            layoutTowerB.addView(mTowerView);
          }

        //}

        if (queue.isEmpty()) {
          this.cancel();
          Toast.makeText(MainActivity.this, Constatns.FINISH_CHANGE + Constatns.countChange + " movimentos!", Toast.LENGTH_SHORT).show();
        } else {
          hanoi(queue);
        }
      }
    }.start();
  }

  public Integer getQntDiscsIncrement() {
    return qntDiscsIncrement;
  }

  public void setQntDiscsIncrement(Integer qntDiscsIncrement) {
    this.qntDiscsIncrement = qntDiscsIncrement;
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu){
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item){
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if(id == R.id.action_settings){
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
    String item = parent.getItemAtPosition(position).toString();
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent){

  }
}
