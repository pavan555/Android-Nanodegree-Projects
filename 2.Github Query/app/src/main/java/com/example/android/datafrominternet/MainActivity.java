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
package com.example.android.datafrominternet;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.datafrominternet.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static com.example.android.datafrominternet.R.*;

public class MainActivity extends AppCompatActivity {

    private EditText search_url;
    private TextView url_displayl;
    private TextView result;
    private TextView error_display_text;
    private ProgressBar pbl;
    // TODO (26) Create an EditText variable called mSearchBoxEditText

    // TODO (27) Create a TextView variable called mUrlDisplayTextView
    // TODO (28) Create a TextView variable called mSearchResultsTextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);



        search_url= (EditText) findViewById(id.search_url);

        url_displayl= (TextView) findViewById(id.url_display);

        result= (TextView) findViewById(id.result);

        error_display_text= (TextView) findViewById(id.error_display_tv);

        pbl= (ProgressBar) findViewById(id.pb_loader);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    public void getQueryText(){

        String search_url_text=search_url.getText().toString();
        URL github_search_url=NetworkUtils.buildUrl(search_url_text);
        url_displayl.setText(github_search_url.toString());
        new github_query_thread().execute(github_search_url);
    }

    public void jsonDataView(){
        error_display_text.setVisibility(View.INVISIBLE);
        result.setVisibility(View.VISIBLE);
    }
    public void showErrorView(){
        error_display_text.setVisibility(View.VISIBLE);
        result.setVisibility(View.INVISIBLE);
    }
    public class github_query_thread extends AsyncTask<URL,Void,String>{


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pbl.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            String str=null;
            URL url=urls[0];
            try {
                str=NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }

        @Override
        protected void onPostExecute(String s) {
            pbl.setVisibility(View.INVISIBLE);

                if( s!=null && !s.equals("")) {
                    jsonDataView();
                    result.setText(s);
                }else{
                    showErrorView();
                }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuItemSelected=item.getItemId();

        if( menuItemSelected == id.action_search){

            getQueryText();
            return true;
            //this is an Error(NetworkOnMainThreadEXception)
           /* try {
                response=NetworkUtils.getResponseFromHttpUrl(getQueryText());
            } catch (IOException e) {
                e.printStackTrace();
            }*/


            
            //Toast.makeText(context,response,Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }
}
