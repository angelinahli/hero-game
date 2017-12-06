import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.awt.image.ImageObserver;
import javax.imageio.*;
import java.io.*;

import java.util.Vector;
import java.util.Random;

public class HeroGame extends JPanel {
    protected static final int BORDER = 10;
    protected static final int WIDTH = 300;
    protected static final int HEIGHT = 500;
    protected static final int HERO_SIZE = 15;

    protected static final Color BORDER_COLOR = new Color(75, 0, 130);
    protected static final Color HERO_COLOR = new Color(148, 0, 211);
    protected static final Color VILLAIN_COLOR = new Color(255, 215, 0);
    protected static final Font SCORE_FONT = new Font("Sans Serif", Font.BOLD, 15);

    private Hero hero;
    private Timer timer;
    private int difficulty, score, time;
    private KeyListener kListener;
    private Vector<Monster> monsters;
    private Random rand;
    private boolean died;

    public HeroGame() {
        hero = new Hero(WIDTH/2, HEIGHT - BORDER - 50, HERO_SIZE, HERO_COLOR);
        kListener = new MoveListener();
        monsters = new Vector<Monster>();
        rand = new Random(100);
        difficulty = 1;
        score = 0;
        timer = new Timer(400, new TimerListener());
        died = false;

        setBorder(BorderFactory.createLineBorder(BORDER_COLOR, BORDER));
        setFocusable(true);
        addKeyListener(kListener);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        makeScore(g);
        hero.drawCharacter(g);
        for(Monster monster : monsters) {
            monster.drawCharacter(g);
        }

        if (died)
            makeDeathPane(g);
        
        repaint();
    }

    private void makeScore(Graphics g) {
        g.setColor(BORDER_COLOR);
        g.setFont(SCORE_FONT);
        g.drawString("Score: " + score, BORDER + 5, BORDER + 15);
    }

    private void makeDeathPane(Graphics g) {
        try {
            Image img = ImageIO.read(new File("images/dead.png"));
            g.drawImage(img, 0, HEIGHT/2 - 50, new PaneObserver()); // img 300x100
        } catch (IOException e) {
            System.out.println("Couldn't open image");
        }
    }

    private void checkDied() {
        Rectangle heroBounds = hero.getBounds();
        for(Monster monster : monsters) {
            if (heroBounds.intersects(monster.getBounds())) {
                died = true;
                timer.stop();
                removeKeyListener(kListener);
            }
        }
    }

    // LISTENERS //

    public class PaneObserver implements ImageObserver {
        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
            return false;
        }
    }

    public class MoveListener implements KeyListener {

        public void keyPressed(KeyEvent e) {
            int keyPressed = e.getKeyCode();
            if (keyPressed == KeyEvent.VK_LEFT || keyPressed == KeyEvent.VK_A) {
                hero.moveLeft();
                checkDied();
            }
            if (keyPressed == KeyEvent.VK_RIGHT || keyPressed == KeyEvent.VK_D) {
                hero.moveRight();
                checkDied();
            }
        }
        public void keyReleased(KeyEvent e) {}
        public void keyTyped(KeyEvent e) {}
    }

    public class TimerListener implements ActionListener {

        public void clearMonsters() {
            Vector<Monster> newMonsters = new Vector<Monster>();
            
            int numMonsters = (HEIGHT / (difficulty*HERO_SIZE)) + 10;
            int count = 0;
            for (int i = monsters.size() - 1; i >= 0 && count < numMonsters; i--) {
                newMonsters.add(monsters.get(i));
                count++;
            }
            monsters = newMonsters;
        }
        
        public void actionPerformed(ActionEvent event) {
            
            if (event.getSource() == timer) {
                int xPos = rand.nextInt(WIDTH - (difficulty*HERO_SIZE));
                
                monsters.add(new Monster(xPos, BORDER, difficulty*HERO_SIZE, VILLAIN_COLOR));
                
                for (Monster monster : monsters)
                    monster.moveDown();

                checkDied();
                time++;
                score += difficulty*5;

                if (time % 50 == 0)
                    clearMonsters();

                if (time % 100 == 0)
                    difficulty ++;
            }
        }

    }

}