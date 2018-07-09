package triangle;

import org.junit.rules.ExpectedException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Double.NaN;
import static org.testng.Assert.fail;

@Test
public class EquivalentClasses {
    private static final double EXPONENT = 2.0;
    private final int TR_EQUILATERAL = 1; // равносторонний
    private final int TR_ISOSCELES = 2;   // равнобедренный
    private final int TR_ORDYNARY = 4;    // обычный
    private final int TR_RECTANGULAR = 8; // прямоугольный
    private ArrayList<Integer> trTypes = new ArrayList<>(Arrays.asList(TR_EQUILATERAL, TR_ISOSCELES, TR_ORDYNARY, TR_RECTANGULAR));
    private Triangle triangleForTest;
    private double a, b, c;
    private String x, y, z;
    //    private final double MIN_VALUE_FOR_RECTANGULAR_ERROR = (Math.sqrt(Double.MAX_VALUE))+.00000000000000008E154);
    //получения квадрата этого значения и ниже не приведет к получению infinity в проверке по теореме Пифагора
    private final double MAX_VALUE_FOR_RECTANGULAR = (Math.sqrt(Double.MAX_VALUE)) + 0.00000000000000007E154;
    //Сложение двух и более таких сторон приведет к получению Infinity в Check Triangle
//    private final double MIN_FOR_CHECK_TRIANGLE_ERROR = Double.MAX_VALUE/2+0.000000000000001E307;
//    // сложение трех сторон больших чем эти три значения даст infinity;
    private final double MAX_VALUE_FOR_GETSQUARE_METHOD = (Double.MAX_VALUE / 3) - 0.000000000000001E307;
    //получения квадрата этого значения и ниже не приведет к получению infinity в проверке по теореме Пифагора

