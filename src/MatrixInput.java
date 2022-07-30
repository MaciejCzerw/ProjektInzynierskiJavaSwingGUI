

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MatrixInput extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel MatrixDialogMainPanel;
    private JButton buttonConfirm;
    public static int criteriaArraySize;

    public interface MatrixInputResponse{
        void getResponse();
    }

    public MatrixInput(int criteriaArraySize) {
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

        buttonConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Creating one-dimensional array with numbers from JTextFields
                ArrayList<Float> numbers = new ArrayList<>();
                int k = 0;
                //Getting values from JTextFields by traversing JTextField array
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
                        default -> numbers.add(Float.parseFloat(text.getText()));
                    }
                }

                //Creating array with preferences
                float[][] array = new float[criteriaArraySize][criteriaArraySize];

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
                //Printing array from JTextFields
                for (float[] x : array) {
                    for (float y : x) {
                        System.out.print(y + " ");
                    }
                    System.out.println();
                }
                System.out.println();

                //creating array to calculate sum of columns
                float[] c = new float[array.length];

                //calculating sum of columns
                for (float[] floats : array) {
                    for (int j = 0; j < array[0].length; j++) {
                        c[j] += floats[j];
                    }
                }

                //printing out sum of columns
                for (int j=0; j < array[0].length; j++) {
                    System.out.print(c[j] + "\t");
                }

                System.out.println();
                System.out.println();

                //creating array to calculate weights
                float[][] arrayWithWeights = new float[criteriaArraySize][criteriaArraySize];

                //dividing columns by sum in column
                for(int i=0;i<array[0].length;i++){
                    for(int j=0; j<array.length;j++){
                        arrayWithWeights[i][j]=array[i][j]/c[j];
                    }
                }

                for (float[] x : arrayWithWeights) {
                    for (float y : x) {
                        System.out.print(y + " ");
                    }
                    System.out.println();
                }

                System.out.println();

                //Creating columns
                float[] sumOfWeightedRows = new float[array.length];

                //sum of elements in columns
                for (int i=0; i < array.length; i++) {
                    for (int j=0; j < array[0].length; j++) {
                        sumOfWeightedRows[i] += arrayWithWeights[i][j];
                    }
                }
                //Calculating sum of columns to create weights
                float sum=0;
                for (int j=0; j < array[0].length; j++) {
                    System.out.println(sumOfWeightedRows[j]);
                    sum+=sumOfWeightedRows[j];
                }

                //creating array with weights
                float[] weights = new float[arrayWithWeights.length];

                //calculating weights
                for(int i=0;i<array.length;i++){
                    weights[i]=sumOfWeightedRows[i]/sum;
                    System.out.println(weights[i]);
                }
            }
        });
    }

    private void onOK() {

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        MatrixInput dialog = new MatrixInput(criteriaArraySize);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
