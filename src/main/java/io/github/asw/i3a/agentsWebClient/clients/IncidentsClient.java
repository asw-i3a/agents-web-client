package io.github.asw.i3a.agentsWebClient.clients;

import io.github.asw.i3a.agentsWebClient.types.Incident;

public interface IncidentsClient {

	/**
	 * From a given incidentId will return the corresponding incident if exists
	 * one.
	 * 
	 * @param incident id is the unique data base id for the incident to look
	 *            for. Must be in Object Id format from BSON.
	 * @return the incident if found, null otherwise.
	 */
	Incident findByIncidentId( String incidentId );

	/**
	 * From a given agent id will find all the incidents reported by that given
	 * agent.
	 * 
	 * @param agentId is the unique database id of the agent we want to get all
	 *            the incidents.
	 * @return
	 */
	Incident[] findByAgentId( String agentId );

	/**
	 * Saves / updates the given incident in the database through the
	 * corresponding service. In other words persists the incident object.
	 * 
	 * @param incident to persist.
	 * @return the id of the incident. Useful in case we are creating an
	 *         incident and we want to know the id assigned in the database or we
	 *         just want to know if the operation was successful.
	 */
	String saveIncident( Incident incident );
}
