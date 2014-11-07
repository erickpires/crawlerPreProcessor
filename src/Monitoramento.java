import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;


public class Monitoramento {

    private static final double lat = -22.9; //latitude Rio de Janeiro
    private static final double lon = -43.2; //longitude Rio de Janeiro

    private static final double lon1 = lon - .5;
    private static final double lat1 = lat - .5;
    private static final double lon2 = lon + .5;
    private static final double lat2 = lat + .5;

    private static final double box[][] = {{lon1, lat1}, {lon2, lat2}};

    private static final String[] language = { "pt" };
	
	public Monitoramento(){
		inicia();
	}
	
	public TwitterStream logar(){
		final String CONSUMER_KEY = "1j5uZ0ywn3VwAaFb7QBEjA";
		final String CONSUMER_SECRET = "XUISgSfHJwMtfegBbcGcH1aj7uqPOXJGCtn3zF4FAsA";
		String token = "1852160593-N7kfpMPrWUoTGzHAFC2gCc5EVPYeyBd2IbDNidO";
	    String tokenSecret = "vOLYi3mLEE4qoOhB71mTzcteQtI4W5bodlIhCXUPaU";
	    AccessToken accessToken = new AccessToken(token, tokenSecret);
	    TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
	    twitterStream.setOAuthAccessToken(accessToken);
	    return twitterStream;
	}
	
	public void filtro(String[] keywordsArray, TwitterStream twitterStream){
        FilterQuery filterQuery = new FilterQuery();

        filterQuery.track(keywordsArray);
//        filterQuery.locations(null);
       filterQuery.language(language);

        twitterStream.filter(filterQuery);
	}
	
	public void inicia(){
		String[] keywordsArray = { "enem","caneta"};
		TwitterStream twitterStream = logar();
		twitterStream.addListener(new MonitoraStatus());
		filtro(keywordsArray, twitterStream);
	}
}
