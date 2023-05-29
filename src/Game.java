package src;
import java.net.URL;

import javax.swing.JPanel;

public class Game {
    private String name;
    private URL link;
    private JPanel rating;
    private String description;
    private int maxCopies;
    private boolean restricted;
    private long returnDays;
    private long copiesLeft;

    public Game(String name, URL image, JPanel ratingPanel, int maxCopies, String description, boolean restricted, long returnDays, long copiesLeft) {
        this.name = name;
        this.link = image;
        this.rating = ratingPanel;
        this.maxCopies = maxCopies;
        this.description = description;
        this.restricted = restricted;
        this.returnDays = returnDays;
        this.copiesLeft = copiesLeft;
    }

    public String getName() {
        return name;
    }

    public URL getLink() {
        return link;
    }

    public JPanel getRating() {
        return rating;
    }

    public int getMaxCopies() {
        return maxCopies;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public long getReturnDays() {
        return returnDays;
    }

    public long getCopiesLeft() {
        return copiesLeft;
    }
}

