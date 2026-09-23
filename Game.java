import javax.swing.*;
import java.awt.*;

class Game extends JFrame{
    public static void main(String[] args){
        Game window = new Game();

        window.setTitle("Spider Boy");
        window.setSize(900,600);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        window.add(new Panel());

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}
