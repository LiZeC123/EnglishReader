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
import java.io.IOException;

import dataGetter.DataModel;
import dataGetter.ModelGetter;
import dataGetter.TranslatePair;

public class MainActivity extends AppCompatActivity {
    TranslatePair thisPair;
    DataModel thisModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化文件夹
        File dataSpaceDir = initDir();

        if(isInitFiles(dataSpaceDir)){
            initFiles(dataSpaceDir);
        }
        thisModel = initModel(dataSpaceDir);
        thisPair = thisModel.getPair();


        final TextView txtViewTag = (TextView) findViewById(R.id.txtViewTag);
        final TextView txtViewWord = (TextView) findViewById(R.id.txtViewWord);
        Button btnTag = (Button) findViewById(R.id.btnTag);
        Button btnNext = (Button) findViewById(R.id.btnNext);

        btnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtViewTag.setText(thisPair.Hide);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisPair = thisModel.getPair();
                txtViewTag.setText("");
                txtViewWord.setText(thisPair.Look);
            }
        });
    }

    private File initDir(){
        File wordFileDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        assert wordFileDir != null;
        if(!wordFileDir.exists()){
            boolean b = wordFileDir.mkdir();
            Log.d("初始化文件夹",Boolean.toString(b));
        }
        return wordFileDir;
    }

    private boolean isInitFiles(File dataSpaceDir){
        File readSentence      = new File(dataSpaceDir,"readSentence.md");
        File translateSentence = new File(dataSpaceDir,"translateSentence.md");
        File translateWord     = new File(dataSpaceDir,"translateWord.md");

        return !(readSentence.exists() && translateSentence.exists() && translateWord.exists());
    }

    private boolean initFiles(File dataSpaceDir){
        File readSentence      = new File(dataSpaceDir,"readSentence.md");
        File translateSentence = new File(dataSpaceDir,"translateSentence.md");
        File translateWord     = new File(dataSpaceDir,"translateWord.md");

        boolean isSuccess = false;
        try{
            isSuccess = readSentence.createNewFile();
            isSuccess &= translateSentence.createNewFile();
            isSuccess &= translateWord.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  isSuccess;
    }

    private DataModel initModel(File dataSpaceDir) {
        File readSentence      = new File(dataSpaceDir,"readSentence.md");
        //File translateSentence = new File(dataSpaceDir,"translateSentence.md");
        //File translateWord     = new File(dataSpaceDir,"translateWord.md");

        DataModel model;
        try {
            model = ModelGetter.mdToList(readSentence);
        } catch (Exception e) {
            model = new DataModel();
            model.add(new TranslatePair("暂无数据","无本地解释"));
        }

        return model;
    }
}
