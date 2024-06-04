import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private Timer timer;
    private Player player1, player2;
    private Ball ball;
    private Goal goal1, goal2;
    private int scorePlayer1, scorePlayer2;
    private final double aspectRatio = 4.0 / 3.0; // יחס רוחב-גובה של הפאנל המקורי
    private int countdown = 3;
    private Timer countdownTimer;

    public GamePanel() {
        setFocusable(true);
        setPreferredSize(new Dimension(800, 600));
        setLayout(null); // מאפשר מיקום חופשי של כפתורים
        addKeyListener(this);
        initGame();
        startCountdown();
    }

    private void initGame() {
        player1 = new Player(50, 300, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        player2 = new Player(700, 300, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        ball = new Ball(400, 0); // התחל עם הכדור מעל המסך

        // אתחול השערים עם גודל ראשוני כלשהו
        goal1 = new Goal(0, 250, 20, 100);
        goal2 = new Goal(780, 250, 20, 100);

        timer = new Timer(5, this);
    }

    private void startCountdown() {
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (countdown > 0) {
                    countdown--;
                } else {
                    countdownTimer.stop();
                    timer.start(); // התחל את המשחק לאחר הספירה לאחור
                }
                repaint();
            }
        });
        countdownTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = (int) (width / aspectRatio); // שומר על היחס המקורי

        if (height > getHeight()) {
            height = getHeight();
            width = (int) (height * aspectRatio); // עדכון הרוחב בהתאמה
        }

        // מרכז הפאנל כדי לשמור על מיקום נכון של המשחק
        int xOffset = (getWidth() - width) / 2;
        int yOffset = (getHeight() - height) / 2;

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(xOffset, yOffset);
        g2d.setClip(0, 0, width, height);

        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, 0, width, height);

        // עדכון מיקום וגודל השערים
        int goalWidth = 20;
        int goalHeight = height / 2;

        goal1 = new Goal(0, (height - goalHeight) / 2, goalWidth, goalHeight);
        goal2 = new Goal(width - goalWidth, (height - goalHeight) / 2, goalWidth, goalHeight);

        player1.draw(g2d, width, height);
        player2.draw(g2d, width, height);
        ball.draw(g2d, width, height);
        goal1.draw(g2d, width, height);
        goal2.draw(g2d, width, height);
        drawScore(g2d, width);

        if (countdown > 0) {
            drawCountdown(g2d, width, height);
        }

        g2d.dispose();
    }

    private void drawScore(Graphics g, int width) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Player 1: " + scorePlayer1, width / 5, 50);
        g.drawString("Player 2: " + scorePlayer2, 4 * width / 5, 50);
    }

    private void drawCountdown(Graphics g, int width, int height) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString(Integer.toString(countdown), width / 2 - 12, height / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (countdown == 0) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            player1.move(panelWidth, panelHeight);
            player2.move(panelWidth, panelHeight);
            ball.move(panelWidth, panelHeight);
            checkCollision(panelWidth);
            checkBallPlayerCollision();
        }
        repaint();
    }

    private void checkCollision(int panelWidth) {
        if (goal1 == null || goal2 == null) {
            return; // אם השערים לא מאותחלים, לא לעשות כלום
        }

        if (ball.getBounds(panelWidth).intersects(goal1.getBounds())) {
            scorePlayer2++;
            resetBall();
        }
        if (ball.getBounds(panelWidth).intersects(goal2.getBounds())) {
            scorePlayer1++;
            resetBall();
        }
    }

    private void checkBallPlayerCollision() {
        if (ball.getBounds(getWidth()).intersects(player1.getHeadBounds()) ||
                ball.getBounds(getWidth()).intersects(player2.getHeadBounds())) {
            ball.reverseDirection();
        }
    }

    private void resetBall() {
        ball.setPosition(getWidth() / 2, 0); // אתחול הכדור למעלה במרכז המסך
        countdown = 3;
        startCountdown();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        player1.keyPressed(e);
        player2.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player1.keyReleased(e);
        player2.keyReleased(e);
    }
}
