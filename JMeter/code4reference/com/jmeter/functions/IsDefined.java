package code4reference.com.jmeter.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;

public class IsDefined extends AbstractFunction {

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__IsDefined";
    private CompoundVariable varName;
    private static final int MAX_PARAM_COUNT = 1
 
    static {
        desc.add("Variable name");
    }
   

    /**
     * No-arg constructor.
     */
    public IsDefined() {
    }

    /** {@inheritDoc} */
    @Override
    public synchronized String execute(SampleResult previousResult, Sampler currentSampler)
            throws InvalidVariableException {
        JMeterVariables vars = getVariables();
        String res = varName.execute().trim();

        if (vars != null) {
            Object var = vars.getObject(res);
            if (var != null) {
                return "1";
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
