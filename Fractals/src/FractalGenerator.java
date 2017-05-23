
public abstract class FractalGenerator 
{
    //Testing to see if its working
    // did you git this?
	// I got it
    
    protected static int maxIterations = 2000;
    protected static long progress = 0;
    
    public static Point[][] generateMandelBrotSet(double maxX, double minX, double maxY, double minY, double xRes, double yRes, int maxIterationsi)
    {
        maxIterations = maxIterationsi;
        Point[][] fractal =  new Point[(int)(1+((maxY-minY) / yRes))][(int)(1+((maxX-minX) / xRes))];
        double x = minX;
        double y = minY;
        double originalY = y;
        progress = 1;
        Complex z = new Complex(0,0);
        int row = 0;
        int col = 0;
        while (x - (x%xRes) <= maxX)
        {
            while (y - (y%yRes) <= maxY)
            {
                int iterations = 0;
                Complex current = new Complex(x,y);
                //Determine whether or not current is in the Mandelbrot set
                while ((maxIterations > iterations) && (2.0 > z.abs()))
                {
                    Complex zSquared = z.times(z);
                    Complex newZValue = zSquared.plus(current);
                    z = newZValue;
                    iterations++;
                }
                try{
                fractal[fractal.length-1-row][col] = new Point(x,y,iterations);
                } 
                catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("ArrayIndexOutOfBounds at " + row + " " + col + ", bummer.");
                }
                y = y + yRes;
                row++;
                z = new Complex(0,0);
                progress++;
                System.out.println((progress*100/ ((int)(1+((maxY-minY) / yRes)) * (int)(1+((maxX-minX) / xRes)))));
            }
            x = x + xRes;
            col++;
            row = 0;
            y = originalY;
        }
        return fractal;
    }
}
