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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;

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

public class GUI extends JFrame {

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
    
    Point[][] desiredSet = null;
    private JRadioButton radRandomColours;
    private JTextField txtJuliaSetImaginaryValue;
    private JRadioButton radSmoothColours;
    private JTextField txtExponent;

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
        contentPane = new DrawPanel();
        contentPane.setBackground(SystemColor.inactiveCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        txtXMinimum = new JTextField();
        txtXMinimum.setText("-2");
        txtXMinimum.setBounds(888, 11, 86, 20);
        contentPane.add(txtXMinimum);
        txtXMinimum.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("X Minimum");
        lblNewLabel.setBounds(818, 15, 70, 14);
        contentPane.add(lblNewLabel);
        
        JLabel lblXMaximum = new JLabel("X Maximum");
        lblXMaximum.setBounds(818, 40, 70, 14);
        contentPane.add(lblXMaximum);
        
        txtXMaximum = new JTextField();
        txtXMaximum.setText("2");
        txtXMaximum.setBounds(888, 36, 86, 20);
        contentPane.add(txtXMaximum);
        txtXMaximum.setColumns(10);
        
        txtYMinimum = new JTextField();
        txtYMinimum.setText("-2");
        txtYMinimum.setColumns(10);
        txtYMinimum.setBounds(888, 61, 86, 20);
        contentPane.add(txtYMinimum);
        
        JLabel lblXMinimum = new JLabel("Y Minimum");
        lblXMinimum.setBounds(818, 65, 70, 14);
        contentPane.add(lblXMinimum);
        
        JLabel lblYMaximum = new JLabel("Y Maximum");
        lblYMaximum.setBounds(818, 90, 70, 14);
        contentPane.add(lblYMaximum);
        
        txtYMaximum = new JTextField();
        txtYMaximum.setText("2");
        txtYMaximum.setColumns(10);
        txtYMaximum.setBounds(888, 86, 86, 20);
        contentPane.add(txtYMaximum);
        
        radGenerateMandelbrotSet = new JRadioButton("Generate Mandelbrot set");
        radGenerateMandelbrotSet.setBackground(SystemColor.inactiveCaption);
        radGenerateMandelbrotSet.setBounds(794, 161, 180, 23);
        radGenerateMandelbrotSet.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
        	radGenerateJuliaSet.setSelected(false);
        	ourRefresh();
        }});
        contentPane.add(radGenerateMandelbrotSet);
        
        radGenerateJuliaSet = new JRadioButton("Generate Julia set");
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
        lblJuliaSetValue.setBounds(818, 282, 125, 14);
        contentPane.add(lblJuliaSetValue);
        
        btnGenerate = new JButton("Generate");
        btnGenerate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                do_btnGenerate_mouseClicked(e);
            }
        });
        btnGenerate.setBounds(845, 516, 89, 23);
        contentPane.add(btnGenerate);
        
        txtMaxIterations = new JTextField();
        txtMaxIterations.setText("100");
        txtMaxIterations.setBounds(834, 419, 86, 20);
        contentPane.add(txtMaxIterations);
        txtMaxIterations.setColumns(10);
        
        JLabel lblMaxIterations = new JLabel("Max Iterations:");
        lblMaxIterations.setBounds(818, 394, 84, 14);
        contentPane.add(lblMaxIterations);
        
        JLabel lblXRes = new JLabel("X Resolution");
        lblXRes.setBounds(814, 115, 83, 14);
        contentPane.add(lblXRes);
        
        JLabel lblYRes = new JLabel("Y Resolution");
        lblYRes.setBounds(814, 140, 70, 14);
        contentPane.add(lblYRes);
        
        txtXResolution = new JTextField();
        txtXResolution.setText("0.01");
        txtXResolution.setBounds(888, 111, 86, 20);
        contentPane.add(txtXResolution);
        txtXResolution.setColumns(10);
        
        txtYResolution = new JTextField();
        txtYResolution.setText("0.01");
        txtYResolution.setColumns(10);
        txtYResolution.setBounds(888, 136, 86, 20);
        contentPane.add(txtYResolution);
        
        radRandomColours = new JRadioButton("Random Colours");
        radRandomColours.setBackground(SystemColor.inactiveCaption);
        radRandomColours.setBounds(794, 446, 180, 23);
        contentPane.add(radRandomColours);
        
        JLabel lblJuliaSetImaginaryValue = new JLabel("Julia Set Imaginary Value:");
        lblJuliaSetImaginaryValue.setBounds(818, 338, 150, 14);
        contentPane.add(lblJuliaSetImaginaryValue);
        
        txtJuliaSetImaginaryValue = new JTextField();
        txtJuliaSetImaginaryValue.setEditable(false);
        txtJuliaSetImaginaryValue.setColumns(10);
        txtJuliaSetImaginaryValue.setBounds(834, 363, 86, 20);
        contentPane.add(txtJuliaSetImaginaryValue);
        
        radSmoothColours = new JRadioButton("Smooth Colours");
        radSmoothColours.setBackground(SystemColor.inactiveCaption);
        radSmoothColours.setBounds(794, 472, 170, 23);
        contentPane.add(radSmoothColours);
        
        txtExponent = new JTextField();
        txtExponent.setText("2");
        txtExponent.setBounds(834, 251, 86, 20);
        contentPane.add(txtExponent);
        txtExponent.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("Exponent Of Z");
        lblNewLabel_1.setBounds(818, 226, 102, 14);
        contentPane.add(lblNewLabel_1);
        
//        btnGenerate.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
//        }});
//        
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
                if ((desiredSet == null)){
                    return;
                }
                if (doNotDraw == true)
                {
                    System.out.println("It gets here!");
                    return;
                }
                else {
                    System.out.println("I better draw!" + LocalDateTime.now().toString());
                }
                    
                
                g.setColor(getBackground());
                
                this.resize(this.getParent().getWidth() - 8, this.getParent().getHeight() - 8);
                double cell_width = ((double)this.getWidth() - 200)/ (desiredSet.length);
                double cell_height = (double)(this.getHeight()) / (desiredSet[0].length);
                
                System.out.println("parent width = " + this.getParent().getWidth());
                System.out.println("DrawPanel width = " + this.getWidth());
                
                for (int y = 0; y < desiredSet.length; y++)
                {
                    for (int x = 0; x < desiredSet[0].length; x++)
                    {
                        if (desiredSet[y][x].iterations == FractalGenerator.maxIterations)
                        {
                            g.setColor(Color.BLACK);
                        }
                        else
                        {
                            int colourIndex = desiredSet[y][x].iterations % colourList.length;
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
            this.repaint();
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
            xRes = Double.parseDouble(txtXResolution.getText());
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
            yRes = Double.parseDouble(txtYResolution.getText());
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
            maxIterations = Integer.parseInt(txtMaxIterations.getText());
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
              desiredSet = FractalGenerator.generateJuliaSet(maxX, minX, maxY, minY, xRes, yRes, maxIterations, juliaSetValue, exponent);
          }
          else if (radGenerateMandelbrotSet.isSelected())
          {
              desiredSet = FractalGenerator.generateMandelBrotSet(maxX, minX, maxY, minY, xRes, yRes, maxIterations, exponent);
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
}
