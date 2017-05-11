
public class Global {
	//Enumeration for BasicGroupAbility
	
	//Used to determine the effects of clicking on a card at a given time
	public static SelectionPhase selectionPhase = SelectionPhase.ADD_CHILD;
	public static boolean isMainPhase = false;
	public static boolean isAttackPhase = false;
	
	public enum AbilityType{
		ANY_ATTEMPT, DIRECT_CONTROL;
	}
	
	public enum AttackType{
		CONTROL, NEUTRALIZE, DESTROY, ALL;
	}
	
	public enum Privilege{
		PRIVILEGED, NOT_PRIVILEGED, ABOLISHED;
	}
	
	public enum SelectionPhase{
		NONE, ADD_CHILD, DRAW_CARD, MAIN_PHASE, SELECT_ATTACKING_GROUP, SELECT_DEFENDING_GROUP, SELECT_AIDING_GROUPS, PRIVILEGE_DISCARD;
	}
}
