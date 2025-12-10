package org.alex.games.bj.beans;

public enum UserAction {
	STAY,
	HIT,
	DOUBLE,
	SPLIT,
	SURRENDER;

	/**
	 * Translates a string into an enum UserAction
	 * @param action
	 * @return a UserAction that matches the string parameter.  Null if the action is not found.
	 */
	public static UserAction find(String action) {
		for (UserAction userAction : UserAction.values()){
			if (action.equals(userAction.toString())) 
				return userAction;
		}
		return null;
	}
}
