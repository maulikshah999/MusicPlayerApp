package com.musicplayer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.musicplayer.adapter.MusicListAdapter;
import com.musicplayer.model.Song;
import com.musicplayer.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends AppCompatActivity {

    private ListView lvMusic;
    private MusicListAdapter adapter;
    private List<Song> listSongs = new ArrayList<Song>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        init();

    }

    private void init(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("ITunes");

        lvMusic = (ListView) findViewById(R.id.musicLV);
        adapter = new MusicListAdapter(this, listSongs);
        lvMusic.setAdapter(adapter);
        lvMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                /** Extra Point: Open song detail activity */
                Intent intent = new Intent(MusicActivity.this, SongDetails.class);
                intent.putExtra(Constants.SONG_DETAILS, listSongs.get(position));
                startActivity(intent);
            }
        });

        // Request to get data from Url
        new MyHttpRequestTask().execute(Constants.iTune_Url,"");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyHttpRequestTask extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            String my_url = params[0];
            String my_data = params[1];
            String response = "";
            try {
                URL url = new URL(my_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                try{

                    httpURLConnection.setDoOutput(true);

                    httpURLConnection.setChunkedStreamingMode(0);

                   InputStream in = new URL(my_url).openStream();
                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    StringBuilder total = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        total.append(line).append('\n');
                    }
                    response = total.toString();
                    Log.d("Response",""+response);

                }catch (Exception e){
                    e.printStackTrace();
                }finally {

                    httpURLConnection.disconnect();
                }


            }catch (Exception e){
                e.printStackTrace();
            }

            return response;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result.length() > 0){
                listSongs.clear();
                try{
                    /**Parsing JSON Object from the result
                     * */
                    JSONObject mainObj = new JSONObject(result);
                    JSONArray jArray = mainObj.getJSONArray("results");

                    JSONObject songJObj = null;
                    for(int i=0;i<jArray.length();i++){
                        songJObj = jArray.getJSONObject(i);
                        /**Store the data into 'Song' model
                         * */
                        Song song = new Song();
                        song.setSong_album_name(songJObj.getString(Constants.ARTIST_NAME));
                        song.setSong_title(songJObj.getString(Constants.TRACK_NAME));
                        song.setSong_cost("$"+songJObj.getDouble(Constants.TRACK_PRICE));
                        song.setSong_img(songJObj.getString(Constants.ARTWORKURL100));
                        song.setSong_collection_name(songJObj.getString(Constants.COLLECTION_NAME));
                        song.setSong_collection_price(""+songJObj.getDouble(Constants.COLLECTION_PRICE));
                        /**add model into list
                         * */
                        listSongs.add(song);
                    }
                } catch(JSONException je){
                    je.printStackTrace();
                }

                // Notify Adapter for data changes
                adapter.notifyDataSetChanged();

            }

        }
    }

}
