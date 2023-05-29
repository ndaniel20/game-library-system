package src;
import AWT_UI.MainUI;
import AWT_UI.ItemUI;

import java.util.ArrayList;
import java.awt.*;
import java.net.URL;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class updatePanel extends MainUI{

    public updatePanel() throws Exception {
        super();
    }

    public static void updateGamePanel(String content, Object[] sort ) throws IOException {
        gamePanel.removeAll(); //remove current game elements

        //add configurations on grid
        GridBagConstraints constraints = new GridBagConstraints();
        int x = 0;
        int y = 0;
        int gamePanelWidth = 175;

        for (Object game : gameList) {
            //get elements
            ArrayList<Object> gameData = (ArrayList<Object>) game;
            String name = (String) gameData.get(0);
            URL imageURL = (URL) gameData.get(1);
            double rating = (double) gameData.get(2);
            int copies = (int) gameData.get(3);
            long copiesLeft = getDatabase.getCopyCount(name, copies);
            String genre = (String) gameData.get(4);
            Boolean isRestricted = (Boolean) gameData.get(5);
            String description = (String) gameData.get(6);
            int returnDays = (int) gameData.get(7);
            if (content != "" && !name.toLowerCase().contains(content.toLowerCase())) continue; //if search isnt empty and doesnt match the game name then return
            
            //sort feature
            String sortGenre = (String) sort[0];
            int sortRating = (int) sort[1];
            Boolean sortAge = (Boolean) sort[2];
            Boolean sortAvailable = (Boolean) sort[3];
            if (sort[0] != "Any" && !genre.equals(sortGenre)) continue;
            if (sortRating > rating) continue;
            if (sortAge == true && isRestricted == true) continue;
            if (sortAvailable == true && copiesLeft == 0) continue;
    
            //game border
            JPanel gameBoundary = new JPanel();
            gameBoundary.setPreferredSize(new Dimension(gamePanelWidth, 200));
            gameBoundary.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            gameBoundary.setBackground(Color.LIGHT_GRAY);
            gameBoundary.setLayout(new BorderLayout());
    
            //game name
            JLabel titleLabel = new JLabel(name);
            Font font = new Font("Verdana", Font.BOLD, 14);
            titleLabel.setFont(font);
            titleLabel.setForeground(Color.darkGray);
            JPanel titlePanel = new JPanel();
            titlePanel.setOpaque(false);
            titlePanel.add(titleLabel, BorderLayout.CENTER);
            gameBoundary.add(titlePanel, BorderLayout.NORTH);
    
            //game image
            BufferedImage originalImage = ImageIO.read(imageURL);
            Image scaledImage = originalImage.getScaledInstance(85, 115, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel(icon);
            JPanel imagePanel = new JPanel();
            imagePanel.setOpaque(false);
            imagePanel.add(imageLabel);
            gameBoundary.add(imagePanel, BorderLayout.CENTER);
    
            //ratings
            JPanel ratingPanel = new JPanel(new GridLayout(1, 5));
            ratingPanel.setOpaque(false);
            for (int i = 0; i < 5; i++) {
                ImageIcon starIcon;
                if (rating >= i + 1) starIcon = new ImageIcon("src/img/full_star.png");
                else if (rating >= i + 0.5) starIcon = new ImageIcon("src/img/half_star.png"); 
                else starIcon = new ImageIcon("src/img/empty_star.png"); 
                JLabel starLabel = new JLabel(starIcon);
                ratingPanel.add(starLabel);
            }
            JPanel ratingPanelWrapper = new JPanel();
            ratingPanelWrapper.setOpaque(false);
            ratingPanelWrapper.add(ratingPanel, BorderLayout.CENTER);
            gameBoundary.add(ratingPanelWrapper, BorderLayout.SOUTH);
    
            //set constraints
            constraints.gridx = x;
            constraints.gridy = y;
            constraints.insets = new Insets(10, 20, 10, 10); //space around each game
            constraints.anchor = GridBagConstraints.CENTER;
            gamePanel.add(gameBoundary, constraints);

            //event listeners for hover and clicks
            gameBoundary.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                
                public void mouseExited(MouseEvent e) {
                    frame.setCursor(Cursor.getDefaultCursor());
                }
                
                @Override
                public void mousePressed(MouseEvent e) {
                    if (ItemUI.isOpened == true) return;
                    try {
                        ItemUI.isOpened = true;
                        Game videoGame = new Game(name, imageURL, ratingPanel, copies, description, isRestricted, returnDays, copiesLeft);
                        new ItemUI(videoGame);
                       } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
    
            //organize rows and collums
            x += 1;
            if (x >= 6) {
                x = 0;
                y += 1;
            }
        }

        //reformat panel
        gamePanel.revalidate();
        gamePanel.repaint();
    }
    
}
