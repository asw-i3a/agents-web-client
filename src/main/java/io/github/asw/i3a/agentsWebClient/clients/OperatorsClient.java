package io.github.asw.i3a.agentsWebClient.clients;

import io.github.asw.i3a.agentsWebClient.types.Operator;

public interface OperatorsClient {
	
	/**
	 * Gives a list of all the operators in the system.
	 * @return a list of all the operators in the system.
	 */
	public Operator[] findAll();

}
