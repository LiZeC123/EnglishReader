package manager;


import java.io.File;
import java.util.ArrayList;

import dataGetter.DataModel;
import dataGetter.ModelGetter;
import dataGetter.TranslatePair;

public class ModelManager {
    private ArrayList<DataModel> modelList;
    private int mIdx = 0;

    public ModelManager(File dataSpaceDir){
        modelList = new ArrayList<>();
        reload(dataSpaceDir);
    }


    public void changeModel(){
        mIdx++;
        if(mIdx >= modelList.size()){
            mIdx = 0;
        }
    }

    public DataModel getModel(){
        return modelList.get(mIdx);
    }

    /**
     *  从网络重新加载数据
     * @param dataSpaceDir 文件保存位置根目录
     */
    public void reload(File dataSpaceDir){
        String[] nameList = {"readSentence.md","translateSentence.md","translateWord.md"};

        for(String name:nameList){
            File file = new File(dataSpaceDir,name);
            DataModel model;
            try {
                model = ModelGetter.mdToList(file);
            } catch (Exception e) {
                model = new DataModel();
                model.add(new TranslatePair("正在从网络下载数据，请稍后重启程序...","无本地解释"));
            }
            modelList.add(model);
        }
    }


}
