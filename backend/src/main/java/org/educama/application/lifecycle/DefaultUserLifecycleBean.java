package org.educama.application.lifecycle;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.FilterService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

/**
 * The component creates a default user if it does not already exist.
 */
@Component
public class DefaultUserLifecycleBean implements SmartLifecycle {

    @Autowired
    IdentityService identityService;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    TaskService taskService;

    @Autowired
    FilterService filterService;

    @Value("${org.educama.configuration.adminUser}")
    private String adminUsername;

    @Value("${org.educama.configuration.adminPassword}")
    private String adminPassword;

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private boolean running;

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable runnable) {
        running = false;
        runnable.run();
    }

    @Override
    public void start() {
        if (userExists(adminUsername)) {
            logger.info("Default user '{}' already exists.", adminUsername);
        } else {
            logger.info("Creating default user '{}'.", adminUsername);
            User user = createDefaultUser(adminUsername, adminPassword);
            Group adminGroup = createAdminGroup(user);
            grantAuthorizationWithPermissions(adminGroup);
            createAssignedTaskQuery();
        }
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getPhase() {
        return 1;
    }

    private boolean userExists(String username) {
        return (identityService.createUserQuery().userId(username).count() > 0);
    }

    private User createDefaultUser(String username, String password) {
        User user = identityService.newUser(username);
        user.setPassword(password);
        user.setFirstName("Educama");
        user.setLastName("User");
        identityService.saveUser(user);

        return user;
    }

    private Group createAdminGroup(User user) {
        Group adminGroup = identityService.newGroup("camunda-admin");
        identityService.saveGroup(adminGroup);
        identityService.createMembership(user.getId(), adminGroup.getId());

        return adminGroup;
    }

    private void grantAuthorizationWithPermissions(Group adminGroup) {
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorization.setGroupId(adminGroup.getId());
        authorization.setResource(Resources.USER);
        authorization.addPermission(org.camunda.bpm.engine.authorization.Permissions.ALL);
        authorizationService.saveAuthorization(authorization);
    }

    /**
     * creates a task query to select task to the logged in user.
     * Otherwise you would have to manually create a filter in TaskList after each boot up
     */
    private void createAssignedTaskQuery() {
        TaskQuery query = taskService.createTaskQuery().taskAssignee(adminUsername);
        Filter taskFilter = filterService.newTaskFilter("assigned Tasks");
        taskFilter.setOwner(adminUsername);
        taskFilter.setQuery(query);
        filterService.saveFilter(taskFilter);
    }

}
