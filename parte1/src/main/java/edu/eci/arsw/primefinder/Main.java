package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		List<PrimeFinderThread> threads = new ArrayList<PrimeFinderThread>();
		Scanner scanner = new Scanner(System.in);
		Boolean isRunning = true;
		Object lock = new Object();
		int totalThreads = 5;
		int N = 30000;
		int initialValue = 0;
		int range = (N + totalThreads - 1) / totalThreads;
		for(int i = 0; i< totalThreads; i++){
			PrimeFinderThread thread;
			if(i== totalThreads-1){
				thread = new PrimeFinderThread(initialValue, N+1, lock, i);
			}else{
				thread = new PrimeFinderThread(initialValue, initialValue + range, lock, i);
			}
			initialValue += range;
			thread.start();
			threads.add(thread);

		}
		while (isRunning){
			try {
				Thread.sleep(5000); // Esperar 5 segundos
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int totalPrimes = 0;
			int totalThreadsAlive = 0;
			int threadPrimes;
			for (PrimeFinderThread thread : threads) {
				threadPrimes = thread.getPrimes().size();
				totalPrimes += threadPrimes;
				System.out.println("Thread " + thread.getThreadId() + ": " + threadPrimes );
				if(!thread.getAlive()){
					totalThreadsAlive+=1;
				}
			}
			System.out.println("Número de primos encontrados hasta el momento: " + totalPrimes + "," + totalThreadsAlive);
			if(totalThreadsAlive == totalThreads){
				isRunning = false;
			}
			if(isRunning){
				System.out.println("Presiona ENTER para reanudar...");
				String input = scanner.nextLine();
				while(isRunning && !input.equals("")){
					System.out.println("Presiona ENTER para reanudar...");
					input = scanner.nextLine();
				}
				synchronized (lock) {
					lock.notifyAll();
				}
			}
		}
	}
}

