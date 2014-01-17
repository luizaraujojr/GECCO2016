package br.unirio.functionpoint.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Class that represents a record element within a data function
 * 
 * @author Marcio Barros
 */
public class RecordElement 
{
	/**
	 * Record element's name
	 */
	private @Getter String name; 
	
	/**
	 * List of data elements recognizable by the user
	 */
	private List<String> visibleDataElements;

	/**
	 * List of data elements unrecognizable by the user
	 */
	private List<String> invisibleDataElements;

	/**
	 * Initializes the record element
	 */
	public RecordElement(String name)
	{
		this.name = name;
		this.visibleDataElements = new ArrayList<String>();
		this.invisibleDataElements = new ArrayList<String>();
	}
	
	/**
	 * Adds a visible data element to the record
	 */
	public void addVisibleDET(String name)
	{
		visibleDataElements.add(name);
	}
	
	/**
	 * Adds an invisible data element to the record
	 */
	public void addInvisibleDataElement(String name)
	{
		invisibleDataElements.add(name);
	}
	
	/**
	 * Returns the number of visible data elements in the record
	 */
	public int countVisibleDataElements()
	{
		return visibleDataElements.size();
	}
	
	/**
	 * Returns the number of invisible data elements in the record
	 */
	public int countInvisibleDataElements()
	{
		return invisibleDataElements.size();
	}
	
	/**
	 * Returns the list of visible data elements
	 */
	public Iterable<String> getVisibleDataElements()
	{
		return visibleDataElements;
	}
	
	/**
	 * Returns the list of invisible data elements
	 * @return
	 */
	public Iterable<String> getInvisibleDataElements()
	{
		return invisibleDataElements;
	}
	
	/**
	 * Returns a complete list of data elements
	 */
	public Iterable<String> getDataElements()
	{
		List<String> allDET = new ArrayList<String>();
		allDET.addAll(visibleDataElements);
		allDET.addAll(invisibleDataElements);
		return allDET;
	}

	/**
	 * Counts the number of data elements with a given name
	 */
	public int countDataElementByName(String name) 
	{
		int count = 0;
		
		for (String s : visibleDataElements)
			if (s.compareToIgnoreCase(name) == 0)
				count++;
		
		for (String s : invisibleDataElements)
			if (s.compareToIgnoreCase(name) == 0)
				count++;
		
		return count;
	}

	/**
	 * Returns the index of a given data element
	 */
	public int getDataElementIndex(String det)
	{
		for (int i = 0; i < visibleDataElements.size(); i++)
			if (visibleDataElements.get(i).compareToIgnoreCase(det) == 0)
				return i;
		
		for (int i = 0; i < invisibleDataElements.size(); i++)
			if (invisibleDataElements.get(i).compareToIgnoreCase(det) == 0)
				return visibleDataElements.size() + i;
		
		return -1;
	}
}