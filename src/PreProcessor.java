import com.sun.javafx.beans.annotations.NonNull;

import java.io.*;

/**
 * Created by erick on 07/11/14.
 */
public class PreProcessor {
    public static final String specialChars = "çáàâãéêíóôõóòúü";
    public static final String userName = "@[a-z0-9_]*(:)?";
    public static final String retweet = "RT";
    public static final String hashTag = "#";
    public static final String link = "http(s)?://t.co/[a-z0-9]*";
    public static final String lastWordIncomplete = "[^ ]*…";
    public static final String stopWords = "(de)|(a)|(o)|(que)|(e)|(do)|(da)|(em)|(um)|(para)|(é)|(com)|(uma)|(os)|(no)|(se)|(na)|(por)|(mais)|(as)|(dos)|(como)|(mas)|(foi)|(ao)|(ele)|(das)|(tem)|(à)|(seu)|(sua)|(ou)|(ser)|(quando)|(muito)|(há)|(nos)|(já)|(está)|(eu)|(também)|(só)|(pelo)|(pela)|(até)|(isso)|(ela)|(entre)|(era)|(depois)|(sem)|(mesmo)|(aos)|(ter)|(seus)|(quem)|(nas)|(me)|(esse)|(eles)|(estão)|(você)|(tinha)|(foram)|(essa)|(num)|(nem)|(suas)|(meu)|(às)|(minha)|(têm)|(numa)|(pelos)|(elas)|(havia)|(seja)|(qual)|(será)|(nós)|(tenho)|(lhe)|(deles)|(essas)|(esses)|(pelas)|(este)|(fosse)|(dele)|(tu)|(te)|(vocês)|(vos)|(lhes)|(meus)|(minhas)|(teu)|(tua)|(teus)|(tuas)|(nosso)|(nossa)|(nossos)|(nossas)|(dela)|(delas)|(esta)|(estes)|(estas)|(aquele)|(aquela)|(aqueles)|(aquelas)|(isto)|(aquilo)|(estou)|(está)|(estamos)|(estão)|(estive)|(esteve)|(estivemos)|(estiveram)|(estava)|(estávamos)|(estavam)|(estivera)|(estivéramos)|(esteja)|(estejamos)|(estejam)|(estivesse)|(estivéssemos)|(estivessem)|(estiver)|(estivermos)|(estiverem)|(hei)|(há)|(havemos)|(hão)|(houve)|(houvemos)|(houveram)|(houvera)|(houvéramos)|(haja)|(hajamos)|(hajam)|(houvesse)|(houvéssemos)|(houvessem)|(houver)|(houvermos)|(houverem)|(houverei)|(houverá)|(houveremos)|(houverão)|(houveria)|(houveríamos)|(houveriam)|(sou)|(somos)|(são)|(era)|(éramos)|(eram)|(fui)|(foi)|(fomos)|(foram)|(fora)|(fôramos)|(seja)|(sejamos)|(sejam)|(fosse)|(fôssemos)|(fossem)|(for)|(formos)|(forem)|(serei)|(será)|(seremos)|(serão)|(seria)|(seríamos)|(seriam)|(tenho)|(tem)|(temos)|(tém)|(tinha)|(tínhamos)|(tinham)|(tive)|(teve)|(tivemos)|(tiveram)|(tivera)|(tivéramos)|(tenha)|(tenhamos)|(tenham)|(tivesse)|(tivéssemos)|(tivessem)|(tiver)|(tivermos)|(tiverem)|(terei)|(terá)|(teremos)|(terão)|(teria)|(teríamos)|(teriam)";
    public static final String stopWord = "(?<![a-z" +specialChars + "])(" + stopWords + ")(?![a-z" + specialChars + "])";
    public static final String BLANK_STRING = "";

    public static String filter(@NonNull String str, boolean shouldRemoveStopWord, boolean shouldIgnoreRetweet) {
        String result;

        if (str.contains(retweet))
            if(shouldIgnoreRetweet)
                return null;
            else
                str = str.replaceAll(retweet, BLANK_STRING);

        result = str.toLowerCase();

        if (shouldRemoveStopWord)
            result = result.replaceAll(stopWord, BLANK_STRING);

        result = result.replaceAll(link, BLANK_STRING);
        result = result.replaceAll(userName, BLANK_STRING);
        result = result.replaceAll(hashTag, BLANK_STRING);
        result = result.replaceAll(lastWordIncomplete, BLANK_STRING);

        result = result.replaceAll("[^\\p{L1}]", BLANK_STRING);  //Remove all characters that are not Latin-1
        result = result.replaceAll("\\p{P}", " ");  //Remove punctuation marks
        result = result.replaceAll("(\\n)+", " ");
        result = result.replaceAll("(\\t)+", " ");

        result = result.replaceAll("[ ]*[ ]", " ");  //Remove duplicated whitespaces. Not necessary, but should make the final file smaller
        result = result.trim();

        return result;
    }

    public static void main(String[] args) {
        try {
            InputStream in = new FileInputStream("/home/erick/IdeaProjects/entrada");
            File outputDir = new File("/home/erick/IdeaProjects/saida");

            if(!outputDir.exists())
                outputDir.mkdirs();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            String tweet = null;
            int tweetsCount = 0;
            while ((line = reader.readLine()) != null) {

                if(line.contains(">>>>>>>>>>>TWEET<<<<<<<<<<<")) {
                    if (tweet != null) {
                        FileWriter writer = new FileWriter(new File(outputDir, "tweet" + tweetsCount + ".txt"));
                        String filtered = filter(tweet, true, true);
                        if(filtered != null) {
                            writer.write(filtered);
                            tweetsCount++;
                            writer.close();
                        }
                        tweet = "";

                    }
                }else
                    tweet += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
