package com.incode.towerofhanoi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.incode.towerofhanoi.TowerofhanoiSolver;

class TowerofhanoiSolverTest {

	@Test
	void test() {
		for (int n = 3; n < 8; n++) {
			var solver = new TowerofhanoiSolver(n);
			
			var result = solver.solve();
			
			assertTrue(result.size() == Math.pow(2, n));
		}
	}
}
