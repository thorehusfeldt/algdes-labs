import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Scanner;

public class StableMatching{
	static Map<Integer, Partner> tinder;
	Map<Integer, String> map;
	private Scanner input;
	private int couples;
	public StableMatching(Scanner input){
		this.input = input;
	}
	
	private void createMap(){
		if(input == null){
			System.out.println("Scanner is empty");
			System.exit(2);
		}
		HashMap<Integer, String> tmp = new HashMap<>();
		input.nextLine();input.nextLine();
		couples = ( Integer.parseInt(input.nextLine().substring(2,3)) * 2);
		System.out.println(couples);
		for(int i = 1; i <= couples; ++i){
			input.next();
			tmp.put(i, input.next());
		}
		input.nextLine();input.nextLine();
		map = tmp;
	}
	
	private String[] extractArray(String choices){
		//Fix the choice
		return null;
	}
	
	private void createTinder(){
		String[] grooms, juliets;
		for(int i = 1; i <= couples; ++i){
			if(i % 2 == 0){ //Brides
					
			}else { //Grooms
				
			}
		}
	}
	 
	public static void main(String args[]){
		try(Scanner input = new Scanner(System.in)){
			StableMatching stable = new StableMatching(input);
			stable.createMap();
			stable.createTinder();	
			
		}
	}
	
}
class Partner{
	protected final int id;
	
	protected Partner(int id){
		this.id = id;
	}
	
	public int getId(){return this.id;};	
}

class Groom extends Partner{
	//Change this with another more efficient DS
	static ArrayList<Groom>  singleMales = new ArrayList<>(); 
	private final int[] juliets;
	private int counter;
	private boolean isEngaged;

	public Groom(int id, int[] juliets){
		super(id);
		this.juliets = juliets;
	}
	
	public void propose(){
		Bride bride = (Bride) StableMatching.tinder.get(juliets[counter++]);
		bride.proposedBy(this);
	}
	@Override
	public int getId(){return super.getId();}
	
	public void youAreNotSingleAnymore(){
		this.isEngaged = true;
	singleMales.remove(this);
	}
	public void itIsNotYouItIsMe(){
		this.isEngaged = false;
		singleMales.add(this);
	}
	
}
class Bride extends Partner{

	private Groom currentGroom;
	// Maps Grooms to position
	private Map<Integer, Integer> doINeedSomeSpace;
	
	public Bride(int id, int[] grooms){
		super(id);
		doINeedSomeSpace = buildMap(grooms);
	}
	
	@Override
	public int getId(){ return super.getId(); }

	public void proposedBy(Groom wannabeGroom){
		if(currentGroom == null){
			wannabeGroom.youAreNotSingleAnymore();
			currentGroom = wannabeGroom;
			return ;
		}else{
			int currentGroomId = currentGroom.getId();
			int wannabeGroomId = wannabeGroom.getId();
			if(doINeedSomeSpace.get(wannabeGroomId).compareTo(doINeedSomeSpace.get(currentGroomId)) > 0){
				currentGroom.itIsNotYouItIsMe();
				wannabeGroom.youAreNotSingleAnymore();
				currentGroom = wannabeGroom;
			}
		}
	}
	
	private static  Map<Integer, Integer> buildMap(int[] groomsId){
		HashMap<Integer, Integer> map = new HashMap<>();
		for(int i = 0; i < groomsId.length; i++){
			map.put(groomsId[i], i);
		} 
		return map;	
	}
}
