package src;

import java.io.FileReader;
import java.util.ArrayList;
import java.net.URI;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//it is more efficient to pass a simple array through a method rather than a JSON object, as JSON involves parsing and serialization which can be computationally expensive.
public class getGames{
    public static ArrayList<Object> getGameList() throws Exception {
        ArrayList<Object> newGameArray = new ArrayList<>();

        FileReader reader = new FileReader("dataset/games_data.json");
        JSONParser parser = new JSONParser();
        ArrayList<Object> gameArray =  (ArrayList<Object>) parser.parse(reader);

        for (Object obj : gameArray) {
            JSONObject gameData = (JSONObject) obj;
            String name = (String) gameData.get("name");
            String link = (String) gameData.get("image");
            URI uri = new URI(link);
            URL image = uri.toURL();
            double rating = (double) gameData.get("rating");
            int maxCopies = (int) (long) gameData.get("copies");
            String genre = (String) gameData.get("genre");
            Boolean restricted = (Boolean) gameData.get("age_restricted");
            String description = (String)  gameData.get("description");
            int returnDays = (int) (long) gameData.get("return_days");

            ArrayList<Object> game = new ArrayList<>();
            game.add(name);
            game.add(image);
            game.add(rating);
            game.add(maxCopies);
            game.add(genre);
            game.add(restricted);
            game.add(description);
            game.add(returnDays);
            
            newGameArray.add(game);
        }

        reader.close();

        return newGameArray;
    }
}
