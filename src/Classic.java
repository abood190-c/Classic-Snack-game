import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Classic extends JFrame {
    static int score = 0;
    int xS = 40, yS = 40;
    int xA = 100, yA = 100;
    int flagDir = 1;
    ArrayList<XY> list = new ArrayList<>();
    Timer timer;
    private boolean gameOver = false;

    public Classic(String title) throws HeadlessException {

        super(title);
        list.add(new XY(40, 40));
        score = 0;
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(450, 100);
        this.setSize(600, 600);
        this.setResizable(false);
        this.setUndecorated(true);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT && flagDir != 2) {
                    flagDir = 1;
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT && flagDir != 1) {
                    flagDir = 2;
                } else if (e.getKeyCode() == KeyEvent.VK_UP && flagDir != 4) {
                    flagDir = 3;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && flagDir != 3) {
                    flagDir = 4;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        timer = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flagDir == 1) {
                    xS = 20;
                    yS = 0;
                } else if (flagDir == 2) {
                    xS = -20;
                    yS = 0;
                } else if (flagDir == 3) {
                    xS = 0;
                    yS = -20;
                } else if (flagDir == 4) {
                    xS = 0;
                    yS = 20;
                }
                repaint();
            }
        });
        timer.start();
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);
        int xF = list.get(0).getX();
        int yF = list.get(0).getY();

        if (!(xF > 19 && xF < getWidth() - 39 && yF > 19 && yF < getHeight() - 39)) {
            endGame();
            return;
        }


        if (list.get(0).getX() == xA && list.get(0).getY() == yA) {
            score += 10;
            xA = (int) (1 + Math.random() * 26) * 20;
            yA = (int) (1 + Math.random() * 26) * 20;
            int yN = 0, xN = 0;
            if (flagDir == 1) {
                xN = list.get(list.size() - 1).getX() - 20;
                yN = list.get(list.size() - 1).getY();
            } else if (flagDir == 2) {
                xN = list.get(list.size() - 1).getX() + 20;
                yN = list.get(list.size() - 1).getY();
            } else if (flagDir == 3) {
                xN = list.get(list.size() - 1).getX();
                yN = list.get(list.size() - 1).getY() + 20;
            } else if (flagDir == 4) {
                xN = list.get(list.size() - 1).getX();
                yN = list.get(list.size() - 1).getY() - 20;
            }

            list.add(new XY(xN, yN));
        }

        g.drawString("Score: " + score, getWidth() - 98, 50);
        g.setColor(Color.RED);
        g.fillOval(xA, yA, 20, 20);
        g.setColor(Color.BLACK);

        for (int i = list.size() - 1; i >= 0; i--) {
            if (i == 0) {
                list.get(i).setX(list.get(i).getX() + xS);
                list.get(i).setY(list.get(i).getY() + yS);
            } else {
                list.get(i).setX(list.get(i - 1).getX());
                list.get(i).setY(list.get(i - 1).getY());
            }
        }
        for (int i = 0; i < list.size(); i++) {
            g.fillOval(list.get(i).getX(), list.get(i).getY(), 20, 20);
        }
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), 20);
        g.fillRect(0, getHeight() - 20, getWidth(), 20);
        g.fillRect(0, 0, 20, getHeight());
        g.fillRect(getWidth() - 20, 0, 20, getHeight());

    }

    private void endGame() {
        timer.stop();
        gameOver = true;
    }

    public void waitForGameOver() {
        while (!gameOver) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        dispose();
    }
}