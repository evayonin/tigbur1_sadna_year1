package com.example;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImagePanel extends JPanel { // מימוש בWindow
  private BufferedImage originalImage; // תמיד חייב לשמור עותק של התמונה המקורית
  private BufferedImage currentImage; // המשתנה שבו נשמור את התמונה המקורית ששינינו
  private double scale;

  public ImagePanel(int x, int y, int width, int height) {
    this.setLayout(null);
    this.setBounds(x, y, width, height);
    // this.scale = 1;
    createMyImage();

    // לא עובד:
    /*
     * JButton hMirror = new JButton("H mirror");
     * hMirror.setBounds(0, 0, 30, 20);
     * this.add(hMirror);
     * hMirror.addMouseListener(new MouseAdapter() {
     * public void mouseClicked(MouseEvent event) {
     * horizontalMirror();
     * }
     * });
     * JButton vMirror = new JButton("V mirror");
     * vMirror.setBounds(31, 0, 30, 20);
     * this.add(vMirror);
     * vMirror.addMouseListener(new MouseAdapter() {
     * public void mouseClicked(MouseEvent event) {
     * verticalMirror();
     * }
     * });
     */

    // הרבה יותר כדאי פשוט לעשות עם אקשן ליסנר כי זה תמיד מגיב לאינטרקציה עם הכפתור
    // בלי שימוש בעוד מתודות:
    // כמו שאביה עשתה

    // כפתור מראה אופקית
    JButton hMirror = new JButton("H mirror");
    hMirror.setBounds(10, 10, 100, 30); // מיקום בתוך הפאנל
    this.add(hMirror);
    hMirror.addActionListener(e -> horizontalMirror());

    // כפתור מראה אנכית
    JButton vMirror = new JButton("V mirror");
    vMirror.setBounds(120, 10, 100, 30);
    this.add(vMirror);
    vMirror.addActionListener(e -> verticalMirror());
  }

  private void createMyImage() {
    try {
      this.originalImage = ImageIO.read(new File("src/demo/src/main/resources/sunset-1373171.jpg")); // copy relative
                                                                                                     // path
      // this.currentImage = this.originalImage; // קורנט קיבל את אותה כתובת בזיכרון
      // כמו אוריג׳ינל ומצביע על אותה תמונה
      // בדיוק ולכן זה לא טוב
      // אנחנו רוצים לעשות דיפ-קופי ז״א להעתיק את אותה תמונה לכתובת אחרת בזיכרון
      // ככה שהשינויים ייראו רק בקורנט ולא באוריגינל
      // ניצור פונקציה שתעתיק כל פיקסל
      this.currentImage = deepCopy();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private BufferedImage deepCopy() {
    // כשיוצרים האפרד אימג׳ חדש צריך לשלוח רוחב גובה וסוג(סיומת)
    BufferedImage copy = new BufferedImage(this.originalImage.getWidth(), this.originalImage.getHeight(),
        this.originalImage.getType());
    for (int y = 0; y < copy.getHeight(); y++) { // שורות(גובה)
      for (int x = 0; x < copy.getWidth(); x++) { // עמודות(רוחב)
        int imageRgb = this.originalImage.getRGB(x, y);
        copy.setRGB(x, y, imageRgb);
      }
    }
    return copy;
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g); // חייב אם אנחנו כותבים מחלקה חדשה ושרוצים להוסיף משהו
    /*
     * g.setColor(Color.BLUE);
     * g.fillRect(50, 50, 186, 30); // דןגמה
     * g.fillOval(100, 100, 60, 60); // דןגמה
     * // כל מצולע: פוליגון=מצולע
     * // משולש:
     * int[] xT = { 60, 100, 150 };
     * int[] yT = { 126, 200, 200 };
     * g.fillPolygon(xT, yT, 3); // מקבל מערך נקודות וקודקודים
     * // מחומש:
     * int[] xT2 = { 60, 100, 150, 20, 50 };
     * int[] yT2 = { 126, 200, 200, 300, 100 };
     * g.fillPolygon(xT2, yT2, 3);
     */
    if (this.currentImage != null) {
      Graphics2D g2d = (Graphics2D) g; // casting
      // לגרפיקס 2די יש הרבה יותר אפשרויות ומתודות מאשר סתם גרפיקס אז נמיר
      /*
       * int imageWidth = this.currentImage.getWidth();
       * int imageHeight = this.currentImage.getHeight();
       * 
       * int panelWidth = this.getWidth();
       * int panelHeight = this.getHeight();
       * 
       * int x = (panelWidth - (int) (imageWidth * scale)) / 2;
       * int y = (panelHeight - (int) (imageHeight * scale)) / 2;
       * 
       * g2d.translate(x, y); // כדי לשים את התמונה במרכז
       */

      // g2d.scale(this.scale, this.scale); // מקבלת דאבל
      // מכפיל את איקס ומכפיל את וויי

      // אם עושים סקייל חייב להגדיר אותו בבנאי אחרת לא יציג את התמונה

      // g2d.drawImage(this.currentImage, 0, 0, this);
      // התמונה שייצייר, איקס, וויי,איפה טוען את התמונה

      g2d.drawImage(this.currentImage, 0, 0, getWidth(), getHeight(), this);
      // שיתאים את גודל התמונה לפאנל

    }
  }

  public void verticalMirror() {
    int width = this.currentImage.getWidth();
    int height = this.currentImage.getHeight();

    for (int y = 0; y < height / 2; y++) { // נחליף את הערכים המתאימים עד החצי (כמו פלינדרום) בגובה
      for (int x = 0; x < width; x++) {
        // עבור כל פיקסל:
        int oppositeY = height - 1 - y; // בצד השני בהתאם

        int originalRGB = this.currentImage.getRGB(x, y); // הפיקסל המקורי
        int oppositeRGB = this.currentImage.getRGB(x, oppositeY); // הפיקסל באותה עמודה בצד השני

        this.currentImage.setRGB(x, y, oppositeRGB); // ישים במיקום המקורי את הצבע ההפוך
        this.currentImage.setRGB(x, oppositeY, originalRGB); // ישים במיקום ההפוך את הצבע המקורי
      }
    }
    repaint();
  }

  public void horizontalMirror() {
    int width = this.currentImage.getWidth();
    int height = this.currentImage.getHeight();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width / 2; x++) { // נחליף את הערכים המתאימים עד החצי (כמו פלינדרום) ברוחב
        // עבור כל פיקסל:
        int oppositeX = width - 1 - x; // בצד השני בהתאם

        int originalRGB = currentImage.getRGB(x, y); // הפיקסל המקורי
        int oppositeRGB = this.currentImage.getRGB(oppositeX, y); // הפיקסל באותה שורה בצד השני

        currentImage.setRGB(x, y, oppositeRGB); // ישים במיקום המקורי את הצבע ההפוך
        currentImage.setRGB(oppositeX, y, originalRGB); // ישים במיקום ההפוך את הצבע המקורי
      }
    }
    repaint();
  }
}
