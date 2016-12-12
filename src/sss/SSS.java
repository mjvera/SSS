package sss;

import java.util.ArrayList;

public class SSS {
	
	//función que determina la distancia de levenshtein (distancia entre 2 palabras)
	public static int levenshteinDistance (String lhs, String rhs) {                          
	    int len0 = lhs.length() + 1;                                                     
	    int len1 = rhs.length() + 1;                                                     
	                                                                                    
	    // the array of distances                                                       
	    int[] cost = new int[len0];                                                     
	    int[] newcost = new int[len0];                                                  
	                                                                                    
	    // initial cost of skipping prefix in String s0                                 
	    for (int i = 0; i < len0; i++) cost[i] = i;                                     
	                                                                                    
	    // dynamically computing the array of distances                                  
	                                                                                    
	    // transformation cost for each letter in s1                                    
	    for (int j = 1; j < len1; j++) {                                                
	        // initial cost of skipping prefix in String s1                             
	        newcost[0] = j;                                                             
	                                                                                    
	        // transformation cost for each letter in s0                                
	        for(int i = 1; i < len0; i++) {                                             
	            // matching current letters in both strings                             
	            int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;             
	                                                                                    
	            // computing cost for each transformation                               
	            int cost_replace = cost[i - 1] + match;                                 
	            int cost_insert  = cost[i] + 1;                                         
	            int cost_delete  = newcost[i - 1] + 1;                                  
	                                                                                    
	            // keep minimum cost                                                    
	            newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
	        }                                                                           
	                                                                                    
	        // swap cost/newcost arrays                                                 
	        int[] swap = cost; cost = newcost; newcost = swap;                          
	    }                                                                               
	                                                                                    
	    // the distance is the cost for transforming all letters in both strings        
	    return cost[len0 - 1];                                                          
	}

	//función que retorne máxima distancia entre dos elementos
	
	//función que retorna una lista de pivotes
	//necesita la lista de los elementos (objetos), distancia máxima (M)
	//y un alpha experimental (entre 0 y 1)
	public static ArrayList<String> seleccionPivotes(ArrayList<String> objetos, int M, double alpha){
		ArrayList<String> pivotes = null;
		pivotes.add(objetos.get(0));
		for(String x: objetos){
			for(String p: pivotes){
				if(levenshteinDistance(x,p)>M*alpha){
					pivotes.add(x);
				}
			}
		}
		return pivotes;
	}
	
	//función que crea el índice a partir de los pivotes
	//se le da a la función como argumentos:
	//la lista de pivotes (esto queda como "estático" si la base de datos no cambia)
	//la lista de objetos(palabras) existentes (esto queda como "estático" si la base de datos no cambia)
	//palabra a consultar (q) con su radio (r)
	public static String indiceSSS(ArrayList<String> pivotes, ArrayList<String> objetos, String q, int r){
		int n =0;
		String objetoFinal = null;
		ArrayList<Integer> dq = null;
		ArrayList<String> L = null;
		for (String p: pivotes){
			dq.add(levenshteinDistance(q,p));
		}
		int i=0;
		for(String o: objetos){
			for(String p: pivotes){
				if((levenshteinDistance(o,p)>(dq.get(i)-r))&&(levenshteinDistance(o,p)<(dq.get(i)+r))){
					n++;
				}
			}
			if(n==pivotes.size()){
				L.add(o);			
			}
		}
		
		for(String o: L){
			if(levenshteinDistance(o,q)<=r){
				objetoFinal = o;
			}
		}
		return objetoFinal;
	}
	
	public static void main(String[] args) {
		//prueba de función de levenshtein
		String str1 = "income";
		String str2 = "topographically";
		System.out.println(levenshteinDistance(str1,str2));
	}

}
