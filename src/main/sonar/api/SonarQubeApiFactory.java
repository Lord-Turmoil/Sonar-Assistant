/**
 * Stores all APIs.
 */

package main.sonar.api;

import java.util.EnumMap;

public class SonarQubeApiFactory {
	private static EnumMap<SonarQubeApiEnum, SonarQubeApi> apiCollection = new EnumMap<>(SonarQubeApiEnum.class);

	static {
		// authentication
		apiCollection.put(SonarQubeApiEnum.LOGIN,
				new SonarQubeApi(RequestMethodEnum.POST, "api/authentication/login"));
		apiCollection.put(SonarQubeApiEnum.LOGOUT,
				new SonarQubeApi(RequestMethodEnum.POST, "api/authentication/logout"));
		apiCollection.put(SonarQubeApiEnum.VALIDATE,
				new SonarQubeApi(RequestMethodEnum.GET, "api/authentication/validate"));

		// projects
		apiCollection.put(SonarQubeApiEnum.PROJECT_CREATE,
				new SonarQubeApi(RequestMethodEnum.POST, "api/projects/create"));
		apiCollection.put(SonarQubeApiEnum.PROJECT_DELETE,
				new SonarQubeApi(RequestMethodEnum.POST, "api/projects/delete"));
		apiCollection.put(SonarQubeApiEnum.PROJECT_BULK_DELETE,
				new SonarQubeApi(RequestMethodEnum.POST, "api/projects/bulk_delete"));
		apiCollection.put(SonarQubeApiEnum.PROJECT_SEARCH,
				new SonarQubeApi(RequestMethodEnum.GET, "api/projects/search"));

		// quality profiles
		apiCollection.put(SonarQubeApiEnum.PROFILE_SEARCH,
				new SonarQubeApi(RequestMethodEnum.GET, "api/qualityprofiles/search"));
		apiCollection.put(SonarQubeApiEnum.PROFILE_PROJECTS,
				new SonarQubeApi(RequestMethodEnum.GET, "api/qualityprofiles/projects"));
		apiCollection.put(SonarQubeApiEnum.PROFILE_ADD_PROJECT,
				new SonarQubeApi(RequestMethodEnum.POST, "api/qualityprofiles/add_project"));

		// quality gates
		apiCollection.put(SonarQubeApiEnum.GATES_SELECT,
				new SonarQubeApi(RequestMethodEnum.POST, "api/qualitygates/select"));

		// permission
		apiCollection.put(SonarQubeApiEnum.USERS,
				new SonarQubeApi(RequestMethodEnum.GET, "api/permissions/users"));

		// miscellany
		apiCollection.put(SonarQubeApiEnum.VERSION,
				new SonarQubeApi(RequestMethodEnum.GET, "api/server/version"));
	}

	public static SonarQubeApi get(SonarQubeApiEnum api) {
		return apiCollection.get(api);
	}

}
