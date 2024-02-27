package edu.jsu.mcis.cs408.calculator;

import android.util.Log;

import java.math.BigDecimal;

public class DefaultModel extends AbstractModel {

    public static final String TAG = "DefaultModel";

    /*
     * This is a simple implementation of an AbstractModel which encapsulates
     * two number fields(lhs and rhs), a calculator state property(state),
     * an operator property(op), and a string buffer which (in this example)
     * are reflected in the View as BigDecimals, state enums, or a StringBuilder.
     */

    private MainActivity.CalculatorState state;
    private BigDecimal lhs;
    private BigDecimal rhs;
    private MainActivity.Operator op;
    private StringBuilder buffer; //buffer for any new digits
    private BigDecimal result;


    /*
     * Initialize the model elements to either known default values, or restore
     * from a previous state.  We use the setter methods instead of changing the
     * values directly so that these changes are properly announced to the
     * Controller, and so that the Views can be updated accordingly.
     */

    //
    public void initDefault() {

        //Default
        setState(MainActivity.CalculatorState.CLEAR);
        setOp(MainActivity.Operator.NONE);
        setLhs(new BigDecimal("0"));
        setRhs(new BigDecimal("0"));
        setBuffer(new StringBuilder());


    }

    /*
     * Simple getter methods
     */

    public MainActivity.CalculatorState getState() { return state; }
    public MainActivity.Operator getOp() { return op; }
    public BigDecimal getLhs() { return lhs; }
    public BigDecimal getRhs() { return rhs; }
    public StringBuilder getBuffer() { return buffer; }
    public BigDecimal getResult() { return result; }

    /*
     * Property Setters. While they all change their values, only the result and
     * LHS and RHS fire PropertyChange events, as they are the only properties
     * connected to physical elements in the views. Any registered AbstractController
     * subclasses will receive this event, and will propagate it to all registered
     * Views so that they can update themselves accordingly.
     */

    public void setState(MainActivity.CalculatorState newState) {

        MainActivity.CalculatorState oldState = this.state;
        this.state = newState;

        Log.i(TAG, "Calculator State Change: From " + oldState + " to " + newState);

        //firePropertyChange(DefaultController.ELEMENT_STATE, oldState, newState);

    }

    public void setOp(MainActivity.Operator newOp) {

        MainActivity.Operator oldOp = this.op;
        this.op = newOp;

        Log.i(TAG, "Operator Change: From " + oldOp + " to " + newOp);

        //firePropertyChange(DefaultController.ELEMENT_OP, oldOp, newOp);

    }

    public void setLhs(BigDecimal newLhs) {

        BigDecimal oldLhs = this.lhs;
        this.lhs = newLhs;

        Log.i(TAG, "LHS Change: From " + oldLhs + " to " + newLhs);

        firePropertyChange(DefaultController.ELEMENT_LHS, oldLhs, newLhs);

    }

    public void setRhs(BigDecimal newRhs) {

        BigDecimal oldRhs = this.rhs;
        this.rhs = newRhs;

        Log.i(TAG, "RHS Change: From " + oldRhs + " to " + newRhs);

        firePropertyChange(DefaultController.ELEMENT_RHS, oldRhs, newRhs);

    }

    public void setBuffer(StringBuilder newDigit) {

        StringBuilder oldDigit = this.buffer;
        this.buffer = newDigit;

        Log.i(TAG, "Buffer Change: From " + oldDigit + " to " + newDigit);

        //firePropertyChange(DefaultController.ELEMENT_BUFFER, oldDigit, newDigit);

    }

    public void setResult(BigDecimal newResult) {

        BigDecimal oldResult = this.result;
        this.result = newResult;

        Log.i(TAG, "Result Change: From " + oldResult + " to " + newResult);

        firePropertyChange(DefaultController.ELEMENT_RESULT, oldResult, newResult);

    }
}
