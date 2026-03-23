public class Pokemon 
{
	private String name;
    private String type;
    private String speed;
    private String imagefile;
	    
    public Pokemon(String initName, String initType, String initSpeed, String initImagefile)
    {
        name = initName;
        type = initType;
        speed = initSpeed;
        imagefile = initImagefile;
    }

    public String getName()
    {
        return name;
    }
    public String getType()
    {
        return type;
    }
    public String getSpeed()
    {
    	return speed;    	
    }
    public String getImagefile()
    {
        return imagefile;
    }
}
