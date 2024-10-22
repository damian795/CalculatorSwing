import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {
    JTextField textField;
    Font myFont = new Font("Arial", Font.BOLD, 25);
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[9];
    JButton addButton, subButton, mulButton, divButton;
    JButton decButton, equButton, delButton, clrButton, negButton;
    private boolean start; //user zaczyna pisac w kalkulatorze
    private String lastCommand;
    private double result;


    Calculator() {
        start = true;
        lastCommand = "=";
        JFrame frame = new JFrame("KALKULATOR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 550);
        frame.setLayout(null);

        textField = new JTextField("");
        textField.setBounds(50, 25, 300, 50);
        textField.setFont(myFont);
        textField.setEditable(false);

        JPanel panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 5, 5));

        createButtons(panel); // Tworzenie przycisków

        // Dodanie elementów do okna
        frame.add(negButton);
        frame.add(delButton);
        frame.add(clrButton);
        frame.add(panel);
        frame.add(textField);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void createButtons(JPanel panel) {
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(calcListener);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
        }

        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("Del");
        clrButton = new JButton("Clr");
        negButton = new JButton("(-)");

        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;
        functionButtons[4] = decButton;
        functionButtons[5] = equButton;
        functionButtons[6] = delButton;
        functionButtons[7] = clrButton;
        functionButtons[8] = negButton;

        for (JButton button : functionButtons) {
            button.addActionListener(calcListener);
            button.setFont(myFont);
            button.setFocusable(false);
        }

        // Dodaj przyciski do panelu
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);
        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(equButton);
        panel.add(divButton);

        // Ustawienia pozycji przycisków do negacji i usuwania
        negButton.setBounds(50, 430, 100, 50);
        delButton.setBounds(150, 430, 100, 50);
        clrButton.setBounds(250, 430, 100, 50);
    }


    private ActionListener calcListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == decButton) {
                // Sprawdzamy, czy kropka już jest w liczbie zeby nie bylo powtórzeń
                if (!textField.getText().contains(".")) {
                    insertNumber(".");
                }
            }

            if (e.getSource() == delButton) {
                decButton.setEnabled(true);
                if (!textField.getText().isEmpty()) {
                    //jezeli nie jest puste to wtedy usuwamy
                    //substirng automatycznie zwraca tekst bez ostatniego znaku
                    textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
                }
            }


            if(e.getSource()==clrButton) {
                decButton.setEnabled(true);
                textField.setText("");
                result=0;
            }
            String v = ((JButton) e.getSource()).getText();

            if("/+-*=".contains(v) && !textField.getText().isEmpty()) {
                decButton.setEnabled(true);
                calculate(v);
            }else if ("0123456789".contains(v)) {
                insertNumber(v);
            }

            if (e.getSource()==negButton){
                double temp = Double.parseDouble(textField.getText());
                temp*=-1;
                textField.setText(String.valueOf(temp));
            }
        }
    };

    public void calculate(String s) {
        try {
            double num = Double.parseDouble(textField.getText());
            if (lastCommand.equals("=")) result = num;
            //wynik po równa zostaje w resultat dopóki nie wyczyscimy CLR

            if (lastCommand.equals("+")) {
                result += num;
            } else if (lastCommand.equals("*")) {
                result *= num;
            } else if (lastCommand.equals("/")) {
                if (num == 0) {
                    textField.setText("Cannot divide by 0");
                    result = 0;
                    return;
                } else {
                    result /= num;
                }
            } else if (lastCommand.equals("-")) {
                result -= num;
            }

            textField.setText(Double.toString(result));
            lastCommand = s;
            start = true;
        } catch (NumberFormatException e) {
            textField.setText("Invalid Input");
        }
    }

    public void insertNumber(String s){
        if (start) {
            textField.setText("");
            start = false;
        }

        System.out.println("insertNumber: " + s);
        textField.setText(textField.getText() + s);
    }
}