package name.bagi.levente.pedometer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by bob on 2015/8/22.
 */
public class MiddleMusicList extends Activity{
    String Path ;
    ListView songs_list ;
    String[] items;
    private MediaPlayer mp3 = new MediaPlayer();
    ArrayList<File> Songs ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Path = Environment.getExternalStorageDirectory().getPath();
        Toast.makeText(getApplicationContext() , Path , Toast.LENGTH_LONG) ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_music_list);

        Button AddSongButton = (Button) findViewById(R.id.Add_song_button) ;
        AddSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MiddleMusicList.this , ShowTotalMusic.class) ;
                String speed = "middle" ;
                Bundle bundle = new Bundle();
                bundle.putString("speed" , speed);
                i.putExtras(bundle) ;
                startActivity(i);
            }
        });

        songs_list = (ListView) findViewById(R.id.music_list);
        DbHelper db = new DbHelper(this) ;
        Cursor res = db.get("middle") ;
        if(res.getCount() == 0){
            return ;
        }
        int res_size = (int)res.getCount() ;

        items = new String[ res_size ];
        res.moveToFirst() ;
        for(int i = 0  ; i < res_size; i++ , res.moveToNext()){


//            Songs.addAll( findSongs(Environment.getExternalStorageDirectory() , res.getString(1)) );
//            if(Songs.isEmpty())
//                Toast.makeText(getApplicationContext() , "Songs is GG" , Toast.LENGTH_SHORT).show() ;
            items[i] = res.getString(1).replace(".mp3", "").replace(".wav","");

//            for ( int j = 0 ; j < 1 ; j++)
//                Toast.makeText(getApplicationContext() , Songs.get(j).getName() , Toast.LENGTH_SHORT).show() ;

        }
        ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(), R.layout.song_layout, R.id.textView, items);
        songs_list.setAdapter(adp);
//
//
        songs_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext() , "good" , Toast.LENGTH_LONG).show() ;
//                if (mp3 != null) {
//                    mp3.stop();
//                    mp3.release();
//                }
//
//                Uri u = Uri.parse(Songs.get(position).toString());
//                Toast.makeText(getApplicationContext(), Songs.get(position).getName().toString(), Toast.LENGTH_LONG).show();
//                mp3 = MediaPlayer.create(getApplicationContext(), u);
//                mp3.start();
                  Toast.makeText(getApplicationContext(), "click!", Toast.LENGTH_LONG).show();
            }
//
        });

    }
    public ArrayList<File> findSongs(File root , String song_name){
        ArrayList<File> al = new ArrayList<File>();
        File[] files = root.listFiles();
        for(File singleFile : files){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                al.addAll(findSongs(singleFile, song_name));
            }
            else{
                if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")){
                    if(singleFile.getName().equals(song_name))
                        al.add(singleFile);
                }
            }
        }
        return al;
    }
}
