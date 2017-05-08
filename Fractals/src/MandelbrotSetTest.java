import static org.junit.Assert.*;

import org.junit.Test;

public class MandelbrotSetTest {

    @Test
    public void testMandelbrotSetGeneration() 
    {
        Point[][]mandelbrotSetTest = FractalGenerator.generateMandelBrotSet(4, -4, 4, -4, 1, 1);
        assertEquals(9, mandelbrotSetTest.length);
        assertEquals(9, mandelbrotSetTest[0].length);
        
        mandelbrotSetTest = FractalGenerator.generateMandelBrotSet(2, -2, 2, -2, 2, 2);
        assertEquals(3, mandelbrotSetTest.length);
        assertEquals(3, mandelbrotSetTest[0].length);
        
        Point p = new Point(0,0,FractalGenerator.MAX_ITERATIONS);
        assertEquals(p.iterations,mandelbrotSetTest[1][1].iterations);
        assertEquals(1,mandelbrotSetTest[0][0].iterations);
        assertEquals(1,mandelbrotSetTest[2][2].iterations);
        assertEquals(1,mandelbrotSetTest[2][0].iterations);
    }

}
