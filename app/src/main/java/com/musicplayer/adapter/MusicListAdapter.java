package com.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.musicplayer.R;
import com.musicplayer.model.Song;
import com.musicplayer.utils.ImageAsync;

import java.util.List;

/**
 * Created by Maulik on 12/8/2017.
 */

public class MusicListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Song> listOfSongs;

    public MusicListAdapter(Context context, List<Song> listSongs) {
        mContext = context;
        listOfSongs = listSongs;
    }

    @Override
    public int getCount() {
        return listOfSongs.size();
    }

    @Override
    public Object getItem(int i) {
        return listOfSongs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        ImageView ivSong;
        TextView tvSongTitle, tvSongAlbumName, tvSongCost;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rowView = view;
        if (view == null) {
            rowView = LayoutInflater.from(mContext).inflate(R.layout.raw_music_player, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.ivSong = (ImageView) rowView.findViewById(R.id.iv_music_thumb);
            viewHolder.tvSongTitle = (TextView) rowView.findViewById(R.id.tvMusicTitle);
            viewHolder.tvSongAlbumName = (TextView) rowView.findViewById(R.id.tvMusicAlbumName);
            viewHolder.tvSongCost = (TextView) rowView.findViewById(R.id.tvMusicCost);

            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.tvSongTitle.setText(listOfSongs.get(position).getSong_title());
        holder.tvSongAlbumName.setText(listOfSongs.get(position).getSong_album_name());
        holder.tvSongCost.setText(listOfSongs.get(position).getSong_cost());

        new ImageAsync.DownloadImageTask(holder.ivSong).execute(listOfSongs.get(position).getSong_img());

        return rowView;
    }



}
