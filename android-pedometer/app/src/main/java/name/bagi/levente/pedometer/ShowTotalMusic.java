package name.bagi.levente.pedometer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Created by bob on 2015/8/22.
 */

class Mp3Filter implements FilenameFilter {
    public boolean accept(File dir, String name){
        return (name.endsWith(".mp3") || name.endsWith(".wav"));
    }
}

public class ShowTotalMusic extends Activity {

    ListView songs_list ;
    ListView source ;
    String[] items;
    ArrayList<File> Songs ;
    private MediaPlayer mp3 = new MediaPlayer();
    String speed ;
    String Path ;

//    Intent intent = this.getIntent() ;
//    Bundle b = intent.getExtras() ;
//    String speed = getIntent().getExtras().getString("speed") ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Path = Environment.getExternalStorageDirectory().getPath() ;

        speed = getIntent().getExtras().getString("speed") ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_music_list);


        songs_list = (ListView) findViewById(R.id.TotalMusicList);
        Songs = findSongs(Environment.getExternalStorageDirectory());

        items = new String[ Songs.size() ];
        for(int i = 0; i < Songs.size(); i++){
            //toast(Songs.get(i).getName().toString());
            items[i] = Songs.get(i).getName().toString().replace(".mp3","").replace(".wav","");
        }

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(), R.layout.song_layout, R.id.textView, items);
        songs_list.setAdapter(adp);
        songs_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mp3 != null) {
                    mp3.stop();
                    mp3.release();
                }

                Uri u = Uri.parse(Songs.get(position).toString());
                Toast.makeText(getApplicationContext(), Songs.get(position).getName().toString() , Toast.LENGTH_LONG).show();
                mp3 = MediaPlayer.create(getApplicationContext(), u);
                mp3.start();
            }

        });
        AdapterView.OnItemLongClickListener itemLongListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                new AlertDialog.Builder(ShowTotalMusic.this)
                        .setTitle("確認視窗")
                        .setMessage("確定要加入此音樂嗎?")
                        .setPositiveButton("確定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (mp3 != null) {
                                            mp3.stop();
                                            mp3.release();
                                        }
                                        DbHelper db = new DbHelper(ShowTotalMusic.this);

                                        boolean isInserted = db.create(Songs.get(position).getName().toString(), speed );
                                        if (isInserted)
                                            Toast.makeText(getApplicationContext(), "Insertion success!", Toast.LENGTH_LONG).show();
                                        else {
                                            Toast.makeText(getApplicationContext(), "Insertion failed!", Toast.LENGTH_LONG).show();
                                        }

                                        finish();
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub

                                    }
                                }).show();
                return true;
            }
        };

        songs_list.setOnItemLongClickListener(itemLongListener);

    }

    public ArrayList<File> findSongs(File root){
        ArrayList<File> al = new ArrayList<File>();
        File[] files = root.listFiles();
        for(File singleFile : files){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                al.addAll(findSongs(singleFile));
            }
            else{
                if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")){
                    al.add(singleFile);
                }
            }
        }
        return  al;
    }

//
}
