import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Stack;

public class StableMatching{
	static Map<Integer, Partner> tinder;
	private HashMap<Integer, String> map;
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
		String diesi = "#", comment;
		HashMap<Integer, String> tmp = new HashMap<>();
		do{
		 comment = input.nextLine();
		}while(comment.startsWith(diesi));
		couples = (Integer.parseInt(comment.substring(2)) * 2);
		Groom.N = couples / 2;
		for(int i = 1; i <= couples; i++){
			input.next();
			tmp.put(i, input.next());
		}
		input.nextLine();input.nextLine();
		map = tmp;
	}
	
	private void extract(String choices){
		String[] str = choices.split(":");
		choices = str[1].trim(); 
		Integer id = Integer.parseInt(str[0]);
		String[] tmp = choices.split(" ");
		int[] arr = new int[tmp.length];
		int count = 0;
		for(String str1 : tmp){
			arr[count++] = Integer.parseInt(str1);
		}
		if(id % 2 == 0){
			tinder.put(id, new Bride(id, arr));
		}else{
			tinder.put(id, new Groom(id, arr));
			Groom.singleMales.push(id);
		}
	}
	
	private void createTinder(){
		String choices;
		Bride bride; Groom groom;
		tinder = new HashMap<>();
		while(input.hasNext()){
			extract(input.nextLine());
		}
	}

	public static void main(String args[]){
		try(Scanner input = new Scanner(System.in)){
			StableMatching stable = new StableMatching(input);
			stable.createMap();
			stable.createTinder();	
			Groom groom;
			while(Groom.singleMales.size() != 0){
				groom = (Groom) tinder.get(Groom.singleMales.pop());
				while(!groom.isEngaged()){
					groom.propose();
				}	
			}
			stable.printMatchings();
		
		}
	}
	private void printMatchings(){
		Bride bride;
		for(int i = 2; i <= couples; i +=2){
			bride =(Bride) (tinder.get(i));
			System.out.println(this.map.get(bride.getCurrentGroomId()) + " -- " + this.map.get(bride.getId()));
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
	static Stack<Integer>  singleMales = new Stack<>(); 
	private final int[] juliets;
	private int counter;
	static int N;
	private boolean isEngaged;

	public Groom(int id, int[] juliets){
		super(id);
		this.juliets = juliets;
	}
	
	public void propose(){
		
		Bride bride = (Bride) StableMatching.tinder.get(juliets[counter++]);;
		bride.proposedBy(this);
	}
	@Override
	public int getId(){return super.getId();}
	
	public void youAreNotSingleAnymore(){
		this.isEngaged = true;
		N--;
	}
	public void itIsNotYouItIsMe(){
		this.isEngaged = false;
		singleMales.push(this.id);
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
				assert(currentGroom.isEngaged() == false);
				wannabeGroom.youAreNotSingleAnymore();
				currentGroom = wannabeGroom;
			}
		}
	}
	public Groom getCurrentGroom(){
		return this.currentGroom;
	}
	
	public Integer getCurrentGroomId(){
		return this.currentGroom.id;
	}
	
	private static  Map<Integer, Integer> buildMap(int[] groomsId){
		HashMap<Integer, Integer> map = new HashMap<>();
		for(int i = 0; i < groomsId.length; i++){
			map.put(groomsId[i], i);
		} 
		return map;	
	}
}
