//package tabani;
import java.util.*;
import java.lang.*;
public class Tabani {
	public static void main(String[] arg) {
		Scanner scan = new Scanner(System.in);
		String input = new String();
		String temp=new String();
		ArrayList<String> commands =new ArrayList<String>();
		input=scan.nextLine();
		while(scan.hasNext()) {
			temp=scan.nextLine();
			commands.add(temp);
			if(temp.matches("[ ]*end[ ]*"))
				break;
		}
		divideCommands(input,commands);	
	}
	static int divideCommands(String input,ArrayList<String> commands) {
		String line=new String();
		for(int i=0;i<commands.size();i++) {
			line=commands.get(i);
			line=line.trim();
			if(line.matches("mul")) 
				input=tripleFunction(input,0);
			else if(line.matches("add"))
				input=tripleFunction(input,1);
			else if(line.matches("sub"))
				input=tripleFunction(input,2);
			else if(line.matches("sum (\\d)+ (-f|-b)"))
				input=sumFunction(input,line);
			else if(line.matches("gcd (\\d)+ (-f|-b)") )
				input=gcdFunction(input,line);
			else if(line.matches("replace (\\S+) (\\S+) (\\d)+"))
				input=replaceFunction(input,line);
			else if(line.matches("count_entail (\\S+)"))
				input=countEntailFunction(input,line);
			else if(line.matches("insert (\\S+) (\\d)+"))
				input=insertFunction(input,line);
			else if(line.matches("insert (\\S+)"))
				input=concatFunction(input,line);
			else if(line.matches("delete (\\S+)( -f)?"))
				input=deleteFunction(input,line);
			else if(line.matches("print"))
				System.out.println(input);
			else if(line.matches("end")) {
				System.out.println("END OF PROGRAM");
				return 0;
			}
			else
				System.out.println("THE COMMAND IS INVALID");
			
		}
		return 0;
	
	}
	static String tripleFunction(String input,int type){
	String result=new String();
	int[] number=new int[2];
	int [] index=new int [2];
	int[] digit=new int [2];
	int res=numberInString(input,2,index,digit,number,0);
	if(res==0) {
		System.out.println("CANNOT PERFORM THE COMMAND SUCCESSFULLY");
		return input;
	}
	int result1;
	if(type==0) {
		result1=number[0]*number[1];

	}
	else if(type==1) {
		result1=number[0]+number[1];
	}
	else {
		result1=number[0]-number[1];
	}
	if(index[0]==0) {
		result=Integer.toString(result1);
		result= result.concat(input.substring(index[1]+digit[1]));
	    System.out.println(result);
	    return result;
	}
	result=input.substring(0, index[0]);
	result=result.concat(Integer.toString(result1));
	result=result.concat(input.substring(index[1]+digit[1]));
	System.out.println(result);
	return result;	
	}
	
	static String sumFunction(String input,String line) {
		char method;
		int count;
		int result;
		method=line.charAt(line.length()-1);
		line=line.substring(4, line.length()-3);
		count=Integer.parseInt(line);
		int[]index=new int[count];
		int[] digit=new int [count];
		int[]number=new int[count];
		if(method=='f')
		    result=numberInString(input,count,index,digit,number,1);
		else
			result=numberInString(input,count,index,digit,number,0);
		if(result==0) {
			System.out.println("CANNOT PERFORM THE COMMAND SUCCESSFULLY");
			return input;
		}
		int sum=0;
		for(int i=0;i<count;i++) {
			sum=sum+number[i];
		}
		input=input+'S'+sum+'S';
		System.out.println(input);
		return input;	
	}
	static String gcdFunction(String input,String line) {
		char method;
		int count;
		int result;
		method=line.charAt(line.length()-1);
		line=line.substring(4, line.length()-3);
		count=Integer.parseInt(line);
		int[]index=new int[count];
		int[] digit=new int [count];
		int[]number=new int[count];
		if(method=='f')
		    result=numberInString(input,count,index,digit,number,1);
		else
			result=numberInString(input,count,index,digit,number,0);
		if(result==0) {
			System.out.println("CANNOT PERFORM THE COMMAND SUCCESSFULLY");
			return input;
		}
		int gcd=gcdCalculator(number);
		input=input+'G'+gcd+'G';
		System.out.println(input);
		return input;	
	}
	static String replaceFunction(String input,String line){
		String str1=new String();
		String str2=new String();
		line=line.substring(8);
		int s=line.indexOf(' ');
		str1=line.substring(0, s);
		line=line.substring(s+1);
		s=line.indexOf(' ');
		str2=line.substring(0, s);
		line=line.substring(s+1);
		int num=Integer.parseInt(line);
		if(num==0) {
			System.out.println(input);
			return input;
		}
		for(int i=0;i<num;i++) {
			int index=input.indexOf(str1);
			int size=str1.length();
			if(index==-1) {
				System.out.println(input);
				return input;
			}
			if(index==0) {
				input=str2+input.substring(size);
			}
			else {
				input=input.substring(0,index)+str2+input.substring(index+size);
			}
		}
		System.out.println(input);
		return input;
	}
	static String countEntailFunction(String input,String line){
		String check=new String();
		check=input;
		line=line.substring(13);
		char firstChar=line.charAt(0);
		int index;
		int count=0;
		int i=0;
		while(true) {
			index=check.indexOf(firstChar, 0);
			if(index==-1) {
				if(count==0) {
					System.out.println("CANNOT PERFORM THE COMMAND SUCCESSFULLY");
					return input;
				}
				input=input+'C'+count+'C';
				System.out.println(input);
				return input;
			}
			check=check.substring(index);
			if(check.startsWith(line)) {
				count++;
				check=check.substring(1);
				if(check.isEmpty())
					break;
			}
			else {
				check=check.substring(1);
				if(check.isEmpty())
					break;
			}	
		}
		input=input+'C'+count+'C';
		System.out.println(input);
		return input;
	}
	
