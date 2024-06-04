import javax.swing.*;
import java.awt.*;

public class HeadBallGame extends JFrame {

    public HeadBallGame() {
        setTitle("Head Ball Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // הוספת לוח המשחק
        add(new GamePanel());
    }

    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            HeadBallGame game = new HeadBallGame();
//            game.setVisible(true);
//        });
        JFrame frame = new JFrame();
        frame.setSize(900,900);
        frame.setLayout(new GridLayout(3,3));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setResizable(false);

        for (int i = 0; i < 9; i++) {
            JButton button = new JButton();
            button.addActionListener((event)->{
                button.setText("X");
            });
            frame.add(button);
        }
        frame.setVisible(true);
    }
}
