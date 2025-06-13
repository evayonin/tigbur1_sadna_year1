package com.example;

import javax.swing.JFrame;

public class ImageWindow extends JFrame { // הצגת הפאנל ImagePanel
  // משתני גודל סטטיים אבל אני עצלנית
  public ImageWindow() {
    this.setSize(600, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setLayout(null);
    this.add(new ImagePanel(0, 0, 600, 600));

    this.setVisible(true);
  }

  // אפשר לממש גם דרך כאן עם מתודת מיין וליצור אובייקט של החלון
}
