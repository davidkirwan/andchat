/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.coderdojo.libretalk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.coderdojo.libretalk.network.ILibretalkMessageEventHandler;
import com.coderdojo.libretalk.network.LibretalkConnection;
import com.coderdojo.libretalk.network.LibretalkMessageReceiver;
import com.coderdojo.libretalk.network.LibretalkMessageSender;
import com.coderdojo.libretalk.network.LibretalkNetworkException;
import com.rabbitmq.client.ShutdownSignalException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * This example illustrates a common usage of the DrawerLayout widget
 * in the Android support library.
 * <p/>
 * <p>When a navigation (left) drawer is present, the host activity should detect presses of
 * the action bar's Up affordance as a signal to open and close the navigation drawer. The
 * ActionBarDrawerToggle facilitates this behavior.
 * Items within the drawer should fall into one of two categories:</p>
 * <p/>
 * <ul>
 * <li><strong>View switches</strong>. A view switch follows the same basic policies as
 * list or tab navigation in that a view switch does not create navigation history.
 * This pattern should only be used at the root activity of a task, leaving some form
 * of Up navigation active for activities further down the navigation hierarchy.</li>
 * <li><strong>Selective Up</strong>. The drawer allows the user to choose an alternate
 * parent for Up navigation. This allows a user to jump across an app's navigation
 * hierarchy at will. The application should treat this as it treats Up navigation from
 * a different task, replacing the current task stack using TaskStackBuilder or similar.
 * This is the only form of navigation drawer that should be used outside of the root
 * activity of a task.</li>
 * </ul>
 * <p/>
 * <p>Right side drawers should be used for actions, not navigation. This follows the pattern
 * established by the Action Bar that navigation should be to the left and actions to the right.
 * An action should be an operation performed on the current contents of the window,
 * for example enabling or disabling a data overlay on top of the current content.</p>
 */
