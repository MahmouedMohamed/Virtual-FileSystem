import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.stream.Collectors;
import java.io.BufferedWriter;
import java.io.File;
import java.util.List;
import java.util.Scanner;
public class ContiguousAllocator implements Allocator {
		int NumberOfBlocks =0;																		
		Vector <Integer> Block=new Vector <Integer>();
		private Vector <Directory> directories=new Vector <Directory>();
	@Override
	public void SetData(Vector<Directory> dir, int number)         ///////////////////////////////setter for data 
	{
		NumberOfBlocks=number;
		directories=dir;
		Block.clear();
		for(int i=0;i<number;i++)
		{
			Block.add(1);
		}
		for(int i=0;i<directories.size();i++)
		{
			for(int j=0;j<directories.elementAt(i).getfilessize();j++)
			{
				Vector <Integer> blocks=new Vector<Integer>();
				blocks=directories.elementAt(i).getblocks(j);
				for(int k=0;k<blocks.size();k++)
				{
					Block.set(blocks.elementAt(k), 0);
				}
			}
		}
	}
	public int parseFile(String file,String foldername) throws FileNotFoundException{ /////////////////////////////get offset of directory that contain a file
        Scanner scan = new Scanner(new File(file));
        int offset=0;
        while(scan.hasNext()){
            String line = scan.nextLine().toString();
            if(line.equals(foldername)){
            	return offset;
            }
            offset++;
        }
        return offset;
    }
	public Vector<Integer> parseFiles(String folder,String foldername) throws FileNotFoundException{          //////get offset of all sub directories when i want to delete header
        Scanner scan = new Scanner(new File(folder));
        Vector<Integer> offset=new Vector <Integer>();
        int Offset=0;
        while(scan.hasNext()){
            String line = scan.nextLine().toString();
            if(line.contains(foldername)){
            	 offset.add(Offset);
            }
            Offset++;
        }
        return offset;
    }
	@Override
	public void Allocate(String filepath, int Size) //////////////File Creation 
	{
		Vector<Integer> AllocatedSpace=new Vector <Integer>();
		String[] ex=filepath.split("/");
		String folder="";
		int offset=0;
		for(int ii=0;ii<ex.length-1;ii++) {folder+=ex[ii];folder+="/";}
    	for(int i=0;i<NumberOfBlocks;i++)
    	{
    		if(Size>0) {
    		if(Block.elementAt(i)==1)
    		{
    			AllocatedSpace.add(i);
    			Size--;
    		}
    		else
    		{
    			Size+=AllocatedSpace.size();
    			AllocatedSpace.clear();
    		}
    		}
    		if(i==NumberOfBlocks-1&&Size>1) /////////////////////if empty blocks aren't connected 
    		{
    			Size+=AllocatedSpace.size();
    			AllocatedSpace.clear();
    			System.out.println("Insufficient Storage");
    		}
    	}
    	if(AllocatedSpace.size()>0)           /////////////////mission succeeded *GTA sound track*
    	{
        		for(int i=0;i<AllocatedSpace.size();i++)
        		{
        			Block.set(AllocatedSpace.elementAt(i), 0);
        		}
                try {
    				offset=parseFile("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\SubDirectories.txt",folder);
    			} catch (FileNotFoundException e) {e.printStackTrace();}
                String x="";
                for(int i=0;i<AllocatedSpace.size();i++)
                {
                	x+=Integer.toString(AllocatedSpace.elementAt(i));
                	if(i+1<AllocatedSpace.size())
                	{
                		x+=" ";
                	}
                }
                directories.elementAt(offset).SetFiles(ex[ex.length-1],x);           ///////set file 
        		PrintWriter writer;
                try {
        			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\SubDirectories.txt");
        			writer.print("");
        			writer.close();
        			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\Files.txt");
        			writer.print("");
        			writer.close();
        			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\Blocks.txt");
        			writer.print("");
        			writer.close();
        		} catch (FileNotFoundException e2) {e2.printStackTrace();}
                String KOLO="";
    			for(int i=0;i<directories.size();i++)                 //////////////////////writing to directory file .txt 
    			{
    				try {
    					KOLO=directories.elementAt(i).getdirectorypath();
    					FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\SubDirectories.txt", true);
    					PrintWriter printWriter = new PrintWriter(fileWriter);
    					if(i+1<directories.size())
    				    {
    					    printWriter.println(KOLO);
    				    }
    				    else
    				    {
    				    	printWriter.print(KOLO);
    				    }
    				    printWriter.close();
    			}catch (IOException e) {	e.printStackTrace();}
    			}
    			KOLO="";
    			for(int i=0;i<directories.size();i++)                        ////writing to files .txt           
    			{
    				for(int j=0;j<directories.elementAt(i).getfilessize();j++)
    				{
    					KOLO+=directories.elementAt(i).getspecificfile(j);
    					if(j+1<directories.elementAt(i).getfilessize())
    					{
    						KOLO+=" ";
    					}
    				}	
    				try {
    							FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\Files.txt", true);
    							PrintWriter printWriter = new PrintWriter(fileWriter);
    							if(i+1<directories.size())
    						    {
    							    printWriter.println(KOLO);
    						    }
    						    else
    						    {
    						    	printWriter.print(KOLO);
    						    }
    							printWriter.close();
    						}catch (IOException e) {e.printStackTrace();}
    					KOLO="";
    				}
    			KOLO="";
    			for(int i=0;i<directories.size();i++)           ////////////////////writing index block for each file 
    			{
    				for(int j=0;j<directories.elementAt(i).getfilessize();j++)
    				{
    						for(int k=0; k<directories.elementAt(i).getblocks(j).size();k++)
    							{
    								KOLO+=directories.elementAt(i).getblock(j,k);
    								if(k+1<directories.elementAt(i).getblocks(j).size())
    	    						{
    	    							KOLO+=" ";
    	    						}
    							}
    						if(j+1<directories.elementAt(i).getfilessize())
    						{
    							KOLO+="  ";
    						}
    				}
    				try {
    					FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\Blocks.txt", true);
    					PrintWriter printWriter = new PrintWriter(fileWriter);
    					System.out.println(KOLO);
    			    	printWriter.println(KOLO);
    			    	printWriter.close();
    				}catch(IOException e) {e.printStackTrace();}
    				KOLO="";
    			}
    			KOLO="";
    	}
	}
	@Override
	public String getEmptyBlocks() {              /////////////getting empty blocks
		String blocks="";
    	for(int i=0;i<Block.size();i++)
    	{
    		if(Block.elementAt(i)==1)
    		{
    			blocks+=i+" ";
    		}
    	}
    	return blocks;
	}
	@Override
	public int getEmptySpace() {           /////////get empty spaces 
		int Size = 0;
    	for(int i=0;i<Block.size();i++)
    	{
    		if(Block.elementAt(i)==1)
    		{
    			Size+=1;
    		}
    	}
    	return Size;
	}
	@Override
	public int getAllocatedSpace() {         ///////////get allocated spaces 
		int Size = 0;
    	for(int i=0;i<Block.size();i++)
    	{
    		if(Block.elementAt(i)==1)
    		{
    			Size+=1;
    		}
    	}
    	return Size;
	}
	@Override
	public String getAllocatedBlocks() {            ///////////getting allocated blocks
		String blocks="";
    	for(int i=0;i<Block.size();i++)
    	{
    		if(Block.elementAt(i)==0)
    		{
    			blocks+=i+" ";
    		}
    	}
    	return blocks;
	}
	@Override
	public void createfolder(String arg1) {            ////////////folder creation 
		String filename="C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\SubDirectories.txt";
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(filename, true));
			writer.newLine();  
			writer.write(arg1+"/");
			writer.close();
		} catch (IOException e) {e.printStackTrace();}
		
	}
	@Override
	public void deletefolder(String folder) {              ////////////deleting folder 
		int offset=0;
		try {
			offset=parseFile("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\SubDirectories.txt",folder);
		} catch (IOException e) {e.printStackTrace();	}
		System.out.println(directories.elementAt(1).getsubdir().toString());
		Vector <Integer> indexes=new Vector <Integer>();
		for(int i=0;i<directories.size();i++)          ////////////////////////////////get all folders indexes which are sub folder 
			{
			if(directories.elementAt(i).getdirectorypath().contains((directories.elementAt(offset).getdirectorypath())))
			{
				indexes.add(i);
			}
			}
		PrintWriter writer;
		try {
			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\SubDirectories.txt");
			writer.print("");
			writer.close();
			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\Files.txt");
			writer.print("");
			writer.close();
			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\Blocks.txt");
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e2) {	e2.printStackTrace();}
		String KOLO="";
		while(indexes.size()>0)              //////////////deleting those folders
		{
			directories.removeElementAt((indexes.elementAt(indexes.size()-1)));
			indexes.removeElementAt(indexes.size()-1);
		}
		for(int i=0;i<directories.size();i++)
		{
			try {
				KOLO=directories.elementAt(i).getdirectorypath();
				FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\SubDirectories.txt", true);
				PrintWriter printWriter = new PrintWriter(fileWriter);
				if(i+1<directories.size())
			    {
				    printWriter.println(KOLO);
			    }
			    else
			    {
			    	printWriter.print(KOLO);
			    }
			    printWriter.close();
		}catch (IOException e) {e.printStackTrace();}
		}
		KOLO="";
		for(int i=0;i<directories.size();i++)
		{
			for(int j=0;j<directories.elementAt(i).getfilessize();j++)
			{
				KOLO+=directories.elementAt(i).getspecificfile(j);
				if(j+1<directories.elementAt(i).getfilessize())
				{
					KOLO+=" ";
				}
			}	
			try {
						FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\Files.txt", true);
						PrintWriter printWriter = new PrintWriter(fileWriter);
						if(i+1<directories.size())
					    {
						    printWriter.println(KOLO);
					    }
					    else
					    {
					    	printWriter.print(KOLO);
					    }
						printWriter.close();
					}catch (IOException e) {e.printStackTrace();}
				KOLO="";
			}
		KOLO="";
		for(int i=0;i<directories.size();i++)           ////////////////////writing index block for each file 
		{
			for(int j=0;j<directories.elementAt(i).getfilessize();j++)
			{
					for(int k=0; k<directories.elementAt(i).getblocks(j).size();k++)
						{
							KOLO+=directories.elementAt(i).getblock(j,k);
							if(k+1<directories.elementAt(i).getblocks(j).size())
    						{
    							KOLO+=" ";
    						}
						}
					if(j+1<directories.elementAt(i).getfilessize())
					{
						KOLO+="  ";
					}
			}
			try {
				FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\Blocks.txt", true);
				PrintWriter printWriter = new PrintWriter(fileWriter);
				System.out.println(KOLO);
		    	printWriter.println(KOLO);
		    	printWriter.close();
			}catch(IOException e) {e.printStackTrace();}
			KOLO="";
		}
		KOLO="";
	}
	@Override
	public void deletefile(String arg1) {
		String[] ex=arg1.split("/");
		String folder="";
		for(int i =0;i<ex.length-1;i++)
		{
			folder+=ex[i];
			folder+="/";
		}
		String file=ex[ex.length-1];
		int offset=0;
		try {
			offset=parseFile("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\SubDirectories.txt",folder);
		} catch (IOException e) {	e.printStackTrace();	}
		directories.elementAt(offset).deletefile(file);              ///////////delete file at offset
		PrintWriter writer;
        try {
			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\SubDirectories.txt");
			writer.print("");
			writer.close();
			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\Files.txt");
			writer.print("");
			writer.close();
			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\Blocks.txt");
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e2) {e2.printStackTrace();	}
        String KOLO="";
		for(int i=0;i<directories.size();i++)
		{
			try {
				KOLO=directories.elementAt(i).getdirectorypath();
				FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\SubDirectories.txt", true);
				PrintWriter printWriter = new PrintWriter(fileWriter);
				if(i+1<directories.size())
			    {
				    printWriter.println(KOLO);
			    }
			    else
			    {
			    	printWriter.print(KOLO);
			    }
			    printWriter.close();
		}catch (IOException e) {	e.printStackTrace();}
		}
		KOLO="";
		for(int i=0;i<directories.size();i++)
		{
			for(int j=0;j<directories.elementAt(i).getfilessize();j++)
			{
				KOLO+=directories.elementAt(i).getspecificfile(j);
				if(j+1<directories.elementAt(i).getfilessize())
				{
					KOLO+=" ";
				}
			}	
			try {
						FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\Files.txt", true);
						PrintWriter printWriter = new PrintWriter(fileWriter);
						if(i+1<directories.size())
					    {
						    printWriter.println(KOLO);
					    }
					    else
					    {
					    	printWriter.print(KOLO);
					    }
						printWriter.close();
					}catch (IOException e) {e.printStackTrace();}
				KOLO="";
			}
		KOLO="";
		for(int i=0;i<directories.size();i++)           ////////////////////writing index block for each file 
		{
			for(int j=0;j<directories.elementAt(i).getfilessize();j++)
			{
					for(int k=0; k<directories.elementAt(i).getblocks(j).size();k++)
						{
							KOLO+=directories.elementAt(i).getblock(j,k);
							if(k+1<directories.elementAt(i).getblocks(j).size())
    						{
    							KOLO+=" ";
    						}
						}
					if(j+1<directories.elementAt(i).getfilessize())
					{
						KOLO+="  ";
					}
			}
			try {
				FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Contiguous Allocation\\Blocks.txt", true);
				PrintWriter printWriter = new PrintWriter(fileWriter);
		    	printWriter.println(KOLO);
		    	printWriter.close();
			}catch(IOException e) {e.printStackTrace();}
			KOLO="";
		}
		KOLO="";
	}
	}


