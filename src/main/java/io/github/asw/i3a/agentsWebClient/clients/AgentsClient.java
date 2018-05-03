package io.github.asw.i3a.agentsWebClient.clients;

public interface AgentsClient {

    /**
     * From the agent id will return the agent name. For that will call the
     * corresponding service and compute the data returned.
     * 
     * @param id
     *            is the agent id for whom we're looking for the name
     * @return the name as a String.
     */
    public String getAgentName(String id);

    /**
	 * Allows to authenticate an agent in the system. In case data provided is
	 * valid for an authentication the method will return an string containing
	 * the id of the agent, else null.
	 * 
	 * @param username of the agent.
	 * @param password of the agent.
	 * @param kind of the agent.
	 * @return the agentId in case the login is successful, null otherwise.
	 */
    public String auth(String username, String password, int kind);
}
