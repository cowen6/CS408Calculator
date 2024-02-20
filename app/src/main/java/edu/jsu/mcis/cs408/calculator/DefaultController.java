package edu.jsu.mcis.cs408.calculator;

//  !!!!!!!!!! UPDATE COMMENTS TO REFLECT THIS PROJECT !!!!!!!!!!

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

    public static final String ELEMENT_TEXT1_PROPERTY = "Text1";
    public static final String ELEMENT_TEXT2_PROPERTY = "Text2";
    // !!!!!!!!!! CHANGE/ADD VARIABLES/CONSTANTS TO REFLECT THIS PROJECT !!!!!!!!!!
    //Use BigDecimal types, rather than float or double
    public static final String ELEMENT_STATE_PROPERTY = "State";
    public static final String ELEMENT_LHS_PROPERTY = "LHS";
    public static final String ELEMENT_RHS_PROPERTY = "RHS";
    public static final String ELEMENT_OP_PROPERTY = "OP";

    /*
     * This is the change method which corresponds to ELEMENT_TEXT1_PROPERTY.
     * It receives the new data for the Model, and invokes "setModelProperty()"
     * (inherited from AbstractController) so that the proper Model can be found
     * and updated properly.
     */

    public void changeElementText1(String newText) {
        setModelProperty(ELEMENT_TEXT1_PROPERTY, newText);
    }

    /*
     * This is the change method which corresponds to ELEMENT_TEXT2_PROPERTY.
     */

    public void changeElementText2(String newText) {
        setModelProperty(ELEMENT_TEXT2_PROPERTY, newText);
    }

    // !!!!!!!!!! CHANGE/ADD METHODS TO REFLECT NEW VARIABLE/CONSTANTS !!!!!!!!!!

    public void changeElementState(MainActivity.CalculatorState state) {
        setModelProperty(ELEMENT_STATE_PROPERTY, state);
    }

    public void changeElementLHS(BigDecimal lhs) {
        setModelProperty(ELEMENT_LHS_PROPERTY, lhs);
    }

    public void changeElementRHS(BigDecimal rhs) {
        setModelProperty(ELEMENT_RHS_PROPERTY, rhs);
    }

    public void changeElementOP(MainActivity.Operator op) {
        setModelProperty(ELEMENT_OP_PROPERTY, op);
    }

}
