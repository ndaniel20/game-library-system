package src;
import AWT_UI.ProfileUI;

import java.util.ArrayList;
import java.util.Locale;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URI;
import java.net.URL;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import java.sql.Date;
import java.text.DateFormat;

import javax.swing.*;
import org.bson.Document;
import org.bson.types.ObjectId;

public class updateItems extends ProfileUI{
    public updateItems() throws Exception {
        super();
    }

    public static void updateGamePanel(String searchResult) throws Exception{
        gamePanel.removeAll(); // remove current game elements

        ArrayList<Document> itemList = getDatabase.getUserItems(userName);
        GridBagConstraints constraints = new GridBagConstraints();
        int x = 0;
        int y = 0;
        for (Document item : itemList) {
            String name = item.getString("name");
            String console = item.getString("console");
            String image = item.getString("image");
            String returnDate = item.getString("return_date");
            int maxCopies = item.getInteger("max_copies");
            long copyCount = getDatabase.getCopyCount(name, maxCopies);
            ObjectId _id = item.getObjectId("_id");
            if (searchResult != "" && !name.toLowerCase().contains(searchResult.toLowerCase())) continue; //if search isnt empty and doesnt match the game name then return
            
            //create panel for item
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            itemPanel.setBackground(Color.LIGHT_GRAY);
            
            //add game image            
            URI uri = new URI(image);
            URL imageURL = uri.toURL();
            BufferedImage originalImage = ImageIO.read(imageURL);
            Image scaledImage = originalImage.getScaledInstance(85, 115, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            itemPanel.add(imageLabel);
            
            //add game name and console
            JLabel nameLabel = new JLabel(name);
            JLabel consoleLabel = new JLabel(console);
            JPanel namePanel = new JPanel(new GridLayout(2, 1));
            nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            consoleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14)); 
            namePanel.add(nameLabel);
            namePanel.add(consoleLabel);
            itemPanel.add(namePanel);
            
            //add return date
            long returnDateLong = Long.parseLong(returnDate);
            Date returnDateObj = new Date(returnDateLong * 1000L); 
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
            String returnDateString = dateFormat.format(returnDateObj);
            JLabel returnDateLabel = new JLabel("Return date: " + returnDateString);
            returnDateLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            itemPanel.add(returnDateLabel);
            
            //add buttons
            JButton printButton = new JButton("Print");
            JButton returnButton = new JButton("Return");
            JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
            buttonPanel.add(printButton);
            buttonPanel.add(returnButton);
            itemPanel.add(buttonPanel);

            //set constraints
            constraints.gridx = x;
            constraints.gridy = y;
            constraints.insets = new Insets(10, 20, 10, 10); //space around each game
            constraints.anchor = GridBagConstraints.CENTER;
            gamePanel.add(itemPanel, constraints);

            //add events listeners
            printButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    writeToTextFile(name, console, returnDateString, maxCopies, copyCount);
                }
            });
            
            returnButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setDatabase.deleteLoanedItem(_id);
                    try {
                        updateGamePanel(searchResult);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });            

            //organize rows and collums
            x += 1;
            if (x >= 5) {
                x = 0;
                y += 1;
            }
        }
        
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    private static void writeToTextFile(String name, String console, String returnDate, int maxCopies, long copyCount){
        try {
            String fileName = "dataset/selected_item.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write("Details: " + name + " (" + console + ")");
            writer.newLine();
            writer.write("Copies available: " + copyCount);
            writer.newLine();
            writer.write("Copies on loan: " + (maxCopies - copyCount));
            writer.newLine();
            writer.write("Return date: " + returnDate);
            writer.close();
            System.out.println("Written to file selected_item.txt");
        } catch (Exception ex) {
            System.out.println("Error saving file");
        }

    }
}

