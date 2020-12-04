package CigaretteSmokers;

import java.util.concurrent.Semaphore;

/*
 * Classe Smoker
 * 
 * Um fumante precisa de 3 recursos para montar um cigarro e fumá-lo.
 * Cada fumante possui um estoque infinito de um destes 3 recursos.
 * 
 * Os 2 recursos faltantes são fornecidos pelo agente
 * 
 * Uma thread fumante so deve acordar quando extamente os 2 recursos faltantes para 
 * a confecção do cigarro são ofertados pelo agente
 * 
 * */
public class Smoker implements Runnable {
	private String infiniteSuply; // String que indica qual dos 3 recursos o fumante possui estoque infinito
	private Semaphore smokerSemaphore; // Semáforo reponsável por bloquear o fumante até que os recursos que ele
										// precisa para montar o cigarro sejam ofertados
	private Agent agent; // Referência para o agente que oferece ao fumante os recursos

	public Smoker(String infiniteSuply, Agent agent) {
		this.infiniteSuply = infiniteSuply;
		this.agent = agent;
		this.smokerSemaphore = new Semaphore(0);
	}

	public void smoke() {
		System.out.println("Smoker with " + infiniteSuply.toUpperCase());
	}

	public Semaphore getSmokerSemaphore() {
		return this.smokerSemaphore;
	}

	@Override
	public void run() {
		try {
			while (true) {
				smokerSemaphore.acquire();
				smoke();
				agent.release();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
