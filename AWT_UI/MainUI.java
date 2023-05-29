package AWT_UI;
import src.getGames;
import src.updatePanel;

import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.net.URL;
import javax.swing.*;

public class MainUI extends JFrame implements ActionListener{

    private JButton searchBtn;
    private JButton sortBtn;
    private JTextField searchField;
    private JButton homeBtn;
    private JButton profileBtn;
    public static JFrame frame;
    public static JPanel gamePanel;
    public static ArrayList<Object> gameList;
    static String genre = "Any";
    static Integer rating = 1;
    static boolean age = false;
    static boolean available = false;
    
    public MainUI() throws Exception {
        super("Game Store");
        frame = this;

        Object[] selectedValues = new Object[]{genre, rating, age, available}; //set default value for sort options
        gameList = getGames.getGameList(); //get all games
        JPanel mainPanel = new JPanel(new BorderLayout()); //top panel

        // header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(Color.DARK_GRAY);
        
        // home button on the left
        URL homeIconUrl = getClass().getResource("../src/img/homeBtn.png"); 
        ImageIcon homeIcon = new ImageIcon(homeIconUrl);
        homeBtn = new JButton(homeIcon);

        // profile button on the left
        URL profileUrl = getClass().getResource("../src/img/profileBtn.png"); 
        ImageIcon profileIcon = new ImageIcon(profileUrl);
        profileBtn = new JButton(profileIcon);

        //search elements
        searchField = new JTextField(20);
        searchBtn = new JButton("Search");
        sortBtn = new JButton("Sort");

        //add elements in header
        headerPanel.add(homeBtn);
        headerPanel.add(profileBtn);
        headerPanel.add(searchField);
        headerPanel.add(searchBtn);
        headerPanel.add(sortBtn);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        //event listeners
        homeBtn.addActionListener(this);
        profileBtn.addActionListener(this);
        searchBtn.addActionListener(this);
        searchField.addActionListener(this);
        sortBtn.addActionListener(this);

        // create game panel
        gamePanel = new JPanel(new GridBagLayout());
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gamePanel.setBackground(Color.GRAY);

        //update game panel with video games dataset
        updatePanel.updateGamePanel("", selectedValues);

        //library element
        JPanel gamePanelBorder = new JPanel(new BorderLayout());
        gamePanelBorder.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        gamePanelBorder.add(gamePanel, BorderLayout.CENTER);
        gamePanelBorder.setBackground(Color.YELLOW);

        //scroll feature
        JScrollPane scrollPane = new JScrollPane(gamePanelBorder, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        //set frame settings
        setBackground(Color.YELLOW);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
            searchAction();
        }
        if (e.getSource() == searchField) {
            searchAction();
        }
        if (e.getSource() == homeBtn){
            new LoginUI();
            dispose();
        }
        if (e.getSource() == sortBtn) {
            try {
                new SortUI();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        if (e.getSource() == profileBtn) {
            try {
                new ProfileUI();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            dispose();
        }
    }

    private void searchAction(){
        String searchTerm = searchField.getText();
        Object[] newValues = new Object[]{genre, rating, age, available};
        try {
            updatePanel.updateGamePanel(searchTerm, newValues);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}

