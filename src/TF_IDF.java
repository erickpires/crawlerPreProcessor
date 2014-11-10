import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class TF_IDF {

    public static void main(String[] args) throws FileNotFoundException {

        PrintStream printStream = new PrintStream("output.arff");
        printStream.println();


        printStream.println("@RELATION tweets\n");

        TfIdf tf = new TfIdf("teste");

        HashMap<String, Integer> words = new HashMap<String, Integer>();
        int wordsCount = 0;

        for (String word : tf.allwords.keySet()) {
           words.put(word, wordsCount++);
           printStream.println("@ATTRIBUTE a" + wordsCount + " REAL"/*%DF=" + tf.allwords.get(word)[0]*/);  //[0] - DF -- [1] - IDF
        }

        printStream.println("@ATTRIBUTE class {pos,neg}\n\n");

        tf.buildAllDocuments();


        printStream.println("@DATA");
        for(String fileName : tf.documents.keySet()){
           // printStream.println("% " + fileName);
            double[] vector = new double[wordsCount];
            Map<String, Double[]> tf_idfMap = tf.documents.get(fileName).getF_TF_TFIDF();
            for(String word : tf_idfMap.keySet()){
                int index = words.get(word);
                double tf_idf = tf_idfMap.get(word)[2]; //[0] - Frequency -- [1] - TF -- [2] - TF-IDF

                vector[index] = tf_idf;
            }

            for(Double d : vector)
                printStream.print(d + ",");

            String attribute = fileName.split("->")[1];
            printStream.println(attribute);
        }
    }
}
