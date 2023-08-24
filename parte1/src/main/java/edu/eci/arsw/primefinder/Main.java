package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<PrimeFinderThread> threads = new ArrayList<PrimeFinderThread>();
		int totalThreads = 3;
		int N = 30000000;
		int initialValue = 0;
		int range = N/totalThreads;
		for(int i = 0; i< totalThreads; i++){
			PrimeFinderThread thread;
			if(i== N-1){
				thread = new PrimeFinderThread(initialValue, N);
			}else{
				thread = new PrimeFinderThread(initialValue, initialValue + range);
			}
			initialValue += range;
			thread.start();
			threads.add(thread);

		}

	}
}
