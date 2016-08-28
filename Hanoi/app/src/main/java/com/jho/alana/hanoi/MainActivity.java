package com.jho.alana.hanoi;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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

import com.jho.alana.async.HanoiTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener{

  Stack stack = new Stack();

  private Spinner spinner;
  private Spinner spinnerTime;
  private LinearLayout layout;

  private View towerBaseOne;
  private View towerBaseTwo;
  private View towerBaseThree;
  private View newTower;
  private Integer qntDiscsIncrement;
  private List<Integer> qtdDiscs;
  private List<Integer> timeExecute;

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

        HanoiTask hanoi = new HanoiTask(MainActivity.this, qntDiscsIncrement, newTower);
        Log.d("discs", qntDiscsIncrement + "");
        hanoi.execute();
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
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

    timeExecute = new ArrayList<Integer>();
    timeExecute.add(2);
    timeExecute.add(5);
    timeExecute.add(10);
    timeExecute.add(15);

    ArrayAdapter<Integer> dataAdapterTime = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, timeExecute);
    dataAdapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    spinnerTime.setAdapter(dataAdapterTime);
  }

  public void onClick(View view){
    towerBaseOne = findViewById(R.id.baseTower1);
    towerBaseOne = findViewById(R.id.baseTower2);
    towerBaseOne = findViewById(R.id.baseTower3);
    layout = (LinearLayout) findViewById(R.id.layoutDiscs);

    layout.removeAllViews();

    Random random = new Random(10);
    qntDiscsIncrement =  qtdDiscs.get(spinner.getSelectedItemPosition());
    LinearLayout.LayoutParams layoutParams;

    for(int i = 0; i < qntDiscsIncrement; i++){
      newTower = new View(this);
      newTower.setBackgroundColor(Color.rgb(random.nextInt(), random.nextInt(), random.nextInt()));



      if(qntDiscsIncrement <= 16){
        layoutParams = new LinearLayout.LayoutParams(
            90 + i * 7, 15
        );

      }
      else if(qntDiscsIncrement == 32){
        layoutParams = new LinearLayout.LayoutParams(
            10 + i * 7, 9
        );
      }
      else {
        layoutParams = new LinearLayout.LayoutParams(
            6 + i * 4, 5
        );
      }

      if(i == 0){
        addTam(layoutParams);
      }

      layoutParams.gravity = Gravity.CENTER;
      layout.addView(newTower, layoutParams);

    }
  }
  //Posição dos discos quanto à base
  private void addTam(LinearLayout.LayoutParams params){
    if(qntDiscsIncrement == 4)
      params.topMargin = 240;
    else if(qntDiscsIncrement == 8)
      params.topMargin = 240 - 60;
    else if(qntDiscsIncrement == 16)
      params.topMargin = 240 - 180;
    else if(qntDiscsIncrement == 32)
      params.topMargin = 240 - 225;
    else
      params.topMargin = 240 - 230;
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
