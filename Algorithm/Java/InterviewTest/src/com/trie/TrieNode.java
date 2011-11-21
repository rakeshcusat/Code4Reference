package com.trie;

public class TrieNode {

	public final static int  NODE_SIZE = 27;

	private TrieNode[] 	nodes;
	private int 		nodeLevel;
	private boolean		isWord;
	private char 		nodeChar;
	
	
	public boolean isWord() {
		return isWord;
	}
	public void setWord(boolean isWord) {
		this.isWord = isWord;
	}
	public char getNodeChar() {
		return nodeChar;
	}
	public void setNodeChar(char nodeChar) {
		this.nodeChar = nodeChar;
	}
	public int getNodeLevel() {
		return nodeLevel;
	}
	public void setNodeLevel(int nodeLevel) {
		this.nodeLevel = nodeLevel;
	}
	
	public TrieNode( char nodeChar,int nodeLevel){
		this.nodeLevel 	= nodeLevel;
		nodes 			= new TrieNode[NODE_SIZE];
		this.nodeChar 	= nodeChar;
		this.isWord 	= false;
	}
	
	public TrieNode insertNode(char character){
		
		TrieNode tempNode=null;
		int index = character - 'a';
		
		if(index >= 0 && index <NODE_SIZE){
			
			if((tempNode =nodes[index])!= null){
				//If node is already present
				return nodes[index];
			}else{
				tempNode =  new TrieNode(character,this.nodeLevel+1);
				nodes[index] = tempNode;
			System.out.println("Inserted node : "+character+" @"+this.nodeLevel);
			}
		}

		return tempNode;
	}
	
	public boolean isNodePresent(char character){
		int index = character - 'a';
		if(index <0 || index >= NODE_SIZE){
			return false;
		}
		if(nodes[index]!=null){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * Fetch the node present for particular character.
	 * 
	 * @param character character location where we are looking for the TrieNode.
	 * @return	returns the node which is present at the particular character location.
	 */
	
	public TrieNode getNode(char character){
		int index = character - 'a';
		if(index <0 || index >= NODE_SIZE){
			return null;
		}
		return nodes[index];
	}
	
	
	public void print(){
		System.out.print("Characters present at level "+nodeLevel+": ");
		for(int index=0;index<NODE_SIZE;index++){

			if(nodes[index]!=null){
				System.out.print(index+'a'+" ");
			}
		}
	}
}
