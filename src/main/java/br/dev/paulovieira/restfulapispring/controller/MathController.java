package br.dev.paulovieira.restfulapispring.controller;

import br.dev.paulovieira.restfulapispring.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/math")
public class MathController {

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) {

        NumberCheck.isNumeric(numberOne, numberTwo);

        return NumberConverter.convertToDouble(numberOne) + NumberConverter.convertToDouble(numberTwo);
    }

    @GetMapping("/subtraction/{numberOne}/{numberTwo}")
    public Double sub(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) {

        NumberCheck.isNumeric(numberOne, numberTwo);

        return NumberConverter.convertToDouble(numberOne) - NumberConverter.convertToDouble(numberTwo);
    }

    @GetMapping("/multiplication/{numberOne}/{numberTwo}")
    public Double mult(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) {

        NumberCheck.isNumeric(numberOne, numberTwo);

        return NumberConverter.convertToDouble(numberOne) * NumberConverter.convertToDouble(numberTwo);
    }

    @GetMapping("/divide/{numberOne}/{numberTwo}")
    public Double divide(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) {

        NumberCheck.isNumeric(numberOne, numberTwo);
        NumberCheck.isDivisibleByZero(numberTwo);

        return NumberConverter.convertToDouble(numberOne) / NumberConverter.convertToDouble(numberTwo);
    }

    @GetMapping("/average/{numberOne}/{numberTwo}")
    public Double average(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) {

        NumberCheck.isNumeric(numberOne, numberTwo);

        return (NumberConverter.convertToDouble(numberOne) + NumberConverter.convertToDouble(numberTwo)) / 2;
    }

    @GetMapping("/square-root/{numberOne}")
    public Double squareRoot(
            @PathVariable(value = "numberOne") String numberOne
    ) {

        NumberCheck.isNumeric(numberOne);
        NumberCheck.canSquareRoot(numberOne);

        return Math.sqrt(NumberConverter.convertToDouble(numberOne));
    }
}
