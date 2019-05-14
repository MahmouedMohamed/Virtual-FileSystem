import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.stream.Collectors;

public class IndexedAllocator implements Allocator {
	int NumberOfBlocks =0;
	Vector <Integer> Block=new Vector <Integer>();
	private Vector <Directory> directories=new Vector <Directory>();
	public int parseFile(String file,String foldername) throws FileNotFoundException{
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
	public Vector<Integer> parseFiles(String folder,String foldername) throws FileNotFoundException{
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
	public void Allocate(String filepath, int Size) {
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
    		}
    	}
    	if(AllocatedSpace.size()>0)
    	{
    		for(int i=0;i<AllocatedSpace.size();i++)
    		{
    			Block.set(AllocatedSpace.elementAt(i), 0);
    		}
            try {
				offset=parseFile("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\SubDirectories.txt",folder);
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
    			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\SubDirectories.txt");
    			writer.print("");
    			writer.close();
    			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Files.txt");
    			writer.print("");
    			writer.close();
    			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Blocks.txt");
    			writer.print("");
    			writer.close();
    			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Blocks2.txt");
    			writer.print("");
    			writer.close();
    		} catch (FileNotFoundException e2) {e2.printStackTrace();}
            String KOLO="";
			for(int i=0;i<directories.size();i++)                 //////////////////////writing to directory file .txt 
			{
				try {
					KOLO=directories.elementAt(i).getdirectorypath();
					FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\SubDirectories.txt", true);
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
							FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Files.txt", true);
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
			for(int i=0;i<directories.size();i++)
			{
				for(int j=0;j<directories.elementAt(i).getfilessize();j++)
				{
					KOLO+=Integer.toString(directories.elementAt(i).getspecificfileindexblock(j));
					if(j+1<directories.elementAt(i).getfilessize())
					{
						KOLO+=" ";
					}
				}
				try {
					FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Blocks.txt", true);
					PrintWriter printWriter = new PrintWriter(fileWriter);
					if(i+1<directories.size())
				    {
					    printWriter.println(KOLO);
				    }
				    else
				    {
				    	printWriter.print(KOLO);
				    }					printWriter.close();
				}catch (IOException e) {e.printStackTrace();}
				KOLO="";
			}
			KOLO="";
		Vector <Vector<Integer>>KOLOO=new Vector<Vector<Integer>>();
				for(int j=0;j<NumberOfBlocks;j++)
				{
					for(int k=0;k<directories.size();k++)
					{
						for(int c=0;c<directories.elementAt(k).getfilessize();c++)
						{
							if(j==directories.elementAt(k).getspecificfileindexblock(c))
							{
									KOLOO.add(directories.elementAt(k).getblocks(c));
							}
						}
					}
				}
				for(int i=0;i<NumberOfBlocks;i++)
				{
					for(int j=0;j<KOLOO.size();j++)
					{
						if(KOLOO.elementAt(j).elementAt(0)==i)
						{
							KOLO+=KOLOO.elementAt(j).elementAt(0)+"  ";
							for(int jj=1;jj<KOLOO.elementAt(j).size();jj++)
							{
								KOLO+=KOLOO.elementAt(j).elementAt(jj);
								if(jj+1<KOLOO.elementAt(j).size())
								{
									KOLO+=" ";
								}
							}
							try {
								FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Blocks2.txt", true);
								PrintWriter printWriter = new PrintWriter(fileWriter);
								printWriter.println(KOLO);
								printWriter.close();
							}catch (IOException e) {e.printStackTrace();}
							break;
						}
					}
					try {
						if(Integer.parseInt(KOLO.substring(0,KOLO.indexOf("  ")))==i);
					}catch(Exception exx)
					{
						KOLO=i + "  nil";
						try {
							FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Blocks2.txt", true);
							PrintWriter printWriter = new PrintWriter(fileWriter);
							printWriter.println(KOLO);
							printWriter.close();
						}catch (IOException e) {e.printStackTrace();}					
					}
					KOLO="";
				}
				KOLO="";
		}
    	}
	
	@Override
	public String getEmptyBlocks() {
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
	public int getEmptySpace() {
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
	public int getAllocatedSpace() {
		int Size = 0;
    	for(int i=0;i<Block.size();i++)
    	{
    		if(Block.elementAt(i)==0)
    		{
    			Size+=1;
    		}
    	}
    	return Size;
	}
	@Override
	public String getAllocatedBlocks() {
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
	public void SetData(Vector<Directory> dir, int number) {
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
	@Override
	public void createfolder(String arg1) throws IOException {
		String filename="C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\SubDirectories.txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
		writer.newLine();  
		writer.write(arg1+"/");
		writer.close();
	}
	@Override
	public void deletefolder(String folder) {
		int offset=0;
		try {
			offset=parseFile("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\SubDirectories.txt",folder);
		} catch (IOException e) {e.printStackTrace();}
		Vector <Integer> indexes=new Vector <Integer>();
		for(int i=0;i<directories.size();i++)
			{
			if(directories.elementAt(i).getdirectorypath().contains((directories.elementAt(offset).getdirectorypath())))
			{
				indexes.add(i);
			}
			}
		PrintWriter writer;
		try {
			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\SubDirectories.txt");
			writer.print("");
			writer.close();
			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Files.txt");
			writer.print("");
			writer.close();
			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Blocks.txt");
			writer.print("");
			writer.close();
			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Blocks2.txt");
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e2) {	e2.printStackTrace();}
		String KOLO="";
		while(indexes.size()>0)                  ///////////////delete sub folders first
		{
			directories.removeElementAt((indexes.elementAt(indexes.size()-1)));
			indexes.removeElementAt(indexes.size()-1);
		}
			for(int i=0;i<directories.size();i++)            /////////////////print folders to file
			{
				try {
					KOLO=directories.elementAt(i).getdirectorypath();
					FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\SubDirectories.txt", true);
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
			for(int i=0;i<directories.size();i++)            ///////////////print files file.txt
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
							FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Files.txt", true);
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
			for(int i=0;i<directories.size();i++)               ////////////print index blocks to file 
			{
				for(int j=0;j<directories.elementAt(i).getfilessize();j++)
				{
					KOLO+=Integer.toString(directories.elementAt(i).getspecificfileindexblock(j));
					if(j+1<directories.elementAt(i).getfilessize())
					{
						KOLO+=" ";
					}
				}
				try {
					FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Blocks.txt", true);
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
		Vector <Vector<Integer>>KOLOO=new Vector<Vector<Integer>>();           //////////////////get all blocks from indexes 
				for(int j=0;j<NumberOfBlocks;j++)
				{
					for(int k=0;k<directories.size();k++)
					{
						for(int c=0;c<directories.elementAt(k).getfilessize();c++)
						{
							if(j==directories.elementAt(k).getspecificfileindexblock(c))
							{
									KOLOO.add(directories.elementAt(k).getblocks(c));
							}
						}
					}
				}
				for(int i=0;i<NumberOfBlocks;i++)              ///////////////////printing blocks to blocks2 files
				{
					for(int j=0;j<KOLOO.size();j++)
					{
						if(KOLOO.elementAt(j).elementAt(0)==i)           ///////////if index == i 
						{
							KOLO+=KOLOO.elementAt(j).elementAt(0)+"  ";       ///print index + "  "
							for(int jj=1;jj<KOLOO.elementAt(j).size();jj++)    //////print rest of blocks numbers
							{
								KOLO+=KOLOO.elementAt(j).elementAt(jj);
								if(jj+1<KOLOO.elementAt(j).size())
								{
									KOLO+=" ";
								}
							}
							try {
								FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Blocks2.txt", true);
								PrintWriter printWriter = new PrintWriter(fileWriter);
								printWriter.println(KOLO);
								printWriter.close();
							}catch (IOException e) {e.printStackTrace();}
							break;
						}
					}
					try {                          //////////////else !!!!!!!!!!!!!!!!!!!!! print i then nil 
						if(Integer.parseInt(KOLO.substring(0,KOLO.indexOf("  ")))==i);
					}catch(Exception exx)
					{
						KOLO=i + "  nil";
						try {
							FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Blocks2.txt", true);
							PrintWriter printWriter = new PrintWriter(fileWriter);
							printWriter.println(KOLO);
							printWriter.close();
						}catch (IOException e) {e.printStackTrace();}					
					}
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
			offset=parseFile("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\SubDirectories.txt",folder);
		} catch (IOException e) {e.printStackTrace();}
		directories.elementAt(offset).deletefile(file);
		PrintWriter writer;
		try {
			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\SubDirectories.txt");
			writer.print("");
			writer.close();
			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Files.txt");
			writer.print("");
			writer.close();
			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Blocks.txt");
			writer.print("");
			writer.close();
			writer = new PrintWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Blocks2.txt");
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e2) {	e2.printStackTrace();}
		String KOLO="";
			for(int i=0;i<directories.size();i++)
			{
				try {
					KOLO=directories.elementAt(i).getdirectorypath();
					System.out.println(KOLO);
					FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\SubDirectories.txt", true);
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
							FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Files.txt", true);
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
			for(int i=0;i<directories.size();i++)
			{
				for(int j=0;j<directories.elementAt(i).getfilessize();j++)
				{
					KOLO+=Integer.toString(directories.elementAt(i).getspecificfileindexblock(j));
					if(j+1<directories.elementAt(i).getfilessize())
					{
						KOLO+=" ";
					}
				}
				try {
					FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Blocks.txt", true);
					PrintWriter printWriter = new PrintWriter(fileWriter);
					if(i+1<directories.size())
				    {
					    printWriter.println(KOLO);
				    }
				    else
				    {
				    	printWriter.print(KOLO);
				    }					printWriter.close();
				}catch (IOException e) {e.printStackTrace();}
				KOLO="";
			}
			KOLO="";
		Vector <Vector<Integer>>KOLOO=new Vector<Vector<Integer>>();
				for(int j=0;j<NumberOfBlocks;j++)
				{
					for(int k=0;k<directories.size();k++)
					{
						for(int c=0;c<directories.elementAt(k).getfilessize();c++)
						{
							if(j==directories.elementAt(k).getspecificfileindexblock(c))
							{
									KOLOO.add(directories.elementAt(k).getblocks(c));
							}
						}
					}
				}
				for(int i=0;i<NumberOfBlocks;i++)
				{
					for(int j=0;j<KOLOO.size();j++)
					{
						if(KOLOO.elementAt(j).elementAt(0)==i)
						{
							KOLO+=KOLOO.elementAt(j).elementAt(0)+"  ";
							for(int jj=1;jj<KOLOO.elementAt(j).size();jj++)
							{
								KOLO+=KOLOO.elementAt(j).elementAt(jj);
								if(jj+1<KOLOO.elementAt(j).size())
								{
									KOLO+=" ";
								}
							}
							try {
								FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Blocks2.txt", true);
								PrintWriter printWriter = new PrintWriter(fileWriter);
								printWriter.println(KOLO);
								printWriter.close();
							}catch (IOException e) {e.printStackTrace();}
							break;
						}
					}
					try {
						if(Integer.parseInt(KOLO.substring(0,KOLO.indexOf("  ")))==i);
					}catch(Exception exx)
					{
						KOLO=i + "  nil";
						try {
							FileWriter fileWriter = new FileWriter("C:\\Users\\hp\\eclipse-workspace\\Mahmoued\\FileSystem\\Indexed Allocation\\Blocks2.txt", true);
							PrintWriter printWriter = new PrintWriter(fileWriter);
							printWriter.println(KOLO);
							printWriter.close();
						}catch (IOException e) {e.printStackTrace();}					
					}
					KOLO="";
				}
				KOLO="";
		}
	}