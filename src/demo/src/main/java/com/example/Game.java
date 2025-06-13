package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

// הקוד יוצר חלון משחק שבו ריבוע ורוד נע על המסך בהתאם ללחיצות על חיצי המקלדת
// (למעלה, למטה, שמאלה, ימינה). 
//בכל תנועה, מיקומו החדש של הריבוע נשמר ברשימת נקודות, וכך מצויר שובל שמראה את כל המקומות שבהם עבר. 
//מחלקת גיים אחראית על הציור והמעקב אחר התנועה, 
//ומחלקת גיים ווינדוו יוצרת את חלון המשחק 
//ומוסיפה אליו את לוח המשחק (גיים) בגודל קבוע.

// הקוד מצייר ריבוע ורוד שנע על המסך ומשאיר שובל בכל תזוזה.  
// כאשר המשתמש לוחץ על אחד מחצי המקלדת, המיקום של הריבוע  
// (איקס סטארט, וויי סטארט) מתעדכן בהתאם לכיוון,  
// והנקודה החדשה מתווספת לרשימת נקודות (טרייל).  
// בכל לחיצה כזו מופעלת המתודה ריפיינט,  
// שגורמת לסווינג לקרוא שוב למתודה פיינט קומפוננט.  
// בתוך פיינט קומפוננט, הפאנל נצבע מחדש בצבע רקע,  
// ואז עוברים על כל הנקודות ברשימת השובל  
// ומציירים עבור כל אחת מהן ריבוע בגודל קבוע (20 על 20 פיקסלים)  
// באמצעות פיל רקט. כך מתקבל רצף של ריבועים בגווני ורוד,  
// שמייצגים את כל המקומות שבהם הריבוע עבר – ממש כמו שובל.

public class Game extends JPanel {

  private int xStart; // נקודה התחלתית
  private int yStart;
  private int size; // גודל של כל ריבוע (פיקסל) שמציירים
  private ArrayList<Point> trail; // רשימת הנקודות של השביל שנצייר
  private int step; // המרחק שכל לחיצה על חץ תזיז את הריבוע, בפיקסלים

  public Game(int x, int y, int width, int height) {
    this.setBounds(x, y, width, height);
    this.setBackground(Color.lightGray);
    this.setFocusable(true); // מאפשר לפאנל לקבל פוקוס מהמקלדת – חובה בשביל לקלוט מקשים.
    // הנקודה ההתחליתית
    this.xStart = 100;
    this.yStart = 100;
    this.size = 20;
    this.step = 10; // כל תזוזה תזיז את הריבוע ב־10 פיקסלים.
    this.trail = new ArrayList<>(); // אתחול
    this.trail.add(new Point(this.xStart, this.yStart)); // מוסיפים את הנקודה ההתחלתית בתחילת השביל

    addKeyListener(new KeyAdapter() { // כי משתמשים בלחצני הכיוון של המקלדת
      // מוסיפים מאזין לאירועי מקלדת. משתמשים בקי-אדפטר כדי לא לכתוב את כל
      // המתודות של הממשק.

      @Override
      // עבור כל לחיצה
      // יזיז את מיקום הנקודה ויוסיף אותה לרשימה שתצייר במיקום הזה ריבוע
      public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) { // בודקים איזה מקש נלחץ לפי הקוד שלו!!
          // אם לחצו על החץ השמאאלי
          case KeyEvent.VK_LEFT -> xStart -= step; // מקטינים את הנקודה בשיעור איקס כדי להזיז שמאלה
          // אם לחצו על החץ הימני
          case KeyEvent.VK_RIGHT -> xStart += step; // מגדילים את הנקודה בשיעור איקס כדי להזיז ימינה
          // אם לחצו על החץ העליו
          case KeyEvent.VK_UP -> yStart -= step; // מקטינים את הנקודה בשיעור הוויי כדי לזוז למעלה
          // אם לחצו על החץ התחתון
          case KeyEvent.VK_DOWN -> yStart += step; // מגדילים את שיעור הוויי של הנקודה כדי לזוז למטה
        }
        trail.add(new Point(xStart, yStart)); // אחרי כל לחיצה מוסיפים את המיקום החדש לרשימת השובל
        repaint();
      }
    });

  }

  protected void paintComponent(Graphics graphics) {
    super.paintComponent(graphics); // ! כי מוסיפים משהו
    graphics.setColor(Color.PINK); // צבע השביל
    for (Point point : trail) { // עבור כל נקודה ברשימה
      graphics.fillRect(point.x, point.y, this.size, this.size); // תצבע אותו כריבוע
    }
  }

}