/* 
 * Group 36
 * Stephen Barrack
 * Joseph Couri
 */
package groupProgramAssignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class BestPath {
	private Node[] hive = new Node[234];
	private Node[] queue = new Node[1000];
	private Node[] stack = new Node[233];
	int head = 0;
	int tail = 0;
	int top = 0;
	
	public BestPath() {
		readIn();
		setup(226);
		enqueue(hive[226]);
		hive[226].total = hive[226].cost;
		mark();
		move(hive[8]);
		pprint();
	}
	
	/**
	 * Reads in the data from the file and puts each node into an array.
	 */
	public void readIn() {
		try {
			BufferedReader in = new BufferedReader(new FileReader("C:/Users/User/Desktop/input.txt"));
			String line;
			int j;
			for (int i = 1; i <= 233; i++) {
				line = in.readLine();
				String[] split = line.split("\\s+");
				j = Integer.parseInt(split[0]);
				hive[j] = new Node(j, Integer.parseInt(split[1]));
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Connects the nodes together starting at the bottom-left corner, spreading up and right by assigning the top, top-right, and bottom-right nodes to the bottom, bottom-left, and top-left of each of the adjacent nodes in tose places.
	 * @param i
	 */
	public void setup(int i) {
		int j = i - 7;
		boolean b = i % 15 != 8;
		if (b && i - 8 > 0 && hive[i].tr == null) {
			hive[i].tr = hive[j];
			hive[i].tr.bl = hive[i];
			setup(j);
		}
		j = i - 15;
		if (j > 0 && hive[i].t == null) {
			hive[i].t = hive[j];
			hive[i].t.b = hive[i];
			setup(j);
		}
		j = i + 8;
		if (b && j <= 233 && hive[i].br == null) {
			hive[i].br = hive[j];
			hive[i].br.tl = hive[i];
			setup(j);
		}
	}
	
	public void enqueue(Node n){
		queue[tail++] = n;
	}
	
	public Node dequeue(){
		if (head != tail){
			Node n = queue[head++];
			return n;
		}
		return null;
	}
	
	/**
	 * Checks if each adjacent node is present, doesn't cost -1, and did not previously have its total cost evaluated.
	 * Only then will it enqueue the next node.
	 */
	public void mark() {
		Node n = dequeue();
		if (n == null) 
			return;		
		if (n.t != null) {
			if (n.t.cost != -1) {
				n.toT = n.total + n.t.cost;
				if (n.toT < n.t.total) {
					n.t.total = n.toT;
					enqueue(n.t);
				}
			}
		}
		if (n.tr != null) {
			if (n.tr.cost != -1) {
				n.toTR = n.total + n.tr.cost;
				if (n.toTR < n.tr.total) {
					n.tr.total = n.toTR;
					enqueue(n.tr);
				}
			}
		}
		if (n.br != null) {
			if (n.br.cost != -1) {
				n.toBR = n.total + n.br.cost;
				if (n.toBR < n.br.total) {
					n.br.total = n.toBR;
					enqueue(n.br);
				}
			}
		}
		if (n.b != null) {
			if (n.b.cost != -1) {
				n.toB = n.total + n.b.cost;
				if (n.toB < n.b.total) {
					n.b.total = n.toB;
					enqueue(n.b);
				}
			}
		}
		if (n.bl != null) {
			if (n.bl.cost != -1) {
				n.toBL = n.total + n.bl.cost;
				if (n.toBL < n.bl.total) {
					n.bl.total = n.toBL;
					enqueue(n.bl);
				}
			}
		}
		if (n.tl != null){
			if (n.tl.cost != -1){
				n.toTL = n.total + n.tl.cost;
				if (n.toTL < n.tl.total) {
					n.tl.total = n.toTL;
					enqueue(n.tl);
				}
			}
		}
		mark();
	}
	
	public void push(Node n) {
		stack[top++] = n;
	}
	
	public Node pop() {
		return stack[--top];
	}
	
	/**
	 * Checks if each adjacent node is present, doesn't cost -1, and has not been visited already.
	 * Only then does it move to the node with the smallest evaluated cost.
	 * @param n
	 */
	public void move(Node n) {
		push(n);
		n.visited = true;
		if (n.t != null) {
			if (n.t.cost != -1) {
				if (!n.t.visited) {
					if (n.t.total < n.minCost) {
						n.minCost = n.t.total;
						n.minPath = 0;
					}
				}
			}
		}
		if (n.tr != null) {
			if (n.tr.cost != -1) {
				if (!n.tr.visited) {
					if (n.tr.total < n.minCost) {
						n.minCost = n.tr.total;
						n.minPath = 1;
					}
				}
			}
		}
		if (n.br != null) {
			if (n.br.cost != -1) {
				if (!n.br.visited) {
					if (n.br.total < n.minCost) {
						n.minCost = n.br.total;
						n.minPath = 2;
					}
				}
			}
		}
		if (n.b != null) {
			if (n.b.cost!=-1) {
				if (!n.b.visited) {
					if (n.b.total < n.minCost) {
						n.minCost = n.b.total;
						n.minPath = 3;
					}
				}
			}
		}
		if (n.bl != null) {
			if (n.bl.cost != -1) {
				if (!n.bl.visited) {
					if (n.bl.total < n.minCost) {
						n.minCost = n.bl.total;
						n.minPath = 4;
					}
				}
			}
		}
		if (n.tl != null) {
			if (n.tl.cost != -1) {
				if (!n.tl.visited) {
					if (n.tl.total < n.minCost) {
						n.minCost = n.tl.total;
						n.minPath = 5;
					}
				}
			}
		}
		switch (n.minPath) {
			case -1:
				break;
			case 0:
				move(n.t);
				break;
			case 1:
				move(n.tr);
				break;
			case 2:
				move(n.br);
				break;
			case 3:
				move(n.b);
				break;
			case 4:
				move(n.bl);
				break;
			case 5:
				move(n.tl);
		}
	}
	
	public void pprint() {
		try {
			PrintWriter out = new PrintWriter("C:/Users/User/Desktop/output.txt");
			int i;
			while(top > 0) {
				i = pop().index;
				out.println(i);
				System.out.println(i);
			}
			String s = "MINIMAL-COST PATH COSTS: " + hive[8].total;
			out.println(s);
			System.out.println(s);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		long time = System.nanoTime();
		new BestPath();
		System.out.println((float) (System.nanoTime() - time) / 1E9 + " seconds");
	}
	
	class Node {
		int cost, index, total, minCost, minPath;
		int toT, toTR, toBR, toB, toBL, toTL;
		boolean visited;
		Node t, b, tl, tr, bl, br;
		
		Node(int i, int data) {
			cost = data;
			index = i;
			total = minCost = 2147483647;
			minPath = -1;
			visited = false;
			t = b = tl = tr = bl = br = null;
		}
	}
}
