import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    Panel(){
        this.setBounds(50, 50, 170, 250);
        this.setBackground(Color.lightGray);
        this.setBorder(BorderFactory.createLoweredBevelBorder());
        this.setLayout(null);
    }
}
