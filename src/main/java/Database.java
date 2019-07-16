import com.mongodb.*;
import javafx.scene.control.Alert;

import java.net.UnknownHostException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Optional;

public class Database {

	public String url;
	public String databaseName;

	public Database(String url, String databaseName) {

		this.url = url;
		this.databaseName = databaseName;

	}

	public boolean testConnection(){

		try {

			MongoClient mongoClient = new MongoClient(new MongoClientURI(this.url));
			DB database = mongoClient.getDB(this.databaseName);

		} catch (UnknownHostException e) {
			return false;
		}

		return true;
	}

	public int genericQuery(String collectionName, String key, String value) throws UnknownHostException {

		int counter = 0;

		MongoClient mongoClient = new MongoClient(new MongoClientURI(this.url));
		DB database = mongoClient.getDB(this.databaseName);
		DBCollection collection = database.getCollection(collectionName);

		BasicDBObject query = new BasicDBObject();
		query.put(key, value);

		DBCursor cursor = collection.find(query);

		while(cursor.hasNext()) {

			counter++;
			cursor.next();
		}

		return counter;
	}

	public double[] queryAge(String collectionName, String key) throws UnknownHostException {

		int counterMan = 0;
		int counterWoman = 0;
		double[] averageAge = new double[3];

		MongoClient mongoClient = new MongoClient(new MongoClientURI(this.url));
		DB database = mongoClient.getDB(this.databaseName);
		DBCollection collection = database.getCollection(collectionName);

		DBCursor cursor = collection.find();

		while(cursor.hasNext()) {
			DBObject users = cursor.next();
			DateFormatter dateFormatter = new DateFormatter(users.get(key).toString());

			if(users.get("gender").toString().equals("Homme")){
				averageAge[1] += dateFormatter.returnAgeFormStringDate();
				counterMan++;

			}else if(users.get("gender").toString().equals("Femme")){
				averageAge[2] += dateFormatter.returnAgeFormStringDate();
				counterWoman++;
			}
			averageAge[0] += dateFormatter.returnAgeFormStringDate();
		}

		averageAge[0] /= (counterMan + counterWoman);
		averageAge[1] /= counterMan;
		averageAge[2] /= counterWoman;

		return averageAge;
	}

	public String[][] queryEvent(String collectionName) throws UnknownHostException {

		String[][] numberOfParticipantsPerEvent = new String[2][];
		numberOfParticipantsPerEvent[0] = new String[50];
		numberOfParticipantsPerEvent[1] = new String[50];

		String idEvent;
		int i = 0;

		MongoClient mongoClient = new MongoClient(new MongoClientURI(this.url));
		DB database = mongoClient.getDB(this.databaseName);
		DBCollection collection = database.getCollection(collectionName);

		DBCursor cursor = collection.find();

		while(cursor.hasNext()) {
			DBObject event = cursor.next();

			idEvent = event.get("_id").toString();
			numberOfParticipantsPerEvent[0][i] = event.get("name").toString();

			numberOfParticipantsPerEvent[1][i] = ""+ genericQuery("register", "eventId",idEvent);
			i++;

		}
		return numberOfParticipantsPerEvent;

	}


	public Optional<DBObject> queryFromAdminEmail(String collectionName, String adminEmail) throws UnknownHostException {

		MongoClient mongoClient = new MongoClient(new MongoClientURI(this.url));
		DB database = mongoClient.getDB(this.databaseName);
		DBCollection collection = database.getCollection(collectionName);

		BasicDBObject query = new BasicDBObject();
		query.put("email", adminEmail);
		DBObject doc = collection.findOne(query);

		return Optional.ofNullable(doc);


	}

	public int reportQuery(String collectionName, String key, String value) throws UnknownHostException {
		int counter = 0;

		MongoClient mongoClient = new MongoClient(new MongoClientURI(this.url));
		DB database = mongoClient.getDB(this.databaseName);
		DBCollection collection = database.getCollection(collectionName);

		BasicDBObject query = new BasicDBObject();
		query.put("reported", true);

		DBCursor cursor = collection.find(query);

		while(cursor.hasNext()) {
			DBObject reported = cursor.next();
			if(reported.get(key).toString().equals(value)) {
				counter++;
			}
		}

		return counter;
	}
}
