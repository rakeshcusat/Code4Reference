package com.trie;

import java.util.Stack;

public class TrieTree {

	TrieNode root;

	public TrieTree(){
		root= new TrieNode(' ',0 ); //Root node of trie.
	}
	/**
	 * This method insert the word in trie tree.
	 * @param word  word which has to be inserted in the trie data structure.
	 */
	public void insertWord(String word){

		char[] charWord = word.toCharArray();
		int wordLen = charWord.length;
		TrieNode currentNode = root;

		for(int index=0;index<wordLen;index++){
			currentNode=currentNode.insertNode(charWord[index]);			
		}		

		currentNode.setWord(true);
	}
	/**
	 * This method searches in the trie data structue. If word found then returns true otherwise false.
	 * @param word	Word which has to be searched in the trie data structure.
	 * @return	true if word is present otherwise false. 
	 */
	public boolean searchWord(String word){

		TrieNode 	currentNode = root; 
		int 		wordLen = word.length();

		for(int index=0;index<wordLen;index++){
			if((currentNode=currentNode.getNode(word.charAt(index)))== null)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * This method will suggest other word whose prefix matches with the given word.
	 * @param str
	 */
	public void printSuggestedWord(String word){
		int 		wordLen = word.length();
		int 		index;
		TrieNode 	currentNode = root;
		
		for(index=0;index<wordLen;index++){

			if((currentNode=currentNode.getNode(word.charAt(index)))== null)
			{
				System.out.println("Word is not existing");
				return;
			}
		}
		
		StringBuffer 	suggestedWord = new StringBuffer(word);
		int				suggestedWordLastIndex = 0;				
		Stack<TrieNode> stack = new Stack<TrieNode>();
		//boolean 		printed=false;
		TrieNode		tempNode;

		for(index =0;index<TrieNode.NODE_SIZE;index++){
			if((tempNode = currentNode.getNode((char)(index+'a')))!=null){
				stack.push(tempNode);
			}
		}

		while(!stack.isEmpty()){

			currentNode = stack.pop();
			suggestedWordLastIndex = suggestedWord.length()- 1;

			if(suggestedWordLastIndex > currentNode.getNodeLevel()){
				suggestedWord.setLength(currentNode.getNodeLevel()+1);
			}
			else if(suggestedWordLastIndex  == currentNode.getNodeLevel()){
				suggestedWord.append(currentNode.getNodeChar());
			}
			if(currentNode.isWord()){
				System.out.println(suggestedWord);
			}

			for(index =0;index<TrieNode.NODE_SIZE;index++){
				if((tempNode = currentNode.getNode((char)(index+'a')))!=null){
					stack.push(tempNode);
				}
			}
		}

	}

}
