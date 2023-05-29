package AWT_UI;
import src.updateItems;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.net.URL;
import java.awt.*;
import javax.swing.*;

public class ProfileUI extends JFrame implements ActionListener{

    private JButton storeBtn;
    private JButton searchBtn;
    private TextField searchField;
    private JButton homeBtn;
    public static String userName;
    public static JPanel gamePanel;
    
    public ProfileUI() throws Exception {
        super("Profile Items");

        // header
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(Color.DARK_GRAY);
        
        // home button on the left
        URL homeIconUrl = getClass().getResource("../src/img/homeBtn.png");
        ImageIcon homeIcon = new ImageIcon(homeIconUrl);
        homeBtn = new JButton(homeIcon);

        // store button on the left
        URL storeUrl = getClass().getResource("../src/img/storeBtn.png");
        ImageIcon storeIcon = new ImageIcon(storeUrl);
        storeBtn = new JButton(storeIcon);

        //search elements
        searchField = new TextField(20);
        searchBtn = new JButton("Search");

        // information icon
        JLabel infoLabel = new JLabel();
        ImageIcon infoIcon = new ImageIcon("src/img/infoIcon.png");
        infoLabel.setIcon(infoIcon);
        infoLabel.setToolTipText("Late returns are charged at £5 per day"); 
        infoLabel.setCursor(Cursor.getDefaultCursor());

        //add elements in header
        headerPanel.add(homeBtn);
        headerPanel.add(storeBtn);
        headerPanel.add(searchField);
        headerPanel.add(searchBtn);
        headerPanel.add(infoLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        //event listeners
        homeBtn.addActionListener(this);
        storeBtn.addActionListener(this);
        searchBtn.addActionListener(this);
        searchField.addActionListener(this);

        // create game panel
        gamePanel = new JPanel(new GridBagLayout());
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gamePanel.setBackground(Color.GRAY);

        //update game panel with video games dataset
        userName = LoginUI.usernameField.getText();
        updateItems.updateGamePanel("");

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
        if (e.getSource() == storeBtn) {
            try {
                new MainUI();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            dispose();
        }
    }

    private void searchAction(){
        String searchTerm = searchField.getText();
        try {
            updateItems.updateGamePanel(searchTerm);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}

