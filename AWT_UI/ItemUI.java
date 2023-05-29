package AWT_UI;

import src.updatePanel;
import src.Game;
import src.setDatabase;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class ItemUI extends Game implements ActionListener {

    // components
    JFrame frame = new JFrame();
    public static Boolean isOpened = false;
    public Map<String, Object> gameObject = new HashMap<>();
    private JComboBox<String> consoleComboBox;
    private String selectedConsole;
    private JButton backButton;
    private JButton loanButton;
    private static String name;
    private static URL imageURL;
    private static JPanel ratingPanel;
    private static String description;
    private static int maxCopies;
    private static boolean restricted;
    private static long daysToAdd;
    private static long loanedCopies;

    public ItemUI(Game game) throws Exception{
        //construct
        super(name, imageURL, ratingPanel, maxCopies, description, restricted, daysToAdd, loanedCopies);
        name = game.getName();
        imageURL = game.getLink();
        ratingPanel = game.getRating();
        maxCopies = game.getMaxCopies();
        description = game.getDescription();
        restricted = game.isRestricted();
        daysToAdd = game.getReturnDays();
        loanedCopies = game.getCopiesLeft();
        
        //game image UI
        JLabel gameNameLabel = new JLabel(name);
        gameNameLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        gameNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        consoleComboBox = new JComboBox<>(new String[]{"Xbox", "PlayStation", "Nintendo"});
        BufferedImage originalImage = ImageIO.read(imageURL);
        Image scaledImage = originalImage.getScaledInstance(150, 210, Image.SCALE_SMOOTH);
        ImageIcon iconImage = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(iconImage);
        
        //game description UI
        JTextArea detailsLabel = new JTextArea(description);
        detailsLabel.setEditable(false);
        detailsLabel.setLineWrap(true);
        detailsLabel.setWrapStyleWord(true);
        detailsLabel.setOpaque(false);
        detailsLabel.setPreferredSize(new Dimension(240, 320));
        JLabel copiesLeftLabel = new JLabel("Copies Available: " + loanedCopies);
        copiesLeftLabel.setBorder(BorderFactory.createEmptyBorder(10, 40, 0, 0));
        backButton = new JButton("Close");
        loanButton = new JButton("Loan");
        if (loanedCopies == 0) loanButton.setEnabled(false); //if no copies available

        // back and loan button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(loanButton);

        //add preview to panel
        JPanel panelWrapper =  new JPanel();
        panelWrapper.setOpaque(false);
        panelWrapper.add(gameNameLabel);
        panelWrapper.add(imageLabel);
        panelWrapper.add(ratingPanel);
        panelWrapper.add(buttonPanel);

        // Add components to panel
        JPanel infoPanel = new JPanel();
        infoPanel.add(consoleComboBox);
        infoPanel.add(detailsLabel);
        infoPanel.add(copiesLeftLabel);

        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(panelWrapper, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);
        
        // Add action listeners to buttons
        consoleComboBox.addActionListener(this);
        backButton.addActionListener(this);
        loanButton.addActionListener(this);

        //set to Object
        gameObject.put("name", name);
        gameObject.put("image", imageURL.toString());
        gameObject.put("console", "Xbox");

        // windows properties
        frame.setVisible(true);
        frame.setBackground(Color.YELLOW);
        frame.setTitle("Game Details");
        frame.setSize(500, 420);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(panel);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isOpened = false;
            }
        });
    }

    // Action listener for buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            isOpened = false;
            frame.dispose();
        } 
        else if (e.getSource() == consoleComboBox) {
            selectedConsole = (String) consoleComboBox.getSelectedItem();
            gameObject.put("console", selectedConsole);
        } 
        else if (e.getSource() == loanButton) {
            String username = LoginUI.usernameField.getText();
            long timestamp = System.currentTimeMillis() / 1000L;
            long futureTimestamp = timestamp + convertToTimestamp(daysToAdd);
            gameObject.put("return_date", Long.toString(futureTimestamp));
            gameObject.put("lender", username);
            gameObject.put("max_copies", maxCopies);

            setDatabase.insertLoanedItem(gameObject);
            try {
                Object[] values = new Object[4];
                values[0] = MainUI.genre;
                values[1] = MainUI.rating;
                values[2] = MainUI.age;
                values[3] = MainUI.available;
                updatePanel.updateGamePanel("", values);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            isOpened = false;
            frame.dispose();
        }
    }

    public long convertToTimestamp(long days){
        return days * 24 * 60 * 60;
    }
}
