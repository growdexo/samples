package com.incode.towerofhanoi;

import java.util.ArrayList;
import java.util.Stack;

import com.incode.towerofhanoi.TowerofhanoiState;

public class TowerofhanoiSolver {
	private ArrayList<TowerofhanoiState> states = new ArrayList<TowerofhanoiState>();
	private final int n;
	
	public TowerofhanoiSolver(int n) {
		this.n = n;
	}
	
	public ArrayList<TowerofhanoiState> solve() {
		ArrayList<Stack<Integer>> stacks = new ArrayList<Stack<Integer>>();
		
		// Build First stack with all of the disks
		var firstStack = new Stack<Integer>();
		for(int i = n; i > 0; i--) {
			firstStack.push(i);
		}
		
		// Add stacks to array list
		stacks.add(0, firstStack);
		stacks.add(1, new Stack<Integer>());
		stacks.add(2, new Stack<Integer>());
		
		TowerofhanoiState intialState  = new TowerofhanoiState(
				stacks, "Initial State");
		
		this.states.add(intialState);
		
		solve(this.n, 0, 2, 1);
		
		return states;
	}
	
	private void solve(int n, int fromStack, int toStack, int auxStack) {
		
		if (n == 1) {
			var lastState = states.get(states.size() - 1);
			var stacks = lastState.getStacks();
			
			var disk = stacks.get(fromStack).pop();
			
			var instruction = "Take disk " + (disk) + " from rod " + intToRod(fromStack) + 
					" to rod " + intToRod(toStack)
					+ ".";

			stacks.get(toStack).push(disk);

			var nextState = new TowerofhanoiState(stacks, instruction);
			
			states.add(nextState);

			return;
		}
		
		solve(n-1, fromStack, auxStack, toStack);
		
		var lastState = states.get(states.size() - 1);
		var stacks = lastState.getStacks();
		

		var disk = stacks.get(fromStack).pop();
		stacks.get(toStack).push(disk);

		var instruction = "Take disk " + (n) + " from rod " + intToRod(fromStack) + 
				" to rod " + intToRod(toStack)
				+ ".";
		
		// Build next state and add to history
		var nextState = new TowerofhanoiState(stacks, instruction);
		
		states.add(nextState);
						
		solve(n-1, auxStack, toStack, fromStack);
	}
	
	private String intToRod(int i) {
		switch (i) {
		case 0:
			return "A";
		case 1: 
			return "B";
		default:
			return "C";
		}
	}
	
	public void printStacks(ArrayList<Stack<Integer>> list) {
		for (int i = 0; i < 3; i++) {
			System.out.print(intToRod(i) + ": ");
			System.out.println(list.get(i));
		}
	}
}
