import java.io.IOException;
import java.util.Vector;

public class FreeSpaceManager {
		private  Allocator allocator;
		public Allocator getAllocator(int ch)         ////////////factory method
		{
			if(ch==1)
			{
				return allocator= new ContiguousAllocator();
			}
			else if(ch==2)
			{
				return allocator= new LinkedAllocator();
			}
			else if(ch==3)
			{
				return allocator= new IndexedAllocator();
			}
			return null;
		}
	    public void SetData(Vector<Directory> directories,int number)
	    {
	    	allocator.SetData(directories,number);
	    }
	    public  int getEmptySpace()
	    {
	    	return allocator.getEmptySpace();
	    }
	    public  int getAllocatedSpace()
	    {
	    	return allocator.getAllocatedSpace();
	    }
	    public  String getEmptyBlocks()
	    {	
	    	return allocator.getEmptyBlocks();
	    }
	    public  String getAllocatedBlocks()
	    {	
	    	return allocator.getAllocatedBlocks();
	    }
	    public  void Allocate(String filepath,int Size)
	    {
	    	allocator.Allocate(filepath, Size);
	    }
		public void createfolder(String arg1) throws IOException {
			allocator.createfolder(arg1);
		}
		public void deletefolder(String arg1) {
			allocator.deletefolder(arg1);
		}
		public void deletefile(String arg1) {
			allocator.deletefile(arg1);
		}  
}