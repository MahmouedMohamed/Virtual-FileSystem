import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class Manager {
	private Vector <Directory> Directories=new Vector<Directory>();
	FreeSpaceManager SpaceManager;
	int Choosen;
	int Number=0;
	private static String commands[]= {"createfile","createfolder","deletefile","deletefolder","displaydiskstatus","displaydiskstructure"};
	public void getall(int Ch)              /////////////////get data based on choice 
	{
		if(Ch==1)
		{
		try {GetFromFileCont();}
		catch (IOException e) {e.printStackTrace();}
		}
		if(Ch==2)
		{
			try {GetFromFileLink();}
			catch (IOException e) {e.printStackTrace();}
		}
		if(Ch==3)
		{
			try {GetFromFileIndx();}
			catch (IOException e) {e.printStackTrace();}
		}
	}
	Manager(int Ch)
	{
		Choosen=Ch;
		SpaceManager=new FreeSpaceManager();
		SpaceManager.getAllocator(Ch);
		getall(Ch);
		SpaceManager.SetData(Directories, Number);
	}
	public void GetFromFileCont() throws IOException            ///////////////if he choosed 1 get data from contiguous allocation folder
	{
        String line = null; String line2=null;
        int numberofblocks=20;
        Number=numberofblocks;
        try {
        	FileReader fileReader = new FileReader("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\SubDirectories.txt");
        	BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
            	Directory dir=new Directory(line);
    	        FileReader filereader=new FileReader("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\SubDirectories.txt");
            	 BufferedReader bufferedreader = new BufferedReader(filereader);
            	 while((line2 =bufferedreader.readLine())!=null)
            	{	            			
            		if(line2.contains(line)&&line2.equals(line)!=true)
            		{
            			dir.SetSub(line2);
            		}
            	}
            	Directories.add(dir);
            	filereader.close();
            	bufferedreader.close();
            }
            FileReader filereader=new FileReader("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\Files.txt");
        	BufferedReader bufferedreader = new BufferedReader(filereader);
        	FileReader blockreader=new FileReader("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\Blocks.txt");
        	BufferedReader bufferedblock = new BufferedReader(blockreader);
        	String line3=null; String line4=null; int linenumber=0;
            while((line3=bufferedreader.readLine())!=null&&(line4=bufferedblock.readLine())!=null)
        	{
            	Directories.elementAt(linenumber).SetFiles(line3,line4);
            	linenumber++;
        	}
            filereader.close();
            bufferedreader.close();
            SpaceManager.SetData(Directories,numberofblocks);
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file" );                
        }
	}
	public  void GetFromFileLink() throws IOException              ///////////////if he choosed 2 get data from link allocation folder
	{
		String line = null;String line2=null;
        try {
        	FileReader fileReader = new FileReader("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Linked Allocation\\SubDirectories.txt");
        	BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
            	Directory dir=new Directory(line);
    	        FileReader filereader=new FileReader("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Linked Allocation\\SubDirectories.txt");
            	 BufferedReader bufferedreader = new BufferedReader(filereader);
            	 while((line2 =bufferedreader.readLine())!=null)
            	{	            			
            		if(line2.contains(line)&&line2.equals(line)!=true)
            		{
            			dir.SetSub(line2);
            		}
            	}
            	Directories.add(dir);
            	filereader.close();
            	bufferedreader.close();
            }
            FileReader filereader1=new FileReader("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Linked Allocation\\Files.txt");
        	BufferedReader bufferedreader1 = new BufferedReader(filereader1);
        	FileReader filereader2=new FileReader("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Linked Allocation\\Blocks.txt");
        	BufferedReader bufferedreader2 = new BufferedReader(filereader2);
        	FileReader filereader3=new FileReader("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Linked Allocation\\Blocks2.txt");
        	BufferedReader bufferedreader3 = new BufferedReader(filereader3);
        	String line3=null; String line4=null; String line5=null;
        	Vector <String> blocks=new Vector <String>();
        	while((line5=bufferedreader3.readLine())!=null)
    		{
    			String subsub[]=line5.split(" ");
    			blocks.add(subsub[1]);
    		}
        	int linenumber=0;
            while((line3=bufferedreader1.readLine())!=null&&(line4=bufferedreader2.readLine())!=null)
        	{	
            	String BLOCKS="";
            	if(line4.contains("  "))
        		{
        			String[] sub=line4.split("  ");
    				for(int i=0;i<sub.length;i++)
        			{	
        				String blockss[]=sub[i].split(" ");
                    	line4="";
                    	while(blockss[0].equals(blockss[1])!=true)
                    	{
                    		line4+=blockss[0]+" ";
                    		int offset=Integer.parseInt(blockss[0]);
                    		blockss[0]=blocks.elementAt(offset);
                    	}
                    	line4+=blockss[1];
                		BLOCKS+=line4;
                		BLOCKS+="  ";
        			}
        		}
        		else if(line4.length()==0){}
        		else
        		{
        			String sub[]=line4.split(" ");
                	line4="";
                	while(sub[0].equals(sub[1])!=true)
                	{
                		line4+=sub[0]+" ";
                		int offset=Integer.parseInt(sub[0]);
                		sub[0]=blocks.elementAt(offset);
                	}
                	line4+=sub[1];
                	BLOCKS+=line4;
        		}
            	Directories.elementAt(linenumber).SetFiles(line3,BLOCKS);
            	BLOCKS="";
            	linenumber++;
        	}
        	filereader1.close();
            bufferedreader1.close();
        	filereader2.close();
            bufferedreader2.close();
            filereader3.close();
            bufferedreader3.close();
            Number=blocks.size();
        }
        catch(FileNotFoundException ex) {  System.out.println("Unable to open file" );  }
        
	}
	public void GetFromFileIndx() throws IOException                 ///////////////if he choosed 3 get data from index allocation folder
	{
        String line = null;
        String line2 = null;
        try {FileReader fileReader = new FileReader("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\SubDirectories.txt");
    	BufferedReader bufferedReader = new BufferedReader(fileReader);
        while((line = bufferedReader.readLine()) != null) {
        	Directory dir=new Directory(line);
	        FileReader filereader=new FileReader("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\SubDirectories.txt");
        	 BufferedReader bufferedreader = new BufferedReader(filereader);
        	 while((line2 =bufferedreader.readLine())!=null)
        	{	            			
        		if(line2.contains(line)&&line2.equals(line)!=true)
        		{
        			dir.SetSub(line2);
        		}
        	}
        	Directories.add(dir);
        	filereader.close();
        	bufferedreader.close();
        }
        FileReader filereader1=new FileReader("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Files.txt");
    	BufferedReader bufferedreader1 = new BufferedReader(filereader1);
    	FileReader filereader2=new FileReader("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Blocks.txt");
    	BufferedReader bufferedreader2 = new BufferedReader(filereader2);
    	FileReader filereader3=new FileReader("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Blocks2.txt");
    	BufferedReader bufferedreader3 = new BufferedReader(filereader3);
    	String line3=null; String line4=null; String line5=null;
    	Vector <String> blocks=new Vector <String>();
    	while((line5=bufferedreader3.readLine())!=null)
		{
			String subsub[]=line5.split("  ");
			blocks.add(subsub[1]);
		}
    	int linenumber=0;
        while((line3=bufferedreader1.readLine())!=null&&(line4=bufferedreader2.readLine())!=null) ////line3 hwa  2.txt 3.txt  ////line4 hwa 5 8 
    	{	
        	String BLOCKS="";
        	if(line3.contains(" "))
        	{
        		String Sub[]=line4.split(" ");
        		for(int i =0;i<Sub.length;i++)
        		{
        			BLOCKS+=Sub[i]+" "+blocks.elementAt(Integer.parseInt(Sub[i]));
        			BLOCKS+="  "	;
        		}
        	}
        	else if (line3.length()==0) {}
        	else
        	{
        		BLOCKS+=line4+" "+blocks.elementAt(Integer.parseInt(line4));												////line3 hwa 1.txt       ///////line4 7 //////line 5 
        	}
        	Directories.elementAt(linenumber).SetFiles(line3,BLOCKS);
        	BLOCKS="";
        	linenumber++;
    	}
    	filereader1.close();
        bufferedreader1.close();
    	filereader2.close();
        bufferedreader2.close();
        filereader3.close();
        bufferedreader3.close();
        Number=blocks.size();
        }
        catch(FileNotFoundException ex) {   System.out.println("Unable to open file" ); }
	}
	boolean fileexist(String folder,String filename)                  /////if file exists in directory or not
	{
		for(int i=0;i<Directories.size();i++)
		{
			if(Directories.elementAt(i).getdirectorypath().equals(folder)&&Directories.elementAt(i).fileExist(filename)==true)
				{return true;}
		}
		return false;
	}
	boolean	direxist(String path)                               ////if  sub directory exists in directory or not
	{
		for(int i=0;i<Directories.size();i++)
		{
			if(Directories.elementAt(i).directoryExist(path)==true)
				{
				return true;
				}
		}
		return false;
	}
	void ExecuteCommand(String command)                  ////Executor 
	{
		String[] args=command.split(" ");                   
		String arg0=args[0].toLowerCase();                  
		for(int i=0;i<commands.length;i++)
		{
			if(arg0.equals(commands[i]))
			{

				if(i==0)            /////createfile
				{
						String arg1=args[1].toLowerCase();
						int arg2=Integer.parseInt(args[2]);
						String[] ex=arg1.split("/");
						String folder="";
						for(int ii=0;ii<ex.length-1;ii++) {folder+=ex[ii];folder+="/";}
						if(fileexist(folder,ex[ex.length-1])==false&&direxist(folder)==true&&arg2<=SpaceManager.getEmptySpace()&&arg2>0&&ex[ex.length-1].contains(" ")!=true)
						{
							SpaceManager.Allocate(arg1, arg2);
						}
				}
				if(i==1)         ////////create folder
				{	
						String arg1=args[1].toLowerCase();
						String ex[]=arg1.split("/");
						String folder="";
						for(int ii=0;ii<ex.length-1;ii++) {folder+=ex[ii];folder+="/";}
						if(direxist(folder)==true&&ex[ex.length-1].contains(" ")!=true)
						{
							try {SpaceManager.createfolder(arg1);}
							catch (IOException e) {e.printStackTrace();}
						}
					
				}
				if(i==2)              /////deletefile
				{
					String arg1=args[1].toLowerCase();
					String[] ex=arg1.split("/");
					String folder="";
					for(int ii=0;ii<ex.length-1;ii++) {folder+=ex[ii];folder+="/";}
						if(fileexist(folder,ex[ex.length-1])==true)
						{
							SpaceManager.deletefile(arg1);
						}
				}
				if(i==3)
				{
						String arg1=args[1].toLowerCase();
						if(direxist(arg1)==true)
						{
							SpaceManager.deletefolder(arg1); ///////////////////MUST BE like "root/newfolder"/"///////////////////////////////
						}
				}
				if(i==4)            ///displaydiskstatus
				{
					System.out.println("EmptySpaces= "+SpaceManager.getEmptySpace()+"KB");
					System.out.println("AllocatedSpaces= "+SpaceManager.getAllocatedSpace()+"KB");
					System.out.println("Empty Blocks are "+SpaceManager.getEmptyBlocks());
					System.out.println("Allocated Blocks are "+SpaceManager.getAllocatedBlocks());
				}
				if(i==5)								//displaydiskstructure
				{
					for(int j=0;j<Directories.size();j++)
					{
						System.out.println(Directories.elementAt(j).getdirectorypath());
						for(int k=0;k<Directories.elementAt(j).getfilessize();k++)
						{
							System.out.println(" "+Directories.elementAt(j).getspecificfile(k));
						}
					}
				}
			}
		}
		SpaceManager.SetData(Directories, Number);    ///////set data to allocator 
	}
}
