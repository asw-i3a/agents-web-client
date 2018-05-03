package io.github.asw.i3a.agentsWebClient.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IncidentInfo {
	private String title;
	private String description;
	private String location;
	private String operatorId;
	private String expirationDate;
	private String tags;
	private String multimedia;
	private String properties;
	private String status;
}
