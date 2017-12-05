import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class HeroGameGUI extends JFrame implements Runnable {

    public HeroGameGUI() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(HeroGame.WIDTH, HeroGame.HEIGHT);
        getContentPane().add(new HeroGame());
    }

    public void run() {
        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new HeroGameGUI());
    }

}