import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Scanner;

public class StableMatching{
	static Map<Integer, Partner> tinder;
	private Map<Integer, String> map;
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
		Groom.N = couples / 2;
		for(int i = 1; i <= couples; i++){
			input.next();
			tmp.put(i, input.next());
		}
		input.nextLine();input.nextLine();
		map = tmp;
	}
	
	private int[] extractArray(String choices){
		String[] str = choices.split(":");
		choices = str[1].trim(); 
		String[] tmp = choices.split(" ");
		int[] arr = new int[tmp.length];
		int count = 0;
		for(String str1 : tmp)
			arr[count++] = Integer.parseInt(str1);
		return arr; 
	}
	
	private void createTinder(){
		String[] grooms, juliets;
		String choices ;
		Bride bride; Groom groom;
		tinder = new HashMap<>();
		for(int i = 1; i <= couples; i++){
			if(i % 2 == 0){ //Brides
				bride = new Bride(i, extractArray(input.nextLine()));	
				tinder.put(i, bride);
			}else { //Grooms
				groom = new Groom(i, extractArray(input.nextLine()));
				tinder.put(i, groom);	
				Groom.singleMales.add(i);
			}
		}
	}

	public static void main(String args[]){
		try(Scanner input = new Scanner(System.in)){
			StableMatching stable = new StableMatching(input);
			stable.createMap();
			stable.createTinder();	
			while(true){
				if(Groom.N == 0){
					printMatchings();
					return;	
				}else{
					Groom groom =  null;
					for(Integer id : Groom.singleMales){
							groom = (Groom) tinder.get(id);
							break;
					}
					while(!groom.isEngaged()){
						groom.propose();
					}	
				}
			}
		}
	}
	private static void printMatchings(){
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
	static ArrayList<Integer>  singleMales = new ArrayList<>(); 
	private final int[] juliets;
	private int counter;
	static int N;
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
		singleMales.remove(this.id);
		N--;
	}
	public void itIsNotYouItIsMe(){
		this.isEngaged = false;
		singleMales.add(this.id);
		N++;
	}
	public boolean isEngaged(){
		return isEngaged;
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
		}else{
			int currentGroomId = currentGroom.getId();
			int wannabeGroomId = wannabeGroom.getId();
			if(doINeedSomeSpace.get(wannabeGroomId).compareTo(doINeedSomeSpace.get(currentGroomId)) < 0){
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
