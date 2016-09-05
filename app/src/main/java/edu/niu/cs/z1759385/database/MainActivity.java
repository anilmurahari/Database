package edu.niu.cs.z1759385.database;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DBAdapter myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DBAdapter(this);
        myDB.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDB.close();
    }
    private void displayText(String message)
    {
        TextView dbContents = (TextView)findViewById(R.id.databaseTextView);
        dbContents.setText(message);
    }
    public void addRecord(View view)
    {
        long newID = myDB.insertRow("Anil",123);
        displayText("Clicked add button - add id : " + newID);
    }
    public void onClear(View view)
    {
        displayText("Clicked clear button");
        myDB.deleteAll();
    }
    public void displayDB(View view)
    {
        displayText("Clicked display button");
        Cursor cursor = myDB.getAllRows();


        if(cursor.moveToFirst())
        {
            String message = "";
            boolean isData = cursor.moveToFirst();
            while(isData)
            {
                int id = cursor.getInt(DBAdapter.COL_ROWID),
                        studNum = cursor.getInt(DBAdapter.COL_STUDENTNUM);
                String name = cursor.getString(DBAdapter.COL_NAME);

                message += "id: " + id + " ,name: " + name + " , number: "+ studNum + "\n";
                isData = cursor.moveToNext();
            }
            displayText(message);
        }
        cursor.close();
    }
}