
public abstract class FractalGenerator 
{
    protected static int maxIterations = 100;
    protected static long progress = 0;
    protected static int displayedProgress = 0;
    
    public static Point[][] generateMandelBrotSet(double maxX, double minX, double maxY, double minY, double xRes, double yRes, int maxIterationsi)
    {
        maxIterations = maxIterationsi;
        Point[][] fractal =  new Point[(int)(1+((maxX-minX) / xRes))][(int)(1+((maxY-minY) / yRes))];
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
                fractal[col][fractal[0].length-1-row] = new Point(x,y,iterations);
                } 
                catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("ArrayIndexOutOfBounds at " + col + " " + row + ", bummer.");
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
        progress = 0;
        return fractal;
    }
    
    public static Point[][] generateMandelBrotSet(double maxX, double minX, double maxY, double minY, double xRes, double yRes, int maxIterationsi, int exponent)
    {
        maxIterations = maxIterationsi;
        Point[][] fractal =  new Point[(int)(1+((maxX-minX) / xRes))][(int)(1+((maxY-minY) / yRes))];
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
                    Complex zToAnExponent = z.times(z);
                    int modifiedExponent = exponent;
                    while (modifiedExponent > 2)
                    {
                        zToAnExponent = zToAnExponent.times(z);
                        modifiedExponent--;
                    }
                    Complex newZValue = zToAnExponent.plus(current);
                    z = newZValue;
                    iterations++;
                }
                try{
                fractal[col][fractal[0].length-1-row] = new Point(x,y,iterations);
                } 
                catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("ArrayIndexOutOfBounds at " + col + " " + row + ", bummer.");
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
        progress = 0;
        return fractal;
    } 
    
    public static int[][] generateMandelBrotSetReturningInts(double maxX, double minX, double maxY, double minY, double xRes, double yRes, int maxIterationsi, int exponent)
    {
        maxIterations = maxIterationsi;
        int[][] fractal =  new int[(int)(1+((maxX-minX) / xRes))][(int)(1+((maxY-minY) / yRes))];
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
                    Complex zToAnExponent = z.times(z);
                    int modifiedExponent = exponent;
                    while (modifiedExponent > 2)
                    {
                        zToAnExponent = zToAnExponent.times(z);
                        modifiedExponent--;
                    }
                    Complex newZValue = zToAnExponent.plus(current);
                    z = newZValue;
                    iterations++;
                }
                try{
                fractal[col][fractal[0].length-1-row] = iterations;
                } 
                catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("ArrayIndexOutOfBounds at " + col + " " + row + ", bummer.");
                }
                y = y + yRes;
                row++;
                z = new Complex(0,0);
                progress++;
                System.out.println((progress*100/ ((int)(1+((maxY-minY) / yRes)) * (int)(1+((maxX-minX) / xRes)))));
                displayedProgress = (int)(progress*100/ ((int)(1+((maxY-minY) / yRes)) * (int)(1+((maxX-minX) / xRes))));
            }
            x = x + xRes;
            col++;
            row = 0;
            y = originalY;
        }
        progress = 0;
        return fractal;
    }
    
    public static Point[][] generateJuliaSet(double maxX, double minX, double maxY, double minY, double xRes, double yRes, int maxIterationsi, Complex juliaSetValue)
    {
        maxIterations = maxIterationsi;
        Point[][] fractal =  new Point[(int)(1+((maxX-minX) / xRes))][(int)(1+((maxY-minY) / yRes))];
        double x = minX;
        double y = minY;
        double originalY = y;
        progress = 1;
        int row = 0;
        int col = 0;
        while (x - (x%xRes) <= maxX)
        {
            while (y - (y%yRes) <= maxY)
            {
                int iterations = 0;
                Complex current = juliaSetValue;
                Complex z = new Complex(x,y);
                //Determine whether or not current is in the Mandelbrot set
                while ((maxIterations > iterations) && (2.0 > z.abs()))
                {
                    Complex zSquared = z.times(z);
                    Complex newZValue = zSquared.plus(current);
                    z = newZValue;
                    iterations++;
                }
                try{
                fractal[col][fractal[0].length-1-row] = new Point(x,y,iterations);
                } 
                catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("ArrayIndexOutOfBounds at " + col + " " + row + ", bummer.");
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
        progress = 0;
        return fractal;
    }
    
    public static Point[][] generateJuliaSet(double maxX, double minX, double maxY, double minY, double xRes, double yRes, int maxIterationsi, Complex juliaSetValue, int exponent)
    {
        maxIterations = maxIterationsi;
        Point[][] fractal =  new Point[(int)(1+((maxX-minX) / xRes))][(int)(1+((maxY-minY) / yRes))];
        double x = minX;
        double y = minY;
        double originalY = y;
        progress = 1;
        int row = 0;
        int col = 0;
        while (x - (x%xRes) <= maxX)
        {
            while (y - (y%yRes) <= maxY)
            {
                int iterations = 0;
                Complex current = juliaSetValue;
                Complex z = new Complex(x,y);
                //Determine whether or not current is in the Mandelbrot set
                while ((maxIterations > iterations) && (2.0 > z.abs()))
                {
                    Complex zToAnExponent = z.times(z);
                    int modifiedExponent = exponent;
                    while (modifiedExponent > 2)
                    {
                        zToAnExponent = zToAnExponent.times(z);
                        modifiedExponent--;
                    }
                    Complex newZValue = zToAnExponent.plus(current);
                    z = newZValue;
                    iterations++;
                }
                try{
                fractal[col][fractal[0].length-1-row] = new Point(x,y,iterations);
                } 
                catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("ArrayIndexOutOfBounds at " + col + " " + row + ", bummer.");
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
        progress = 0;
        return fractal;
    }
    
    public static int[][] generateJuliaSetReturningInts(double maxX, double minX, double maxY, double minY, double xRes, double yRes, int maxIterationsi, Complex juliaSetValue, int exponent)
    {
        maxIterations = maxIterationsi;
        int[][] fractal =  new int[(int)(1+((maxX-minX) / xRes))][(int)(1+((maxY-minY) / yRes))];
        double x = minX;
        double y = minY;
        double originalY = y;
        progress = 1;
        int row = 0;
        int col = 0;
        while (x - (x%xRes) <= maxX)
        {
            while (y - (y%yRes) <= maxY)
            {
                int iterations = 0;
                Complex current = juliaSetValue;
                Complex z = new Complex(x,y);
                //Determine whether or not current is in the Mandelbrot set
                while ((maxIterations > iterations) && (2.0 > z.abs()))
                {
                    Complex zToAnExponent = z.times(z);
                    int modifiedExponent = exponent;
                    while (modifiedExponent > 2)
                    {
                        zToAnExponent = zToAnExponent.times(z);
                        modifiedExponent--;
                    }
                    Complex newZValue = zToAnExponent.plus(current);
                    z = newZValue;
                    iterations++;
                }
                try{
                fractal[col][fractal[0].length-1-row] = iterations;
                } 
                catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("ArrayIndexOutOfBounds at " + col + " " + row + ", bummer.");
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
        progress = 0;
        return fractal;
    }
    
    public static String canGenerateMandelbrotSet(double maxX, double minX, double maxY, double minY, double xRes, double yRes, int maxIterationsi, int exponent)
    {
        progress = progress;
        if (xRes <= 0)
        {
            return "X resolution must be larger than 0";
        }
        else if (yRes <= 0)
        {
            return "Y resolution must be larger than 0";
        }
        else if (maxIterations <= 0)
        {
            return "The maximum number of iterations must be larger than 0";
        }
        else if (exponent < 2)
        {
            return "The exponent must be larger than 1";
        }
        else if (maxX < minX)
        {
            return "Minimum x value is larger than the maximum x value"; 
        }
        else if (maxY < minY)
        {
            return "Minimum y value is larger than the maximum y value";
        }
        
        return "No issues with any of the values";
    }
    
    public static long getProgress()
    {
        return progress;
    }
}
