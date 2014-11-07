import java.sql.SQLException;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class MonitoraStatus implements StatusListener {

	@Override
    public synchronized void onStatus(Status status) {

        String tweet = status.getText();

        String filtered = PreProcessor.filter(tweet, false, true);

        System.out.println(">>>>>>>>>>>>>>>>>>>TWEET<<<<<<<<<<<<<<<<<\n" + tweet +
                       "\n\n>>>>>>>>>>>>>>>>>>FILTERED<<<<<<<<<<<<<<<\n" + filtered + "\n\n\n");
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
