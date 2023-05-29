package AWT_UI;
import src.updatePanel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class SortUI {
    JFrame frame = new JFrame();

    public SortUI() throws Exception {
        //main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //genre drop down menu
        JPanel genrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel genreLabel = new JLabel("Genre:");
        JComboBox<String> genreBox = new JComboBox<>(new String[]{"Any", "Action", "Puzzle", "Sports", "Combat", "Adventure", "Racing", "Simulation", "RPG", "Educational"});
        genreBox.setSelectedItem(MainUI.genre);
        genrePanel.add(genreLabel);
        genrePanel.add(genreBox);
        genreBox.addActionListener(e -> {
            MainUI.genre = (String) genreBox.getSelectedItem();
        });

        //rating drown down menu
        JLabel ratingLabel = new JLabel("Rating");
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<Integer> ratingBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        ratingBox.setSelectedItem(MainUI.rating);
        ratingPanel.add(ratingLabel);
        ratingPanel.add(ratingBox);
        ratingBox.addActionListener(e -> {
            MainUI.rating = (Integer) ratingBox.getSelectedItem();
        });

        //age restrictions checkbox
        JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JCheckBox ageBox = new JCheckBox("Age restrictions");
        ageBox.setSelected(MainUI.age);
        agePanel.add(ageBox);        
        ageBox.addActionListener(e -> {
            MainUI.age = ageBox.isSelected();
        });

        //available checkbox
        JPanel availablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JCheckBox availableBox = new JCheckBox("Show available only");
        availableBox.setSelected(MainUI.available);
        availablePanel.add(availableBox);
        availableBox.addActionListener(e -> {
            MainUI.available = availableBox.isSelected();
        });

        //submit button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
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
            }
        });
        buttonPanel.add(submitButton);

        panel.add(genrePanel);
        panel.add(ratingPanel);
        panel.add(agePanel);
        panel.add(availablePanel);
        panel.add(buttonPanel);

        frame.setBackground(Color.YELLOW);
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
