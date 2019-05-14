import java.util.Vector;

public class File {
		private String filepath;
		private Vector<Integer> allocatedblocks=new Vector<Integer>();
		public File(String name,Vector <Integer> v)
		{
			filepath=name;
			allocatedblocks=v;
		}
		public File(Vector <Integer> v)
		{
			allocatedblocks=v;
		}
		public String getfilepath()                     //get name of file 
		{
			return filepath;
		}
		public Vector<Integer> getblocks()               ///get blocks allocated to file
		{	
			return allocatedblocks;
		}
		public int getindexblock()                      ////get first block of blocks
		{	
			return allocatedblocks.elementAt(0);
		}
		public int getfinalindexblock()                 /////get last block of blocks
		{	
			return allocatedblocks.elementAt(allocatedblocks.size()-1);
		}
		public void setpath(String path)
		{
			filepath=path;
		}
		public int numexist(int num)                           ////if block number X is taken by a file   and if there : get it or next one if not : get -1 
		{
			for(int i=0;i<allocatedblocks.size();i++)
			{
				if(num==allocatedblocks.elementAt(i)&&i+1<allocatedblocks.size()) {
					return allocatedblocks.elementAt(i+1);
				}
			}
			return -1;
		}
		public void setblocks(Vector <Integer>v)
		{
			allocatedblocks=v;
		}
		public File(){	}
		public int getspecificblock(int index)
		{
			return allocatedblocks.elementAt(index);
		}
}
