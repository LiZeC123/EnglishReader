package dataGetter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelGetter {

    public static DataModel mdToList(File file) throws IOException{
        DataModel mode = new DataModel();


        Scanner in = new Scanner(new FileInputStream(file));

        String regexLook = "\\d[.] (.*)"; // 数字开始，加上一个点
        String regexHide = "\\s*- (.*)"; // 开始空白字符，之后以-开始

        Pattern ptnLook = Pattern.compile(regexLook);
        Pattern ptnHide = Pattern.compile(regexHide);

        mode.tag = tryMatchTag(in.nextLine());

        String line = in.nextLine();
        while (in.hasNextLine()) {
            Matcher matcher = ptnLook.matcher(line);

            if(matcher.find()){
                TranslatePair pair = new TranslatePair(matcher.group(1),"无本地解释");

                try{
                    line = in.nextLine();
                    matcher = ptnHide.matcher(line);
                    if(matcher.find()){
                        pair.Hide = matcher.group(1);
                        line = in.nextLine();
                    }
                }
                catch (Exception e) {
                    // End Of File, Do Nothing
                }
                finally {
                    mode.add(pair);
                }
            }
            else{
                if(in.hasNextLine()){
                    line = in.nextLine();
                }
            }
        }

        in.close();
        return mode;
    }

    private static String tryMatchTag(String line) {
        String tagStatus = "";
        String regexTag  = "## (.*)"; // 匹配模式标题
        Pattern ptnTag  = Pattern.compile(regexTag);
        Matcher matcher = ptnTag.matcher(line);
        if(matcher.find()){
            tagStatus = matcher.group(1);
        }

        return tagStatus;
    }
}


