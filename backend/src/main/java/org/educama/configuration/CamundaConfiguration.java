package org.educama.configuration;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.authorization.Authorization;

import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.task.TaskQuery;
import org.camunda.bpm.spring.boot.starter.configuration.Ordering;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(Ordering.DEFAULT_ORDER + 1)
public class CamundaConfiguration implements ProcessEnginePlugin {
	
	/*
	 * The values of the cofiguration-variables are defined in Spring-Properties.
	 * For example: src/main/resources/application.properties.
	 * 
	 * All of the configurations below should be checked again the needs of a production environment.
	 * During developing-stage this configuration will be helpful.
	 */
	
	@Value("${org.educama.configuration.adminUser}")
	private String adminUsername;
	
	@Value("${org.educama.configuration.adminPassword}")
	private String adminPassword;
	
	@Override
	public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
		
	}

	@Override
	public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
		
	}

	@Override
	public void postProcessEngineBuild(ProcessEngine processEngine) {
		
		// create user based on properties given through spring
		User user = processEngine.getIdentityService().newUser(adminUsername);
			user.setPassword(adminPassword);
			user.setFirstName("EduCaMa");
			user.setLastName("User");		
		processEngine.getIdentityService().saveUser(user);
		
		// create a admin group and put the new user into it
		Group adminGroup = processEngine.getIdentityService().newGroup("camunda-admin"); 
		processEngine.getIdentityService().saveGroup(adminGroup);
		processEngine.getIdentityService().createMembership(user.getId(), adminGroup.getId());
		
		// authorize the group with type 'grant' to resource 'user' and enable 'all' permissions
		Authorization authorization = processEngine.getAuthorizationService()
				.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
			authorization.setGroupId(adminGroup.getId());
			authorization.setResource(Resources.USER);
			authorization.addPermission(org.camunda.bpm.engine.authorization.Permissions.ALL);
		processEngine.getAuthorizationService().saveAuthorization(authorization);
		
		// define a default query which shows all assigned tasks
		TaskQuery query = processEngine.getTaskService().createTaskQuery().taskAssignee(adminUsername);
		Filter taskFilter = processEngine.getFilterService().newTaskFilter("assigned Tasks");
			taskFilter.setOwner(adminUsername);
			taskFilter.setQuery(query);
		processEngine.getFilterService().saveFilter(taskFilter);
	}
}