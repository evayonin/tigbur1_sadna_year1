package com.example;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

// במקרה הזה, השימוש בג׳ייפאנל לא נחוץ,  
// כי החלון פשוט ומכיל מעט רכיבים בלבד.  
// אין כאן צורך לנהל מסכים במקביל או שכבות,  
// אלא רק מעבר אחד לוגין אל המשחק.  
// לכן הוספה ישירה לג׳ייפריים היא יעילה,  
// וקלה להבנה בלי לסבך את מבנה הקוד.  
// אם בעתיד תוסיפי מסכים או תפריטים –  
// אז שימוש בפאנל יהיה נכון ומומלץ.  

public class LoginWindow extends JFrame {
  private JTextField usernameTextField;
  private JPasswordField passwordField;
  private JLabel feesBack;

  // הגדרות ברירת מחדל לשם משתמש וסיסמה תקינים כדי שיעבוד
  public static final String USER_NAME = "abc";
  public static final String PASSWORD = "123";

  public LoginWindow() {
    this.setSize(500, 500);
    this.setLayout(null);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setTitle("Login");

    createUsernameField();
    creatPasswordFiled();
    creatFeedBack();
    creatLoginButton();
    this.setVisible(true);
  }

  // הכי נכון לעשות את הפעולות של הפידבק או יצירת המשחק כאן כי הפעולות של הצגת
  // הפידבק או יצירת המשחק נמצאות במאזין של הכפתור כי הן חלק מהתגובה הישירה ללחיצה
  // – וזה בדיוק המקום בו מקבלים החלטה לפי קלט המשתמש. כך נשמרת בהירות, סדר
  // והיגיון בתכנון המערכת.
  private void creatLoginButton() {
    JButton loginButton = new JButton("Login");
    // טיפה מימין ולמטה מהשדה של הסיסמה
    loginButton.setBounds(this.passwordField.getX() + 10,
        (this.passwordField.getY() + this.passwordField.getHeight() + 20), 100, 30);
    this.add(loginButton);
    loginButton.addActionListener((event) -> { // תמיד עם אקשן ליסנר כמו שאביה עושה
      //// אחרי כל לחיצה
      String username = this.usernameTextField.getText(); // עדכון משתנה היוזרניים לפי מה שהוכנס לשדה הססמה
      String password = new String(this.passwordField.getPassword()); // עדכון משתנה הססמה לפי הסיסמה שהוכנסה לשדה הססמה
                                                                      // (מומר ממערך תווים למחרוזת של נקודות)
      // הדפסה לקונסול (לבדיקה)
      System.out.println(username);
      System.out.println(password);

      if (username.equals(USER_NAME) && password.equals(PASSWORD)) { // אם לא שווה לברירת מחדל(המקרה שבו אמור לעבוד לפי
                                                                     // מה שקבענו)
        switchToGamePanel(); // יעבור למסך של המשחק
      } else {
        this.feesBack.setText("שם משתמש או סיסמא שגויים ");
        this.feesBack.setForeground(Color.RED);
      }
    }); ////
  }

  // המתודה הזאת מחליפה את תוכן החלון
  // ממסך ההתחברות למסך המשחק בצורה חלקה ופשוטה.
  // היא מוחקת את כל הרכיבים הקיימים בחלון (רמוב אול),
  // יוצרת פאנל חדש של המשחק ומוסיפה אותו לחלון.
  // לאחר מכן המרעננת את התצוגה עם ריואלידייט וריפיינט,
  // ודואגת שהפאנל החדש יקבל פוקוס לקבלת קלט.
  // כך מבוצע מעבר מסך בתוך אותו ג׳ייפריים בלי לפתוח חלון חדש.
  private void switchToGamePanel() {
    getContentPane().removeAll(); // מסיר את כל הרכיבים מהחלון
    // !!1!111!!!!!!!1!!!!
    Game game = new Game(0, 0, 500, 500); // יצירת אובייקטמסשחק חדש
    this.add(game);
    revalidate(); // ריענון ללייאאוט!!
    repaint(); // ציור מחדש של המסך!!
    game.requestFocusInWindow(); // מוודא שהמקלדת תפעל ישירות על המשחק!!

  }

  private void creatFeedBack() { // יוצר תווית להודעות שגיאה או פידבק
    this.feesBack = new JLabel(""); // תווית ריקה בהתחלה
    this.feesBack.setBounds(40, 130, 300, 25);
    this.add(this.feesBack);
  }

  private void creatPasswordFiled() { // כנ״ל כמו יוזרניים פילד
    JLabel passwordLabel = new JLabel("Password:");
    // ארבעים פיקסלים מתחת לדשה שם משתמש
    passwordLabel.setBounds(40, 60, 100, 25);
    passwordLabel.setOpaque(true);
    passwordLabel.setBackground(Color.CYAN);
    this.add(passwordLabel);

    this.passwordField = new JPasswordField();
    this.passwordField.setBounds((this.usernameTextField.getX()),
        passwordLabel.getY(), 180, 25);
    this.add(this.passwordField);
  }

  private void createUsernameField() {
    JLabel usernameLabel = new JLabel("username: "); // גיילייבל זה הצגת כותרת
    usernameLabel.setBounds(40, 20, 100, 25);
    usernameLabel.setOpaque(true); // רקע אטום
    usernameLabel.setBackground(Color.CYAN);
    this.add(usernameLabel);

    this.usernameTextField = new JTextField(); // יוצרים שדה טקסט לשם המשתמש (אתחול)
    // מיקום השדה בצמוד לתווית באותה שורה ובאותו גובה
    this.usernameTextField.setBounds((usernameLabel.getX() + usernameLabel.getWidth() + 20),
        usernameLabel.getY(), 180, 25);

    this.add(this.usernameTextField);
  }

}