public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence appName, mTitle;
    private static CharSequence friendlist;
    
    private DBManager datasource;	
	private List<LibretalkUser> userList;
	private ArrayList<String> mFriendsListArray;
	private ArrayList<String> mMessageListArray;
	
	//XXX NETWORKING CODE END
	private LibretalkConnection connection;
	private LibretalkMessageReceiver receiver;
	private LibretalkMessageSender sender;
	//XXX NETWORKING CODE END

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appName = mTitle = getTitle();
        getActionBar().setTitle(appName);
        
        // Read the User data from the database
        datasource = new DBManager(this);
		datasource.open();
		userList = datasource.getAllUsers();
		
		mFriendsListArray = new ArrayList<String>();
		mMessageListArray = new ArrayList<String>();
		
		for(int i = 0; i < userList.size(); i++){
			mFriendsListArray.add(userList.get(i).getProfile().getName());
		}
        
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        friendlist = getResources().getString(R.string.friendlist);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mFriendsListArray));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(friendlist);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(appName);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
        	setTitle(friendlist);
            selectItem(0);
        }
        
        //XXX NETWORKING CODE BEGIN
        //[!] TODO: This is configured to use Android's localhost. Change the host value to whatever our server's address is.
        this.connection = new LibretalkConnection("10.0.2.2", 0L);
        
        final ILibretalkMessageEventHandler eventHandler = new ILibretalkMessageEventHandler()
        {

            @Override
            public void onMessageReceived(byte[] message)
            {              
                try
                {
                    printMsg(new String(message, "UTF-8")); // Always specify encoding!
                }
                catch (UnsupportedEncodingException ex)
                {
                    // Seriously? o-o
                    ex.printStackTrace();
                }
            }
            
            @Override
            public void onDisconnect(final Exception ex)
            {
                showErrDialog("Disconnected! - " + ex.getClass().getSimpleName(),
                              "A network error has occurred and you have been disconnected! " +
                              "Please check you are connected to the internet.",
                              ":("
                             );
            }
            
        };
        
        this.receiver = new LibretalkMessageReceiver(this.connection, eventHandler);
        this.sender = new LibretalkMessageSender(this.connection);
        
        try
        {
            this.connection.connect();
            this.receiver.startConsuming();
        }
        catch (LibretalkNetworkException ex)
        {
            this.showErrDialog("Connection failure - " + ex.getClass().getSimpleName(),
                               "Unfortunately, an error has occurred and we were unable to connect you to" +
                               "the Libretalk Network, please check you are connected to the internet.",
                               ":(");
            
            ex.printStackTrace();
            return;
        }
        //XXX NETWORKING CODE END
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_addfriend:
        	Fragment fragment = new AddFriendFragment();
            Bundle args = new Bundle();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        	
        	return true;
        	
        case R.id.action_websearch:
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new BackgroundFragment();
        Bundle args = new Bundle();
        
        args.putInt(BackgroundFragment.ARG_BACKGROUND, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        datasource.close();
        //XXX NETWORKING CODE BEGIN
        
        if (this.connection.getStatus() == LibretalkConnection.ConnectionStatus.CONNECTED)
        {
            try
            {
                this.connection.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            catch (ShutdownSignalException sig)
            {
                Log.w("libretalk::LibretalkConnection", "Caught shutdownSignal while attempting to close Channel:");
                Log.w("libretalk::LibretalkConnection", "\t[== " + sig.getMessage() + "==]");
                Log.w("libretalk::LibretalkConnection", "\tHard Error : " + sig.isHardError());
                Log.w("libretalk::LibretalkConnection", "\tReason     : " + sig.getReason());
            }
        }
        
        //XXX NETWORKING CODE END
    }
    
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        datasource.open();
        
        //XXX NETWORKING CODE BEGIN
        
        if (this.connection.getStatus() == LibretalkConnection.ConnectionStatus.NOT_CONNECTED)
        {
            try
            {
                this.connection.connect();
                this.receiver.startConsuming();
            }
            catch (LibretalkNetworkException ex)
            {
                this.showErrDialog("Connection failure - " + ex.getClass().getSimpleName(),
                        "Unfortunately, an error has occurred and we were unable to connect you to" +
                        "the Libretalk Network, please check you are connected to the internet.",
                        ":(");
            }

        }
        
        //XXX NETWORKING CODE END
    }
    
    
    public void addMessage(View view)
    {
    	EditText messageEditText = (EditText) findViewById(R.id.edit_message);
    	String message = messageEditText.getText().toString();
    	messageEditText.setText("");
    	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
    															mMessageListArray);
    	ListView listView = (ListView) findViewById(R.id.message_list);
    	listView.setAdapter(adapter);
    	listView.setStackFromBottom(true);
    	
    	//mMessageListArray.add(new String(message));
    	//adapter.notifyDataSetChanged();
    	
    	//XXX NETWORKING CODE BEGIN
    	this.sender.send(message.getBytes(), this.connection.getUserTag());
    	//XXX NETWORKING CODE END
    }
    
    //XXX NETWORKING CODE BEGIN
    private final void printMsg(final String message)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                mMessageListArray);
        ListView listView = (ListView) findViewById(R.id.message_list);
        listView.setAdapter(adapter);
        listView.setStackFromBottom(true);

        mMessageListArray.add(message);
        adapter.notifyDataSetChanged();
    }
    
    private final void showErrDialog(final String title, final String msg, final String buttonText)
    {
        final AlertDialog.Builder bob = new AlertDialog.Builder(this);
        
        // TODO replace hardcoded strings with Android Resources.
        bob.setTitle(title);
        bob.setMessage(msg);
        bob.setNeutralButton(buttonText, new DialogInterface.OnClickListener()
        {             
            @Override
            public void onClick(DialogInterface dialog, int which)
            {                    
                finish();                  
            }
        }).show();
        
    }
    //XXX NETWORKING CODE END
    
    public void addFriend(View view)
    {
    	EditText firstNameEditText = (EditText) findViewById(R.id.first_name);
    	String firstName = firstNameEditText.getText().toString();
    	firstNameEditText.setText("");
    	
    	// Ignore if the box is empty
    	if(firstName.equals(""))
    	{
    		Toast.makeText(getBaseContext(), "You must enter a name first!", Toast.LENGTH_LONG).show();
    		return;
    	}
    	
    	if(mFriendsListArray.contains(new String(firstName))) {
    		Toast.makeText(getBaseContext(), "You already have this friend!", Toast.LENGTH_LONG).show();
    		return;
    	}
    	
        
        mFriendsListArray.add(new String(firstName));
    	
    	LibretalkUser user = new LibretalkUser();
    	user.getProfile().setName(firstName);
    	this.datasource.insertUser(firstName, "", "", 0l);
    	
    	if(mFriendsListArray.get(0).equals("No friends yet, why not add some!"))
        {
        	this.mFriendsListArray.remove(0);
        }
    	
    	mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mFriendsListArray));
    }
    
    public void addFriendConfirmation(final View item){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	
    	builder.setMessage(R.string.addfriend)
        .setTitle(R.string.addfriend);
    	
    	builder.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
    	
    	builder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
        		addFriend(item);
            }
        });
    	
    	AlertDialog dialog = builder.create();
    	dialog.show();
    	
    }
    
    public void deleteFriend(String friend) {
        mFriendsListArray.remove(friend);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mFriendsListArray));
        Toast.makeText(getBaseContext(), "Friend Removed", Toast.LENGTH_LONG).show();
    }
    
    public void displayDeleteFriendConfirmation(final String item){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	
    	builder.setMessage(R.string.delete_dialog_title)
        .setTitle(R.string.delete_dialog_title);
    	
    	builder.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
    	
    	builder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
        		deleteFriend(item);
            }
        });
    	
    	AlertDialog dialog = builder.create();
    	dialog.show();
    	
    }
    

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class BackgroundFragment extends Fragment {
    	public static final String ARG_BACKGROUND = "";

        public BackgroundFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_background, container, false);
            int i = getArguments().getInt(ARG_BACKGROUND);
            int imageResourceArraySize = getResources().getStringArray(R.array.librechat_background_image_array).length;
            
            if(i >= imageResourceArraySize)
            {
            	i = (int) (Math.random() * imageResourceArraySize);
            }
            
            String background = getResources().getStringArray(R.array.librechat_background_image_array)[i];

            int imageId = getResources().getIdentifier(background.toLowerCase(Locale.getDefault()),
                            "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            //getActivity().setTitle(planet);
            return rootView;
        }
    }
    
    
    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class AddFriendFragment extends Fragment {
    	
        public AddFriendFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_add_friend, container, false);
            
            return rootView;
        }
    }
}
