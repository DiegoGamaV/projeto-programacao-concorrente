package CigaretteSmokers;

import java.util.concurrent.Semaphore;

public class Main {

	public static void main(String args[]) {
		Agent agent = new Agent();

		Smoker matchSmoker = new Smoker("match", agent);
		Smoker paperSmoker = new Smoker("paper", agent);
		Smoker tobaccoSmoker = new Smoker("tobacco", agent);

		Semaphore matchSemaphore = agent.getMatchSemaphore();
		Semaphore paperSemaphore = agent.getPaperSemaphore();
		Semaphore tobaccoSemaphore = agent.getTobaccoSemaphore();

		Semaphore matchSmokerSemaphore = matchSmoker.getSmokerSemaphore();
		Semaphore paperSmokerSemaphore = paperSmoker.getSmokerSemaphore();
		Semaphore tobaccoSmokerSemaphore = tobaccoSmoker.getSmokerSemaphore();

		Object pushersLock = new Object();

		/*
		 * Referência do Agent, lock dos pushers, Recurso atribuido ao pusher, Semáforo
		 * do recurso atribuido, primeira opção de segundo Recurso, Semáforo do Smoker
		 * complementar a essa combinação, segunda opção de segundo Recurso, Semáforo do
		 * Smoker complementar a essa combinação
		 */
		Pusher matchPusher = new Pusher(agent, pushersLock, "match", matchSemaphore, "paper", tobaccoSmokerSemaphore,
				"tobacco", paperSmokerSemaphore);

		Pusher paperPusher = new Pusher(agent, pushersLock, "paper", paperSemaphore, "match", tobaccoSmokerSemaphore,
				"tobacco", matchSmokerSemaphore);

		Pusher tobaccoPusher = new Pusher(agent, pushersLock, "tobacco", tobaccoSemaphore, "paper",
				matchSmokerSemaphore, "match", paperSmokerSemaphore);

		new Thread(matchSmoker).start();
		new Thread(paperSmoker).start();
		new Thread(tobaccoSmoker).start();

		new Thread(matchPusher).start();
		new Thread(paperPusher).start();
		Thread tobbacoPusherThread = new Thread(tobaccoPusher);
		tobbacoPusherThread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
