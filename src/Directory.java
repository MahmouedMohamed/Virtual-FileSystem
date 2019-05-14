import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Directory {
		private String DirectoryPath;
		private   Vector <File> files=new Vector<File>();
		private   Vector <Directory> SubDirectories= new Vector <Directory>();
		public void SetPath(String path)
		{
			DirectoryPath=path;
		}
		
		Directory(String s)
		{
			DirectoryPath=s;
		}
		public Directory() {}
		public void deletefile(String name)                 ////delete file with it's name
		{
			for(int i=0;i<files.size();i++)
			{
				if(name.equals(files.elementAt(i).getfilepath()))
				{
					files.remove(i);
				}
			}
		}
		public String getspecificfile(int index)            ///get file with it's index
		{
			return files.elementAt(index).getfilepath();
		}
		public int getspecificfileindexblock(int index)             ////get first block
		{
			return files.elementAt(index).getindexblock();
		}
		public int getspecificfilelastindexblock(int index)              ////get last block
		{
			return files.elementAt(index).getfinalindexblock();
		}
		public void SetFiles(String names,String blocks)         ////////////set file with it's blocks
		{
			if(names.contains(" "))                  /////that means more that 1 file
    		{
    			String[] name=names.split(" ");
    			String blockss[]=blocks.split("  ");
				for(int i=0;i<name.length;i++)
    			{	
    				Vector<Integer>vv=new Vector <Integer>();
    				String blocksss[]=blockss[i].split(" ");
    				for(int j=0;j<blocksss.length;j++){  vv.add(Integer.parseInt(blocksss[j])); }
    				File f = new File(name[i],vv);
    				files.add(f);
    			}
    		}
    		else if(names.length()==0)                     //////that means directory doesn't contain any files
    		{}
    		else                              /////that means directory contain 1 file
    		{
    			Vector <Integer> v=new Vector <Integer>();
    			String blockss[]=blocks.split(" ");
    			for(int i=0;i<blockss.length;i++){ 	v.add(Integer.parseInt(blockss[i])); }
    			File f=new File(names,v);
				files.add(f);
    		}
		}
		public void SetSub(String sub)
		{
			Directory Sub=new Directory(sub);
			SubDirectories.add(Sub);
		}
		public int numexist(int filenumber,int number)              ////get number or -1
		{
			return files.elementAt(filenumber).numexist(number);
		}
		public String getdirectorypath()                ////get directory path
		{
			return DirectoryPath;
		}
		public int getfilessize()               ////get number of files in directory
		{
			return files.size();
		}
		public Vector<Integer> getblocks(int x)              ///get blocks allocated to file number x
		{
			return files.elementAt(x).getblocks();
		}
		public Vector<String> getsubdir()               ////get all sub directories
		{	
			Vector <String> names=new Vector <String>();
			for(int i=0;i<SubDirectories.size();i++){names.add(SubDirectories.elementAt(i).getdirectorypath());}
				return names;
		}
		public Vector getfiles()          //get all files in directory
		{	
			Vector <String> names=new Vector <String>();
			for(int i=0;i<files.size();i++){ names.add(files.elementAt(i).getfilepath()); }
				return names;
		}
		public boolean fileExist(String path)			///file exist in this directory or not
		{
			if(files.size()==0)
				return false;
						
			for(int i=0;i<files.size();i++)
			{
				if(path.equals(files.elementAt(i).getfilepath())) 
					{
					return true;
					}
				
			}
			return false;
		}
		public  boolean directoryExist(String path)                  //Directory is there or not
		{
			if(SubDirectories.size()==0)
				return false;
						
			for(int i=0;i<SubDirectories.size();i++)
			{
				if(path.equals(SubDirectories.elementAt(i).getdirectorypath())||path.equals(getdirectorypath())) {
					return true;
				}
			}
			return false;
		}
		public int getblock(int ind,int index)
			{
				return files.elementAt(ind).getspecificblock(index);
			}
}