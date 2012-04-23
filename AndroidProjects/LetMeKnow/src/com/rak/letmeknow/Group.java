package com.rak.letmeknow;

public class Group {
    private String event;
    private boolean state;

    public Group( String event, boolean state ) {
        this.event = event;
        this.state = state;
    }

    public String getEvent() {
	    return event;
    }

 
    public boolean getState() {
	    return state;
    }

	public void setEvent(String event) {
		this.event = event;
	}

	public void setState(boolean state) {
		this.state = state;
	}

}
