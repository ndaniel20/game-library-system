package src;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class connectDatabase {
    public static MongoClient client;

    public connectDatabase() {
        //credential
        String connectionString = "mongodb+srv://gameLibrary:ikHDBiYDRFBmNsQa@noahdb.l43tvqi.mongodb.net/"; //add mongoDB credentials here
        MongoClientURI uri = new MongoClientURI(connectionString);
        client = new MongoClient(uri);
    }
}