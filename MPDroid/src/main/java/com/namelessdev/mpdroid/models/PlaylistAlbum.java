package com.namelessdev.mpdroid.models;

import com.namelessdev.mpdroid.helpers.AlbumInfo;

import com.anpmech.mpd.item.Directory;
import com.anpmech.mpd.item.Music;
import com.anpmech.mpd.item.Artist;
import com.anpmech.mpd.item.Album;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.text.TextUtils.isEmpty;
import static android.text.TextUtils.join;


/*
 * Playlist representation of one album
 * to show one line for a sequence of tracks belonging to one album
 */
public class PlaylistAlbum extends AbstractPlaylistMusic {

    int lastPlayingID = -1;
    boolean isPlaying = false;

    int firstSongId = -1;

    List<Music> music;
    Album album = null;

    AlbumInfo ainfo;

    public PlaylistAlbum(AlbumInfo ainfo) {
        super(new Music());
        this.ainfo = ainfo;
        music = new ArrayList<Music>();
    }

    public PlaylistAlbum(List<Music> music) {
        super(new Music());
        this.music = music;
        Music m = music.get(0);
        this.album = m.getAlbum();
    }

    public void add(Music m) {
        if (music.size() == 0)  {
            firstSongId = m.getSongId();
        }
        music.add(m);
    }

    public List<Music> getMusic() {
        return music;
    }

    // identify by first song id
    public int getSongId() {
        return firstSongId;
    }

    public boolean hasSongId(int songId) {
        for (Music m : music) {
            if (m.getSongId() == songId) {
                return true;
            }
        }
        return false;
    }

    // all ids
    public List<Integer> getSongIds() {
        List<Integer> l = new ArrayList<Integer>();
        for (Music m : music) {
            l.add(m.getSongId());
        }
        return l;
    }

    public void setLastPlayingID(int id) {
        lastPlayingID = id;
    }
    public int getLastPlayingID() {
        return lastPlayingID;
    }

    public void setPlaying(boolean p) {
        isPlaying = true;
    }
    public boolean isPlaying() {
        return isPlaying;
    }

    public AlbumInfo getAlbumInfo() {
        return ainfo;
    }

    public String getPlayListMainLine() {
        if (ainfo == null) return "Empty";
        String album =  ainfo.getAlbumName();
        if (album == null) album = "";
        return "("+music.size()+") ["+album+"]";
    }

    public String getPlaylistSubLine() {
        if (ainfo == null) return "Empty";
        String artist =  ainfo.getArtistName();
        if (artist == null) artist = "";
        return "["+artist+"]";
    }

    public int size() {
        return music.size();
    }

    public String getArtistName() {
        return ainfo == null ? "null": ainfo.getArtistName();
    }

    public String getAlbumName() {
        return ainfo == null ? "null": ainfo.getAlbumName();
    }

    public Album getAlbum() {
        return music.get(0).getAlbum();
    }

    public Artist getArtist() {
        return music.get(0).getArtist();
    }

    public String getAlbumArtistName() {
        return  music.get(0).getAlbumArtistName();
    }
    public Artist getAlbumArtist() {
        return music.get(0).getAlbumArtist();
    }

    public boolean hasText(String filter) {
        if ( (getArtistName() != null ? getArtistName() : "")
             .toLowerCase(Locale.getDefault()).contains(filter) ||
             (getAlbumName() != null ? getAlbumName() : "")
             .toLowerCase(Locale.getDefault()).contains(filter)) {
            return true;
        }
        for (Music m : music) {
            if ( (m.getTitle() != null ? m.getTitle() : "")
                 .toLowerCase(Locale.getDefault()).contains(filter)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "PlAlbum: " + ainfo.getArtistName() + "/"+ ainfo.getAlbumName() + " (" + music.size()+")";
    }


}
