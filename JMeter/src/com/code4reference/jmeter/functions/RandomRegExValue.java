package com.code4reference.jmeter.functions;

import java.lang.Math;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

public class RandomRegExValue extends AbstractFunction {

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__RandomRegExValue";
    private static final String MATCH_NR = "_matchNr";
    private static final int MAX_PARAM_COUNT = 1;
    private static final int MIN_RANGE = 1;
    private static final Logger log = LoggingManager.getLoggerForClass();
    private CompoundVariable varName;

    static {
        desc.add("Regular expression variable name");
    }
   

    /**
     * No-arg constructor.
     */
    public RandomRegExValue() {
        super();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized String execute(SampleResult previousResult, Sampler currentSampler)
            throws InvalidVariableException {
        JMeterVariables vars = getVariables();
        String res = varName.execute().trim();

        if (vars != null) {
             int maxRange = Integer.valueOf(vars.get(res+MATCH_NR));

            if (maxRange == 0) {
                log.info(res+" is not created in the previous Regular expression extractor");
                return "0";
            }
            else {
               int randomIndex =  maxRange + (int)(Math.random() * (maxRange - MIN_RANGE + 1));
                //The previous check will make sure that all regular expression variable are created.
                //And below expression will extract the variable value.
               return vars.get(String.format("%s_%d", res, randomIndex)); 
            }

        }

        return "0";

    }

    /** {@inheritDoc} */
    @Override
    public synchronized void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkMinParameterCount(parameters, MAX_PARAM_COUNT);
        Object[] values = parameters.toArray();
        varName = (CompoundVariable)values[0];
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
