package com.test;

public class Node {
	Integer 	data;
	Node 		leftNode;
	Node 		rightNode;
	boolean 	visited;
	
	public Node(Integer data){
		this.data = data;
	}

	public Integer getData() {
		return data;
	}

	public void setData(Integer data) {
		this.data = data;
	}
	public boolean hasLeftNode(){
		return leftNode != null;
	}
	public Node getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(Node leftNode) {
		this.leftNode = leftNode;
	}
	
	public boolean hasRightNode(){
		return rightNode != null;
	}
	public Node getRightNode() {
		return rightNode;
	}

	public void setRightNode(Node rightNode) {
		this.rightNode = rightNode;
	}
	
	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public void print(){
		System.out.print(data+" ");
	}
	public String toString(){
		return data+" ";
	}
}
