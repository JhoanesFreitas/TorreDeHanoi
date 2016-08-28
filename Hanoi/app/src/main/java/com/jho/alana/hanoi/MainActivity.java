package com.jho.alana.hanoi;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
  private LinearLayout layout;

  private View towerBaseOne;
  private View towerBaseTwo;
  private View towerBaseThree;
  private Integer qntDiscsIncrement;
  private List<Integer> qtdDiscs;

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
        HanoiTask hanoi = new HanoiTask(MainActivity.this, 16);
        hanoi.execute();
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });

    spinner = (Spinner) findViewById(R.id.qtdDiscs);

    spinner.setOnItemSelectedListener(this);

    qtdDiscs = new ArrayList<Integer>();

    qtdDiscs.add(4);
    qtdDiscs.add(8);
    qtdDiscs.add(16);
    qtdDiscs.add(32);
    qtdDiscs.add(64);

    ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, qtdDiscs);

    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    spinner.setAdapter(dataAdapter);
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
      View newTower = new View(this);
      newTower.setBackgroundColor(Color.rgb(random.nextInt(), random.nextInt(), random.nextInt()));



      if(qntDiscsIncrement <= 16){
        layoutParams = new LinearLayout.LayoutParams(
            90 + i * 7,15
        );

        //layoutParams.setMargins(50 - (i * 3), 1, 1, 1);
        layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER;
        layout.addView(newTower, layoutParams);
      }
      else {
        layout.addView(newTower, 10 + i * 7, 10);
      }
    }
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
