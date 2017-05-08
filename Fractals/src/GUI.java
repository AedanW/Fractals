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
import java.awt.Font;

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
    private float minX;
    private float maxX;
    private float minY;
    private float maxY;
    private float xRes;
    private float yRes;
    boolean generateMandelbrot = false;
    boolean generateJulia = false;
    Color[] colourList = new Color[]{Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.CYAN,Color.BLUE,Color.MAGENTA,Color.PINK,new Color(112,39,195)};
    private JTextField txtXResolution;
    private JTextField txtYResolution;

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
        
        txtXMinimum = new JTextField();
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
        txtXMaximum.setBounds(888, 36, 86, 20);
        contentPane.add(txtXMaximum);
        txtXMaximum.setColumns(10);
        
        txtYMinimum = new JTextField();
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
        txtYMaximum.setColumns(10);
        txtYMaximum.setBounds(888, 86, 86, 20);
        contentPane.add(txtYMaximum);
        
        radGenerateMandelbrotSet = new JRadioButton("Generate Mandelbrot set");
        radGenerateMandelbrotSet.setBackground(SystemColor.inactiveCaption);
        radGenerateMandelbrotSet.setBounds(794, 161, 180, 23);
        radGenerateMandelbrotSet.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
        	radGenerateJuliaSet.setSelected(false);
        	refresh();
        }});
        contentPane.add(radGenerateMandelbrotSet);
        
        radGenerateJuliaSet = new JRadioButton("Generate Julia set");
        radGenerateJuliaSet.setBackground(SystemColor.inactiveCaption);
        radGenerateJuliaSet.setBounds(794, 188, 180, 23);
        contentPane.add(radGenerateJuliaSet);
        radGenerateJuliaSet.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
        	radGenerateMandelbrotSet.setSelected(false);
        	refresh();
        }});
        txtJuliaSetValue = new JTextField();
        txtJuliaSetValue.setEditable(false);
        txtJuliaSetValue.setBounds(888, 218, 86, 20);
        contentPane.add(txtJuliaSetValue);
        txtJuliaSetValue.setColumns(10);
        
        JLabel lblJuliaSetValue = new JLabel("Julia Set Value:");
        lblJuliaSetValue.setBounds(790, 222, 94, 14);
        contentPane.add(lblJuliaSetValue);
        
        btnGenerate = new JButton("Generate");
        btnGenerate.setBounds(845, 516, 89, 23);
        contentPane.add(btnGenerate);
        
        txtMaxIterations = new JTextField();
        txtMaxIterations.setBounds(888, 249, 86, 20);
        contentPane.add(txtMaxIterations);
        txtMaxIterations.setColumns(10);
        
        JLabel lblMaxIterations = new JLabel("Max Iterations:");
        lblMaxIterations.setBounds(790, 252, 84, 14);
        contentPane.add(lblMaxIterations);
        
        Canvas Canvas = new Canvas();
        Canvas.setFont(new Font("Webdings", Font.PLAIN, 12));
        Canvas.setBackground(Color.WHITE);
        Canvas.setBounds(-146, 40, 755, 528);
        contentPane.add(Canvas);
        
        JLabel lblXRes = new JLabel("X Resolution");
        lblXRes.setBounds(818, 115, 70, 14);
        contentPane.add(lblXRes);
        
        JLabel lblYRes = new JLabel("Y Resolution");
        lblYRes.setBounds(818, 140, 70, 14);
        contentPane.add(lblYRes);
        
        txtXResolution = new JTextField();
        txtXResolution.setBounds(888, 111, 86, 20);
        contentPane.add(txtXResolution);
        txtXResolution.setColumns(10);
        
        txtYResolution = new JTextField();
        txtYResolution.setColumns(10);
        txtYResolution.setBounds(888, 136, 86, 20);
        contentPane.add(txtYResolution);
        
        refresh();    
    }
    
    class DrawPanel extends JPanel
    {
            public void paintComponent(Graphics g, Point[][] desiredSet)
            {
                if (g == null) 
                {
                    return;
                }
                
                this.resize(this.getParent().getWidth() - 8, this.getParent().getHeight() - 8);
                double cell_width = (double)this.getWidth() / (desiredSet.length);
                double cell_height = (double)(this.getHeight()) / (desiredSet[0].length);
                
                System.out.println("parent width = " + this.getParent().getWidth());
                System.out.println("DrawPanel width = " + this.getWidth());
                
                //BS way of clearing out the old rectangle to save CPU.
                g.setColor(getBackground());

                for (int y = 0; y < desiredSet[0].length; y++)
                {
                    for (int x = 0; x < desiredSet.length; x++)
                    {
                        if (desiredSet[x][y].iterations == FractalGenerator.MAX_ITERATIONS)
                        {
                            g.setColor(Color.BLACK);
                        }
                        else
                        {
                            int colourIndex = desiredSet[x][y].iterations % colourList.length;
                            g.setColor(colourList[colourIndex]);
                        }
                        g.fillRect((int)(x * cell_width),(int)(y * cell_height + 400), (int)(cell_width) + 1, (int)(cell_height) + 1);                                
                    }
                }
                refresh();
            }
    }

    
    public void refresh()
    {
        boolean everythingFilled = true;
        btnGenerate.setEnabled(false);
        if (radGenerateJuliaSet.isSelected() == true)
        {
            txtJuliaSetValue.setEditable(true);
        }
        else
        {
            txtJuliaSetValue.setEditable(false);
            txtJuliaSetValue.setText("");
        }
        
        
        if (txtXMinimum.getText().equals("") == false)
        {
            minX = Float.parseFloat(txtXMinimum.getText());
        }
        else
        {
            everythingFilled = false;
        }
        if (txtXMaximum.getText().equals("") == false)
        {
            maxX = Float.parseFloat(txtXMaximum.getText());
        }
        else
        {
            everythingFilled = false;
        }
        if (txtYMinimum.getText().equals("") == false)
        {
            minY = Float.parseFloat(txtYMinimum.getText());
        }
        else
        {
            everythingFilled = false;
        }
        if (txtYMaximum.getText().equals("") == false)
        {
            maxY = Float.parseFloat(txtYMaximum.getText());
        }
        else
        {
            everythingFilled = false;
        }
        if (txtXResolution.getText().equals("") == false)
        {
            xRes = Float.parseFloat(txtXResolution.getText());
        }
        else
        {
            everythingFilled = false;
        }
        if (txtYResolution.getText().equals("") == false)
        {
            yRes = Float.parseFloat(txtYResolution.getText());
        }
        else
        {
            everythingFilled = false;
        }
        btnGenerate.setEnabled(everythingFilled);
    }
}
