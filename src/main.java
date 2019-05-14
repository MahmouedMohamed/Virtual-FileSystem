import java.io.IOException;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		System.out.println("Enter Type Of Saving: \n"+"1-Contiguous Allocation \n"+"2-Linked Allocation \n"+"3-Indexed Allocation \n");
		int choosen=-1;
		while(choosen>3||choosen<0)
		{
			try{
				choosen=new Scanner(System.in).nextInt();   
				}
			catch(Exception ex) {ex.printStackTrace();}
		}
		Manager root=new Manager(choosen);
		while(true) 
		{
			System.out.println("Please Enter Your Command");
		    String Command=new Scanner(System.in).nextLine();
		if(Command.equals("stop"))
		{
			System.exit(0);
		}
		try{
			root.ExecuteCommand(Command);
			}
		catch(Exception ex)
		{
			System.out.print("Bad Command");
		}
		
		}
	}
}
