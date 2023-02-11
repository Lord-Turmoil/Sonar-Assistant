package main.sonar.api;

public enum SonarQubeApiEnum {
	////////////////////////////// Authentication
	LOGIN,
	LOGOUT,
	VALIDATE,

	////////////////////////////// Projects
	PROJECT_CREATE,
	PROJECT_DELETE,
	PROJECT_BULK_DELETE,
	PROJECT_SEARCH,

	////////////////////////////// Quality Profiles
	PROFILE_SEARCH,
	PROFILE_PROJECTS,
	PROFILE_ADD_PROJECT,


	////////////////////////////// Quality Gates
	GATES_SELECT,

	////////////////////////////// Permission
	USERS,

	////////////////////////////// Miscellany
	VERSION,
}
