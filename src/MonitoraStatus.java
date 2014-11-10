import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Locale;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

import static java.util.Calendar.*;

public class MonitoraStatus implements StatusListener {

    private FileWriter outputFileWriter;
    private long timeToStop;

	public MonitoraStatus(){
        super();

        /*Calendar now = getInstance();

        String dirName = now.get(DAY_OF_MONTH) + "-" + now.get(MONTH) + "_" + now.get(HOUR_OF_DAY) + "-" + now.get(MINUTE) + "-" + now.get(SECOND);
        String fileName = "[" + Integer.toHexString(now.hashCode()) + "] tweets.txt";

        now.add(HOUR_OF_DAY, 1);
        timeToStop = now.getTimeInMillis();

        File dir = new File(dirName);
        if(!dir.exists())
            dir.mkdirs();

        File file = new File(dir, fileName);

        try {
            outputFileWriter = new FileWriter(file);
        } catch (IOException ignored) {
        }*/
    }

    @Override
    public synchronized void onStatus(Status status) {

        String tweet = status.getText();

        System.out.println(tweet);

        /*try {
            //if(!tweet.contains("RT")) {
                outputFileWriter.write(">>>>>>>>>>>TWEET<<<<<<<<<<<\n" + tweet + "\n");
                outputFileWriter.flush();
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(System.currentTimeMillis() >= timeToStop){
            try {
                outputFileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }*/
    }
		

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
        System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
    }

    @Override
    public void onStallWarning(StallWarning warning) {
        System.out.println("Got stall warning:" + warning);
    }

    @Override
    public void onException(Exception ex) {
        ex.printStackTrace();
    }
    
    
}
