import javax.swing.*;

public class AlternativeChoiceFlagMenu extends JDialog {
    private JButton matrixInput;
    private JPanel choicePanel;
    private JButton visualInput;
    private JLabel choiceLabel;
    public boolean alternativeChoiceFlag;


    public AlternativeChoiceFlagMenu(){
        setContentPane(choicePanel);
        setModal(true);
        setTitle("Alternative Choice");
        choiceLabel.setText("Choose input mode");

        matrixInput.addActionListener(e -> matrix());
        visualInput.addActionListener(e -> input());
    }

    void matrix(){
        setAlternativeChoiceFlag(true);
    }
    void input(){
        setAlternativeChoiceFlag(false);
    }

    public boolean getAlternativeChoiceFlag(){return alternativeChoiceFlag;}
    public void setAlternativeChoiceFlag(boolean alternativeChoiceFlag){this.alternativeChoiceFlag = alternativeChoiceFlag;}

}
