import java.awt.*;

public class Ball {
    private int x, y;
    private int diameter = 30; // שלושת רבעי מגודל הראש של השחקן
    private int dx = 2, dy = 2;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g, int panelWidth, int panelHeight) {
        Graphics2D g2d = (Graphics2D) g;

        // ציור כדור בצבע כדורגל מקצועי
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x, y, diameter, diameter);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x, y, diameter, diameter);
        drawPentagons(g2d);
    }

    private void drawPentagons(Graphics2D g2d) {
        int[] xPoints = { x + 15, x + 22, x + 30, x + 22, x + 15, x + 7, x + 0, x + 7 };
        int[] yPoints = { y + 0, y + 7, y + 15, y + 22, y + 30, y + 22, y + 15, y + 7 };
        g2d.setColor(Color.BLACK);

        // צייר מספר מחומשים כדי להמחיש את הכדור כדורגל מקצועי
        for (int i = 0; i < xPoints.length; i++) {
            g2d.fillPolygon(new int[] { xPoints[i], xPoints[(i + 1) % xPoints.length], x + diameter / 2 },
                    new int[] { yPoints[i], yPoints[(i + 1) % yPoints.length], y + diameter / 2 }, 3);
        }
    }

    public void move(int panelWidth, int panelHeight) {
        if (x + dx < 0 || x + dx > panelWidth - diameter) {
            dx *= -1;
        }
        if (y + dy < 0 || y + dy > panelHeight - diameter) {
            dy *= -1;
        }

        x += dx;
        y += dy;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Rectangle getBounds(int panelWidth) {
        return new Rectangle(x, y, diameter, diameter);
    }

    public void reverseDirection() {
        dx *= -1;
        dy *= -1;
    }
}
