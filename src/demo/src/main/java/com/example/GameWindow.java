package com.example;

import javax.swing.*;

public class GameWindow extends JFrame {
  // משתני גודל סטטיים אבל אני עצלנית

  public GameWindow() {
    this.setTitle("Pink Trail Game");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(600, 600);
    this.setLocationRelativeTo(null);
    this.setLayout(null);
    Game gamePanel = new Game(0, 0, 600, 600);
    this.add(gamePanel);
    this.setVisible(true);
  }

  // אפשר לממש גם דרך כאן עם מתודת מיין וליצור אובייקט של החלון

}
