package CigaretteSmokers;

import java.util.concurrent.Semaphore;

/*
 * Classe de suporte
 * 
 * Cada thread Pusher é atrelada a um recurso e é responsável por acordar o fumante que precisa
 * dos recursos disponibilizados pelo agente ou sinalizar que o seu recurso está disponível
 * */

public class Pusher implements Runnable {
	private Object pushersLock;
	private Semaphore mainResourceSemaphore;
	private Agent agent;
	private String mainResource;
	private String resource1;
	private String resource2;
	private Semaphore complementSmoker1;
	private Semaphore complementSmoker2;

	/*
	 * @param agent: referencia para o agente que o pusher vai coletar os recursos
	 * 
	 * @param pushersLock: Trava para garantir exclusão mútua entre pushers
	 * 
	 * @param mainResource: Recurso atribuído ao pusher
	 * 
	 * @param mainResourceSemaphore: Semaforo que indica que o recurso foi
	 * disponibilizado e a thread pode proseguir
	 * 
	 * @param resource1: Um dos outros dois recursos necessários
	 * 
	 * @param complementSmoker1: Fumante que tem o recurso complementar ao recurso
	 * atribuído ao pusher e o Recurso1
	 * 
	 * @param resource2: Um dos outros dois recursos necessários
	 * 
	 * @param complementSmoker2: Fumante que tem o recurso complementar ao recurso
	 * atribuído ao pusher e o Recurso2
	 * 
	 */

	public Pusher(Agent agent, Object pushersLock, String mainResource, Semaphore mainResourceSemaphore,
			String resource1, Semaphore complementSmoker1, String resource2, Semaphore complementSmoker2) {
		this.agent = agent;
		this.pushersLock = pushersLock;
		this.mainResource = mainResource;
		this.mainResourceSemaphore = mainResourceSemaphore;
		this.resource1 = resource1;
		this.complementSmoker1 = complementSmoker1;
		this.resource2 = resource2;
		this.complementSmoker2 = complementSmoker2;
	}

	private void setAgentAtt(String att, boolean value) {
		switch (att) {
		case "paper":
			agent.setIsPaper(value);
			break;
		case "match":
			agent.setIsMatch(value);
			break;
		case "tobacco":
			agent.setIsTobacco(value);
			break;
		}
	}

	private boolean getAgentAtt(String att) {
		switch (att) {
		case "paper":
			return agent.getIsPaper();
		case "match":
			return agent.getIsMatch();
		case "tobacco":
			return agent.getIsTobacco();
		}
		return false;
	}

	@Override
	public void run() {
		try {
			while (true) {
				// Espera o recurso atribuido ao pusher ser disponibilizado
				mainResourceSemaphore.acquire();
				System.out.println(mainResource.toUpperCase() + " available ");
				// Verifica se outro recurso já está disponível
				synchronized (pushersLock) {
					if (getAgentAtt(resource1)) {
						/*
						 * Caso seja o recurso 1 o pusher indica que o recurso nao esta mais disponível
						 * e acorda o fumante que precisa do recurso atribuído ao pusher e o recurso 1
						 */
						setAgentAtt(resource1, false);
						complementSmoker1.release();
					} else if (getAgentAtt(resource2)) {
						/*
						 * O mesmo que o if anterior mas com o recurso 2 e o fumante respectivo
						 */
						setAgentAtt(resource2, false);
						complementSmoker2.release();
					} else {
						// Indica que o recurso atribuido ao pusher está disponivel
						setAgentAtt(mainResource, true);
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Exemplo de execução 1 - Pusher atribuido a PAPEL com recurso 1 definido para
	 * TABACO acorda o fumante que tem o complemento a esses recursos, no caso
	 * FÓSFOROS.
	 *
	 * 2 - Pusher atribuido a PAPEL com recurso 2 definido para FÓSFOROS acorda o
	 * fumante que tem o complemento a esses recursos, no caso TABACO.
	 */
}
