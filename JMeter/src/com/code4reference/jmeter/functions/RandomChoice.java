package com.code4reference.jmeter.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

public class RandomChoice extends AbstractFunction {

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__RandomChoice";
    private static final int MIN_PARAM_COUNT = 2;
    private static final Logger log = LoggingManager.getLoggerForClass();
    private static final Random random = new Random(System.currentTimeMillis());    
    private Object[] values;

    static {
        desc.add("comma separtated choices. e.g __RandomChoice(Apple, Mango)");
    }
   

    /**
     * No-arg constructor.
     */
    public RandomChoice() {
    }

    /** {@inheritDoc} */
    @Override
    public synchronized String execute(SampleResult previousResult, Sampler currentSampler)
            throws InvalidVariableException {
        int index = random.nextInt(values.length - 1);
        log.debug(String.format("total # options : %d, random index of choice : %d", values.length, index));
        String choice = ((CompoundVariable) values[index]).execute();
        return choice;
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkMinParameterCount(parameters, MIN_PARAM_COUNT);
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
