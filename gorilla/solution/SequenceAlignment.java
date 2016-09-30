import java.io.FileReader;
import java.util.Scanner;
import java.io.BufferedReader;
import java.util.stream.Stream;
import java.io.IOException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class SequenceAlignment{
	final private int[][] penalties;
	final private Map<Character, Integer> indexMap;
	final private Organism[] organisms;
	final private int delta;

	public static void main(String[] args){
		SequenceAlignment sq = null;
		try{
			sq = new SequenceAlignment();
		}catch(IOException e){
			System.out.println("File blosum not found..");
			System.exit(19);
		}
			sq.print();
		
	}

	public SequenceAlignment() throws IOException {
		Map<Character, Integer> map = new HashMap<>();
		penalties = createBlosum(map);
		indexMap = map;
		delta = penalties[map.get('*')][1];
		organisms = build();
	}

	private static Organism[] build() {
		Scanner input = new Scanner(System.in);
		List<Organism> l = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		String st = "", name= input.nextLine().substring(1).split(" ")[0], start = ">"; 
		while(input.hasNext()){
			st = input.nextLine();
			if(st.startsWith(start)){
				l.add(new Organism(name, builder.toString()));
				builder = new StringBuilder();
				name = st.substring(1).split(" ")[0];
			}else{
				builder.append(st);
			}
		}
		l.add(new Organism(name, builder.toString()));
		Organism[] o = new Organism[l.size()];
		return l.toArray(o);
		
	}

	private void print(){
		for(int i = 0; i < organisms.length; i++){
			for(int j = i + 1; j < organisms.length; j++){
				sequence_Alignment(organisms[i], organisms[j],
							this.indexMap, this.penalties, this.delta);
			}
		}
	}
	private static void sequence_Alignment(Organism ox, Organism oy, 
					Map<Character, Integer> map, int[][] penalties, int delta){
		String x = ox.getGenome(), y = oy.getGenome();
		if(x.length() > y.length()){
			String temp = x;
			x = y;
			y = temp;
			Organism tempO = ox;
			ox = oy;
			oy = tempO;
		}
		
		final char[] arrX = x.toCharArray(), arrY = y.toCharArray();
		final int lx = arrX.length, ly = arrY.length;
		final int[][] cost = new int[lx + 1][ly + 1];	
		int[] al = new int[lx];
		for(int i =0; i <= lx; i++){ cost[i][0] = delta * i;}
		for(int j =0; j <= ly; j++){ cost[0][j] = delta * j;}
		char chx = ' ', chy = ' ';
		for(int i = 1; i <= lx; i++){
			chx = arrX[i-1];
			int idxX = map.get(chx); 
			int positionX = i - 1;
			int positionY;
			for(int j = 1; j <= ly; j++){
				chy = arrY[j - 1];
				positionY = j - 1;
				int idxY = map.get(chy);
				if(((penalties[idxX][idxY] + cost[i-1][j-1]) > (delta + cost[i][j-1])) &&
					(((penalties[idxX][idxY] + cost[i - 1][j -1]) > delta + cost[i - 1][j])
					)){
						cost[i][j] = penalties[idxX][idxY] + cost[i-1][j-1];
						al[positionX] = positionY;

					}else if((delta + cost[i][j-1]) > (delta + cost[i-1][j])) {
						cost[i][j] = delta + cost[i][j-1];
					}else {
						cost[i][j] = delta + cost[i-1][j] ;
					}
			}
			
		}
		
		System.out.println(ox.getName() +"--"+ oy.getName()+": "+ cost[lx][ly]);
		printer(al, arrX, arrY);
	}

	private static void printer(int[] al, char[] arrX, char[] arrY){
		StringBuilder sb = new StringBuilder(arrY.length);
		char[] ch = new char[arrY.length];
		for(int i = 0; i < arrY.length; i++)
			ch[i] = '-';
		for(int i = 0; i < al.length; i++){
			ch[al[i]] = arrX[i];
		}
		String align = String.copyValueOf(ch);
		System.out.println(align);
		System.out.println(String.copyValueOf(arrY));
	}
	private static int[][] createBlosum(Map<Character, Integer> indexMap) throws IOException{
		try(BufferedReader br = new BufferedReader(new FileReader("../data/BLOSUM62.txt"))){
			String comments, diesi = "#";
			while((comments = br.readLine()).startsWith(diesi)){
				System.out.println(comments);
			}
			char[] arr = comments.toCharArray();
			int count = 0;
			char c = ' ';
			for(int i = 0; i<arr.length; i++){
				if(arr[i] != c)
					indexMap.put(arr[i], count++);
			}
			int[][] penalties = new int[count][count];
			int row = 0 , col = 0;
			String empty = " ", nl = "";
			String[] arr1;
			while((comments = br.readLine()) != null){ 
				col = 0;
				arr1 = comments.trim().split(" ");
				for(int i = 1; i< arr1.length; ++i){
					if (!arr1[i].equals(empty) && !arr1[i].equals(nl)){
						penalties[row][col++] = Integer.parseInt(arr1[i]);
					}
				}
				row++;
			}
			return penalties;
		}
	}
	private static class Organism{
		final String name;
		final String genome;

		public Organism(String name, String genome){
			this.name = name;
			this.genome = genome;
		}
		public String toString(){
				return this.name +" : "+ this.genome;
		}

		public String getGenome(){
				return this.genome;
		}
		public String getName(){
			return this.name;
		}
	}
}
