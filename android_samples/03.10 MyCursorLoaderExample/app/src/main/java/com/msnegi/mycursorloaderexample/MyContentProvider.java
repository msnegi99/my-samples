package com.msnegi.mycursorloaderexample;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;

public class MyContentProvider extends ContentProvider
{
    static final String PROVIDER_NAME = "com.msnegi.mycursorloaderexample.BirthdayProv";
    static final String URL = "content://" + PROVIDER_NAME + "/friends";
    static final Uri CONTENT_URI = Uri.parse(URL);

    // integer values used in content URI
    static final int FRIENDS = 1;
    static final int FRIENDS_ID = 2;

    DBHelper dbHelper;

    private static HashMap<String, String> BirthMap;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "friends", FRIENDS);
        uriMatcher.addURI(PROVIDER_NAME, "friends/#", FRIENDS_ID);
    }

    // database declarations
    private SQLiteDatabase database;

    @Override
    public boolean onCreate()
    {
        Context context = getContext();
        dbHelper = new DBHelper(context);

        // permissions to be writable
        database = dbHelper.getWritableDatabase();

        if(database == null)
            return false;
        else
            return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DBHelper.TABLE_NAME);

        int match = uriMatcher.match(uri);

        switch (match)
        {
            // maps all database column names
            case FRIENDS:
                queryBuilder.setProjectionMap(BirthMap);
                break;
            case FRIENDS_ID:
                queryBuilder.appendWhere( DBHelper.ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (sortOrder == null || sortOrder == "")
        {
            sortOrder = DBHelper.NAME;
        }

        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        long row = database.insert(DBHelper.TABLE_NAME, "", values);

        // If record is added successfully
        if(row > 0)
        {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }

        throw new SQLException("Fail to add a new record into " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        int rec_count = 0;
        int match = uriMatcher.match(uri);

        switch (match)
        {
            case FRIENDS:
                rec_count = database.update(DBHelper.TABLE_NAME, values, selection, selectionArgs);
                break;
            case FRIENDS_ID:
                String id = uri.getLastPathSegment();	//gets the id
                String where = DBHelper.ID  + " = " + id;

                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                rec_count = database.update(DBHelper.TABLE_NAME,
                                            values,
                                            where,
                                            selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri );
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rec_count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        int rec_count = 0;
        int match = uriMatcher.match(uri);

        switch (match)
        {
            case FRIENDS:
                // delete all the records of the table
                rec_count = database.delete(DBHelper.TABLE_NAME, selection, selectionArgs);
                break;

            case FRIENDS_ID:
                String id = uri.getLastPathSegment();	//gets the id
                rec_count = database.delete( DBHelper.TABLE_NAME, DBHelper.ID +  " = " + id, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rec_count;
    }

    @Override
    public String getType(Uri uri)
    {
        int match = uriMatcher.match(uri);

        switch (match)
        {
            // Get all friend-birthday records
            case FRIENDS:
                return "vnd.android.cursor.dir/friends";

            // Get a particular friend
            case FRIENDS_ID:
                return "vnd.android.cursor.item/friends";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

}
