package triangle;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

@Test
public class TriangleSmokePositive {
    private static final double EXPONENT = 2.0;
    private final int TR_EQUILATERAL = 1; // равносторонний
    private final int TR_ISOSCELES = 2;   // равнобедренный
    private final int TR_ORDYNARY = 4;    // обычный
    private final int TR_RECTANGULAR = 8; // прямоугольный
    private ArrayList<Integer> trTypes = new ArrayList<>(Arrays.asList(TR_EQUILATERAL, TR_ISOSCELES, TR_ORDYNARY, TR_RECTANGULAR));
    private Triangle triangleForTest;
    private double a, b, c;
    //получения квадрата этого значения и ниже не приведет к получению infinity в проверке по теореме Пифагора
    private final double MAX_VALUE_FOR_RECTANGULAR = (Math.sqrt(Double.MAX_VALUE)) + 0.00000000000000007E154;

    //    @DataProvider(name = "BigData")
//    public Object[][] createAllTriangles() {
//        a = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, 1000);
//        b = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, 1000);
//        b = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, 1000);
//        return new Object[][]{};
//
//
//    }
    @DataProvider(name = "TrianglesWithNegativeSides")
    public Iterator<Object[]> createTriangleNegSides() {
        a = (ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, MAX_VALUE_FOR_RECTANGULAR));
        b = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, MAX_VALUE_FOR_RECTANGULAR);
        c = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, MAX_VALUE_FOR_RECTANGULAR);
        return Arrays.asList(new Object[][]
                {

                        {new ArrayList<>(Arrays.asList(-a, b, c))},
                        {new ArrayList<>(Arrays.asList(c, -a, b))},
                        {new ArrayList<>(Arrays.asList(b, c, -a))},
                        {new ArrayList<>(Arrays.asList(-a, b, c))},
                        {new ArrayList<>(Arrays.asList(c, -a, b))},
                        {new ArrayList<>(Arrays.asList(b, c, -a))},
                        {new ArrayList<>(Arrays.asList(-a, -b, c))},
                        {new ArrayList<>(Arrays.asList(c, -a, -b))},
                        {new ArrayList<>(Arrays.asList(-b, c, -a))},
                        {new ArrayList<>(Arrays.asList(-a, -b, -c))},
                        {new ArrayList<>(Arrays.asList(-c, -a, -b))},
                        {new ArrayList<>(Arrays.asList(-b, -c, -a))},
                        {new ArrayList<>(Arrays.asList(0.0, b, c))},
                        {new ArrayList<>(Arrays.asList(c, 0.0, b))},
                        {new ArrayList<>(Arrays.asList(b, c, 0.0))},
                        {new ArrayList<>(Arrays.asList(0.0, 0.0, c))},
                        {new ArrayList<>(Arrays.asList(c, 0.0, 0.0))},
                        {new ArrayList<>(Arrays.asList(0.0, c, 0.0))},
                        {new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0))},


                }).iterator();
    }

    @DataProvider(name = "TrianglesWithTooLongSides")
    public Iterator<Object[]> createTriangleTooBigSides() {
        a = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, MAX_VALUE_FOR_RECTANGULAR);
        b = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, MAX_VALUE_FOR_RECTANGULAR);
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
        a = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, MAX_VALUE_FOR_RECTANGULAR / 2);
        b = 1.5 * a;
        c = 2 * a;
        return Arrays.asList(new Object[][]
                {
                        {new ArrayList<Double>(Arrays.asList(a, b, c))},
                        {new ArrayList<Double>(Arrays.asList(c, a, b))},
                        {new ArrayList<Double>(Arrays.asList(b, c, a))},
                }).iterator();
    }

    @DataProvider(name = "RectangularTriangleGenerator")
    public Iterator<Object[]> createRetTriangle() {
        a = ThreadLocalRandom.current().nextDouble(1, MAX_VALUE_FOR_RECTANGULAR);
        b = ThreadLocalRandom.current().nextDouble(1, MAX_VALUE_FOR_RECTANGULAR);
        c = Math.sqrt(Math.pow(a, EXPONENT) + Math.pow(b, EXPONENT));
        return Arrays.asList(new Object[][]
                {
                        {new ArrayList<Double>(Arrays.asList(a, b, c))},
                        {new ArrayList<Double>(Arrays.asList(c, a, b))},
                        {new ArrayList<Double>(Arrays.asList(b, c, a))},
                }).iterator();
    }

    @DataProvider(name = "RectangularAndIsoscelesTriangleGenerator")
    public Iterator<Object[]> createRetIsoTriangle() {
        a = b = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, 500);
        c = Math.sqrt(Math.pow(a, EXPONENT) + Math.pow(b, EXPONENT));
        return Arrays.asList(new Object[][]
                {
                        {new ArrayList<Double>(Arrays.asList(a, b, c))},
                        {new ArrayList<Double>(Arrays.asList(c, a, b))},
                        {new ArrayList<Double>(Arrays.asList(b, c, a))},
                }).iterator();
    }

    @DataProvider(name = "IsoscelesTriangleGenerator")
    public Iterator<Object[]> createIsoTriangle() {
        a = b = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, 1000);
        c = a + 0.0000000000001;
        return Arrays.asList(new Object[][]
                {
                        {new ArrayList<Double>(Arrays.asList(a, b, c))},
                        {new ArrayList<Double>(Arrays.asList(c, a, b))},
                        {new ArrayList<Double>(Arrays.asList(b, c, a))},
                }).iterator();
    }

    @DataProvider(name = "EquilateralTriangleGenerator")
    public Iterator<Object[]> createEquTriangle() {
        a = b = c = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, 1000);

        return Arrays.asList(new Object[][]
                {
                        {new ArrayList<Double>(Arrays.asList(a, b, c))},
                        {new ArrayList<Double>(Arrays.asList(c, a, b))},
                        {new ArrayList<Double>(Arrays.asList(b, c, a))},
                }).iterator();
    }

    @Test(dataProvider = "OrdinaryTriangleGenerator")
    public void TriangleConstructor_Positive(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertEquals(triangleForTest.getMessage(), "", "Error,message is not empty");
    }

    @Test(dataProvider = "RectangularTriangleGenerator")
    public void TriangleConstructor_Re_Positive(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertEquals(triangleForTest.getMessage(), "", "Error,message is not empty");
    }

    @Test(dataProvider = "RectangularAndIsoscelesTriangleGenerator")
    public void TriangleConstructor_ReAndIso_Positive(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertEquals(triangleForTest.getMessage(), "", "Error,message is not empty");
    }

    @Test(dataProvider = "IsoscelesTriangleGenerator")
    public void TriangleConstructor_Is_Positive(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertEquals(triangleForTest.getMessage(), "", "Error,message is not empty");
    }

    @Test(dataProvider = "EquilateralTriangleGenerator")
    public void TriangleConstructor_Eq_Positive(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertEquals(triangleForTest.getMessage(), "", "Error,message is not empty");
    }

    @Test(dataProvider = "RectangularTriangleGenerator")
    public void CheckTriangle_Rectangular(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertTrue(triangleForTest.checkTriangle());
        Assert.assertEquals(triangleForTest.getMessage(), "");
    }

    @Test(dataProvider = "RectangularAndIsoscelesTriangleGenerator")
    public void CheckTriangle_ReAndIso_Positive(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertTrue(triangleForTest.checkTriangle());
        Assert.assertEquals(triangleForTest.getMessage(), "");
    }

    @Test(dataProvider = "IsoscelesTriangleGenerator")
    public void CheckTriangle_Iso_Positive(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertTrue(triangleForTest.checkTriangle());
        Assert.assertEquals(triangleForTest.getMessage(), "");
    }

    @Test(dataProvider = "EquilateralTriangleGenerator")
    public void CheckTriangle_Eq_Positive(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertTrue(triangleForTest.checkTriangle());
        Assert.assertEquals(triangleForTest.getMessage(), "");
    }

    @Test(dataProvider = "OrdinaryTriangleGenerator")
    public void CheckTriangle_Positive(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertTrue(triangleForTest.checkTriangle());
        Assert.assertEquals(triangleForTest.getMessage(), "");
    }

    @Test(dataProvider = "RectangularTriangleGenerator")
    public void TriangleType_RE(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertEquals(((triangleForTest.detectTriangle()) | (trTypes.get(3))), (int) trTypes.get(3));
    }

    @Test(dataProvider = "EquilateralTriangleGenerator")
    public void TriangleType_EQ(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertEquals((triangleForTest.detectTriangle()) | (trTypes.get(0)) | (trTypes.get(1)), (int) trTypes.get(0) | (trTypes.get(1))); // равносторонний

    }

    @Test(dataProvider = "IsoscelesTriangleGenerator")
    public void TriangleType_IS(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertEquals((triangleForTest.detectTriangle()) | (trTypes.get(1)), (int) trTypes.get(1));

    }

    @Test(dataProvider = "OrdinaryTriangleGenerator")
    public void TriangleType_OR(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        if (triangleForTest.detectTriangle() == 0) {
            Assert.assertEquals(triangleForTest.detectTriangle(), (int) trTypes.get(2));// обычный

        }
    }

    @Test(dataProvider = "RectangularAndIsoscelesTriangleGenerator")
    public void TriangleType_RecAndIso(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertEquals(triangleForTest.detectTriangle() | (trTypes.get(1)) | (trTypes.get(3)), (int) trTypes.get(1) | (trTypes.get(3)));


    }

    @Test(dataProvider = "OrdinaryTriangleGenerator")
    public void GetSquare_PositiveTest(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        double p = (sides.get(0) + sides.get(1) + sides.get(2)) / 2;
        Assert.assertEquals(triangleForTest.getSquare(), (Math.sqrt(p * (p - sides.get(0)) * (p - sides.get(1)) * (p - sides.get(2)))));
    }

}
