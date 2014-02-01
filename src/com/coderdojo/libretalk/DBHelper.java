package com.coderdojo.libretalk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	// Database constants
	private static final String DATABASE_NAME = "andchat.db";
	private static final int DATABASE_VERSION = 2;
	
	// Users Table
	public static final String TABLE_USERS = "usersTable";
	public static final String COLUMN_ID = "c_id";
	public static final String COLUMN_NAME = "c_name";
	public static final String COLUMN_META = "c_meta";
	public static final String COLUMN_BIO = "c_bio";
	public static final String COLUMN_JOINDATE = "c_joindate";
	
	
	// Create Users Table
	private static final String CREATE_TABLE_USERS = "create table "
			+ TABLE_USERS + "( "
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_NAME + " text not null, "
			+ COLUMN_META + " text not null, "
			+ COLUMN_BIO + " text not null, "
			+ COLUMN_JOINDATE + " text not null "
			+ ");";
	
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_USERS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop the Users Table
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		
		// Rebuild the DB
		onCreate(db);
	}

}
