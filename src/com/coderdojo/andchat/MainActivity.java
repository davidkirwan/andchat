package com.coderdojo.andchat;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.coderdojo.andchat.MESSAGE";
	public ArrayList<String> listItems = new ArrayList<String>();
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        String str = new String("Nothing sent yet!");
        
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
        									listItems);
        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);

        listItems.add(str);
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    public void sendMessage(View view)
    {
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	
    	ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
        									listItems);
        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        
    	listItems.add(message);
        adapter.notifyDataSetChanged();
    	
        /*
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    	*/
    }
    
}
