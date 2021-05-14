import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainFrame extends JFrame implements MouseListener {

    JMenuItem createList;
    JMenuItem loadList;
    JMenuItem saveList;

    Buttons remove;
    Buttons save;
    Buttons exit;

    Panel panel;
    TextArea textArea;
    NewListWindow newList;
    JFileChooser fileChooser;
    File name;

    String defaultPath = ""; //set default path for fileChooser


    MainFrame(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("TODO List Application");
        this.setSize(500, 500);
        this.setLayout(null);
        createMenu();
        setButtons();

        panel = new Panel();
        this.add(panel);
        repaint();

        setMenu();
        this.setVisible(true);
    }

    public void createMenu(){
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new FlowLayout());
        menuBar.setBackground(Color.lightGray);

        createList = new JMenuItem("Create new TODO List");
        loadList = new JMenuItem("Load TODO List");
        saveList = new JMenuItem("Save As...");

        menuBar.add(createList);
        menuBar.add(loadList);
        menuBar.add(saveList);

        createList.addMouseListener(this);
        loadList.addMouseListener(this);
        saveList.addMouseListener(this);

        this.setJMenuBar(menuBar);
    }

    public void setMenu(){
        createList.addActionListener(e->{
            textArea = new TextArea();
            newList = new NewListWindow(textArea, panel);
            name = newList.getFileN();
        });

        loadList.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(defaultPath));

            int responce = fileChooser.showOpenDialog(null);
            if (responce == JFileChooser.APPROVE_OPTION){

                name = fileChooser.getSelectedFile();

                try {
                    Scanner in = new Scanner(name);
                    textArea = new TextArea();
                    String line = "";
                    while (in.hasNextLine()){
                        line += in.nextLine() + "\n";
                    }
                    in.close();

                    textArea.setText(line);
                    panel.add(textArea);
                    repaint();

                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });

        saveList.addActionListener(e -> {
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(defaultPath));

            int response = fileChooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION){

                name = fileChooser.getSelectedFile();
                System.out.println(name);
                try {
                    PrintWriter save = new PrintWriter(name);
                    Scanner sc = new Scanner(textArea.getText());
                    while (sc.hasNextLine())
                        save.println(sc.nextLine());

                    save.close();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });
    }

    public void setButtons(){

        save = new Buttons("Save List", 300, 100);
        remove = new Buttons("Delete List", 300, 140);
        exit = new Buttons("Exit list", 300, 180);

        remove.addActionListener(e -> {
            try{
                if (name == null)
                    name = newList.getFileN();

                int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this list?",
                        null, JOptionPane.YES_NO_OPTION);

                if (answer == 0) {
                    File file = new File(String.valueOf(name));
                    if (file.delete()) {
                        JOptionPane.showMessageDialog(null, "The list was successfully deleted.",
                                "Error", JOptionPane.INFORMATION_MESSAGE);
                            panel.remove(textArea);
                            repaint();
                            name = null;
                    }

                }
            }catch (Exception exception){
                    JOptionPane.showMessageDialog(null, "No list is open.",
                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        save.addActionListener(e -> {
            System.out.println(name);
            try {
                if (name == null)
                    name = newList.getFileN();

                PrintWriter save = new PrintWriter(name);
                Scanner sc = new Scanner(textArea.getText());
                while (sc.hasNextLine())
                    save.println(sc.nextLine());

                save.close();
                sc.close();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (Exception exception){
                JOptionPane.showMessageDialog(null, "No list is open.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        exit.addActionListener(e -> {
            try {
                if (name == null)
                    name = newList.getFileN();

                ArrayList<String> textAreaList = new ArrayList<>();
                ArrayList<String> fileList = new ArrayList<>();

                Scanner sc = new Scanner(textArea.getText());
                while (sc.hasNextLine())
                    textAreaList.add(sc.nextLine()+"\n");

                sc = new Scanner(name);
                while (sc.hasNextLine()){
                    fileList.add(sc.nextLine()+"\n");
                }

                int partialCounter = 0;
                int totalCounter = 0;

                for (String elem: textAreaList) {
                    if (fileList.contains(elem)){
                        partialCounter++;
                    }
                    totalCounter++;
                }

                if (partialCounter != totalCounter){

                    int answer = JOptionPane.showConfirmDialog(null, "Do you want to exit without saving changes?",
                            null, JOptionPane.YES_NO_OPTION);//YES=0 NO=1
                    if (answer == 0){
                        panel.remove(textArea);
                        repaint();
                    }

                }else{
                    panel.remove(textArea);
                    repaint();
                    name = null;
                }
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (Exception exception){
                JOptionPane.showMessageDialog(null, "No list is open.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        this.add(remove);
        this.add(save);
        this.add(exit);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == createList) {
            createList.setBackground(new Color(0xCCCCFF));
        }

        else if (e.getSource() == loadList) {
            loadList.setBackground(new Color(0xCCCCFF));
        }

        else {
            saveList.setBackground(new Color(0xCCCCFF));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        createList.setBackground(new Color(0xE8EAF6));
        loadList.setBackground(new Color(0xE8EAF6));
        saveList.setBackground(new Color(0xE8EAF6));
    }
}
