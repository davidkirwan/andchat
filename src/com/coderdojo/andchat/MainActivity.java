package com.coderdojo.andchat;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends Activity  {
	public final static String EXTRA_MESSAGE = "com.coderdojo.andchat.MESSAGE";
	public final static String LISTITEMS1 = "listItems1";
	
	public ArrayList<StringParcel> listItems;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
     // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) 
        {
            // Restore value of members from saved state
        	listItems = savedInstanceState.getParcelableArrayList(LISTITEMS1);
        } 
        else 
        {
        	listItems = new ArrayList<StringParcel>();
        }
        
        
        String str = new String("No friends yet, why not add some!");
        
        ArrayAdapter<StringParcel> adapter;
        adapter = new ArrayAdapter<StringParcel>(this, android.R.layout.simple_list_item_1,
        									listItems);
        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        
        if(listItems.size() == 0)
        {
        	listItems.add(new StringParcel(str));
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
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putParcelableArrayList(LISTITEMS1, listItems);
        
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
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
    	
    	ArrayAdapter<StringParcel> adapter;
        adapter = new ArrayAdapter<StringParcel>(this, android.R.layout.simple_list_item_1,
        									listItems);
        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        
    	listItems.add(new StringParcel(message));
    	listItems.set(0, new StringParcel("Number of friends: " + (listItems.size() - 1)));
    	
        adapter.notifyDataSetChanged();
    }

    
// End of the MainActivity
}

