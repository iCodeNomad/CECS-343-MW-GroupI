
public class Global {
	//Enumeration for BasicGroupAbility
	
	//Used to determine the effects of clicking on a card at a given time
	public static SelectionPhase selectionPhase = SelectionPhase.ADD_CHILD;
	
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
		NONE, ADD_CHILD, SELECT_ATTACKING_GROUP;
	}
}
