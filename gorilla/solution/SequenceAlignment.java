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
		SequenceAlignment sq;
		try{
			sq = new SequenceAlignment(args[0]);
			sq.sequenceAlignment();

		}catch(IOException e){
			System.out.println("File: "+ args[0]+" not found");
			System.exit(1);
		}
	}

	public SequenceAlignment(String fastas) throws IOException {
		Map<Character, Integer> map = new HashMap<>();
		penalties = createBlosum(map);
		indexMap = map;
		delta = penalties[map.get('*')][1];
		organisms = build(fastas);
	}


	private static Organism[] build(String fastas) throws IOException {
		try(BufferedReader br = new BufferedReader(new FileReader(fastas))){
			List<Organism> l = new ArrayList<>();
			StringBuilder builder = new StringBuilder();
			String st, name= br.readLine().substring(1).split(" ")[0], start = ">"; 
			while((st = br.readLine()) != null){
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
	}

	private String[] sequenceAlignment(){
		for(int i = 0; i < organisms.length; i++){
			for(int j = i + 1; j < organisms.length; j++){
				System.out.println(organisms[i].getName() +" -----" + organisms[j].getName());
				System.out.println(sequence_Alignment(organisms[i].getGenome(), organisms[j].getGenome(),
								this.indexMap, this.penalties, this.delta)[0]);
			}
		}
		return null;						
	}
	private static String[] sequence_Alignment(String x, String y, 
					Map<Character, Integer> map, int[][] penalties, int delta){
		final char[] arrX = x.toCharArray(), arrY = y.toCharArray();
		final int lx = arrX.length, ly = arrY.length;
		final int[][] cost = new int[lx + 1][ly + 1];	
		for(int i =0; i < lx; i++){ cost[i][0] = delta * i;}
		for(int j =0; j < ly; j++){ cost[0][j] = delta * j;}
		for(int i = 1; i <= lx; i++){
			int idxX = map.get(arrX[i-1]); 
			for(int j = 1; j <= ly; j++){
				int idxY = map.get(arrY[j - 1]);
				cost[i][j] = Math.max(
								Math.max((penalties[idxX][idxY] + cost[i-1][j-1]), (delta + cost[i][j - 1])),
								(delta + cost[i - 1][j]));
			}
		}
		return new String[]{String.valueOf(cost[lx][ly])};
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
