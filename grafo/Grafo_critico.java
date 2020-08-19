package grafo;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Grafo_critico {
	public static final int INF = Integer.MAX_VALUE;
	
	private int v;
	private List<List<Nodo>> ady;
	private int[] distancia;
	private boolean[] visitado;
	private int[] previo;
	private PriorityQueue<Nodo> Q;
	
	public Grafo_critico(int vertices) {
		v = vertices;
		
		//instancio
		ady = new ArrayList<>();
		distancia = new int[v+1];
		visitado = new boolean[v+1];
		previo = new int[v+1];
		Q = new PriorityQueue<Nodo>();
		
		//inicializo
		for(int i = 0; i <= v; i++) {
			distancia[i] = INF;
			visitado[i] = false;
			previo[i] = -1;
		}
	}
	
	public void agregaArista(int vertice, int adyacente, int peso) {
		ady.get(vertice).add(new Nodo(adyacente, peso));
	}
	
	public String dijkstra(int inicial) {
		return dijkstra(inicial, -1);
	}
	
	public String dijkstra(int inicial, int destino) {
		StringBuilder minimo = new StringBuilder();
		int actual, adyacente, peso;
		
		Q.add(new Nodo(inicial, 0));
		distancia[inicial] = 0;
		
		while(!Q.isEmpty()) {
			actual = Q.poll().ady;
			
			if(visitado[actual]) continue;
			visitado[actual] = true;
			
			for(int i = 0; i < ady.get(actual).size(); i++) {
				adyacente = ady.get(actual).get(i).ady;
				peso = ady.get(actual).get(i).peso;
				
				if(!visitado[adyacente]) relajacion(actual, adyacente, peso);
			}
		}
		
		if(destino == -1) {
			for(int i = 1; i <= v; i++) {
				minimo.append("v1->v" + i + " = " + distancia[i] + "\n");
			}
		}else {
			minimo.append(obtieneCamino(destino));
		}
		return minimo.toString();
	}
	
	private void relajacion(int actual, int adyacente, int peso) {
		if((distancia[actual] + peso) >= distancia[adyacente]) return;
		
		distancia[adyacente] = distancia[actual] + peso;
		previo[adyacente] = actual;
		Q.add(new Nodo(adyacente, distancia[adyacente]));
	}
	
	private String obtieneCamino(int destino) {
		String camino =  "v" + String.valueOf(destino);
		while(previo[destino] != -1) {
			camino = "v" + previo[destino] + "->" + camino;
			previo[destino] = previo[previo[destino]];
		}
		return camino;
	}
	
	class Nodo implements Comparable<Nodo> {
		int ady, peso;
		
		public Nodo(int ady, int peso){
			this.ady = ady;
			this.peso = peso;
		}
		
		@Override
		public int compareTo(Nodo nodo) {
			if(peso > nodo.peso) return 1;
			if(peso < nodo.peso) return -1;
			return 0;
		}
	}
}
