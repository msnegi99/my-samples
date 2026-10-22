package com.msnegi.mycursorloaderexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MyCustomAdapter extends CursorAdapter {

    Context context;

    public MyCustomAdapter(Context context, Cursor c)
    {
        super(context, c);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // when the view will be created for first time,
        // we need to tell the adapters, how each item will look
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.item_row, parent, false);

        return retView;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // here we are setting our data
        // that means, take the data from the cursor and put it in views

        final TextView txtId = (TextView) view.findViewById(R.id.txtId);
        txtId.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0))));

        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        txtName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));

        TextView txtBirthday = (TextView) view.findViewById(R.id.txtBirthday);
        txtBirthday.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));

        Button btnEdit = (Button) view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ContentValues values = new ContentValues();
                values.put(DBHelper.NAME, "Mahender");
                values.put(DBHelper.BIRTHDAY, "28-03-1974");

                final String PROVIDER_NAME = "com.msnegi.mycursorloaderexample.BirthdayProv";
                final String URL = "content://" + PROVIDER_NAME + "/friends/" + txtId.getText().toString();
                final Uri CONTENT_URI = Uri.parse(URL);

                context.getContentResolver().update(CONTENT_URI, values , null, null);
            }
        });

        Button btnDelete = (Button) view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final String PROVIDER_NAME = "com.msnegi.mycursorloaderexample.BirthdayProv";
                final String URL = "content://" + PROVIDER_NAME + "/friends/" + txtId.getText().toString();
                final Uri CONTENT_URI = Uri.parse(URL);

                context.getContentResolver().delete(CONTENT_URI, null , null);
            }
        });

    }
}