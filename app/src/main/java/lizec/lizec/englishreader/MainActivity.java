package lizec.lizec.englishreader;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;

import reader.WordGetter;

public class MainActivity extends AppCompatActivity {
    WordGetter wordGetter = new WordGetter();
    WordGetter.WordTagPair thisPair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File wordFileDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if(!wordFileDir.exists()){
            boolean b = wordFileDir.mkdir();
            Log.d("mkdir",Boolean.toString(b));
        }

        File wordFile = new File(wordFileDir,"wordList.txt");
        try {
            wordGetter.readWord(wordFile);
            thisPair = wordGetter.getRandomPair();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final TextView txtViewTag = (TextView) findViewById(R.id.txtViewTag);
        final TextView txtViewWord = (TextView) findViewById(R.id.txtViewWord);
        Button btnTag = (Button) findViewById(R.id.btnTag);
        Button btnNext = (Button) findViewById(R.id.btnNext);

        btnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtViewTag.setText(thisPair.tag);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisPair = wordGetter.getRandomPair();
                txtViewTag.setText("");
                txtViewWord.setText(thisPair.word);
            }
        });
    }
}
