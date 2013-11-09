package com.coderdojo.andchat;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.app.ListActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class MainActivity extends Activity  {
	public final static String EXTRA_MESSAGE = "com.coderdojo.andchat.MESSAGE";
	public final static String LISTITEMS = "listItems";
	
	public ArrayList<String> listItems;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        listItems = new ArrayList<String>();
        
        SharedPreferences sharedPref = getPreferences(this.MODE_PRIVATE);
        String listItemsJson = sharedPref.getString(LISTITEMS, null);
        
        if(listItemsJson != null)
        {
        	listItems = new Gson().fromJson(listItemsJson, this.listItems.getClass());
        }

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
        									listItems);
        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        
        if(listItems.size() == 0)
        {
        	String str = new String("No friends yet, why not add some!");
        	listItems.add(str);
        	adapter.notifyDataSetChanged();
        }
        
        listView.setOnItemClickListener(new OnItemClickListener() 
        {
        	@Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) 
            {
            	String item = ((TextView)view).getText().toString();
            	//Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
            	
            	sendMessage(item);
            }
        });
    }
    
    
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        String listItemsJson = new Gson().toJson(this.listItems);
        
        SharedPreferences sharedPref = getPreferences(this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LISTITEMS, listItemsJson);
        editor.commit();
        
        Toast.makeText(getBaseContext(), "Pausing", Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Toast.makeText(getBaseContext(), "Resumed", Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    
    public void openSettings()
    {
    	Toast.makeText(getBaseContext(), "Settings Clicked", Toast.LENGTH_LONG).show();
    }
    
    
    public void openSearch()
    {
    	Toast.makeText(getBaseContext(), "Search Clicked", Toast.LENGTH_LONG).show();
    }
    
    
    public void sendMessage(String name)
    {
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	intent.putExtra(EXTRA_MESSAGE, name);
    	startActivity(intent);
    }
    
    
    public void addFriend(View view)
    {
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	
    	// Ignore if the box is empty
    	if(message.equals(""))
    	{
    		Toast.makeText(getBaseContext(), "You must enter a name first!", Toast.LENGTH_LONG).show();
    		return;
    	}
    	
    	ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
        									listItems);
        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        
    	listItems.add(new String(message));
    	
    	if(listItems.get(0).equals("No friends yet, why not add some!"))
        {
        	this.listItems.remove(0);
        }
    	
        adapter.notifyDataSetChanged();
    }

    
// End of the MainActivity
}

