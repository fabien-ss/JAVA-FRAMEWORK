
package utilitaire;

/**
 *
 * @author fabien
 */

public class FileUpload {
    
    String name;
    String path;
    byte[] bytes;
    
    public FileUpload(){}
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) throws Exception{
        if(path.equals("")) throw new Exception("no path found");
        this.path = path;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) throws Exception{
        this.bytes = bytes;
    }
    
    public void setName(String name) throws Exception{
        if(name.equals("")) throw new Exception("Tsy hita ilay sary");
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public static void main(String[] args) {
        System.out.println("hello");
    }
}
