package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{


	int a,b;


	private List<Integer> primes=new LinkedList<Integer>();
	private int id;
	private Object lock;
	private Boolean alive;

	public PrimeFinderThread(int a, int b, Object lock, int name) {
		super();
		this.alive = true;
		this.a = a;
		this.b = b;
		this.id = name;
		this.lock  = lock;
	}

	public void run(){
		long startTime = System.currentTimeMillis();
		for (int i=a;i<b;i++){
			if (isPrime(i)){
				primes.add(i);
			}else if(System.currentTimeMillis() - startTime >= 5000){
				synchronized (lock) {
					try {
						lock.wait();
						startTime = System.currentTimeMillis();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		this.alive = false;
	}

	boolean isPrime(int n) {
	    if (n%2==0) return false;
	    for(int i=3;i*i<=n;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}

	public List<Integer> getPrimes() {
		return primes;
	}

	public String getStringWhitPrimes(){
		return "Hilo " + id + " : " + primes.toString();
	}

	public Boolean getAlive() {
		return alive;
	}

	public int getThreadId(){
		return this.id;
	}
}
