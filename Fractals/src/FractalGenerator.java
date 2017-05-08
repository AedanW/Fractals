
public abstract class FractalGenerator 
{
    //Testing to see if its working
    // did you git this?
	// I got it
    
    protected static final int MAX_ITERATIONS = 20000;
    
    public static Point[][] generateMandelBrotSet(int maxX, int minX, int maxY, int minY, float xRes, float yRes)
    {
        Point[][] fractal =  new Point[(int)(1+((maxY-minY) / yRes))][(int)(1+((maxX-minX) / xRes))];
        float x = minX;
        float y = minY;
        float originalY = y;
        Complex z = new Complex(0,0);
        int row = 0;
        int col = 0;
        while (x <= maxX)
        {
            while (y <= maxY)
            {
                int iterations = 0;
                Complex current = new Complex(x,y);
                //Determine whether or not current is in the Mandelbrot set
                while ((MAX_ITERATIONS > iterations) && (2.0 > z.abs()))
                {
                    Complex zSquared = z.times(z);
                    Complex newZValue = zSquared.plus(current);
                    z = newZValue;
                    iterations++;
                }
                fractal[fractal.length-1-row][col] = new Point(x,y,iterations);
                y = y + yRes;
                row++;
                z = new Complex(0,0);
            }
            x = x + xRes;
            col++;
            row = 0;
            y = originalY;
        }
        return fractal;
    }
}
