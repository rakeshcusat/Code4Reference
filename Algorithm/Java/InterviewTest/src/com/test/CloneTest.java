package com.test;

public class CloneTest implements Cloneable{
	private int value;
	String name;
	
	public CloneTest() {
		super();
	}
	
	public CloneTest(int value) {
		super();
		this.value = value;
	}

	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println("Clone is not supported by class");
			return this;
		}
	}
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CloneTest [value=" + value + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CloneTest other = (CloneTest) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	

}