	static String concatFunction(String input,String line){
		line=line.substring(7);
		input=input.concat(line);
		System.out.println(input);
		return input;
	}
	static String insertFunction(String input,String line){
		String number=new String();
		int index;
		line=line.substring(7);
		int t=line.indexOf(" ");
		number=line.substring(t+1);
		line=line.substring(0, t);
		index=Integer.parseInt(number);
		if(index>input.length()) {
			System.out.println("CANNOT PERFORM THE COMMAND SUCCESSFULLY");
			return input;
		}
		if(index==0) {
			line=line.concat(input);
			System.out.println(line);
			return line;
		}
		number=input.substring(0, index);
		number=number.concat(line);
		number=number.concat(input.substring(index));
		System.out.println(number);
		return number;
		
		
		
	}
	static String deleteFunction(String input,String line){
		int index;
		int lengthOfString;
		line=line.substring(7);
		int size=line.length();
		if(line.lastIndexOf(" -f")!=-1) {
			line=line.substring(0,line.lastIndexOf(" -f") );
		    index=input.lastIndexOf(line);
		    lengthOfString=line.length();
		}
		else {
			line=line.replace(" ", "");
			index=input.indexOf(line);
			lengthOfString=line.length();	
		}
		if(index==-1) {
			System.out.println("CANNOT PERFORM THE COMMAND SUCCESSFULLY");
			return input;
		}
		if(index==0) {
			input=input.substring(lengthOfString);
			System.out.println(input);
			return input;	
		}
		String result=new String();
		result=input.substring(0, index);
		result=result.concat(input.substring(index+lengthOfString));
		System.out.println(result);
		return result;	
	}
	
	static int numberInString(String input,int count,int []index,int []digit,int[]number,int checkReverse) {
		String num=new String();
		int checkExistence=0;
		int countDigit=0;
		if(checkReverse==1) {//reversing hole string
			StringBuilder reverse=new StringBuilder();
			reverse=reverse.append(input);
			reverse=reverse.reverse();
			input=reverse.toString();
		}
		for(int i=0;i<input.length();i++) {
			char temp=input.charAt(i);
			if(temp<='9'&&temp>='0') {
				index[checkExistence]=i;
				while(input.charAt(i)<='9'&&input.charAt(i)>='0') {
					countDigit++;
			     	i++;
			     	if(i==input.length())
			     		break;
			     	}
				digit[checkExistence]=countDigit;
				num=input.substring(i-countDigit, i);
				if(checkReverse==1) {//reversing the number
					StringBuilder reverseNum=new StringBuilder();
					reverseNum=reverseNum.append(num);
					reverseNum=reverseNum.reverse();
					num=reverseNum.toString();
				}
				number[checkExistence]=Integer.parseInt(num);
				if(i-countDigit-1>=0&&input.charAt(i-countDigit-1)=='-') {
					number[checkExistence]=-number[checkExistence];
					index[checkExistence]-=1;
					digit[checkExistence]+=1;			
				}
				checkExistence++;
				countDigit=0;
				if(checkExistence==count) 
					break;
				i--;
			}
		}
	if(checkExistence<count) 
		return 0;
	return 1;
}

	static int gcdCalculator(int[] number) {
		for(int i=1;i<number.length;i++) {
			number[0]=gcd(number[0],number[i]);	
		}
		return Math.abs(number[0]);
	}
	
	static int gcd(int num1,int num2) {
		if(num2==0)
			return num1;
		return gcd(num2,num1%num2);
	}

}

