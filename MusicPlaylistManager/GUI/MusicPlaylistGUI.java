package GUI;

import Entity.Song;
import File.MusicFileHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MusicPlaylistGUI extends JFrame {
    private JTextField titleField, artistField, albumField, durationField, searchField;
    private JTextArea displayArea;
    private List<Song> songs;
    private List<Song> favoriteSongs = new ArrayList<>();

    public MusicPlaylistGUI() {
        setTitle("?? Music Playlist Manager");
        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Background panel
            JPanel contentPane = new JPanel(null) {
            ImageIcon background = new ImageIcon("./Images/illustration-music.jpg");
            Image img = background.getImage();

            @Override
            protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
            setContentPane(contentPane);

            Font font15 = new Font("Consolas", Font.BOLD, 15);

        String[] labels = { "Title", "Artist", "Album", "Duration", "Search" };
        JTextField[] fields = {
            titleField = new JTextField(),
            artistField = new JTextField(),
            albumField = new JTextField(),
            durationField = new JTextField(),
            searchField = new JTextField()
        };

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i] + ":");
            label.setBounds(30, 20 + i * 40, 100, 30);
            label.setFont(font15);
            label.setForeground(Color.WHITE);
            contentPane.add(label);

            fields[i].setBounds(130, 20 + i * 40, 200, 30);
            contentPane.add(fields[i]);
        }

        // Existing buttons with colors
        JButton addBtn    = createButton     ("Add",    360, 20, new Color(0, 128, 0));            // Green
        JButton showBtn   = createButton     ("Show",   360, 60, new Color(0, 102, 204));         // Blue
        JButton saveBtn   = createButton     ("Save",   360, 100,new Color(255, 204, 0));        // Yellow
        JButton updateBtn = createButton     ("Update", 360, 140,new Color(255, 140, 0));      // Orange
        JButton searchBtn = createButton     ("Search", 360, 180,new Color(204, 0, 0));        // Red
        JButton deleteBtn = createButton     ("Delete", 360, 220,new Color(220, 20, 60));      // Crimson Red
        JButton playBtn   = createButton     ("Play Song", 360, 260, new Color(50, 205, 50));   // Lime Green

        // New Favorites buttons
        JButton favBtn = createButton("Mark Favorite", 500, 20, new Color(218, 112, 214));  // Orchid
        JButton showFavBtn = createButton("Show Favorites", 500, 60, new Color(148, 0, 211)); // Dark Violet

        contentPane.add(addBtn);
        contentPane.add(showBtn);
        contentPane.add(saveBtn);
        contentPane.add(updateBtn);
        contentPane.add(searchBtn);
        contentPane.add(deleteBtn);
        contentPane.add(playBtn);
        contentPane.add(favBtn);
        contentPane.add(showFavBtn);

        displayArea = new JTextArea();
        displayArea.setFont(font15);
        displayArea.setEditable(false);
        displayArea.setBackground(new Color(230, 230, 250));  // Lavender background
        displayArea.setForeground(Color.BLACK);           // Text color

        JScrollPane scroll = new JScrollPane(displayArea);
        scroll.setBounds(30, 320, 730, 220);
        contentPane.add(scroll);

        songs = MusicFileHandler.readSongs();

        // Action listeners
        addBtn.addActionListener(e -> {
                try {
                Song song = new Song(
                titleField.getText(),
                artistField.getText(),
                albumField.getText(),
                Integer.parseInt(durationField.getText())
                );
                songs.add(song);
                MusicFileHandler.saveSong(song);
                displayArea.append(song.toString());
                clearFields();
            }   catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        showBtn.addActionListener(e -> {
        displayArea.setText("");
        for (Song song : songs) displayArea.append(song.toString());
        });

        saveBtn.addActionListener(e -> {
            MusicFileHandler.overwriteSongs(songs);
            JOptionPane.showMessageDialog(this, "All changes saved.");
        });

            updateBtn.addActionListener(e -> {
            String title = titleField.getText();
            for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getTitle().equalsIgnoreCase(title)) {
                    songs.set(i, new Song(
                    titleField.getText(),
                    artistField.getText(),
                    albumField.getText(),
                    Integer.parseInt(durationField.getText())
                    ));
                    MusicFileHandler.overwriteSongs(songs);
                    JOptionPane.showMessageDialog(this, "Song updated!");
                    return;
                }
            }
                    JOptionPane.showMessageDialog(this, "Song not found!");
        });

            searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().toLowerCase();
            displayArea.setText("");
            for (Song song : songs) {
            if (song.getTitle().toLowerCase().contains(keyword) ||
                song.getArtist().toLowerCase().contains(keyword)) {
                displayArea.append(song.toString());
                }
            }
        });

        deleteBtn.addActionListener(e -> {
            String title = titleField.getText();
            boolean removed = songs.removeIf(song -> song.getTitle().equalsIgnoreCase(title));
            if (removed) {
                MusicFileHandler.overwriteSongs(songs);
                JOptionPane.showMessageDialog(this, "Song deleted.");
                displayArea.setText("");
                for (Song song : songs) displayArea.append(song.toString());
            } else {
                JOptionPane.showMessageDialog(this, "Song not found.");
            }
        });

        playBtn.addActionListener(e -> {
            String title = titleField.getText();
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a song title to play.");
            } else {
                JOptionPane.showMessageDialog(this, "?? Playing song: " + title);
            }
        });

        favBtn.addActionListener(e -> {
            String title = titleField.getText();
            for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(title)) {
            if (!favoriteSongs.contains(song)) {
                favoriteSongs.add(song);
                JOptionPane.showMessageDialog(this, "Added to Favorites!");
            } else {
                JOptionPane.showMessageDialog(this, "Already in Favorites.");
            }
            return;
                }
            }
                JOptionPane.showMessageDialog(this, "Song not found.");
        });

                showFavBtn.addActionListener(e -> {
                displayArea.setText("? Favorite Songs:\n\n");
            for(Song fav : favoriteSongs) {
                displayArea.append(fav.toString());
            }
        }); 

        setVisible(true);
    }

    private JButton createButton(String text, int x, int y, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 120, 30);
        btn.setFont(new Font("Consolas", Font.BOLD, 14));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    private void clearFields() {
        titleField.setText(""); artistField.setText("");
        albumField.setText(""); durationField.setText(""); searchField.setText("");
    }
}
