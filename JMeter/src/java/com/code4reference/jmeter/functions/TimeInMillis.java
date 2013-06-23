package com.code4reference.jmeter.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Calendar;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

public class TimeInMillis extends AbstractFunction {

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__TimeInMillis";
    private static final int MAX_PARAM_COUNT = 1;
    private static final int MIN_PARAM_COUNT = 0;
    private static final Logger log = LoggingManager.getLoggerForClass();
    private Object[] values;

    static {
        desc.add("(Optional)Pass the milliseconds that should be added/subtracted from current time.");
    }
   

    /**
     * No-arg constructor.
     */
    public TimeInMillis() {
        super();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized String execute(SampleResult previousResult, Sampler currentSampler)
            throws InvalidVariableException {
        //JMeterVariables vars = getVariables();
        Calendar cal = Calendar.getInstance();

        if (values.length == 1 ) { //If user has provided offset value then adjust the time.
            log.info("Got one paramenter");
            try {
                Integer offsetTime =  new Integer(((CompoundVariable) values[0]).execute().trim());
                cal.add(Calendar.MILLISECOND, offsetTime);
            } catch (Exception e) { //In case user pass invalid parameter.
                throw new InvalidVariableException(e);
            }           
        }

        return String.valueOf(cal.getTimeInMillis());
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkParameterCount(parameters, MIN_PARAM_COUNT, MAX_PARAM_COUNT);
        values = parameters.toArray();
    }

    /** {@inheritDoc} */
    @Override
    public String getReferenceKey() {
        return KEY;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }
}
