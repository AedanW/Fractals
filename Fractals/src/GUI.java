import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.SystemColor;
import java.awt.Canvas;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class GUI extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JRadioButton radGenerateMandelbrotSet;
    private JRadioButton radGenerateJuliaSet;
    private JTextField JuliaSetValueTextField;
    private JButton btnGenerate;
    private JTextField textField_5;
    boolean generateMandelbrot = false;
    boolean generateJulia = false;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI frame = new GUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public GUI() 
    {
    	setIconImage(Toolkit.getDefaultToolkit().getImage("Z:\\Desktop\\mandelbrot.jpg"));
        setTitle("Fractals");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.inactiveCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        textField = new JTextField();
        textField.setBounds(888, 11, 86, 20);
        contentPane.add(textField);
        textField.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("X Minimum");
        lblNewLabel.setBounds(818, 15, 70, 14);
        contentPane.add(lblNewLabel);
        
        JLabel lblXMaximum = new JLabel("X Maximum");
        lblXMaximum.setBounds(818, 40, 70, 14);
        contentPane.add(lblXMaximum);
        
        textField_1 = new JTextField();
        textField_1.setBounds(888, 36, 86, 20);
        contentPane.add(textField_1);
        textField_1.setColumns(10);
        
        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(888, 61, 86, 20);
        contentPane.add(textField_2);
        
        JLabel lblXMinimum = new JLabel("Y Minimum");
        lblXMinimum.setBounds(818, 65, 70, 14);
        contentPane.add(lblXMinimum);
        
        JLabel lblYMaximum = new JLabel("Y Maximum");
        lblYMaximum.setBounds(818, 90, 70, 14);
        contentPane.add(lblYMaximum);
        
        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(888, 86, 86, 20);
        contentPane.add(textField_3);
        
        radGenerateMandelbrotSet = new JRadioButton("Generate Mandelbrot set");
        radGenerateMandelbrotSet.setBackground(SystemColor.inactiveCaption);
        radGenerateMandelbrotSet.setBounds(794, 111, 180, 23);
        radGenerateMandelbrotSet.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
        	radGenerateJuliaSet.setSelected(false);
        	refresh();
        }});
        contentPane.add(radGenerateMandelbrotSet);
        
        radGenerateJuliaSet = new JRadioButton("Generate Julia set");
        radGenerateJuliaSet.setBackground(SystemColor.inactiveCaption);
        radGenerateJuliaSet.setBounds(794, 138, 180, 23);
        contentPane.add(radGenerateJuliaSet);
        radGenerateJuliaSet.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
        	radGenerateMandelbrotSet.setSelected(false);
        	refresh();
        }});
        JuliaSetValueTextField = new JTextField();
        JuliaSetValueTextField.setEditable(false);
        JuliaSetValueTextField.setBounds(888, 168, 86, 20);
        contentPane.add(JuliaSetValueTextField);
        JuliaSetValueTextField.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("Julia Set Value:");
        lblNewLabel_1.setBounds(790, 172, 94, 14);
        contentPane.add(lblNewLabel_1);
        
        btnGenerate = new JButton("Generate");
        btnGenerate.setBounds(845, 516, 89, 23);
        contentPane.add(btnGenerate);
        
        textField_5 = new JTextField();
        textField_5.setBounds(888, 199, 86, 20);
        contentPane.add(textField_5);
        textField_5.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("Max Iterations:");
        lblNewLabel_2.setBounds(790, 202, 84, 14);
        contentPane.add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("");
        lblNewLabel_3.setIcon(new ImageIcon("Z:\\Desktop\\Mandel_zoom_08_satellite_antenna.jpg"));
        lblNewLabel_3.setBounds(48, 11, 717, 540);
        contentPane.add(lblNewLabel_3);
        
        refresh();
    }
    

    
    public void refresh()
    {
        if (radGenerateJuliaSet.isSelected() == true)
        {
            JuliaSetValueTextField.setEditable(true);
        }
        else
        {
            JuliaSetValueTextField.setEditable(false);
            JuliaSetValueTextField.setText("");
        }
    }
}
