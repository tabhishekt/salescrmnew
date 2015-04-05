package com.propmgr.resource;

public class ProjectPhaseResource {
	private long id;
	private ProjectResource project;
	private String projectName;
	private String name;
	private String description;
	
	public ProjectPhaseResource(long id, ProjectResource project, String projectName, String name, String description) {
		super();
		this.id = id;
		this.project = project;
		this.projectName = projectName;
		this.name = name;
		this.description = description;
	}
	
	public long getId() {
		return id;
	}
	
	public ProjectResource getProject() {
		return project;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
}
