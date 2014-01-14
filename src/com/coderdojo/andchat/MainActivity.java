package com.coderdojo.andchat;

import java.util.ArrayList;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;



public class MainActivity extends Activity  {
	public final static String EXTRA_MESSAGE = "com.coderdojo.andchat.MESSAGE";
	public final static String LISTITEMS = "listItems";
	
	private DBManager datasource;	
	public ArrayList<String> listItems;
    

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        listItems = new ArrayList<String>();
        
        datasource = new DBManager(this);
		datasource.open();
        
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
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
        
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
        	@Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        		String item = ((TextView)view).getText().toString();
        		if(!item.equals("No friends yet, why not add some!")) {
        			displayDeleteFriendConfirmation(item);
        		}
        		//deleteFriend(item);
        		return true;
            }
        });
        
        
    }
    
    public void addFriendConfirmation(final View item){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	
    	builder.setMessage(R.string.delete_message_message)
        .setTitle(R.string.addfriend);
    	
    	builder.setNegativeButton(R.string.delete_message_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
    	
    	builder.setPositiveButton(R.string.delete_friend_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            	
        		addFriend(item);
            }
        });
    	
    	AlertDialog dialog = builder.create();
    	dialog.show();
    	
    }
    
    
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        String listItemsJson = new Gson().toJson(this.listItems);
        
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LISTITEMS, listItemsJson);
        editor.commit();
        
        //Toast.makeText(getBaseContext(), "Pausing", Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        //Toast.makeText(getBaseContext(), "Resumed", Toast.LENGTH_LONG).show();
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
    	editText.setText("");
    	
    	// Ignore if the box is empty
    	if(message.equals(""))
    	{
    		Toast.makeText(getBaseContext(), "You must enter a name first!", Toast.LENGTH_LONG).show();
    		return;
    	}
    	
    	if(listItems.contains(new String(message))) {
    		Toast.makeText(getBaseContext(), "You already have this friend!", Toast.LENGTH_LONG).show();
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
    
    public void deleteFriend(String friend) {
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listItems);
        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);
    	listItems.remove(friend);
        adapter.notifyDataSetChanged();
        Toast.makeText(getBaseContext(), "Friend Removed", Toast.LENGTH_LONG).show();
    }
    
    public void displayDeleteFriendConfirmation(final String item){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	
    	builder.setMessage(R.string.delete_friend_message)
        .setTitle(R.string.delete_dialog_title);
    	
    	builder.setNegativeButton(R.string.delete_friend_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
    	
    	builder.setPositiveButton(R.string.delete_friend_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            	
        		deleteFriend(item);
            }
        });
    	
    	AlertDialog dialog = builder.create();
    	dialog.show();
    	
    }

    
// End of the MainActivity
}

