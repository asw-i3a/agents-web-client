package org.uniovi.i3a.incimanager.clients;


import org.uniovi.i3a.incimanager.types.Incident;

public interface IncidentsServiceClient {

    Incident[] findByAgentId(String agentId);
}
