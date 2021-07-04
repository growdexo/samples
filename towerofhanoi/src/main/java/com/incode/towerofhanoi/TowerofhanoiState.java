package com.incode.towerofhanoi;

import java.util.ArrayList;
import java.util.Stack;

public class TowerofhanoiState {
    private final ArrayList<Stack<Integer>> stacks;
    private final String instruction;

    public TowerofhanoiState(ArrayList<Stack<Integer>> stacks, String instruction) {
    	this.stacks = stacks;
        this.instruction = instruction;
    }

	public String getInstruction() {
		return instruction;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Stack<Integer>> getStacks() {
		// Return a copy of the stacks
		ArrayList<Stack<Integer>> newList = new ArrayList<Stack<Integer>>();
		
		for (var stack : this.stacks) {
			newList.add((Stack<Integer>) stack.clone());
		}
		
		return newList;
	}
}
