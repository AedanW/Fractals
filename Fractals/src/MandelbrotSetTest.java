import static org.junit.Assert.*;

import org.junit.Test;

public class MandelbrotSetTest {

    @Test
    public void testMandelbrotSetGeneration() 
    {
        Point[][]aedanwen = FractalGenerator.generateMandelBrotSet(4, -4, 4, -4, 1, 1);
        assertEquals(9, aedanwen.length);
        assertEquals(9, aedanwen[0].length);
        
        aedanwen = FractalGenerator.generateMandelBrotSet(2, -2, 2, -2, 2, 2);
        assertEquals(3, aedanwen.length);
        assertEquals(3, aedanwen[0].length);
        
        Point p = new Point(0,0,FractalGenerator.MAX_ITERATIONS);
        assertEquals(p.iterations,aedanwen[1][1].iterations);
        assertEquals(1,aedanwen[0][0].iterations);
        assertEquals(1,aedanwen[2][2].iterations);
        assertEquals(1,aedanwen[2][0].iterations);
    }

}
