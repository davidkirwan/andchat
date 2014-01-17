package com.coderdojo.andchat;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

	private SQLiteDatabase database;
	private DBHelper dbHelper;

	public DBManager(Context context) {
		dbHelper = new DBHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	public void insertUser(String name, String meta, String bio, long joindate) {
		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_NAME, name);
		values.put(DBHelper.COLUMN_META, meta);
		values.put(DBHelper.COLUMN_BIO, bio);
		values.put(DBHelper.COLUMN_JOINDATE, String.valueOf(joindate));
		database.insert(DBHelper.TABLE_USERS, null, values);
	}

	public void deleteUser(AndchatUser u) {
		long id = u.getId();
		database.delete(DBHelper.TABLE_USERS, DBHelper.COLUMN_ID + " = " + id, null);
	}
	
	public void updateUser(AndchatUser u){
		ContentValues values = new ContentValues();
		
		values.put(DBHelper.COLUMN_NAME, u.getProfile().getName());
		values.put(DBHelper.COLUMN_META, u.getProfile().getMetaData());
		values.put(DBHelper.COLUMN_BIO, u.getProfile().getBio());
		values.put(DBHelper.COLUMN_JOINDATE, String.valueOf( u.getProfile().getJoinDate().getEpoch()) );
		
		long insertId = database.update(DBHelper.TABLE_USERS, values, DBHelper.COLUMN_ID + " = " + u.getId(), null);
	}	

	public List<AndchatUser> getAllUsers() {
		List<AndchatUser> users = new ArrayList<AndchatUser>();
		Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_USERS, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			AndchatUser r = cursorToUser(cursor);
			users.add(r);
			cursor.moveToNext();
		}
		cursor.close();
		return users;
	}

	private AndchatUser cursorToUser(Cursor cursor) {
		AndchatUser u = new AndchatUser(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getLong(4));
		return u;
	}

}
