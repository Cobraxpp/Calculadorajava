package Calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interfaz extends JFrame implements ActionListener {
    private JTextField calu;
    private String operador;
    private double num1, num2, resultado;

    public Interfaz() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

       
        calu = new JTextField();
        calu.setFont(new Font("Arial", Font.BOLD, 20));
        calu.setHorizontalAlignment(JTextField.RIGHT);
        calu.setEditable(false);
        add(calu, BorderLayout.NORTH);

     
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(4, 4, 10, 10));

        String[] botones = {
                "7", "8", "9", "+",
                "4", "5", "6", "-",
                "1", "2", "3", "*",
                "C", "0", "=", "/"
        };

        for (String texto : botones) {
            JButton boton = new JButton(texto);
            boton.setFont(new Font("Arial", Font.BOLD, 18));
            boton.addActionListener(this);
            panelBotones.add(boton);
        }

        add(panelBotones, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String comando = e.getActionCommand();

            if (comando.matches("\\d")) { 
                calu.setText(calu.getText() + comando);
            } else if (comando.matches("[+\\-*/]")) { 
                if (calu.getText().isEmpty()) {
                    throw new IllegalArgumentException("No se puede operar sin un número");
                }
                num1 = Double.parseDouble(calu.getText());
                operador = comando;
                calu.setText("");
            } else if (comando.equals("=")) { 
                if (calu.getText().isEmpty()) {
                    throw new IllegalArgumentException("No se puede calcular sin un número");
                }
                num2 = Double.parseDouble(calu.getText());
                switch (operador) {
                    case "+":
                        resultado = num1 + num2;
                        break;
                    case "-":
                        resultado = num1 - num2;
                        break;
                    case "*":
                        resultado = num1 * num2;
                        break;
                    case "/":
                        if (num2 == 0) {
                            throw new ArithmeticException("División entre cero");
                        }
                        resultado = num1 / num2;
                        break;
                    default:
                        throw new IllegalArgumentException("Operador inválido");
                }
                calu.setText(String.valueOf(resultado));
            } else if (comando.equals("C")) { 
                calu.setText("");
                num1 = num2 = resultado = 0;
                operador = "";
            }
        } catch (NumberFormatException ex) {
            calu.setText("Error numérico");
        } catch (ArithmeticException ex) {
            calu.setText("Error: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            calu.setText("Error: " + ex.getMessage());
        }
    }
}
