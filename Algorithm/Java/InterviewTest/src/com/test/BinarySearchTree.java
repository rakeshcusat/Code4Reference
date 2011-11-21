package com.test;

import java.util.Stack;

public class BinarySearchTree {
	Node root;
	
	public void makeMinHightTree(Integer[] arr){
		root = makeMinHightTree(arr,0,arr.length-1);
	}
	/**
	 * print in-order BST using iterative algorithm
	 */
	public void printIterInOrder(){
		Node node = root;
		Stack<Node> stack = new Stack<Node>();
		while(!stack.isEmpty() || node != null){
			if(node!=null){
				stack.push(node);
				node = node.getLeftNode();
			}else{
				node = stack.pop();
				System.out.print(node);
				node = node.getRightNode();
			}
		}
	}
	
	public void printIterPreOrder(){
		Stack<Node> stack = new Stack<Node>();
		Node node = root;
		stack.push(node);
		
		while(!stack.isEmpty()){
			node = stack.pop();
			if(node.hasRightNode()){
				stack.push(node.getRightNode());
			}
			if(node.hasLeftNode()){
				stack.push(node.getLeftNode());
			}
			node.print();
		}
	}
	
	/**
	 * Prints the Tree in in-order form by recursive method
	 */
	public void printRecInOrder(){
		if(root!=null){
			printRecInOrder(root);
		}
	}
	
	/**
	 * internal method for recursively prints binary search tree in In-order form
	 * @param node
	 */
	private void printRecInOrder(Node node){
		if(node!=null){
			printRecInOrder(node.getLeftNode());
			System.out.print(node);
			printRecInOrder(node.getRightNode());
			}
		
	}

	public Node makeMinHightTree(Integer[] arr,int start, int end){
		if(start>end){
			return null;
		}
		int mid = (start + end)/2;
		Node n = new Node(arr[mid]);
		
		n.setLeftNode(makeMinHightTree(arr, start, mid-1));
		n.setRightNode(makeMinHightTree(arr, mid+1, end));
		
		return n;
	}
	
	
	
}
