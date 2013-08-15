/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodcu;

import javafx.util.StringConverter;

/**
 * @author usta
 */
public class DoubleToStringConverter extends StringConverter<Double> {

    @Override
    public String toString(Double t) {
        return Double.toString(t);
    }

    @Override
    public Double fromString(String string) {
        return Double.valueOf(string);
    }


}
