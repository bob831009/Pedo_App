package name.bagi.levente.pedometer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by bob on 2015/8/22.
 */
public class Category extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        Button fast_button = (Button) findViewById(R.id.F_button) ;
        Button middle_button = (Button)findViewById(R.id.M_button) ;
        Button low_button = (Button)findViewById(R.id.L_button) ;

        fast_button.setOnClickListener ( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Category.this , FastMusicList.class) ;
                startActivity(i);
            }
        });

        middle_button.setOnClickListener ( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Category.this , MiddleMusicList.class) ;

                startActivity(i);
            }
        });

        low_button.setOnClickListener ( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Category.this , LowMusicList.class) ;
                startActivity(i);
            }
        });
    }
}
