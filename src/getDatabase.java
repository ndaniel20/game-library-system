package src;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;

import org.bson.Document;

public class getDatabase {
    public static MongoClient client = connectDatabase.client;
    
    public static ArrayList getUserItems(String lenderName) {
        MongoDatabase database = client.getDatabase("LibraryDatabase");
        MongoCollection<Document> collection = database.getCollection("items");
        ArrayList<Document> documents = new ArrayList<>();
        FindIterable<Document> iterable = collection.find(Filters.eq("lender", lenderName));
        for (Document document : iterable) {
            documents.add(document);
        }

        return documents;
    }

    public static long getCopyCount(String gameName, int maxCopies) {
        // Get the item details collection
        MongoDatabase database = client.getDatabase("LibraryDatabase");
        MongoCollection<Document> collection = database.getCollection("items");
        long count = collection.countDocuments(Filters.eq("name", gameName));
        count = maxCopies - count;
    
        // Return count of lenders with the game at their disposal
        return count;
    }

    public static boolean checkUsername(String username) {
        // Get the login details collection
        MongoDatabase database = client.getDatabase("LibraryDatabase");
        MongoCollection<Document> collection = database.getCollection("logins");
        Document loginDoc = collection.find(new Document("username", username)).first();
    
        // Check if a document was found
        return loginDoc != null;
    }

    public static boolean checkLoginDetails(String username, String password) {
        // Get the login details collection
        MongoDatabase database = client.getDatabase("LibraryDatabase");
        MongoCollection<Document> collection = database.getCollection("logins");
        Document loginDoc = collection.find(new Document("username", username).append("password", password)).first();
    
        // Check if a document was found
        return loginDoc != null;
    }
}
