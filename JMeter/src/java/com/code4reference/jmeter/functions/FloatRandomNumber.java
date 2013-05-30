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
import org.apache.jmeter.threads.JMeterVariables;

public class FloatRandomNumber extends AbstractFunction {
	private static final List<String> desc = new LinkedList<String>();
	private static final String KEY = "__FloatRandomNumber";
	private static final Random random = new Random(System.currentTimeMillis());

	static {
		desc.add("Returns the next pseudorandom, uniformly distributed float value between 0.0 and 1.0.");
	}
	private Object[] values;

	/**
	 * No-arg constructor.
	 */
	public FloatRandomNumber() {
        super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized String execute(SampleResult previousResult, Sampler currentSampler)
			throws InvalidVariableException {
		JMeterVariables vars = getVariables();
		
		Float randomFloat = random.nextFloat();
		String varName = null;
		
		if (values.length > 0) {
			varName = ((CompoundVariable) values[0]).execute().trim();
		}
		
		if (vars != null && varName != null && varName.length() > 0) {// vars will be null on TestPlan
			vars.put(varName, randomFloat.toString());
		}

		return randomFloat.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
		values = parameters.toArray();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getReferenceKey() {
		return KEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getArgumentDesc() {
		return desc;
	}
}
