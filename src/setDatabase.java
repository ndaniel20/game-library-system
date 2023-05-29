package src;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.util.Map;
import org.bson.Document;
import org.bson.types.ObjectId;

public class setDatabase {
    public static MongoClient client = connectDatabase.client;

    public static void addLoginDetails(String username, String password, Boolean isAdmin) {
        //fetch collection
        MongoDatabase database = client.getDatabase("LibraryDatabase");
        MongoCollection<Document> collection = database.getCollection("logins");
        Document loginDB = new Document("username", username).append("password", password).append("isAdmin", isAdmin);
        
        // add document to database
        collection.insertOne(loginDB);
        System.out.println("Login details added successfully");
    }

    public static void insertLoanedItem(Map<String, Object> gameObject) {
        MongoDatabase database = client.getDatabase("LibraryDatabase");
        MongoCollection<Document> collection = database.getCollection("items");
        Document itemsDoc = new Document(gameObject);

        collection.insertOne(itemsDoc);
        System.out.println("Items added successfully");
    }

    public static void deleteLoanedItem(ObjectId id) {
        MongoDatabase database = client.getDatabase("LibraryDatabase");
        MongoCollection<Document> collection = database.getCollection("items");

        collection.deleteOne(Filters.eq("_id", id));
        System.out.println("Items deleted successfully");
    }
}
