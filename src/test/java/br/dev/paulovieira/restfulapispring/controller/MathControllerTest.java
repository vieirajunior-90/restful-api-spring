package br.dev.paulovieira.restfulapispring.controller;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.*;

@SpringBootTest
class MathControllerTest {

    @InjectMocks
    private MathController mathController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Nested
    @DisplayName("Test sum method")
    class SumMethod {

        @Test
        @DisplayName("Should return 10 when sum 5 and 5")
        void shouldReturn10WhenSum5And5() {
            Double result = mathController.sum("5", "5");
            assertEquals(10, result);
        }

        @Test
        @DisplayName("Should return an error when any parameter is not numeric")
        void shouldReturnAnErrorWhenAnyParameterIsNotNumeric() {
            assertThrows(UnsupportedOperationException.class, () -> mathController.sum("5", "A"));
            assertThrows(UnsupportedOperationException.class, () -> mathController.sum("A", "5"));
            assertThrows(UnsupportedOperationException.class, () -> mathController.sum("A", "A"));
        }
    }

    @Nested
    @DisplayName("Test sub method")
    class SubMethod {

        @Test
        @DisplayName("Should return 10 when sub 15 and 5")
        void shouldReturn10WhenSubtract15And5() {
            Double result = mathController.sub("15", "5");
            assertEquals(10, result);
        }

        @Test
        @DisplayName("Should return an error when any parameter is not numeric")
        void shouldReturnAnErrorWhenAnyParameterIsNotNumeric() {
            assertThrows(UnsupportedOperationException.class, () -> mathController.sub("5", "A"));
            assertThrows(UnsupportedOperationException.class, () -> mathController.sub("A", "5"));
            assertThrows(UnsupportedOperationException.class, () -> mathController.sub("A", "A"));
        }
    }

    @Nested
    @DisplayName("Test multiply method")
    class MultiMethod {

        @Test
        @DisplayName("Should return 50 when multiply 10 times 5")
        void shouldReturn50WhenMultiply50And10() {
            Double result = mathController.mult("10", "5");
            assertEquals(50, result);
        }

        @Test
        @DisplayName("Should return an error when any parameter is not numeric")
        void shouldReturnAnErrorWhenAnyParameterIsNotNumeric() {
            assertThrows(UnsupportedOperationException.class, () -> mathController.mult("5", "A"));
            assertThrows(UnsupportedOperationException.class, () -> mathController.mult("A", "5"));
            assertThrows(UnsupportedOperationException.class, () -> mathController.mult("A", "A"));
        }
    }

    @Nested
    @DisplayName("Test divide method")
    class DivideMethod {

        @Test
        @DisplayName("Should return 2 when divide 10 by 5")
        void shouldReturn2WhenDivide10By5() {
            Double result = mathController.divide("10", "5");
            assertEquals(2, result);
        }

        @Test
        @DisplayName("Should return an error when any parameter is not numeric")
        void shouldReturnAnErrorWhenAnyParameterIsNotNumeric() {
            assertThrows(UnsupportedOperationException.class, () -> mathController.divide("5", "A"));
            assertThrows(UnsupportedOperationException.class, () -> mathController.divide("A", "5"));
            assertThrows(UnsupportedOperationException.class, () -> mathController.divide("A", "A"));
        }

        @Test
        @DisplayName("Should return an error when divide by zero")
        void shouldReturnAnErrorWhenDivideByZero() {
            assertThrows(UnsupportedOperationException.class, () -> mathController.divide("5", "0"));
        }
    }

    @Nested
    @DisplayName("Test average method")
    class AverageMethod {

        @Test
        @DisplayName("Should return 20 when the average were made with 10 and 30")
        void shouldReturn20WhenTheNumbersWere10And30() {
            Double result = mathController.average("10", "30");
            assertEquals(20, result);
        }

        @Test
        @DisplayName("Should return an error when any parameter is not numeric")
        void shouldReturnAnErrorWhenAnyParameterIsNotNumeric() {
            assertThrows(UnsupportedOperationException.class, () -> mathController.average("5", "A"));
            assertThrows(UnsupportedOperationException.class, () -> mathController.average("A", "5"));
            assertThrows(UnsupportedOperationException.class, () -> mathController.average("A", "A"));
        }
    }

    @Nested
    @DisplayName("Test square root method")
    class SquareRootMethod {

        @Test
        @DisplayName("Should return 10 when the square root of 100")
        void shouldReturn10WhenTheSquareRootOf100() {
            Double result = mathController.squareRoot("100");
            assertEquals(10, result);
        }

        @Test
        @DisplayName("Should return an error when any parameter is not numeric")
        void shouldReturnAnErrorWhenAnyParameterIsNotNumeric() {
            assertThrows(UnsupportedOperationException.class, () -> mathController.squareRoot("A"));
        }

        @Test
        @DisplayName("Should return an error when the number is negative")
        void shouldReturnAnErrorWhenTheNumberIsNegative() {
            assertThrows(UnsupportedOperationException.class, () -> mathController.squareRoot("-10"));
        }
    }
}