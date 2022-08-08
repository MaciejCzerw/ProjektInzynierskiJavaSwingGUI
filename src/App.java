import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class App extends JFrame {
    private JPanel panelMain;
    private JButton newModelButton;
    private JTextField getNodeName;
    private JLabel showNodeName;
    private JTextField criteriaTextBox;
    private JButton criteriaButton;
    private JLabel criteriaLabel;
    private JPanel criteriaPanel;
    private JButton matrixButton;
    private JButton visualButton;
    private JButton verbalButton;
    private JPanel ButtonPanel;
    private JButton showWeights;
    private JList criteriaList;
    private JPanel criteriaListPanel;
    public ArrayList<String> criteriaArray;

    public float[] getWeights() {
        return weights;
    }

    public void setWeights(float[] weights) {
        this.weights = weights;
    }

    public float[] weights;

    String nodeName = "";


    public App() {

        criteriaArray = new ArrayList<>();
        criteriaPanel.setVisible(false);
        criteriaPanel.setSize(100, 100);
        ButtonPanel.setVisible(false);
        DefaultListModel<String> defListModel = new DefaultListModel<String>();
        criteriaList.setModel(defListModel);
        criteriaList.setVisible(false);

        newModelButton.addActionListener(e -> {
            if (!getNodeName.getText().isBlank()) {
                nodeName = "" + getNodeName.getText();
                getNodeName.setVisible(false);
                newModelButton.setVisible(false);
                showNodeName.setText(nodeName);
                showNodeName.setFont(new Font("Times New Roman", Font.BOLD, 20));
                criteriaPanel.setVisible(true);
                criteriaList.setVisible(true);
            }
        });

        getNodeName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getNodeName.setText("");
            }
        });

        criteriaButton.addActionListener(e -> {
            if (!criteriaTextBox.getText().isBlank() && criteriaArray.size() < 5) {
                criteriaArray.add(criteriaTextBox.getText());
                defListModel.addElement(criteriaTextBox.getText());
            }

            if (criteriaArray.size() == 2) {
                ButtonPanel.setVisible(true);
            }
        });

        matrixButton.addActionListener(e -> {
            MatrixInput matrixWindow = new MatrixInput(criteriaArray.size(),criteriaArray);
            matrixWindow.setSize(300, 300);
            matrixWindow.setLocationRelativeTo(null);
            matrixWindow.setVisible(true);
            setWeights(matrixWindow.getWeights());
        });

        showWeights.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(float weight : weights){
                    System.out.println(weight);
                }
            }
        });

    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("AHP Program");
        frame.setResizable(true);
        frame.setSize(512, 512);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}

//        menuBar = new JMenuBar();
//        fileMenu = new JMenu("File");
//        export = new JMenu("Export To");
//        newFileItem = new JMenuItem("New File");
//        openFileItem = new JMenuItem("Open File");
//        saveFileItem = new JMenuItem("Save File");
//        toCSVFileItem = new JMenuItem("to CSV");
//        toXMLFileItem = new JMenuItem("to XML");
//
//        helpMenu = new JMenu("Help");
//
//        fileMenu.add(newFileItem);
//        fileMenu.add(openFileItem);
//        fileMenu.add(saveFileItem);
//        export.add(toCSVFileItem);
//        export.add(toXMLFileItem);
//        fileMenu.add(export);
//        menuBar.add(fileMenu);
//        menuBar.add(helpMenu);


//    JMenuItem newFileItem;
//    JMenuItem openFileItem;
//    JMenuItem saveFileItem;
//    JMenuItem toCSVFileItem;
//    JMenuItem toXMLFileItem;
//    JMenu fileMenu;
//    JMenu helpMenu;
//    JMenu export;
