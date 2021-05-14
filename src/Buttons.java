import javax.swing.*;

public class Buttons extends JButton {

    Buttons(String text, int x, int y){
        this.setBounds(x, y, 120, 30);
        this.setText(text);
        this.setFocusable(false);
    }
}
