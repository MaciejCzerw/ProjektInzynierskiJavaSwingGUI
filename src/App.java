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
    private JButton addCriteriaButton;
    private JLabel criteriaLabel;
    private JPanel addCriteriaPanel;
    private JButton matrixButton;
    private JButton visualButton;
    private JPanel buttonPanel;
    private JList criteriaList;
    private JList alternativesList;
    private JPanel criteriaListPanel;
    private JPanel alternativesListPanel;
    private JPanel criteriaPanel;
    private JPanel alternativePanel;
    private JButton clearButton;
    private JButton alternativesChoiceFlagButton;
    private JPanel addAlternativePanel;
    private JButton addAlternativeButton;
    private JTextField alternativeTextBox;
    private JPanel operationalButtonsPanel;
    private JButton alternativeValuesButton;
    private JButton calculateButton;
    public ArrayList<String> criteriaArray;
    public ArrayList<String> alternativeArray;
    public boolean alternativeChoiceFlag;
    float sumRow;

    public float finalValue;

    public ArrayList<Float> finalValues;
    public float[][] finalFinalValue;

    public float[] getWeights() {
        return weights;
    }

    public void setWeights(float[] weights) {
        this.weights = weights;
    }

    public boolean getAlternativeChoiceFlag(){return alternativeChoiceFlag;}
    public void setAlternativeChoiceFlag(boolean alternativeChoiceFlag){this.alternativeChoiceFlag = alternativeChoiceFlag;}
    public void setAlternativeWeights(float[] alternativeWeights){this.alternativeWeights = alternativeWeights;}

    public float[] weights;
    public float[] alternativeWeights;

    String nodeName = "";


    public App() {
        DefaultListModel<String> criteriaListModel = new DefaultListModel<>();
        DefaultListModel<String> alternativesListModel = new DefaultListModel<>();
        criteriaArray = new ArrayList<>();
        alternativeArray = new ArrayList<>();
        finalValues = new ArrayList<>();
        addCriteriaPanel.setVisible(false);
        addAlternativePanel.setVisible(false);
        addCriteriaPanel.setSize(100, 100);
        buttonPanel.setVisible(false);
        criteriaList.setModel(criteriaListModel);
        alternativesList.setModel(alternativesListModel);
        criteriaListPanel.setVisible(false);
        alternativesListPanel.setVisible(false);
        operationalButtonsPanel.setVisible(false);


        newModelButton.addActionListener(e -> {
            if (!getNodeName.getText().isBlank()) {
                nodeName = "" + getNodeName.getText();
                getNodeName.setVisible(false);
                newModelButton.setVisible(false);
                showNodeName.setText(nodeName);
                showNodeName.setFont(new Font("Times New Roman", Font.BOLD, 20));
                addCriteriaPanel.setVisible(true);
                criteriaListPanel.setVisible(true);
                alternativesListPanel.setVisible(true);
                addAlternativePanel.setVisible(true);
                operationalButtonsPanel.setVisible(true);
            }
        });

        getNodeName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getNodeName.setText("");
            }
        });

        addCriteriaButton.addActionListener(e -> {
            if (!criteriaTextBox.getText().isBlank() && criteriaArray.size() < 10) {
                criteriaArray.add(criteriaTextBox.getText());
                criteriaListModel.addElement(criteriaTextBox.getText());
            }

            if (criteriaArray.size() == 2) {
                buttonPanel.setVisible(true);
            }
            criteriaTextBox.setText("");
        });

        addAlternativeButton.addActionListener(e -> {
            if (!alternativeTextBox.getText().isBlank() && alternativeArray.size() < 10) {
                alternativeArray.add(alternativeTextBox.getText());
                alternativesListModel.addElement(alternativeTextBox.getText());
            }

            alternativeTextBox.setText("");
        });

        matrixButton.addActionListener(e -> {
            MatrixInput matrixWindow = new MatrixInput(criteriaArray.size(),criteriaArray);
            matrixWindow.setSize(300, 300);
            matrixWindow.setLocationRelativeTo(null);
            matrixWindow.setVisible(true);
            int i=0;
            setWeights(matrixWindow.getWeights());
            for(float weight : weights){
                criteriaListModel.setElementAt(criteriaListModel.getElementAt(i) +" (" + weight +")",i);
                i++;
            }
        });

        visualButton.addActionListener(e -> {
            VisualInput visualWindow = new VisualInput(criteriaArray.size(),criteriaArray);
            visualWindow.setSize(300, 300);
            visualWindow.setLocationRelativeTo(null);
            visualWindow.setVisible(true);
        });

        alternativesChoiceFlagButton.addActionListener(e -> {
            AlternativeChoiceFlagMenu alternativeChoiceFlagMenu = new AlternativeChoiceFlagMenu();
            alternativeChoiceFlagMenu.setSize(200, 130);
            alternativeChoiceFlagMenu.setLocationRelativeTo(null);
            alternativeChoiceFlagMenu.setVisible(true);
            setAlternativeChoiceFlag(alternativeChoiceFlagMenu.getAlternativeChoiceFlag());
        });

        alternativeValuesButton.addActionListener(e -> {
            if(getAlternativeChoiceFlag()){
                for (int k = 0; k < criteriaArray.size(); k++) {
                    String s = criteriaArray.get(k);
                    int i = 0;
                    System.out.println("\n");
                    MatrixInput matrixWindow = new MatrixInput(alternativeArray.size(), alternativeArray, s);
                    matrixWindow.setSize(300, 300);
                    matrixWindow.setLocationRelativeTo(null);
                    matrixWindow.setVisible(true);

                    setAlternativeWeights(matrixWindow.getAlternativeWeights());
                    for (int j = 0; j < alternativeWeights.length; j++) {
                        float alternativeWeight = alternativeWeights[j];
                        float criteriaWeight = weights[k];
                        float finalWeight = alternativeWeight * criteriaWeight;
                        finalValues.add(finalWeight);
                    }




                }
            }

        });

        calculateButton.addActionListener(e -> {
            System.out.println("\n");
            int i=0;
            float[][] values = new float[alternativeArray.size()][criteriaArray.size()];
            for(int r=0; r<alternativeArray.size();r++){
                for(int c=0; c<criteriaArray.size();c++){
                    values[r][c] = finalValues.get(i++);
                }
            }

            for(int r = 0; r < alternativeArray.size(); r++){
                sumRow = 0;
                for(int c = 0; c < criteriaArray.size(); c++){
                    sumRow = sumRow + values[r][c];
                }
                System.out.println("Sum of " + (r+1) +" row: " + sumRow);
                alternativesListModel.setElementAt(alternativesListModel.getElementAt(r) +" (" + sumRow +")",r);
            }

        });

        clearButton.addActionListener(e ->{
            criteriaArray.clear();
            alternativeArray.clear();
            alternativesListModel.clear();
            criteriaListModel.clear();
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
