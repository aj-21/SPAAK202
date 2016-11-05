import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * PropertyInfo is a class to store Characters' Proteries Info
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PropertyInfo  
{
    Map<String,Set<String>> properties = new HashMap<String,Set<String>>();
    
    public void PropertyInfo()
    {
    }
    
    /**
     * add new key-value pairs of properties.
     */
    public void putProperties(Map<String,String> properties)
    {
        for(Map.Entry<String,String> entry:properties.entrySet())
            putProperty(entry);
    }
    
    /**
     * add a new key-value pair of property
     */
    public void putProperty(Map.Entry<String,String> property)
    {
        putProperty(property.getKey(),property.getValue());
    }
    
    /**
     * mach a new value to a key. If key does not exist, new key will be created
     */
    public void putProperty(String key, String value)
    {
        Set<String> values = properties.get(key);            
        if (values == null)
        {
            values = new HashSet<String>();
            values.add(value);
            properties.put(key,values);
            return;
        }
        values.add(value);
    }
    
    /**
     * get only value info mapped with an option key. Optionkey is required
     */
    public Set<String> getValues(String key)
    {
        return properties.get(key);
    }
    
    /**
     * get all property Info
     */
    public Set<String> getKeys()
    {
        return properties.keySet();
    }
    
    /**
     * method to for printing info
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        for(Map.Entry<String,Set<String>> prop:properties.entrySet())
        {
            String key = prop.getKey();
            sb.append(key).append(" has: ");
            sb.append(prop.getValue().toString()).append("\n");
        }
        
        return sb.toString();
    }
    
}
