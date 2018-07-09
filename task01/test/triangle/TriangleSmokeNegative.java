package triangle;

import org.junit.rules.ExpectedException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import static org.testng.Assert.fail;


@Test
public class TriangleSmokeNegative {
    private int TR_EQUILATERAL = 1; // равносторонний
    private int TR_ISOSCELES = 2;   // равнобедренный
    private int TR_ORDYNARY = 4;    // обычный
    private int TR_RECTANGULAR = 8; // прямоугольный
    private ArrayList<Integer> trTypes = new ArrayList<Integer>(Arrays.asList(TR_EQUILATERAL, TR_ISOSCELES, TR_ORDYNARY, TR_RECTANGULAR));
    private Triangle triangleForTest;
    private double a, b, c;
    private String x, y, z;
    private int final_state;

//    @BeforeClass
//    public void generateRandomTriangle() {
//        a = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, Double.MIN_VALUE + Double.MIN_VALUE);
//        b = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, Double.MIN_VALUE + Double.MIN_VALUE);
//        c = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, Double.MIN_VALUE + Double.MIN_VALUE);
//        triangleForTest = new Triangle(a, b, c);
//    }

    @DataProvider(name = "TrianglesWithNegativeSides")
    public Iterator<Object[]> createTriangleNegSides() {
        a = (ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, 1000.0000));
        b = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, 1000.0000);
        c = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, 1000.0000);
        return Arrays.asList(new Object[][]
                {
                        {new ArrayList<>(Arrays.asList(-a, b, c))},
                        {new ArrayList<>(Arrays.asList(c, -a, b))},
                        {new ArrayList<>(Arrays.asList(b, c, -a))},
                        {new ArrayList<Double>(Arrays.asList(-a, b, c))},// одна отрицательная
                        {new ArrayList<Double>(Arrays.asList(c, -a, b))},// одна отрицательная
                        {new ArrayList<Double>(Arrays.asList(b, c, -a))},// одна отрицательная
                        {new ArrayList<Double>(Arrays.asList(-a, -b, c))}, // две отрицаетльных
                        {new ArrayList<Double>(Arrays.asList(c, -a, -b))}, // две отрицаетльных
                        {new ArrayList<Double>(Arrays.asList(-b, c, -a))}, // две отрицаетльных
                        {new ArrayList<Double>(Arrays.asList(-a, -b, -c))}, //три отрицательных
                        {new ArrayList<Double>(Arrays.asList(-c, -a, -b))}, //три отрицательных
                        {new ArrayList<Double>(Arrays.asList(-b, -c, -a))}, //три отрицательных
                        {new ArrayList<Double>(Arrays.asList(0.0, b, c))}, // один ноль
                        {new ArrayList<Double>(Arrays.asList(c, 0.0, b))}, // один ноль
                        {new ArrayList<Double>(Arrays.asList(b, c, 0.0))}, // один ноль
                        {new ArrayList<Double>(Arrays.asList(0.0, 0.0, c))}, //два нуля
                        {new ArrayList<Double>(Arrays.asList(c, 0.0, 0.0))}, //два нуля
                        {new ArrayList<Double>(Arrays.asList(0.0, c, 0.0))}, //два нуля
                        {new ArrayList<Double>(Arrays.asList(0.0, 0.0, 0.0))}, // три нуля


                }).iterator();
    }

    @DataProvider(name = "TrianglesWithTooLongSides")
    public Iterator<Object[]> createTriangleTooBigSides() {
        a = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, 1000);
        b = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, 1000);
        c = a + b;
        return Arrays.asList(new Object[][]
                {
                        {new ArrayList<Double>(Arrays.asList(a, b, c))},
                        {new ArrayList<Double>(Arrays.asList(c, a, b))},
                        {new ArrayList<Double>(Arrays.asList(b, c, a))},
                        {new ArrayList<Double>(Arrays.asList(a, b, c + 1))},// третья сторона больше суммы двух других на MIN_VALUE
                        {new ArrayList<Double>(Arrays.asList(c + 1, a, b))},
                        {new ArrayList<Double>(Arrays.asList(b, c + 1, a))},

                }).iterator();
    }


    @DataProvider(name = "OrdinaryTriangleGenerator")
    public Iterator<Object[]> createOrdTriangle() {
        a = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, 1000);
        b = 2 * a;
        c = 2.5 * a;
        return Arrays.asList(new Object[][]
                {
                        {new ArrayList<Double>(Arrays.asList(a, b, c))},
                        {new ArrayList<Double>(Arrays.asList(c, a, b))},
                        {new ArrayList<Double>(Arrays.asList(b, c, a))},
                }).iterator();
    }
    @Test(dataProvider = "TrianglesWithNegativeSides")
    public void TriangleConstructor_NegativeSides(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertEquals(triangleForTest.getMessage(), "", "Error,message is not empty");
    }
    @Test(dataProvider = "TrianglesWithTooLongSides")
    public void TriangleConstructor_TooLongSides(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertEquals(triangleForTest.getMessage(), "", "Error,message is not empty");
    }


    @Test(dataProvider = "TrianglesWithNegativeSides")
    public void TriangleCheck_NegativeSides(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        if (sides.get(0) <= 0) {
            Assert.assertFalse(triangleForTest.checkTriangle());
            Assert.assertEquals(triangleForTest.getMessage(), "a<=0");
            return;
        } else if (sides.get(1) <= 0) {
            Assert.assertFalse(triangleForTest.checkTriangle());
            Assert.assertEquals(triangleForTest.getMessage(), "b<=0");
            return;
        } else if (sides.get(2) <= 0) {
            Assert.assertFalse(triangleForTest.checkTriangle());
            Assert.assertEquals(triangleForTest.getMessage(), "c<=0");
            return;
        }
    }

    @Test(dataProvider = "TrianglesWithTooLongSides")
    public void TriangleCheck_TooBigSides(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        if (sides.get(0) + sides.get(1) <= sides.get(2)) {
            Assert.assertFalse(triangleForTest.checkTriangle());
            Assert.assertEquals(triangleForTest.getMessage(), "a+b<=c");
            return;
        } else if (sides.get(0) + sides.get(2) <= sides.get(1)) {
            Assert.assertFalse(triangleForTest.checkTriangle());
            Assert.assertEquals(triangleForTest.getMessage(), "a+c<=b");
            return;
        } else if (sides.get(1) + sides.get(2) <= sides.get(0)) {
            Assert.assertFalse(triangleForTest.checkTriangle());
            Assert.assertEquals(triangleForTest.getMessage(), "b+c<=a");
            return;
        } else {
            Assert.assertTrue(triangleForTest.checkTriangle());
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void CheckTriangle_null () {
        triangleForTest = null;
        Assert.assertFalse(triangleForTest.checkTriangle());
    }
    @Test
            (dataProvider = "TrianglesWithNegativeSides")
    public void detectTriangle_negativeSides(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertEquals(triangleForTest.detectTriangle(), "error"); /// заваливаем тест ожиданием возврата "errror",не знаем какое исключение породить
        fail();

    }

    @Test
            (dataProvider = "TrianglesWithTooLongSides")
    public void detectTriangle_tooBigSides(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertEquals(triangleForTest.detectTriangle(), "error");    /// заваливаем тест ожиданием возврата "errror",не знаем какое исключение породить

    }

    @Test(expectedExceptions = NullPointerException.class)
    public void detectTriangle_null () {
        triangleForTest = null;
        Assert.assertEquals(triangleForTest.detectTriangle(),-1);
    }

    @Test(dataProvider = "TrianglesWithNegativeSides")                  // проваливаем проверки, не указан тип
    // исключения при бесконечности и NaN в кач-ве стороны
    public void getSquare_NegativeSides(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertEquals(triangleForTest.getSquare(), "Negative or zero side");
    }

    @Test(dataProvider = "TrianglesWithTooLongSides")                  // проваливаем проверки, не указан тип
    // исключения при бесконечности и NaN в кач-ве стороны
    public void getSquare_TooLongSide(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertEquals(triangleForTest.getSquare(), "One of the sides is longer than other two or equal to their length summ");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getSquare_null () {
        triangleForTest = null;
        Assert.assertEquals(triangleForTest.getSquare(),0,"Cannot count the square of null object");
        fail();
    }
}
