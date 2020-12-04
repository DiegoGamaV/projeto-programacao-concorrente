package CigaretteSmokers;

import java.util.concurrent.Semaphore;

/*
 * Classe Agent que disponibiliza os recursos necessários para os fumantes 
 * montarem seus cigarros.
 * 
 * Os recursos são tabaco, papel e fósforos.
 * 
 * Um Agente é composto por 3 threads, chamadas de SubAgents, cada SubAgent é responsável por disponibilizar
 * um conjunto de 2 recursos diferentes:
 *  1 - Tabaco e Papel;
 *  2 - Papel e fósforos;
 *  3 - Tabaco e fósforos;
 *  
 * Apenas um SubAgent executa por vez e o próximo só é 
 * executado caso os recursos disponibilizados sejam consumidos. 
 * 
 * */

public class Agent {

	// Variáveis que informam se um recurso está disponível
	private boolean isTobacco;
	private boolean isMatch;
	private boolean isPaper;

	// Semáforo que permite apenas um SubAgent executar por vez e apenas se não há
	// recursos disponibilizados
	private Semaphore agentSemaphore;

	// Semáforos responsáveis por informar quando cada recurso está disponível.
	// Acordando a thread responsável por redirecioná-los
	private Semaphore tobaccoSemaphore;
	private Semaphore matchSemaphore;
	private Semaphore paperSemaphore;

	public Agent() {
		this.agentSemaphore = new Semaphore(1);
		this.tobaccoSemaphore = new Semaphore(0);
		this.matchSemaphore = new Semaphore(0);
		this.paperSemaphore = new Semaphore(0);

		this.isTobacco = false;
		this.isMatch = false;
		this.isPaper = false;

		// Innicialização dos SubAgents
		// SubAgent que gera tabaco e papel
		new Thread(new SubAgent(agentSemaphore, tobaccoSemaphore, paperSemaphore)).start();
		// SubAgent que gera fósforos e papel
		new Thread(new SubAgent(agentSemaphore, matchSemaphore, paperSemaphore)).start();
		// SubAgent que gera tabaco e fósforos
		new Thread(new SubAgent(agentSemaphore, tobaccoSemaphore, matchSemaphore)).start();
	}

	public void release() {
		agentSemaphore.release();
	}

	// Getter e Setters para os semáforos e variáveis booleanas indicando presença
	// dos recursos

	public Semaphore getTobaccoSemaphore() {
		return tobaccoSemaphore;
	}

	public Semaphore getMatchSemaphore() {
		return matchSemaphore;
	}

	public Semaphore getPaperSemaphore() {
		return paperSemaphore;
	}

	synchronized public boolean getIsTobacco() {
		return this.isTobacco;
	}

	synchronized public void setIsTobacco(boolean isTobacco) {
		this.isTobacco = isTobacco;
	}

	synchronized public boolean getIsMatch() {
		return this.isMatch;
	}

	synchronized public void setIsMatch(boolean isMatch) {
		this.isMatch = isMatch;
	}

	synchronized public boolean getIsPaper() {
		return this.isPaper;
	}

	synchronized public void setIsPaper(boolean isPaper) {
		this.isPaper = isPaper;
	}
}

/*
 * Classe SubAgent Implementa uma thread que disponibiliza 2 recursos e sinaliza
 * esta ação
 */

class SubAgent implements Runnable {
	private Semaphore generalSemaphore;
	private Semaphore resource1;
	private Semaphore resource2;

	/*
	 * @param generalSemaphore: semáforo que garante a exclusão mútua entre os
	 * SubAgents
	 * 
	 * @param resource1: semáforo usado para indicar que o recurso1 foi
	 * disponibilizado e a thread requisitando-o pode proseguir (ex: Papel)
	 * 
	 * @param resource2: semáforo usado para indicar que o recurso1 foi
	 * disponibilizado e a thread requisitando-o pode proseguir (ex: Fósforos)
	 */
	public SubAgent(Semaphore generalSemaphore, Semaphore resource1, Semaphore resource2) {
		this.generalSemaphore = generalSemaphore;
		this.resource1 = resource1;
		this.resource2 = resource2;
	}

	/*
	 * Espera a vez de executa
	 */
	@Override
	public void run() {
		while (true) {
			try {
				generalSemaphore.acquire();
				resource1.release();
				resource2.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
