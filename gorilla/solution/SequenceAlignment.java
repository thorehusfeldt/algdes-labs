import java.io.FileReader;
import java.io.BufferedReader;
//import java.util.stream.Stream;
import java.io.IOException;
//import java.util.function.Function;
//import java.util.function.Predicate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class SequenceAlignment{
	final private int[][] penalties;
	final private Map<Character, Integer> indexMap;
	final private List<Organism> list;

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
		list = build(fastas);
		sequenceAlignment();
	}


	private static List<Organism> build(String fastas) throws IOException {
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
			return l;
		}
	}

	private void sequenceAlignment(){
			sequence_Alignment(list[0].getGenome(), list[1].getGenome,
							this.list, this.penalties);
						
	}
	private static void sequence_Alignment(String x, String y, 
					Map<Character, Integer> map, int[][] penalties){
		final int lx = x.length(), ly = y.length();
		final int[][] cost = new int[lx][ly];	
		for(char cx : x.toCharArray()){
				for(char cy : y.toCharArray()){
						System.out.println( "cx: " + cx + ", cy:  " + cy+ " penalty cost: "+ penalties[map.get(cx)][map.get(cy)]);
				}
		}
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
	}
}
