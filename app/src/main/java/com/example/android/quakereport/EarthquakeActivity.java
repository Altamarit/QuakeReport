/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    //private static final String USGS_REQUEST_URL =
    //        "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2017-01-01&endtime=2017-03-02&minmagnitude=7";
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2017-03-01&endtime=2017-03-02&minmagnitude=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.earthquake_activity);



        EarthquakeAssyncTask newtask = new EarthquakeAssyncTask();
        newtask.execute(USGS_REQUEST_URL);



    }

    private void updateUi(ArrayList<Earthquake> earthquakes){

        // Create an Adaptor view The adapter knows how to create list item views for each item
        // in the list.
        EarthquakeAdapter quakesAdapter = new EarthquakeAdapter(this, earthquakes);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(quakesAdapter);

        // Set and define the listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Intention is to display the web page with the detailed information of an EarthQuake
                // Item Clicked is an Earthquake object
                Earthquake itemClicked = (Earthquake) adapterView.getItemAtPosition(i);

                // Set te URL
                String url = itemClicked.getDetailPage();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    private class EarthquakeAssyncTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {
        @Override
        protected ArrayList<Earthquake> doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            // Perform the HTTP request for earthquake data and process the response.
            // Create a fake list of earthquakes.
            ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes(USGS_REQUEST_URL);

            return earthquakes;
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> result) {
            if (result==null){
                return;
            }
            updateUi(result);
        }
    }
}
