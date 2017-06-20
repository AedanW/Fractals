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
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.Arrays;

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
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.awt.event.KeyAdapter;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class GUI extends JFrame {
    
    boolean moved = false;
    boolean alreadyRandomized = false;
    boolean firstRunthrough = true;
    private JPanel contentPane;
    private JTextField txtXMinimum;
    private JTextField txtXMaximum;
    private JTextField txtYMinimum;
    private JTextField txtYMaximum;
    private JRadioButton radGenerateMandelbrotSet;
    private JRadioButton radGenerateJuliaSet;
    private JTextField txtJuliaSetValue;
    private JButton btnGenerate;
    private JTextField txtMaxIterations;
    private int exponent;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;
    private double xRes;
    private double yRes;
    private Complex juliaSetValue;
    private boolean doNotDraw = false;
    private int maxIterations;
    final int FRAMES_PER_SECOND = 10;
    long current_time = 0;                              //MILLISECONDS
    long next_refresh_time = 0;                         //MILLISECONDS
    long minimum_delta_time = 1000 / FRAMES_PER_SECOND; //MILLISECONDS
    long actual_delta_time = 0;                         //MILLISECONDS
    boolean generateMandelbrot = false;
    boolean generateJulia = false;
    Color[] colourList = new Color[]{Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.CYAN,Color.BLUE,Color.MAGENTA,Color.PINK,new Color(112,39,195)};
    private JTextField txtXResolution;
    private JTextField txtYResolution;
    
    int[][] desiredSet = null;
    private JRadioButton radRandomColours;
    private JTextField txtJuliaSetImaginaryValue;
    private JRadioButton radSmoothColours;
    private JTextField txtExponent;
    private JRadioButton radDiscoSet;

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
        getContentPane().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent arg0) {
                //do_thisContentPane_keyPressed(arg0);
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                do_this_mouseClicked(arg0);
            }
        });
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    	setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/resources/Really Cool Small Mandelbrot Set (2).png")));
        setTitle("Fractals");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        contentPane = new DrawPanel();
        contentPane.setBackground(SystemColor.inactiveCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        getContentPane().setLayout(null);
        contentPane.setLayout(null);
        
        txtXMinimum = new JTextField();
        txtXMinimum.setText("-2");
        txtXMinimum.setBounds(888, 11, 86, 20);
        contentPane.add(txtXMinimum);
        txtXMinimum.setColumns(10);
        txtXMinimum.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                do_textField_keyTyped(arg0);
            }
        });
        
        JLabel lblNewLabel = new JLabel("X Minimum");
        lblNewLabel.setBounds(818, 15, 70, 14);
        contentPane.add(lblNewLabel);
        lblNewLabel.setFocusable(false);
        
        JLabel lblXMaximum = new JLabel("X Maximum");
        lblXMaximum.setBounds(818, 40, 70, 14);
        contentPane.add(lblXMaximum);
        lblXMaximum.setFocusable(false);
        
        txtXMaximum = new JTextField();
        txtXMaximum.setText("2");
        txtXMaximum.setBounds(888, 36, 86, 20);
        contentPane.add(txtXMaximum);
        txtXMaximum.setColumns(10);
        txtXMaximum.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                do_textField_keyTyped(arg0);
            }
        });
        
        txtYMinimum = new JTextField();
        txtYMinimum.setText("-2");
        txtYMinimum.setColumns(10);
        txtYMinimum.setBounds(888, 61, 86, 20);
        contentPane.add(txtYMinimum);
        txtYMinimum.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                do_textField_keyTyped(arg0);
            }
        });
        
        JLabel lblXMinimum = new JLabel("Y Minimum");
        lblXMinimum.setBounds(818, 65, 70, 14);
        lblXMinimum.setFocusable(false);
        contentPane.add(lblXMinimum);
        
        JLabel lblYMaximum = new JLabel("Y Maximum");
        lblYMaximum.setBounds(818, 90, 70, 14);
        lblYMaximum.setFocusable(false);
        contentPane.add(lblYMaximum);
        
        
        txtYMaximum = new JTextField();
        txtYMaximum.setText("2");
        txtYMaximum.setColumns(10);
        txtYMaximum.setBounds(888, 86, 86, 20);
        txtYMaximum.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                do_textField_keyTyped(arg0);
            }
        });
        contentPane.add(txtYMaximum);
        
        radGenerateMandelbrotSet = new JRadioButton("Generate Mandelbrot set");
        radGenerateMandelbrotSet.setSelected(true);
        radGenerateMandelbrotSet.setBackground(SystemColor.inactiveCaption);
        radGenerateMandelbrotSet.setBounds(794, 161, 180, 23);
        radGenerateMandelbrotSet.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
        	radGenerateJuliaSet.setSelected(false);
        	ourRefresh();
        }});
        contentPane.add(radGenerateMandelbrotSet);
        radGenerateMandelbrotSet.setFocusable(false);
        
        radGenerateJuliaSet = new JRadioButton("Generate Julia set");
        radGenerateJuliaSet.setFocusable(false);
        radGenerateJuliaSet.setBackground(SystemColor.inactiveCaption);
        radGenerateJuliaSet.setBounds(794, 188, 180, 23);
        contentPane.add(radGenerateJuliaSet);
        radGenerateJuliaSet.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
        	radGenerateMandelbrotSet.setSelected(false);
        	ourRefresh();
        }});
        txtJuliaSetValue = new JTextField();
        txtJuliaSetValue.setEditable(false);
        txtJuliaSetValue.setBounds(834, 307, 86, 20);
        contentPane.add(txtJuliaSetValue);
        txtJuliaSetValue.setColumns(10);
        
        JLabel lblJuliaSetValue = new JLabel("Julia Set Real Value:");
        lblJuliaSetValue.setFocusable(false);
        lblJuliaSetValue.setBounds(818, 282, 125, 14);
        contentPane.add(lblJuliaSetValue);
        
        btnGenerate = new JButton("Generate");
        btnGenerate.setFocusable(false);
        btnGenerate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                do_btnGenerate_mouseClicked(e);
            }
        });
        btnGenerate.setBounds(845, 528, 89, 23);
        contentPane.add(btnGenerate);
        
        txtMaxIterations = new JTextField();
        txtMaxIterations.setText("100");
        txtMaxIterations.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                do_textField_keyTyped(arg0);
            }
        });
        txtMaxIterations.setBounds(834, 419, 86, 20);
        contentPane.add(txtMaxIterations);
        txtMaxIterations.setColumns(10);
        
        JLabel lblMaxIterations = new JLabel("Max Iterations:");
        lblMaxIterations.setFocusable(false);
        lblMaxIterations.setBounds(818, 394, 84, 14);
        contentPane.add(lblMaxIterations);
        
        JLabel lblXRes = new JLabel("X Resolution");
        lblXRes.setFocusable(false);
        lblXRes.setBounds(814, 115, 83, 14);
        contentPane.add(lblXRes);
        
        JLabel lblYRes = new JLabel("Y Resolution");
        lblYRes.setFocusable(false);
        lblYRes.setBounds(814, 140, 70, 14);
        contentPane.add(lblYRes);
        
        txtXResolution = new JTextField();
        txtXResolution.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                do_textField_keyTyped(arg0);
            }
        });
        txtXResolution.setText("0.1");
        txtXResolution.setBounds(888, 111, 86, 20);
        contentPane.add(txtXResolution);
        txtXResolution.setColumns(10);
        
        txtYResolution = new JTextField();
        txtYResolution.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                do_textField_keyTyped(arg0);
            }
        });
        txtYResolution.setText("0.1");
        txtYResolution.setColumns(10);
        txtYResolution.setBounds(888, 136, 86, 20);
        contentPane.add(txtYResolution);
        
        radRandomColours = new JRadioButton("Random Colours");
        radRandomColours.setFocusable(false);
        radRandomColours.setBackground(SystemColor.inactiveCaption);
        radRandomColours.setBounds(794, 446, 180, 23);
        radRandomColours.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
            radDiscoSet.setSelected(false);
            radSmoothColours.setSelected(false);
            ourRefresh();
        }});
        contentPane.add(radRandomColours);
        
        JLabel lblJuliaSetImaginaryValue = new JLabel("Julia Set Imaginary Value:");
        lblJuliaSetImaginaryValue.setFocusable(false);
        lblJuliaSetImaginaryValue.setBounds(818, 338, 150, 14);
        contentPane.add(lblJuliaSetImaginaryValue);
        
        txtJuliaSetImaginaryValue = new JTextField();
        txtJuliaSetImaginaryValue.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                do_textField_keyTyped(arg0);
            }
        });
        txtJuliaSetImaginaryValue.setEditable(false);
        txtJuliaSetImaginaryValue.setColumns(10);
        txtJuliaSetImaginaryValue.setBounds(834, 363, 86, 20);
        contentPane.add(txtJuliaSetImaginaryValue);
        
        radSmoothColours = new JRadioButton("Smooth Colours");
        radSmoothColours.setFocusable(false);
        radSmoothColours.setBackground(SystemColor.inactiveCaption);
        radSmoothColours.setBounds(794, 472, 170, 23);
        radSmoothColours.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
            radDiscoSet.setSelected(false);
            radRandomColours.setSelected(false);
            ourRefresh();
        }});
        contentPane.add(radSmoothColours);
        
        txtExponent = new JTextField();
        txtExponent.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                do_textField_keyTyped(arg0);
            }
        });
        txtExponent.setText("2");
        txtExponent.setBounds(834, 251, 86, 20);
        contentPane.add(txtExponent);
        txtExponent.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("Exponent Of Z");
        lblNewLabel_1.setFocusable(false);
        lblNewLabel_1.setBounds(818, 226, 102, 14);
        contentPane.add(lblNewLabel_1);
        
        radDiscoSet = new JRadioButton("Disco Set");
        radDiscoSet.setFocusable(false);
        radDiscoSet.setBackground(SystemColor.inactiveCaption);
        radDiscoSet.setBounds(794, 498, 170, 23);
        radDiscoSet.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
            radRandomColours.setSelected(false);
            radSmoothColours.setSelected(false);
            ourRefresh();
        }});
        contentPane.add(radDiscoSet);
        
        this.addKeyListener(new KeyListener() 
        {
            public void keyTyped(KeyEvent e) 
            {
                System.out.println("Key typed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
            }
            public void keyPressed(KeyEvent e) 
            {
                System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
                if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                    int row = 1;
                    int col = 0;
                   
                    while (row < desiredSet.length)
                    {
                        while (col < desiredSet[0].length)
                        {
                            desiredSet[row-1][col] = desiredSet[row][col];
                            col++;
                        }
                        row++;
                        col = 0;
                    }
                   
                    int[][] tempSet = null;
                    if (radGenerateMandelbrotSet.isSelected())
                    {
                        tempSet = FractalGenerator.generateMandelBrotSetReturningInts(maxX+xRes, maxX, maxY, minY, xRes, yRes, maxIterations, exponent);
                    }
                    else
                    {
                        tempSet = FractalGenerator.generateJuliaSetReturningInts(maxX+xRes, maxX, maxY, minY, xRes, yRes, maxIterations, juliaSetValue, exponent);
                    }
                    System.out.println(maxX);
                    System.out.println(minX);
                    maxX = maxX + xRes;
                    minX = minX + xRes;
                    int f = 1;
                    double xResTemp = xRes;
                    while (xResTemp < 1.0)
                    {
                        xResTemp = xResTemp * 10;
                        f++;
                    }
                    maxX = Math.round(maxX*Math.pow(10, f)) / (Math.pow(10, f));
                    minX = Math.round(minX*Math.pow(10, f)) / (Math.pow(10, f));
                    txtXMaximum.setText(maxX + "");
                    txtXMinimum.setText(minX + "");
                    moved = true;
                    desiredSet[desiredSet[0].length-1]= Arrays.copyOf(tempSet[0], tempSet[0].length);
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    int row = desiredSet.length-1;
                    int col = 0;
                   
                    while (row > 0)
                    {
                        while (col < desiredSet[0].length)
                        {
                            desiredSet[row][col] = desiredSet[row-1][col];
                            col++;
                        }
                        row--;
                        col = 0;
                    }
                   
                    
                    int[][] tempSet = null;
                    if (radGenerateMandelbrotSet.isSelected())
                    {
                        tempSet = FractalGenerator.generateMandelBrotSetReturningInts(minX, minX-xRes, maxY, minY, xRes, yRes, maxIterations, exponent);
                    }
                    else
                    {
                        tempSet = FractalGenerator.generateJuliaSetReturningInts(minX, minX-xRes, maxY, minY, xRes, yRes, maxIterations, juliaSetValue, exponent);
                    }
                    System.out.println(maxX);
                    System.out.println(minX);
                    maxX = maxX - xRes;
                    minX = minX - xRes;
                    int f = 1;
                    double xResTemp = xRes;
                    while (xResTemp < 1.0)
                    {
                        xResTemp = xResTemp * 10;
                        f++;
                    }
                    maxX = Math.round(maxX*Math.pow(10, f)) / (Math.pow(10, f));
                    minX = Math.round(minX*Math.pow(10, f)) / (Math.pow(10, f));
                    txtXMaximum.setText(maxX + "");
                    txtXMinimum.setText(minX + "");
                    moved = true;
                    desiredSet[0]= Arrays.copyOf(tempSet[0], tempSet[0].length);
                }
                else if (e.getKeyCode() == KeyEvent.VK_UP)
                {
                    int row = 0;
                    int col = desiredSet[0].length-1;
                    
                    while (col > 0)
                    {
                        while (row < desiredSet.length)
                        {
                            desiredSet[row][col] = desiredSet[row][col-1];
                            row++;
                        }
                        col--;
                        row = 0;
                    }
                    int[][] tempSet = null;
                    if (radGenerateMandelbrotSet.isSelected())
                    {
                        tempSet = FractalGenerator.generateMandelBrotSetReturningInts(maxX, minX, maxY+yRes, maxY, xRes, yRes, maxIterations, exponent);
                    }
                    else
                    {
                        tempSet = FractalGenerator.generateJuliaSetReturningInts(maxX, minX, maxY+yRes, maxY, xRes, yRes, maxIterations, juliaSetValue, exponent);
                    }
                    int i = 0;
                    System.out.println(maxY);
                    System.out.println(minY);
                    maxY = maxY + yRes;
                    minY = minY + yRes;
                    int f = 1;
                    double yResTemp = yRes;
                    while (yResTemp < 1.0)
                    {
                        yResTemp = yResTemp * 10;
                        f++;
                    }
                    maxY = Math.round(maxY*Math.pow(10, f)) / (Math.pow(10, f));
                    minY = Math.round(minY*Math.pow(10, f)) / (Math.pow(10, f));
                    txtYMaximum.setText(maxY + "");
                    txtYMinimum.setText(minY + "");
                    moved = true;
                    while (i < desiredSet.length)
                    {
                        desiredSet[i][0] = tempSet[i][1];
                        i++;
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    int row = 0;
                    int col = 1;
                   
                    while (col < desiredSet[0].length)
                    {
                        while (row < desiredSet.length)
                        {
                            desiredSet[row][col-1] = desiredSet[row][col];
                            row++;
                        }
                        col++;
                        row = 0;
                    }
                   
                    
                    int[][] tempSet = null;
                    if (radGenerateMandelbrotSet.isSelected())
                    {
                        tempSet = FractalGenerator.generateMandelBrotSetReturningInts(maxX, minX, minY, minY-yRes, xRes, yRes, maxIterations, exponent);
                    }
                    else
                    {
                        tempSet = FractalGenerator.generateJuliaSetReturningInts(maxX, minX, minY, minY-yRes, xRes, yRes, maxIterations, juliaSetValue, exponent);
                    }
                    int i = 0;
                    System.out.println(maxY);
                    System.out.println(minY);
                    maxY = maxY - yRes;
                    minY = minY - yRes;
                    int f = 1;
                    double yResTemp = yRes;
                    while (yResTemp < 1.0)
                    {
                        yResTemp = yResTemp * 10;
                        f++;
                    }
                    maxY = Math.round(maxY*Math.pow(10, f)) / (Math.pow(10, f));
                    minY = Math.round(minY*Math.pow(10, f)) / (Math.pow(10, f));
                    txtYMaximum.setText(maxY + "");
                    txtYMinimum.setText(minY + "");
                    moved = true;
                    while (i < desiredSet.length)
                    {
                        desiredSet[i][desiredSet[0].length-1] = tempSet[i][1];
                        i++;
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_Z)
                {
                    int[][] tempSet = null;
                    try
                    {
                        tempSet = new int[desiredSet.length-2][desiredSet[0].length-2];
                    }
                    catch(java.lang.NegativeArraySizeException f)
                    {
                        return;
                    }
                    int col = 1;
                    int row = 1;
                    while (col < desiredSet[0].length-1)
                    {
                        while (row < desiredSet.length-1)
                        {
                            tempSet[row-1][col-1] = desiredSet[row][col];
                            row++;
                        }
                        row = 1;
                        col++;
                    }
                    if (maxX - xRes > maxX)
                    {
                        System.out.println("This is when we've gone too deep for maxX");
                    }
                    if (minX - xRes > minX)
                    {
                        System.out.println("This is when we've gone too deep for minX");
                    }
                    maxX = maxX - xRes;
                    minX = minX + xRes;
                    int f = 1;
                    double xResTemp = xRes;
                    while (xResTemp < 1.0)
                    {
                        xResTemp = xResTemp * 10;
                        f++;
                    }
                    maxX = Math.round(maxX*Math.pow(10, f)) / (Math.pow(10, f));
                    minX = Math.round(minX*Math.pow(10, f)) / (Math.pow(10, f));
                    txtXMaximum.setText(maxX + "");
                    txtXMinimum.setText(minX + "");
                    maxY = maxY - yRes;
                    minY = minY + yRes;
                    f = 1;
                    double yResTemp = yRes;
                    while (yResTemp < 1.0)
                    {
                        yResTemp = yResTemp * 10;
                        f++;
                    }
                    maxY = Math.round(maxY*Math.pow(10, f)) / (Math.pow(10, f));
                    minY = Math.round(minY*Math.pow(10, f)) / (Math.pow(10, f));
                    txtYMaximum.setText(maxY + "");
                    txtYMinimum.setText(minY + "");
                    desiredSet = tempSet;
                    moved = true;
                }
                else if (e.getKeyCode() == KeyEvent.VK_X)
                {
                    int[][] newSet = new int[desiredSet.length+2][desiredSet[0].length+2];
                    int col = 1;
                    int row = 1;
                    while (col < newSet[0].length-1)
                    {
                        while (row < newSet.length-1)
                        {
                            newSet[row][col] = desiredSet[row-1][col-1];
                            row++;
                        }
                        row = 1;
                        col++;
                    }
                    
                    //UP
                    int[][] tempSet = null;
                    if (radGenerateMandelbrotSet.isSelected())
                    {
                        tempSet = FractalGenerator.generateMandelBrotSetReturningInts(maxX + xRes, minX - xRes, maxY+yRes, maxY, xRes, yRes, maxIterations, exponent);
                    }
                    else
                    {
                        tempSet = FractalGenerator.generateJuliaSetReturningInts(maxX + xRes, minX - xRes, maxY+yRes, maxY, xRes, yRes, maxIterations, juliaSetValue, exponent);
                    }
                    int i = 0;
                    moved = true;
                    while (i < newSet.length-1)
                    {
                        try
                        {
                            newSet[i][0] = tempSet[i][1];
                        }
                        catch(java.lang.ArrayIndexOutOfBoundsException t)
                        {
                            newSet[i][0] = tempSet[i][0];
                        }
                        i++;
                    }
                    
                    //DOWN
                    tempSet = null;
                    if (radGenerateMandelbrotSet.isSelected())
                    {
                        tempSet = FractalGenerator.generateMandelBrotSetReturningInts(maxX + xRes, minX - xRes, minY, minY-yRes, xRes, yRes, maxIterations, exponent);
                    }
                    else
                    {
                        tempSet = FractalGenerator.generateJuliaSetReturningInts(maxX + xRes, minX - xRes, minY, minY-yRes, xRes, yRes, maxIterations, juliaSetValue, exponent);
                    }
                    i = 0;
                    while (i < newSet.length-1)
                    {
                        try
                        {
                            newSet[i][newSet[0].length-1] = tempSet[i][1];
                        }
                        catch(java.lang.ArrayIndexOutOfBoundsException t)
                        {
                            newSet[i][newSet[0].length-1] = tempSet[i][0];
                        }
                        i++;
                    }
                    
                    //LEFT
                    if (radGenerateMandelbrotSet.isSelected())
                    {
                        tempSet = FractalGenerator.generateMandelBrotSetReturningInts(minX, minX-xRes, maxY+yRes, minY-yRes, xRes, yRes, maxIterations, exponent);
                    }
                    else
                    {
                        tempSet = FractalGenerator.generateJuliaSetReturningInts(minX, minX-xRes, maxY+yRes, minY-yRes, xRes, yRes, maxIterations, juliaSetValue, exponent);
                    }
                    newSet[0]= Arrays.copyOf(tempSet[0], tempSet[0].length);
                    
                    //RIGHT
                    if (radGenerateMandelbrotSet.isSelected())
                    {
                        tempSet = FractalGenerator.generateMandelBrotSetReturningInts(maxX+xRes, maxX, maxY+yRes, minY-yRes, xRes, yRes, maxIterations, exponent);
                    }
                    else
                    {
                        tempSet = FractalGenerator.generateJuliaSetReturningInts(maxX+xRes, maxX, maxY+yRes, minY-yRes, xRes, yRes, maxIterations, juliaSetValue, exponent);
                    }
                    newSet[newSet[0].length-1]= Arrays.copyOf(tempSet[0], tempSet[0].length);
                    
                    
                    
                    
                    maxX = maxX + xRes;
                    minX = minX - xRes;
                    int f = 1;
                    double xResTemp = xRes;
                    while (xResTemp < 1.0)
                    {
                        xResTemp = xResTemp * 10;
                        f++;
                    }
                    maxX = Math.round(maxX*Math.pow(10, f)) / (Math.pow(10, f));
                    minX = Math.round(minX*Math.pow(10, f)) / (Math.pow(10, f));
                    txtXMaximum.setText(maxX + "");
                    txtXMinimum.setText(minX + "");
                    maxY = maxY + yRes;
                    minY = minY - yRes;
                    f = 1;
                    double yResTemp = yRes;
                    while (yResTemp < 1.0)
                    {
                        yResTemp = yResTemp * 10;
                        f++;
                    }
                    maxY = Math.round(maxY*Math.pow(10, f)) / (Math.pow(10, f));
                    minY = Math.round(minY*Math.pow(10, f)) / (Math.pow(10, f));
                    txtYMaximum.setText(maxY + "");
                    txtYMinimum.setText(minY + "");
                    
                    
                    
                    desiredSet = newSet;
                }
                else if(e.getKeyCode() == KeyEvent.VK_C)
                {
                    xRes = xRes / 10;
                    yRes = yRes / 10;
                    txtXResolution.setText(xRes + "");
                    txtYResolution.setText(yRes + "");
                    if (radGenerateMandelbrotSet.isSelected())
                    {
                        desiredSet = FractalGenerator.generateMandelBrotSetReturningInts(maxX, minX, maxY, minY, xRes, yRes, maxIterations, exponent);
                    }
                    else if (radGenerateJuliaSet.isSelected())
                    {
                        desiredSet = FractalGenerator.generateJuliaSetReturningInts(maxX, minX, maxY, minY, xRes, yRes, maxIterations, juliaSetValue, exponent);
                    }
                }
            }
            
            public void keyReleased(KeyEvent e) 
            {
                System.out.println("Key released code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
            }
        });
        
        ourRefresh(); 
        run();
    }
    
    
    
    class DrawPanel extends JPanel
    {
            public void paintComponent(Graphics g)
            {
                if (g == null) 
                {
                    return;
                }
                
                if (firstRunthrough)
                {
                    desiredSet = FractalGenerator.generateMandelBrotSetReturningInts(maxX, minX, maxY, minY, xRes, yRes, maxIterations, exponent);
                    firstRunthrough = false;
                }

                if ((desiredSet == null)){
                    g.setColor(getBackground());
                    return;
                }
                if (doNotDraw == true)
                {
                    //System.out.println("It gets here!");
                    return;
                }
                else {
                    //System.out.println("I better draw!" + LocalDateTime.now().toString());
                }
                    
                
                g.setColor(getBackground());
                
                this.resize(this.getParent().getWidth() - 8, this.getParent().getHeight() - 8);
                double cell_width = ((double)this.getWidth() - 200)/ (desiredSet.length);
                double cell_height = (double)(this.getHeight()) / (desiredSet[0].length);
                
                //System.out.println("parent width = " + this.getParent().getWidth());
                //System.out.println("DrawPanel width = " + this.getWidth());
                
                for (int y = 0; y < desiredSet.length; y++)
                {
                    for (int x = 0; x < desiredSet[0].length; x++)
                    {
                        //This code removes the red patches but it causes more problems than it solves, specifically
                        //with the Julia sets where 0 iterations can happen. Try 0.628 and 0.777 as julia set values
                        //with all sets enabled on if you want to see the problem, for the time being only Mandelbrot sets
                        //will recieve this treatment because they never should show up red.
                        if ((desiredSet[y][x] == 0) && (radGenerateMandelbrotSet.isSelected()))
                        {
                            if (y < desiredSet.length-1)
                            {
                                desiredSet[y][x] = desiredSet[y+1][x];
                            }
                            else if (y < 1)
                            {
                                desiredSet[y][x] = desiredSet[y-1][x];
                            }
                            else if (x < desiredSet.length-1)
                            {
                                desiredSet[y][x] = desiredSet[y][x+1];
                            }
                            else if (x < 1)
                            {
                                desiredSet[y][x] = desiredSet[y][x-1];
                            }
                        }
                        if (desiredSet[y][x] == FractalGenerator.maxIterations)
                        {
                            g.setColor(Color.BLACK);
                        }
                        else
                        {
                            int colourIndex = desiredSet[y][x] % colourList.length;
                            g.setColor(colourList[colourIndex]);
                        }
                        g.fillRect((int)(y * cell_width),(int)(x * cell_height + 8), (int)(cell_width) + 1, (int)(cell_height) + 1);
                    }
                    ourRefresh();
                    repaint();
                }
                ourRefresh();
                repaint();
                //generateSet.stop();
            }
    }

    public void run()
    {
        Thread loop = new Thread()
        {
           public void run()
           {
              gameLoop();
           }
        };
        loop.start();  
    }
    
    private void gameLoop() {

        while (true) { // main game loop
            
            //HANDLE EVENTS

            //UPDATE STATE
            updateTime();
            
            //REFRESH
            this.getContentPane().repaint();
            ourRefresh();

            //adapted from http://www.java-gaming.org/index.php?topic=24220.0
            next_refresh_time = current_time + minimum_delta_time;
            while (current_time < next_refresh_time)
            {
               Thread.yield();
            
               //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
               //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
               //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
               try {Thread.sleep(1);} catch(Exception e) {} 
            
               current_time = System.currentTimeMillis();
            }
        }
    }
    
    private void updateTime() {

        long previous_time = 0;
        
        previous_time = current_time;
        actual_delta_time = minimum_delta_time;
    }
    
    public void ourRefresh()
    {
        boolean everythingFilled = true;
        if (radRandomColours.isSelected() == false)
        {
            alreadyRandomized = false;
        }
        if ((radRandomColours.isSelected()) && (alreadyRandomized == false))
        {
            colourList = new Color[maxIterations];
            for(int i = 0; i < colourList.length ; i++){
                colourList[i] = new Color((int)(Math.random() * 255 + 1),(int)(Math.random() * 255 + 1),(int)(Math.random() * 255 + 1));
            }
            alreadyRandomized = true;
        }
        else if (radDiscoSet.isSelected())
        {
            colourList = new Color[maxIterations];
            for(int i = 0; i < colourList.length ; i++){
                colourList[i] = new Color((int)(Math.random() * 255 + 1),(int)(Math.random() * 255 + 1),(int)(Math.random() * 255 + 1));
            }
        }
        else if (radSmoothColours.isSelected() && maxIterations <= 255)
        {
            colourList = new Color[maxIterations];
            double increment = 255/maxIterations;
            double sumOfIncrements = 255;
            for(int i = 0; i < colourList.length ; i++){
                colourList[i] = new Color(0,0,(int)sumOfIncrements);
                sumOfIncrements = sumOfIncrements - increment;
            }
        }
        else if (radRandomColours.isSelected() == false)
        {
            colourList = new Color[]{Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.CYAN,Color.BLUE,Color.MAGENTA,Color.PINK,new Color(112,39,195)};
        }
        
        if (radGenerateJuliaSet.isSelected() == true)
        {
            txtJuliaSetValue.setEditable(true);
            txtJuliaSetImaginaryValue.setEditable(true);
            try{
                juliaSetValue = new Complex(Double.parseDouble(txtJuliaSetValue.getText()),Double.parseDouble(txtJuliaSetImaginaryValue.getText()));
            }catch(NumberFormatException e){}
            
        }
        else
        {
            txtJuliaSetValue.setEditable(false);
            txtJuliaSetValue.setText("");
            txtJuliaSetImaginaryValue.setEditable(false);
            txtJuliaSetImaginaryValue.setText("");
        }
        
        
        if (txtXMinimum.getText().equals("") == false)
        {
            try{
            minX = Double.parseDouble(txtXMinimum.getText());
            }catch(NumberFormatException e){
                
            }
        }
        else
        {
            everythingFilled = false;
        }
        if (txtXMaximum.getText().equals("") == false)
        {
            try{
            maxX = Double.parseDouble(txtXMaximum.getText());
            }catch(NumberFormatException e){
                
            }
        }
        else
        {
            everythingFilled = false;
        }
        if (txtYMinimum.getText().equals("") == false)
        {
            try{
            minY = Double.parseDouble(txtYMinimum.getText());
            }catch(NumberFormatException e){ 
                
            }
        }
        else
        {
            everythingFilled = false;
        }
        if (txtYMaximum.getText().equals("") == false)
        {
            try{
            maxY = Double.parseDouble(txtYMaximum.getText());
            }catch(NumberFormatException e){
                
            }
        }
        else
        {
            everythingFilled = false;
        }
        if (txtXResolution.getText().equals("") == false)
        {
            try{
                xRes = Double.parseDouble(txtXResolution.getText());
                }catch(NumberFormatException e){
                    
                }
        }
        else
        {
            everythingFilled = false;
        }
        if (txtExponent.getText().equals("") == false)
        {
            try{
                exponent = Integer.parseInt(txtExponent.getText());
                }catch(NumberFormatException e){
                    
                }
        }
        else
        {
            everythingFilled = false;
        }
        if (txtYResolution.getText().equals("") == false)
        {
            try
            {
                yRes = Double.parseDouble(txtYResolution.getText());
            }
            catch(NumberFormatException e){}
        }
        else
        {
            everythingFilled = false;
        }
        if (radGenerateMandelbrotSet.isSelected() || radGenerateJuliaSet.isSelected())
        {
            
        }
        else
        {
            everythingFilled = false;
        }
        if (radGenerateJuliaSet.isSelected() && txtJuliaSetValue.getText().equals(""))
        {
            everythingFilled = false;
        }
        if (radGenerateJuliaSet.isSelected() && txtJuliaSetImaginaryValue.getText().equals(""))
        {
            everythingFilled = false;
        }
        if (txtMaxIterations.getText().equals("") == false)
        {
            try
            {
                maxIterations = Integer.parseInt(txtMaxIterations.getText());
            }
            catch(java.lang.NumberFormatException e)
            {
                
            }
        }
        else
        {
            everythingFilled = false;
        }
        btnGenerate.setEnabled(everythingFilled);
    }
    
    protected void do_btnGenerate_mouseClicked(MouseEvent e) 
    {
      doNotDraw = true;
      Container cp = getContentPane();
      DrawPanel panel = new DrawPanel();
      getContentPane().add(panel, BorderLayout.CENTER);
      getContentPane().setLayout(null);
      /*Thread generateSet = new Thread(){
          public void run(){*/
              //desiredSet = FractalGenerator.generateMandelBrotSet(maxX, minX, maxY, minY, xRes, yRes, maxIterations, 4);
      //    }
      //};
      //generateSet.start();
      
      //Example values for Julia Sets
      
      //Complex c = new Complex(0.657,0.718);
      //Complex c = new Complex (0.324,0.324);
      //Complex c = new Complex(1.68, 2.34);
      
      if (FractalGenerator.canGenerateMandelbrotSet(maxX, minX, maxY, minY, xRes, yRes, maxIterations, exponent).equals("No issues with any of the values"))
      {
          if (radGenerateJuliaSet.isSelected())
          {
              desiredSet = FractalGenerator.generateJuliaSetReturningInts(maxX, minX, maxY, minY, xRes, yRes, maxIterations, juliaSetValue, exponent);
          }
          else if (radGenerateMandelbrotSet.isSelected())
          {
              desiredSet = FractalGenerator.generateMandelBrotSetReturningInts(maxX, minX, maxY, minY, xRes, yRes, maxIterations, exponent);
          }
          else
          {
              System.out.println("How did you get here?????");
          }
      }
      else
      {
          System.out.println(FractalGenerator.canGenerateMandelbrotSet(maxX, minX, maxY, minY, xRes, yRes, maxIterations, exponent));
          desiredSet = null;
      }
      if (radRandomColours.isSelected())
      {
          colourList = new Color[maxIterations];
          for(int i = 0; i < colourList.length ; i++){
              colourList[i] = new Color((int)(Math.random() * 255 + 1),(int)(Math.random() * 255 + 1),(int)(Math.random() * 255 + 1));
          }
      }
      else
      {
          colourList = new Color[]{Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.CYAN,Color.BLUE,Color.MAGENTA,Color.PINK,new Color(112,39,195)};
      }
      if (radSmoothColours.isSelected() && maxIterations <= 255)
      {
          colourList = new Color[maxIterations];
          double increment = 255/maxIterations;
          double sumOfIncrements = 255;
          for(int i = 0; i < colourList.length ; i++){
              colourList[i] = new Color(0,0,(int)sumOfIncrements);
              sumOfIncrements = sumOfIncrements - increment;
          }
      }
      this.ourRefresh();
      this.repaint();
      doNotDraw = false;
    }
    protected void do_textField_keyTyped(KeyEvent arg0) 
    {
        
    }
    protected void do_this_mouseClicked(MouseEvent arg0) 
    {
        this.getContentPane().setFocusable(true);
        this.requestFocus();
        System.out.println("Focus Switched!!");
    }
}
