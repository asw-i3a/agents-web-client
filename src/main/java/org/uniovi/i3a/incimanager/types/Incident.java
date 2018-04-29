package org.uniovi.i3a.incimanager.types;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;

import lombok.Data;

@Data
public class Incident {
    private String incidentId = "";
    private String title = "";
    private String description = "";
    private String status = "";
    private String location = "";
    private String[] tags = {""};
    private String[] multimedia = {""};
    private Map<String, String> propertyVal = new HashMap<String, String>();
    private Comment[] comments;
    private String agentId = "";
    private String operatorId = "";
    
    public String getDate() {
	if(this.incidentId != null && this.incidentId!="")
	    return new ObjectId(this.incidentId).getDate().toString();
	return "";
    }
}
