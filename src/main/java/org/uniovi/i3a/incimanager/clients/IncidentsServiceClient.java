package org.uniovi.i3a.incimanager.clients;


import org.uniovi.i3a.incimanager.types.Incident;

public interface IncidentsServiceClient {

    Incident findByIncidentId(String incidentId);
    
    Incident[] findByAgentId(String agentId);
    
    String saveIncident(Incident incident);
}
