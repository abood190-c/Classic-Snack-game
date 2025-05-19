import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String[] modes = {"Classic", "Open Field"};
        Icon icon = new ImageIcon("src/1.jpg");
        int k;

        while (true) {
            int selection = JOptionPane.showOptionDialog(null, "Choose the mode", "Snake Game", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, icon, modes, null);

            if (selection == 0) {
                Classic game = new Classic("Classic Snake Game");
                game.waitForGameOver();
            } else if (selection == 1) {
                OpenField game = new OpenField("Open Field Snake Game");
                game.waitForGameOver();
            } else {
                break;
            }

            k = JOptionPane.showConfirmDialog(null, "You Lose with Score: " + (OpenField.score + Classic.score) + "\nDo you want to try again?", "Game Over", JOptionPane.YES_NO_OPTION);
            if (k != JOptionPane.YES_OPTION) {
                break;
            }
        }

        System.exit(0);
    }
}
