//package forwardGoal;
import java.util.*;
import java.lang.Math;
public class forwardGoal {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		String input=new String();
		int dataCount;//tedade data haye khoruji
		int goal;
		double[][] data=new double [100][2];
		input=scan.nextLine();
		dataCount=efficientData(input,data);
		goal=checkGoal(data,dataCount);
		System.out.println(goal);
		
		
		
		
		
		
	}
	static int efficientData(String input,double[][] data) {
		String regexOfForward="@#forward,x=(\\d+),y=(\\d+),distance=(\\d+)#@(.*)";
		String regexOfKeeper="@#keeper,x=(\\d+),y=(\\d+),distance=(\\d+)#@(.*)";
		int dataCount=0;
		while(true) {
		   	if(input.isEmpty()) {
				return dataCount;
			}
		   	else if(input.matches(regexOfForward)) {
		   		int indexOfAssignment;
				int indexOfComma;
				String stringOfNumber=new String();
		   		indexOfComma=input.indexOf(",");
		   		input=input.substring(indexOfComma+1);
		   		int[] x=new int[1];
				for(int i=0;i<3;i++) {
					indexOfAssignment=input.indexOf("=");
					indexOfComma=input.indexOf(",");
					if(i==2) {
						indexOfComma=input.indexOf("#");
					}
					stringOfNumber=input.substring(indexOfAssignment+1,indexOfComma);
					if(i==0) {
						 x[0] = Integer.parseInt(stringOfNumber);//converting string of number to integer
					}
					else if(i==1) {
						int y=Integer.parseInt(stringOfNumber);
						data[dataCount][0]=Math.sqrt(x[0]*x[0]+y*y);
					}
					
					else if(i==2) {
						data[dataCount][1]=Integer.parseInt(stringOfNumber);
						indexOfComma++;//bad az akharin adade har dastur do character baghi mimanad ke bayad hazf shavad
					}
					input=input.substring(indexOfComma+1);
				}
		   		dataCount++;
			}
			else if(input.matches(regexOfKeeper)) {
				input=input.substring(1);
				int endOfCommand=input.indexOf("@");
				input=input.substring(endOfCommand+1);	
			}
			else {
				int noiseCount=0;
				while(!input.matches(regexOfKeeper)&&!input.matches(regexOfForward)&&!input.isEmpty()) {
					noiseCount++;
					input=input.substring(1);
					if(noiseCount==200) {
						return dataCount;
					}
				}	
			}
		}		
	}
	static int checkGoal(double[][] data,int countData) {
		int goal=0;
	     	for(int i=0;i<countData;i++) {
			while(data[i][1]>=10) {
				i++;
				if(i==countData) 
					return goal;
			}
			while(data[i+1][1]>data[i][1] && data[i][0]-data[i][1]>10) {
				i++;
				
				if(i==countData)
					return goal;
			}
			if(data[i][0]-data[i][1]<=10)
				goal++;	
		}
		return goal;
		
		
		
		
	}
	
	
	
	
	
	
}
	








