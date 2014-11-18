import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class TF_IDF {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("meta"));
        String tmpLine;
        HashMap<String, String> metas = new HashMap<String, String>();
        while ((tmpLine = reader.readLine()) != null){
            String[] tmp = tmpLine.split(":");
            metas.put(tmp[0].trim(), tmp[1].trim());
        }
        reader.close();

        PrintStream printStream = new PrintStream("output.arff");
        PrintStream metaStream = new PrintStream("meta.data");

        printStream.println("@RELATION tweets\n");

        TfIdf tf = new TfIdf("input");

        HashMap<String, Integer> words = new HashMap<String, Integer>();
        int wordsCount = 0;

        for (String word : tf.allwords.keySet()) {
           words.put(word, wordsCount++);
           printStream.println("@ATTRIBUTE a" + wordsCount + " REAL"/*%DF=" + tf.allwords.get(word)[0]*/);  //[0] - DF -- [1] - IDF
        }

        printStream.println("@ATTRIBUTE class {pos,neg}\n\n");

        tf.buildAllDocuments();


        printStream.println("@DATA");

        int dataIndex = 0;
        for(String fileName : tf.documents.keySet()){
           // printStream.println("% " + fileName);
            dataIndex++;
            double[] vector = new double[wordsCount];
            String tweetLine = metas.get(fileName);

            Map<String, Double[]> tf_idfMap = tf.documents.get(fileName).getF_TF_TFIDF();
            for(String word : tf_idfMap.keySet()){
                int index = words.get(word);
                double tf_idf = tf_idfMap.get(word)[2]; //[0] - Frequency -- [1] - TF -- [2] - TF-IDF

                vector[index] = tf_idf;
            }

            for(Double d : vector)
                printStream.print(d + ",");

            String attribute = fileName.split("__")[1];
            printStream.println(attribute);

            metaStream.println(dataIndex + " : " + tweetLine + " : " + attribute + " //" + fileName);
        }

        printStream.close();
        metaStream.close();
    }
}
