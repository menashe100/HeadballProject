import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame {

    private GamePanel gamePanel;
    private boolean isFullScreen = false;
    private Dimension windowedSize = new Dimension(800, 600);

    public GameFrame() {
        setTitle("Head Ball Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new GamePanel();
        add(gamePanel);
        pack();
// SSS
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem fullScreenItem = new JMenuItem("Toggle Full Screen");

        fullScreenItem.addActionListener(e -> toggleFullScreen());

        optionsMenu.add(fullScreenItem);
        menuBar.add(optionsMenu);
        setJMenuBar(menuBar);

        setVisible(true);
    }

    private void toggleFullScreen() {
        if (isFullScreen) {
            dispose();
            setUndecorated(false);
            setPreferredSize(windowedSize);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
            isFullScreen = false;
        } else {
            dispose();
            setUndecorated(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setVisible(true);
            isFullScreen = true;
        }
        gamePanel.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}
