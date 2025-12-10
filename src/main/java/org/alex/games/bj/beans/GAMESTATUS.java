package org.alex.games.bj.beans;

public enum GAMESTATUS {
	OPEN,  // game is served
	USER_BJ_WON,
	DEALER_BJ_WON,
	BJ_PUSHED,   //both dealer and hand are black jacks
	
	USER_WON,  //with only one decision made by the player  Stay/double/hit
	DEALER_WON,//with only one decision made by the player  Stay/double/hit
	PUSHED,  // game ended even //with only one decision    Stay/double/hit
	SURRENDER,  //only on initial
	
//	//The tables don't show this data
//	USER_WON_MULT_HITS,  //with more than one hit
//	DEALER_WON_MULT_HITS,//after user made more than one hit
//	PUSHED_MULT_HITS,  //after user made more than one hit
//	
//	
//	//if we split the initial hand,  then each hand is reported on its own.
//	// the original hand is marked with the total sum of all the hands
//	// The original hand can be split up to 4 times. 
//	USER_WON_SPLIT,
//	DEALER_WON_SPLIT,
//	PUSHED_SPLIT,  // game ended even
	
	WHO_WON  // ERROR ??
}
