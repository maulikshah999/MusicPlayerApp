package com.musicplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.musicplayer.model.Song;
import com.musicplayer.utils.Constants;
import com.musicplayer.utils.ImageAsync;

/**Song Detail Informations on different page
 * */
public class SongDetails extends AppCompatActivity {

    private ImageView ivSongThumb;
    private TextView tvSongTitle, tvSongCost, tvSongAlbumName, tvCollectionName, tvCollectionPrice;
    private Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);

        init();
    }

    private void init(){
        setTitle("ITunes");

        // Get the data from intent bundle
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            song = (Song) bundle.getParcelable(Constants.SONG_DETAILS);
        }

        tvSongTitle = (TextView) findViewById(R.id.tvSongTitleDetails);
        tvSongAlbumName = (TextView) findViewById(R.id.tvSongAlbumNameDetails);
        tvSongCost = (TextView) findViewById(R.id.tvSongCostDetails);
        ivSongThumb = (ImageView) findViewById(R.id.ivSongDetails);
        tvCollectionName = (TextView) findViewById(R.id.tvCollectioname);
        tvCollectionPrice = (TextView) findViewById(R.id.tvCollectionPrice);

        tvSongTitle.setText(song.getSong_title());
        tvSongAlbumName.setText(song.getSong_album_name());
        tvSongCost.setText(song.getSong_cost());
        tvCollectionName.setText("Collection Name: "+song.getSong_collection_name());
        tvCollectionPrice.setText("Collection Price: "+song.getSong_collection_price());

        // Image Load using async task and display in imageView
        new ImageAsync.DownloadImageTask(ivSongThumb).execute(song.getSong_img());

    }

}
