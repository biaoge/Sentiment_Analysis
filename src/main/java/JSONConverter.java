import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.*;

public class JSONConverter {

    /*
        将MapReduce得到的SentimentAnalysis结果转为json格式
     */

    public static void main(String[] args) throws IOException, JSONException{
        JSONArray jsonArray = new JSONArray();

        FileReader fr = new FileReader("/Users/zhangbiaogetian/Documents/JZ/Project1/Sentiment_Analysis/src/main/resources/output/part-r-00000");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();

        FileWriter fw = new FileWriter("/Users/zhangbiaogetian/Documents/JZ/Project1/Sentiment_Analysis/src/main/resources/result.json");
        BufferedWriter bw = new BufferedWriter(fw);

        while(line != null){
            JSONObject article = new JSONObject();
            //reducer结果默认用/t分开
            String[] title_emotion_count = line.split("\t");
            //把所有情感放到emotionList中去
            JSONObject emotionList = new JSONObject();

            //每篇文章有三行，negative，neutral, positive
            //读入第一行的情感
            emotionList.put(title_emotion_count[1], title_emotion_count[2]);
            article.put("title", title_emotion_count[0]);
            //读剩下两行的情感
            for(int i = 0; i < 2; i++){
                line = br.readLine();
                title_emotion_count = line.split("\t");
                emotionList.put(title_emotion_count[1], title_emotion_count[2]);
            }
            article.put("data", emotionList);
            jsonArray.put(article);

            line = br.readLine();
        }

        bw.write(jsonArray.toString());

        br.close();
        bw.close();
    }
}
