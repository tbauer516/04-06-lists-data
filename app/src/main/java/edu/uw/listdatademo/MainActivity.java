package edu.uw.listdatademo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ArrayAdapter<String> middleMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

//        Button btn = (Button) findViewById(R.id.btnSearch);
//        btn .setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.v(TAG, "Button Clicked!");
//            }
//        });

//        model
//        String[] data = new String[99];
//        for (int i = 99; i > 0; i--) {
//            data[99-i] = i + " bottes of beer on the wall";
//        }
//        Log.v(TAG, "Data:" + Arrays.toString(data));

//        view
//        -> See the XML

//        controller
        middleMan = new ArrayAdapter<String>(
                this, R.layout.list_item, R.id.txtItem, new ArrayList<String>());

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(middleMan);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = (String) parent.getItemAtPosition(position);
                Log.v(TAG, "You clicked: " + text);
            }
        });

    }

    public void handleClick(View v) {
        Log.v(TAG, "Button Handled!");

        EditText text = (EditText) findViewById(R.id.txtSearch);
        String searchTerm = text.getText().toString();
        Log.v(TAG, "You searched for: " + searchTerm);

        MovieDownloadTask task = new MovieDownloadTask();
        task.execute("Titanic");

    }

    public class MovieDownloadTask extends AsyncTask<String, Void, String[]> {


        @Override
        protected String[] doInBackground(String... params) {

            return MovieDownloader.downloadMovieData(params[0]);

        }

        @Override
        protected void onPostExecute(String[] movies) {
            super.onPostExecute(movies);

            middleMan.clear();
            for (String movie : movies) {
                middleMan.add(movie);
            }

        }
    }

}
