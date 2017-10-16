package lizec.lizec.englishreader;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import dataGetter.DataModel;
import dataGetter.ModelGetter;
import dataGetter.TranslatePair;
import http.HttpRequest;
import manager.ModelManager;

public class MainActivity extends AppCompatActivity {
    TranslatePair thisPair;
    DataModel thisModel;
    ModelManager manager;
    File dataSpaceDir;

    private static final int RELOAD_FINISH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化文件夹
        dataSpaceDir = initDir();

        if(isInitFiles(dataSpaceDir)){
            initFiles();
        }

        manager = new ModelManager(dataSpaceDir);
        thisModel = manager.getModel();
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_changeMode:
                manager.changeModel();
                thisModel = manager.getModel();
                Toast.makeText(this,"切换完成",Toast.LENGTH_SHORT).show();
                Log.i("菜单","切换模式");
                return true;
            case R.id.action_reload:
                initFiles();
                Log.i("菜单","重新载入");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
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

    private void initFiles(){
        new Thread(ReloadDataThread).start();
    }

    Runnable ReloadDataThread = new Runnable() {
        @Override
        public void run() {
            String[] nameList = {"readSentence.md","translateSentence.md","translateWord.md"};
            for(String name:nameList){
                try{
                    PrintStream out =  new PrintStream(
                            new BufferedOutputStream(
                                    new FileOutputStream(
                                            new File(dataSpaceDir,name))));

                    String read = HttpRequest.sendGet(
                            "https://raw.githubusercontent.com/LiZeC123/" +
                                    "Data/master/English/" + name,"");
                    out.print(read);
                    out.close();
                    Log.i("网络下载完成",name);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }

            Message msg = new Message();
            msg.what = RELOAD_FINISH;
            handler.sendMessage(msg);
        }
    };

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case RELOAD_FINISH:
                    manager.reload(dataSpaceDir);
                    thisModel = manager.getModel();
                    Toast.makeText(MainActivity.this,"更新完成",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
