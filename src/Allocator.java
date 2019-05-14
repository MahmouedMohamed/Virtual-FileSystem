import java.io.IOException;
import java.util.Vector;

public interface Allocator {
	public void Allocate(String filepath,int Size);
	public  String getEmptyBlocks();
    public  int getEmptySpace();
    public  int getAllocatedSpace();
    public  String getAllocatedBlocks();
    public void SetData(Vector<Directory> directories,int number);
	public void createfolder(String arg1) throws IOException;
	public void deletefolder(String arg1);
	public void deletefile(String arg1);
}
