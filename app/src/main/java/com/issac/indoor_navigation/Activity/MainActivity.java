package com.issac.indoor_navigation.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.issac.indoor_navigation.R;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private  String[] names ={"guidelayout","ARlayout"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadview();
    }

    private void loadview() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,android.R.layout.simple_list_item_1,names
        );
        ListView listView = (ListView)findViewById(R.id.mylistview);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = null;
        if(position == 0)
          intent = new Intent(this.getApplicationContext(),GuideActivity.class);
        if (position == 1)
            intent = new Intent(this.getApplicationContext(),ARNavigationActivity.class);
        startActivity(intent);
    }
}
