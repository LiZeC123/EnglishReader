package reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WordGetter {
    private String tag;
    private ArrayList<WordTagPair> arrayList;
    private Random random = new Random();

    public class WordTagPair{
        public String word;
        public String tag;

        public WordTagPair(String word, String tag) {
            super();
            this.word = word;
            this.tag = tag;
        }


    }

    public WordGetter() {
        //tag = "None";

    }


    public void readWord(File file) throws FileNotFoundException{
        Scanner in = new Scanner(new FileInputStream(file));
        arrayList = new ArrayList<>();
        while (in.hasNext()) {
            String string =  in.nextLine();
            if(string.contains(">")){
                tag = string;
            }
            else if(!string.isEmpty()){
                arrayList.add(new WordTagPair(string, tag));
            }
        }
        in.close();
    }

    public WordTagPair getRandomPair(){
        int index = random.nextInt(arrayList.size());
        return arrayList.get(index);
    }

    public void show(){
        for(WordTagPair pair:arrayList){
            System.out.println(pair.tag +"-->"+pair.word);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
//		File file = new File("word.txt");
//		WordGetter wordGetter = new WordGetter();
//		wordGetter.readWord(file);
//
//		Scanner in = new Scanner(System.in);
//		Random random = new Random();
//		ArrayList<WordTagPair> arrayList = wordGetter.arrayList;
//
//		wordGetter.show();
    }
}

