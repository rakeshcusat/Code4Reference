package com.code4reference.jmeter.threads;

import java.awt.BorderLayout;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.LongProperty;
import org.apache.jmeter.threads.AbstractThreadGroup;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.AbstractThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

public class HostThreadGroupGui extends AbstractThreadGroupGui {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3310L;

	private static final Logger log = LoggingManager.getLoggerForClass();

	private LoopControlPanel loopPanel;

	private static final String THREAD_NAME = "Thread Field";

	private static final String RAMP_NAME = "Ramp Up Field";

	private static final String HOST_NAMES = "Host Names Field";

	private JTextField threadInput;

	private JTextField rampInput;

	private JTextField hostNamesInput;
	
	private JCheckBox delayedStart;
	
	
	public HostThreadGroupGui(){
		super();
		init();
		initGui();
	}
	@Override
	public String getLabelResource() {
		return this.getClass().getSimpleName();
	}
	public String getStaticLabel() {
		return "Host Thread Group";
	}

	@Override
	public TestElement createTestElement() {
		HostThreadGroup htg = new HostThreadGroup();
		modifyTestElement(htg);
		return htg;
	}

	@Override
	public void modifyTestElement(TestElement te) {
		log.info("ModifyTestElement() ");
		super.configureTestElement(te);
		if(te instanceof HostThreadGroup) {
			HostThreadGroup htg = (HostThreadGroup) te;
			htg.setSamplerController((LoopController) loopPanel.createTestElement());
			htg.setProperty(AbstractThreadGroup.NUM_THREADS, threadInput.getText());
			htg.setProperty(HostThreadGroup.RAMP_TIME, rampInput.getText());
	        htg.setProperty(HostThreadGroup.DELAYED_START, delayedStart.isSelected(), false);
	        htg.setProperty(HostThreadGroup.HOST_NAMES, hostNamesInput.getText());
		}
	}

	@Override
	public void configure(TestElement te) {
		super.configure(te);
		threadInput.setText(te.getPropertyAsString(AbstractThreadGroup.NUM_THREADS));
        rampInput.setText(te.getPropertyAsString(HostThreadGroup.RAMP_TIME));
        loopPanel.configure((TestElement) te.getProperty(AbstractThreadGroup.MAIN_CONTROLLER).getObjectValue());
        delayedStart.setSelected(te.getPropertyAsBoolean(HostThreadGroup.DELAYED_START));
        hostNamesInput.setText(te.getPropertyAsString(HostThreadGroup.HOST_NAMES));
	}

	private JPanel createControllerPanel() {
		loopPanel = new LoopControlPanel(false);
		LoopController looper = (LoopController) loopPanel.createTestElement();
		looper.setLoops(1);
		loopPanel.configure(looper);
		return loopPanel;
	}

	private void init(){
		// THREAD PROPERTIES
		VerticalPanel threadPropsPanel = new VerticalPanel();
		threadPropsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				JMeterUtils.getResString("thread_properties"))); // $NON-NLS-1$

		// NUMBER OF THREADS
		JPanel threadPanel = new JPanel(new BorderLayout(5, 0));

		JLabel threadLabel = new JLabel(JMeterUtils.getResString("number_of_threads")); // $NON-NLS-1$
		threadPanel.add(threadLabel, BorderLayout.WEST);

		threadInput = new JTextField(5);
		threadInput.setName(THREAD_NAME);
		threadLabel.setLabelFor(threadInput);
		threadPanel.add(threadInput, BorderLayout.CENTER);

		threadPropsPanel.add(threadPanel);

		// RAMP-UP
		JPanel rampPanel = new JPanel(new BorderLayout(5, 0));
		JLabel rampLabel = new JLabel(JMeterUtils.getResString("ramp_up")); // $NON-NLS-1$
		rampPanel.add(rampLabel, BorderLayout.WEST);

		rampInput = new JTextField(5);
		rampInput.setName(RAMP_NAME);
		rampLabel.setLabelFor(rampInput);
		rampPanel.add(rampInput, BorderLayout.CENTER);

		threadPropsPanel.add(rampPanel);

		// LOOP COUNT
		threadPropsPanel.add(createControllerPanel()); 

		// mainPanel.add(threadPropsPanel, BorderLayout.NORTH);
		// add(mainPanel, BorderLayout.CENTER);
		delayedStart = new JCheckBox(JMeterUtils.getResString("delayed_start")); // $NON-NLS-1$
        threadPropsPanel.add(delayedStart);

		// Host-Name
		JPanel hostNamesPanel = new JPanel(new BorderLayout(5, 0));
		JLabel hostNamesLabel = new JLabel("Host name(s):"); // $NON-NLS-1$
		hostNamesPanel.add(hostNamesLabel, BorderLayout.WEST);

		hostNamesInput = new JTextField(5);
		hostNamesInput.setName(HOST_NAMES);
		hostNamesLabel.setLabelFor(hostNamesInput);
		hostNamesPanel.add(hostNamesInput, BorderLayout.CENTER);

		threadPropsPanel.add(hostNamesPanel);

		VerticalPanel intgrationPanel = new VerticalPanel();
		intgrationPanel.add(threadPropsPanel);
		add(intgrationPanel, BorderLayout.CENTER);
	}
	@Override
    public void clearGui(){
        super.clearGui();
        initGui();
    }
	/**
	 * This method initializes the GUI field values.
	 */
    private void initGui(){
        threadInput.setText("1"); // $NON-NLS-1$
        rampInput.setText("1"); // $NON-NLS-1$
        loopPanel.clearGui();
        hostNamesInput.setText("");
        delayedStart.setSelected(false);
    }
}
