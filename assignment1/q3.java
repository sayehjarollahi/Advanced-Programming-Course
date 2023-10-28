import java.util.*;
public class Goal {
	public static void main(String[] args) {
		int time;
		int edge;//tedad ravabet,tedad yaal haye graph
		Scanner s=new Scanner(System.in);
		time=s.nextInt();
		edge=s.nextInt();
		int [][] passingList=new int [edge][2];
		for(int i=0;i<edge;i++) {
			for(int j=0;j<2;j++) {
				passingList[i][j]=s.nextInt();
			}
		}
		int result=leastPassing(passingList,time);
		System.out.println(result);
		
	}

	static int leastPassing(int[][] passingList,int time) {
		boolean[] visited=new boolean[11];
		ArrayList<Integer> queue= new ArrayList<Integer>();
		ArrayList<Integer> temp= new ArrayList<Integer>();//harbar in arraylist ferestade mishe be yek function ta node hayi ke be oon vasle peyda she
		int count=0;//tedad pas ha ta residan be 11
		visited[0]=true;
		queue.add(1);
		while(true) {
			temp.clear();
			temp=relatedNodes(passingList,queue,visited);
			if(temp.contains(11)) {
				count++;
				return 90/(count*time);
			}
			if(temp.isEmpty()) {
				return 0;
			}
			else {
				count++;
				queue.clear();
				for(int x:temp) {
					queue.add(x);
				}
			}
		}		
	}
	static ArrayList relatedNodes(int[][] passingList,ArrayList<Integer> queue,boolean[] visited) {
		int node;
		int sizeQueue=queue.size();
		ArrayList<Integer> neighbors=new ArrayList<Integer>();
		neighbors.clear();
		for(int i=0;i<queue.size();i++) {
			node=queue.get(i);
			for(int j=0;j<passingList.length;j++) {
				if(passingList[j][0]==node && visited[passingList[j][1]-1]==false) {
					neighbors.add(passingList[j][1]);
					visited[passingList[j][1]-1]=true;
				}
				if(passingList[j][1]==node && visited[passingList[j][0]-1]==false) {
					neighbors.add(passingList[j][0]);
					visited[passingList[j][0]-1]=true;
				}
			}
		}
		
		return neighbors;
		
	}
	
	
	
	
	
	
	
}
