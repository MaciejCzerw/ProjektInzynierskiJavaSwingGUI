

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MatrixInput extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel MatrixDialogMainPanel;
    private JButton buttonConfirm;
    private JLabel errorLabel;
    public static int criteriaArraySize;
    public static ArrayList<String> criteriaArray;
    public float[] weights;
    public float[] alternativeWeights;
    public int errorCounter;

    public MatrixInput(int criteriaArraySize, ArrayList<String> criteriaArray) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Matrix Input");
        ArrayList<JTextField> jTextFieldArrayList = new ArrayList<>();

        GridBagConstraints gbc = new GridBagConstraints();

        //Creating JTextField Matrix
        for (int i = 1; i < criteriaArraySize + 1; i++) {
            for (int j = 1; j < criteriaArraySize + 1; j++) {
                if (i == j) {
                    gbc.gridx = i;
                    gbc.gridy = j;
                    MatrixDialogMainPanel.add(new JLabel("1"), gbc);
                }
                if (i < j) continue;
                if (i > j) {
                    gbc.gridx = i;
                    gbc.gridy = j;
                    JTextField jTextField = new JTextField(2);
                    jTextFieldArrayList.add(jTextField);
                    MatrixDialogMainPanel.add(jTextField, gbc);
                }
            }
        }

        //TODO Ask if it should say the criteria name
        for (int i = 1; i < criteriaArraySize + 1; i++) {
            gbc.gridx = i;
            gbc.gridy = 0;
            String label = "";
            if (criteriaArray.get(i - 1).length() > 3) {
                label = criteriaArray.get(i - 1).substring(0, 3);
            } else {
                label = criteriaArray.get(i - 1);
            }
            JLabel jLabel = new JLabel(label);
            jLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
            MatrixDialogMainPanel.add(jLabel, gbc);

        }

        for (int j = 1; j < criteriaArraySize + 1; j++) {
            gbc.gridx = 0;
            gbc.gridy = j;
            String label = "";
            if (criteriaArray.get(j - 1).length() > 3) {
                label = criteriaArray.get(j - 1).substring(0, 3);
            } else {
                label = criteriaArray.get(j - 1);
            }
            JLabel jLabel = new JLabel(label);
            jLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
            MatrixDialogMainPanel.add(jLabel, gbc);
        }


        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        buttonConfirm.addActionListener(e -> {
            //Creating one-dimensional array with numbers from JTextFields
            ArrayList<Float> numbers = new ArrayList<>();
            int k = 0;
            //Getting values from JTextFields by traversing JTextField array
            try {
                for (JTextField text : jTextFieldArrayList) {
                    switch (text.getText()) {
                        case "1/2" -> numbers.add(1 / 2f);
                        case "1/3" -> numbers.add(1 / 3f);
                        case "1/4" -> numbers.add(1 / 4f);
                        case "1/5" -> numbers.add(1 / 5f);
                        case "1/6" -> numbers.add(1 / 6f);
                        case "1/7" -> numbers.add(1 / 7f);
                        case "1/8" -> numbers.add(1 / 8f);
                        case "1/9" -> numbers.add(1 / 9f);
                        default -> {
                            if (Float.parseFloat(text.getText()) <= 9 && Float.parseFloat((text.getText())) >= 1)
                                numbers.add(Float.parseFloat(text.getText()));
                        }
                    }
                }
            } catch (Exception notValidNumber) {
                //TODO add an error message for user
                errorLabel.setText("At least one number is invalid");
                errorLabel.setForeground(Color.RED);
            }

            //Creating array with preferences
            float[][] array = new float[criteriaArraySize][criteriaArraySize];

            if (!numbers.isEmpty()) {

                for (int i = 0; i < criteriaArraySize; i++) {
                    for (int j = 0; j < criteriaArraySize; j++) {
                        if (i == j) array[i][j] = 1;
                        if (i > j) {
                            array[j][i] = numbers.get(k);
                            array[i][j] = 1 / (numbers.get(k));
                            k++;
                        }
                    }
                }

                //creating array to calculate sum of columns
                float[] c = new float[array.length];

                //calculating sum of columns
                for (float[] floats : array) {
                    for (int j = 0; j < array[0].length; j++) {
                        c[j] += floats[j];
                    }
                }

                //creating array to calculate weights
                float[][] arrayWithWeights = new float[criteriaArraySize][criteriaArraySize];

                //dividing columns by sum in column
                for (int i = 0; i < array[0].length; i++) {
                    for (int j = 0; j < array.length; j++) {
                        arrayWithWeights[i][j] = array[i][j] / c[j];
                    }
                }

                //Creating columns
                float[] sumOfWeightedRows = new float[array.length];

                //sum of elements in columns
                for (int i = 0; i < array.length; i++) {
                    for (int j = 0; j < array[0].length; j++) {
                        sumOfWeightedRows[i] += arrayWithWeights[i][j];
                    }
                }
                //Calculating sum of columns to create weights
                float sum = 0;
                for (int j = 0; j < array[0].length; j++) {
                    //System.out.println(sumOfWeightedRows[j]);
                    sum += sumOfWeightedRows[j];
                }

                //creating array with weights
                float[] weights = new float[arrayWithWeights.length];

                //calculating weights
                for (int i = 0; i < array.length; i++) {
                    weights[i] = sumOfWeightedRows[i] / sum;
                   // System.out.println(weights[i]);
                }

                setWeights(weights);
            }

        });
    }

    public MatrixInput(int alternativeArraySize, ArrayList<String> alternativeArray, String criteria) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle(criteria);
        ArrayList<JTextField> jTextFieldArrayList = new ArrayList<>();

        GridBagConstraints gbc = new GridBagConstraints();

        //Creating JTextField Matrix
        for (int i = 1; i < alternativeArraySize + 1; i++) {
            for (int j = 1; j < alternativeArraySize + 1; j++) {
                if (i == j) {
                    gbc.gridx = i;
                    gbc.gridy = j;
                    MatrixDialogMainPanel.add(new JLabel("1"), gbc);
                }
                if (i < j) continue;
                if (i > j) {
                    gbc.gridx = i;
                    gbc.gridy = j;
                    JTextField jTextField = new JTextField(2);
                    jTextFieldArrayList.add(jTextField);
                    MatrixDialogMainPanel.add(jTextField, gbc);
                }
            }
        }

        //TODO Ask if it should say the criteria name
        for (int i = 1; i < alternativeArraySize + 1; i++) {
            gbc.gridx = i;
            gbc.gridy = 0;
            String label = "";
            if (alternativeArray.get(i - 1).length() > 3) {
                label = alternativeArray.get(i - 1).substring(0, 3);
            } else {
                label = alternativeArray.get(i - 1);
            }
            JLabel jLabel = new JLabel(label);
            jLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
            MatrixDialogMainPanel.add(jLabel, gbc);

        }

        for (int j = 1; j < alternativeArraySize + 1; j++) {
            gbc.gridx = 0;
            gbc.gridy = j;
            String label = "";
            if (alternativeArray.get(j - 1).length() > 3) {
                label = alternativeArray.get(j - 1).substring(0, 3);
            } else {
                label = alternativeArray.get(j - 1);
            }
            JLabel jLabel = new JLabel(label);
            jLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
            MatrixDialogMainPanel.add(jLabel, gbc);
        }


        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        buttonConfirm.addActionListener(e -> {
            //Creating one-dimensional array with numbers from JTextFields
            ArrayList<Float> numbers = new ArrayList<>();
            int k = 0;
            //Getting values from JTextFields by traversing JTextField array
            try {
                for (JTextField text : jTextFieldArrayList) {
                    switch (text.getText()) {
                        case "1/2" -> numbers.add(1 / 2f);
                        case "1/3" -> numbers.add(1 / 3f);
                        case "1/4" -> numbers.add(1 / 4f);
                        case "1/5" -> numbers.add(1 / 5f);
                        case "1/6" -> numbers.add(1 / 6f);
                        case "1/7" -> numbers.add(1 / 7f);
                        case "1/8" -> numbers.add(1 / 8f);
                        case "1/9" -> numbers.add(1 / 9f);
                        default -> {
                            if (Float.parseFloat(text.getText()) <= 9 && Float.parseFloat((text.getText())) >= 1)
                                numbers.add(Float.parseFloat(text.getText()));
                        }
                    }
                }
            } catch (Exception notValidNumber) {
                //TODO add an error message for user
                errorLabel.setText("At least one number is invalid");
                errorLabel.setForeground(Color.RED);
            }

            //Creating array with preferences
            float[][] array = new float[alternativeArraySize][alternativeArraySize];

            if (!numbers.isEmpty()) {

                for (int i = 0; i < alternativeArraySize; i++) {
                    for (int j = 0; j < alternativeArraySize; j++) {
                        if (i == j) array[i][j] = 1;
                        if (i > j) {
                            array[j][i] = numbers.get(k);
                            array[i][j] = 1 / (numbers.get(k));
                            k++;
                        }
                    }
                }

                //creating array to calculate sum of columns
                float[] c = new float[array.length];

                //calculating sum of columns
                for (float[] floats : array) {
                    for (int j = 0; j < array[0].length; j++) {
                        c[j] += floats[j];
                    }
                }

                //creating array to calculate weights
                float[][] arrayWithWeights = new float[alternativeArraySize][alternativeArraySize];

                //dividing columns by sum in column
                for (int i = 0; i < array[0].length; i++) {
                    for (int j = 0; j < array.length; j++) {
                        arrayWithWeights[i][j] = array[i][j] / c[j];
                    }
                }

                //Creating columns
                float[] sumOfWeightedRows = new float[array.length];

                //sum of elements in columns
                for (int i = 0; i < array.length; i++) {
                    for (int j = 0; j < array[0].length; j++) {
                        sumOfWeightedRows[i] += arrayWithWeights[i][j];
                    }
                }
                //Calculating sum of columns to create weights
                float sum = 0;
                for (int j = 0; j < array[0].length; j++) {
//                    System.out.println(sumOfWeightedRows[j]);
                    sum += sumOfWeightedRows[j];
                }

                //creating array with weights
                float[] alternativeWeights = new float[arrayWithWeights.length];

                //calculating weights
                for (int i = 0; i < array.length; i++) {
                    alternativeWeights[i] = sumOfWeightedRows[i] / sum;
                    System.out.println(alternativeWeights[i]);
                }

                setAlternativeWeights(alternativeWeights);
            }

        });
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public void setWeights(float[] weights) {
        this.weights = weights;
    }

    public void setAlternativeWeights(float[] alternativeWeights){
        this.alternativeWeights = alternativeWeights;
    }

    public float[] getWeights() {
        return weights;
    }

    public float[] getAlternativeWeights(){return alternativeWeights;}
}
