package main;
import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("idk");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        System.out.println("hello word");
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
        System.out.println();
    }
}