    @DataProvider(name = "TrianglesInfinitiesAndNanAsSides")
    public Iterator<Object[]> createTriangleInfinities() {
        a = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, MAX_VALUE_FOR_RECTANGULAR);
        b = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, MAX_VALUE_FOR_RECTANGULAR);
        c = Double.POSITIVE_INFINITY;
        return Arrays.asList(new Object[][]
                {
                        {new ArrayList<>(Arrays.asList(a, b, NaN))},
                        {new ArrayList<>(Arrays.asList(NaN, a, b))},
                        {new ArrayList<>(Arrays.asList(b, NaN, a))},
                        {new ArrayList<>(Arrays.asList(a, NaN, NaN))},
                        {new ArrayList<>(Arrays.asList(NaN, a, NaN))},
                        {new ArrayList<>(Arrays.asList(NaN, NaN, a))},
                        {new ArrayList<>(Arrays.asList(NaN, NaN, NaN))},
                        {new ArrayList<>(Arrays.asList(c, c, c))},
                        {new ArrayList<>(Arrays.asList(a, c, c))},
                        {new ArrayList<>(Arrays.asList(c, a, c))},
                        {new ArrayList<>(Arrays.asList(c, c, a))},
                        {new ArrayList<>(Arrays.asList(a, b, c))},
                        {new ArrayList<>(Arrays.asList(a, b, c))},
                        {new ArrayList<>(Arrays.asList(c, a, b))},
                }).iterator();
    }

    @DataProvider(name = "TriangleDoubleOverflowInDetectTriangle")
    public Iterator<Object[]> createTriangleDetectTriangleOverflow() {
        b = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, MAX_VALUE_FOR_RECTANGULAR);
        b = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, MAX_VALUE_FOR_RECTANGULAR);
        ///получения квадрата этого значения приведет к получению infinity,
        c = Double.MAX_VALUE;

        return Arrays.asList(new Object[][]
                {
                        {new ArrayList<>(Arrays.asList(a, b, c))},
                        {new ArrayList<>(Arrays.asList(c, a, b))},
                        {new ArrayList<>(Arrays.asList(b, c, a))},
                        {new ArrayList<>(Arrays.asList(a, c, c))},
                        {new ArrayList<>(Arrays.asList(c, a, c))},
                        {new ArrayList<>(Arrays.asList(c, c, a))},
                        {new ArrayList<>(Arrays.asList(c, c, c))},
                }).iterator();
    }

    @DataProvider(name = "TriangleDoubleOverflowInSquareCount")
    public Iterator<Object[]> createTriangleSquareOverflow() {
        b = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, MAX_VALUE_FOR_RECTANGULAR);
        b = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, MAX_VALUE_FOR_RECTANGULAR);
        ///получения квадрата этого значения  приведет к получению infinity,
        c = Double.MAX_VALUE;

        return Arrays.asList(new Object[][]
                {
                        {new ArrayList<>(Arrays.asList(a, b, c))},
                        {new ArrayList<>(Arrays.asList(c, a, b))},
                        {new ArrayList<>(Arrays.asList(b, c, a))},
                        {new ArrayList<>(Arrays.asList(a, c, c))},
                        {new ArrayList<>(Arrays.asList(c, a, c))},
                        {new ArrayList<>(Arrays.asList(c, c, a))},
                        {new ArrayList<>(Arrays.asList(c, c, c))},
                }).iterator();
    }


    @DataProvider(name = "OrdinaryTriangleGenerator")
    public Iterator<Object[]> ccreateOrdTriangle() {
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

    @DataProvider(name = "RectangularTriangleGenerator")
    public Iterator<Object[]> createRetTriangle() {
        a = ThreadLocalRandom.current().nextDouble(1, 1000);
        b = ThreadLocalRandom.current().nextDouble(1, 1000);
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
        c = a * a + b * b;
        return Arrays.asList(new Object[][]
                {
                        {new ArrayList<Double>(Arrays.asList(a, b, c))},
                        {new ArrayList<Double>(Arrays.asList(c, a, b))},
                        {new ArrayList<Double>(Arrays.asList(b, c, a))},
                }).iterator();
    }

    @DataProvider(name = "IsoscelesTriangleGenerator")
    public Iterator<Object[]> createIsoTriangle() {
        a = b = ThreadLocalRandom.current().nextDouble(2 * Double.MIN_VALUE, 1000);
        c = a + Double.MIN_VALUE;
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

    @DataProvider(name = "TriangleForCalculationAccuracyCheck")
    public Iterator<Object[]> CreateTriForAcuCheck() {
        a = b = MAX_VALUE_FOR_RECTANGULAR;               //Проеверям максимальную величину, при которой подсчет по методу
        c = Double.MIN_VALUE;                            //пифагора не дает Infinity  в типе double и минимальную величину.
        // проверка должна пройти успешно, а тип установлен,как равнобедренный
        return Arrays.asList(new Object[][]
                {
                        {new ArrayList<Double>(Arrays.asList(a, b, c))},
                        {new ArrayList<Double>(Arrays.asList(c, a, b))},
                        {new ArrayList<Double>(Arrays.asList(b, c, a))},
                }).iterator();
    }

    @Test(dataProvider = "TrianglesInfinitiesAndNanAsSides")                  // проваливаем проверки, не указан тип
    // исключения при бесконечности и NaN в кач-ве стороны
    public void CheckTriangle_InfAndNan(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertFalse(triangleForTest.checkTriangle());
        Assert.assertEquals(triangleForTest.getMessage(), "Infinity or Nan As side");
    }

    @Test(dataProvider = "TriangleDoubleOverflowInDetectTriangle")
    public void checkTriangle_InfAtRectangular(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        triangleForTest.checkTriangle();
        fail("Infinity,when trying Math.pow a side");  // проваливаем проверки, не указан тип
        // исключения или сообщения при бесконечности при возведениии в кадрат стороны
    }

    @Test(dataProvider = "TriangleForCalculationAccuracyCheck")                  // проваливаем проверки, не указан тип
    // исключения при бесконечности и NaN в кач-ве стороны
    public void CheckTriangle_Calc_Accuracy(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertTrue(triangleForTest.checkTriangle());
        Assert.assertEquals(triangleForTest.getMessage(), "");
    }

    @Test(dataProvider = "TrianglesInfinitiesAndNanAsSides")                 // проваливаем проверки, не указан тип
    public void detectTriangle_InfAndNan(ArrayList<Double> sides) {                  // исключения при бесконечности и NaN в кач-ве стороны
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        triangleForTest.detectTriangle();
        fail("Infinity or Nan As side");
    }


    @Test(dataProvider = "TriangleDoubleOverflowInDetectTriangle")
    public void detectTriangle_InfAtRectangular(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        triangleForTest.detectTriangle();
        fail("Infinity or Nan As side");// проваливаем проверки, не указан тип
                                           // исключения или сообщения при бесконечности при возведениии в квадрат стороны
    }

    @Test(dataProvider = "TriangleForCalculationAccuracyCheck")                  // проверка, введена
                                                                                 // максимально возможная и минимально возможная сторона
    public void detectTriangle_Calc_Accuracy(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        Assert.assertEquals(triangleForTest.detectTriangle(), 2);
    }


    @Test(dataProvider = "TrianglesInfinitiesAndNanAsSides")                  // проваливаем проверки, не указан тип
                                                                              // исключения при бесконечности и NaN в кач-ве стороны
    public void getSquare_InfAndNan(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        triangleForTest.getSquare();
        fail("Infinity or Nan As side");
    }

    @Test(dataProvider = "TriangleDoubleOverflowInDetectTriangle")
    // проваливаем проверки, не указан тип
    // исключения при бесконечности и NaN в кач-ве стороны
    public void getSquare_InfAtSquare(ArrayList<Double> sides) {
        triangleForTest = new Triangle(sides.get(0), sides.get(1), sides.get(2));
        triangleForTest.getSquare();
        fail("Infinity or Nan As side");
    }
}
