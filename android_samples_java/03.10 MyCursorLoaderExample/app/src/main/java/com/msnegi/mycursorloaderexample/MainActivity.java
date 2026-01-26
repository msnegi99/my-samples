package com.msnegi.mycursorloaderexample;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView lstList;
    private EditText edtName;
    private EditText edtDOB;
    private Button btnSave;

    //private SimpleCursorAdapter adapter;
    private MyCustomAdapter customAdapter;
    Uri todoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstList = (ListView)findViewById(R.id.lstList);
        edtName = (EditText)findViewById(R.id.edtName);
        edtDOB = (EditText)findViewById(R.id.edtDOB);
        btnSave = (Button)findViewById(R.id.btnSave);

        String[] from = new String[] { DBHelper.ID, DBHelper.NAME, DBHelper.BIRTHDAY };
        int[] to = new int[] { R.id.txtId, R.id.txtName, R.id.txtBirthday };

        getLoaderManager().initLoader(0, null, this);
        //adapter = new SimpleCursorAdapter(this, R.layout.item_row, null, from, to, 0);

        Cursor c = getContentResolver().query(MyContentProvider.CONTENT_URI, null, null, null, "name");
        customAdapter = new MyCustomAdapter(MainActivity.this, c);

        //lstList.setAdapter(adapter);
        lstList.setAdapter(customAdapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String name = edtName.getText().toString();
                String dob = edtDOB.getText().toString();

                if(!name.isEmpty() && !dob.isEmpty())
                {
                    ContentValues values = new ContentValues();
                    values.put(DBHelper.NAME, name);
                    values.put(DBHelper.BIRTHDAY, dob);

                    todoUri = getContentResolver().insert(MyContentProvider.CONTENT_URI, values);

                    edtName.setText("");
                    edtDOB.setText("");
                }
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { DBHelper.ID, DBHelper.NAME, DBHelper.BIRTHDAY };
        //cursorloader will help you find the contentprovider you provide to query your given uri
        return new CursorLoader(this, MyContentProvider.CONTENT_URI, projection, null, null, null);
    }

    //在cursorloader 处理完成之后调用
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //adapter.swapCursor(data);
        customAdapter.swapCursor(data);
    }

    //在cursorloader重置时候调用
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //adapter.swapCursor(null);
        customAdapter.swapCursor(null);
    }
}
