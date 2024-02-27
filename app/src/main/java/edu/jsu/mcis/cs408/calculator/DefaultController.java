package edu.jsu.mcis.cs408.calculator;

import java.math.BigDecimal;

public class DefaultController extends AbstractController
{

    /*
     * These static property names are used as the identifiers for the elements
     * of the Models and Views which may need to be updated.  These updates can
     * be a result of changes to the Model which must be reflected in the View,
     * or a result of changes to the View (in response to user input) which must
     * be reflected in the Model.
     */

    public static final String ELEMENT_STATE = "State";
    public static final String ELEMENT_LHS = "Lhs";
    public static final String ELEMENT_RHS = "Rhs";
    public static final String ELEMENT_OP = "Op";
    public static final String ELEMENT_BUFFER ="Buffer";
    public static final String ELEMENT_RESULT ="Result";

    /*
     * These are the change methods which correspond to the element properties.
     * It receives the new data for the Model, and invokes "setModelProperty()"
     * (inherited from AbstractController) so that the proper Model can be found
     * and updated properly.
     */

    public void changeElementState(MainActivity.CalculatorState state) { setModelProperty(ELEMENT_STATE, state); }

    public void changeElementLHS(BigDecimal lhs) {
        setModelProperty(ELEMENT_LHS, lhs);
    }

    public void changeElementRHS(BigDecimal rhs) {
        setModelProperty(ELEMENT_RHS, rhs);
    }

    public void changeElementOP(MainActivity.Operator op) {
        setModelProperty(ELEMENT_OP, op);
    }

    public void changeElementBuffer(StringBuilder digit) { setModelProperty(ELEMENT_BUFFER, digit); }

    public void changeElementResult(BigDecimal result) { setModelProperty(ELEMENT_RESULT, result); }

}
