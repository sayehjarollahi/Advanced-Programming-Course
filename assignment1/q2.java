
import java.util.*;
public class team{
	public static void main(String[] args) {
		Scanner s=new Scanner(System.in);
		int numberOfMem;
		int n =s.nextInt();
		int m = s.nextInt(); 
		s.nextLine();
		ArrayList<String> people = new ArrayList<String>();
		ArrayList<String> teamName = new ArrayList<String>();
		String [][] teamMember=new String [m][];
		for(int i=0;i<n;i++) {
			people.add(s.nextLine());
		}
	    for(int i=0;i<m;i++) {
	    	teamName.add(s.nextLine());
	    	numberOfMem=s.nextInt();
	    	teamMember[i]=new String[numberOfMem];
	    	s.nextLine();
	    	for(int j=0;j<numberOfMem;j++) {
	    	teamMember[i][j]=(s.nextLine());
	    	}	
	    }
	 checkTeams(teamMember,people,m,teamName);
	}
	
	static void checkTeams(String[][] teamMember,ArrayList<String> people,int m,ArrayList<String>teamName) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(int i=0;i<m;i++) {
			if(result.contains(i)==false) {
				    for(String x:teamMember[i]) {
				    	int count=0;
				    	for(String y:teamMember[i]) {
				    		if(x.equals(y)) {
				    			count++;
				    		}
				    		if(count>1) {
				    			result.add(i);
				    			break;
				    		}
				    		
				    	}
					for(int j=0;j<m;j++) {
						if(j!=i) {
							for(String y:teamMember[j]) {
								if(x.equals(y)) {
								result.add(i);
								result.add(j);
								break;
								}	
							}
						}												
					}				
				}												
			}							
		}

	checkList(teamMember,result,people,m,teamName);
	}	
	
	static void checkList(String[][] teamMember,ArrayList<Integer> result,ArrayList<String> people,int m,ArrayList<String>teamName) {
		for(int i=0;i<m;i++) {
			if(result.contains(i)==false) {
				for(String x:teamMember[i]) {
					if(people.contains(x)==false) {
						result.add(i);
						break;
					}
				}	
			}
		}
		
	sortArray(result,teamName,m);
	}

	static void sortArray(ArrayList<Integer> result,ArrayList<String>teamName,int m) {
		ArrayList<String> ans = new ArrayList<String>();
		for(int i=m-1;i>=0;i-- ) {
			if(result.contains(i)==true) {
				ans.add(teamName.get(i));
			}
		}
		Collections.sort(ans);
		for(String x:ans)
			System.out.println(x);
	}
}
		
		
		
		

