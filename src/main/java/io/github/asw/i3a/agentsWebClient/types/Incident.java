package io.github.asw.i3a.agentsWebClient.types;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
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
    
    public String tagsAsString() {
	return Arrays.toString(tags);
    }
    
    public String multimediaAsString() {
	return Arrays.toString(multimedia);
    }
    
    public Comment[] getComments() {
	ArrayUtils.reverse(comments);
	return comments;
    }
}
