

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

    public MatrixInput(int criteriaArraySize) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Matrix Input");
        this.criteriaArraySize = criteriaArraySize;
        Integer[][] integers = new Integer[criteriaArraySize][criteriaArraySize];
        ArrayList<JTextField> jTextFieldArrayList = new ArrayList<>();

        GridBagConstraints gbc = new GridBagConstraints();
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
                ArrayList<Float> numbers = new ArrayList<Float>();
                int k = 0;
                for (JTextField text : jTextFieldArrayList) {
                    switch(text.getText()){
                        case "1/2": numbers.add(1/2f); break;
                        case "1/3": numbers.add(1/3f); break;
                        case "1/4": numbers.add(1/4f); break;
                        case "1/5": numbers.add(1/5f); break;
                        case "1/6": numbers.add(1/6f); break;
                        case "1/7": numbers.add(1/7f); break;
                        case "1/8": numbers.add(1/8f); break;
                        case "1/9": numbers.add(1/9f); break;
                        default: numbers.add(Float.parseFloat(text.getText()));
                    }
                }

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


                for (float[] x : array) {
                    for (float y : x) {
                        System.out.print(y + " ");
                    }
                    System.out.println();
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
