package dataGetter;

import java.util.ArrayList;


public class DataModel extends ArrayList<TranslatePair>{

    String tag;
    private int count = -1;


    public TranslatePair getPair(){
        count++;
        if(count >= this.size()){
            count = 0;
        }
        return this.get(count);
    }
}