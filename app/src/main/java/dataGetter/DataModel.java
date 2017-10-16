package dataGetter;

import java.util.ArrayList;


public class DataModel extends ArrayList<TranslatePair>{

    String tag;
    private int count = 0;


    public TranslatePair getPair(){
        return this.get((count++)%this.size());
    }
}