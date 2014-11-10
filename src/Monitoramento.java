import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;

public final class Monitoramento {

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
	
	private static TwitterStream logar(){

	    AccessToken accessToken = new AccessToken(Constante.TOKEN, Constante.TOKEN_SECRET);
	    TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.setOAuthConsumer(Constante.CONSUMER_KEY, Constante.CONSUMER_SECRET);
	    twitterStream.setOAuthAccessToken(accessToken);
	    return twitterStream;
	}
	
	private static void filtro(String[] keywordsArray, TwitterStream twitterStream){
        FilterQuery filterQuery = new FilterQuery();

        filterQuery.track(keywordsArray);
//        filterQuery.locations(null);
       filterQuery.language(language);

        twitterStream.filter(filterQuery);
	}
	
	private static void inicia(){
		String[] keywordsArray = { "enem"};
		TwitterStream twitterStream = logar();
		twitterStream.addListener(new MonitoraStatus());
		filtro(keywordsArray, twitterStream);
	}
}
