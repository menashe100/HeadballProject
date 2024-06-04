import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    private int x, y;
    private int width = 40, height = 80; // גודל הדמות: גובה מוגדל כדי להכיל ראש וגוף
    private int dx, dy;
    private int up, down, left, right, jump;
    private boolean isJumping = false;
    private int jumpHeight = 2 * height; // גובה קפיצה פי שתיים מגובה השחקן
    private int initialY;
    private boolean goingUp = true;

    public Player(int x, int y, int up, int down, int left, int right, int jump) {
        this.x = x;
        this.y = y;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.jump = jump;
    }

    public void draw(Graphics g, int panelWidth, int panelHeight) {
        g.setColor(Color.RED);
        // צייר ראש
        g.fillOval(x, y, width, width); // ראש בגובה ורוחב של 40
        // צייר גוף
        g.fillRect(x, y + width, width, height - width); // גוף בגובה של 40 (סה"כ גובה 80)
    }

    public void move(int panelWidth, int panelHeight) {
        int halfHeight = panelHeight / 2;

        if (x + dx > 0 && x + dx < panelWidth - width) {
            x += dx;
        }

        if (isJumping) {
            if (goingUp) {
                y -= 5; // עלייה
                if (y <= initialY - jumpHeight) {
                    goingUp = false;
                }
            } else {
                y += 5; // ירידה
                if (y >= initialY) {
                    y = initialY;
                    isJumping = false;
                }
            }
        } else {
            if (y + dy > 0 && y + dy < halfHeight - height) {
                y += dy;
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == up) {
            dy = -1;
        }
        if (key == down) {
            dy = 1;
        }
        if (key == left) {
            dx = -1;
        }
        if (key == right) {
            dx = 1;
        }
        if (key == jump && !isJumping) {
            isJumping = true;
            initialY = y;
            goingUp = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == up || key == down) {
            dy = 0;
        }
        if (key == left || key == right) {
            dx = 0;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public Rectangle getHeadBounds() {
        return new Rectangle(x, y, width, width); // מחזיר את הגבולות של הראש בלבד
    }
}
