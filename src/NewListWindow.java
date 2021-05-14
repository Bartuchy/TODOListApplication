import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class NewListWindow{
    JFrame frame = new JFrame();
    JTextField textField = new JTextField();
    JButton ok = new JButton("OK");
    JButton cancel = new JButton("Cancel");
    JLabel label = new JLabel("Name the list");

    String name;
    File file = null;

    public File getFileN(){
        return file;
    }

    NewListWindow(TextArea textArea, Panel panel){
        textField.setBounds(75, 35, 150, 20);
        label.setBounds(110, 10, 120, 20);
        ok.setBounds( 70, 65,80,20);
        cancel.setBounds(150,65, 80, 20);

        ok.addActionListener(e -> {
            name = textField.getText();
            file = new File("C:/Users/barte/Desktop/"+name+".txt");

            if (file.exists()){
                JOptionPane.showMessageDialog(null, "This file already exists",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                try {
                    file.createNewFile();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                panel.add(textArea);
                panel.repaint();
                frame.dispose();
            }
        });

        cancel.addActionListener(e -> {
            frame.dispose();
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300,150);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.add(textField);
        frame.add(label);
        frame.add(ok);
        frame.add(cancel);
    }

}
