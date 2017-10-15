package dataGetter;

/**
 *  翻译对，用于表示一组双语数据
 */

public class TranslatePair {
    public String Look;
    public String Hide;

    public TranslatePair(String Look,String Hide){
        this.Look = Look;
        this.Hide = Hide;
    }

    @Override
    public String toString() {
        return "TranslatePair [Look=" + Look + ",\n Hide=" + Hide + "]";
    }
}
