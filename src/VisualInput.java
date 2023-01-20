import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class VisualInput extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel visualInput;
    public static int criteriaArraySize;
    public static ArrayList<String> criteriaArray;

    public VisualInput(int criteriaArraySize,ArrayList<String> criteriaArray) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Visual Input");

        GridBagConstraints gbc = new GridBagConstraints();

        for(int i=0;i<criteriaArraySize;i++){
            for(int j=i+1;j<criteriaArraySize;j++){
                System.out.println(criteriaArray.get(i)+criteriaArray.get(j));
            }
        }


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        VisualInput dialog = new VisualInput(criteriaArraySize,criteriaArray);
        dialog.pack();
        dialog.setResizable(true);
        dialog.setVisible(true);
        System.exit(0);
    }
}
