package com.teamderpy.victusludus.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;


/**
 * The Class MultiMap.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class MultiMap<K, V>{
	
	/** The multi map. */
	private Map<K, Vector<V>> multiMap;

	/* this little guy holds all current values */
	/** The raw value list. */
	private Vector<V> rawValueList;

	/**
	 * Instantiates a new multi map.
	 */
	public MultiMap(){
		this.multiMap = new HashMap<K, Vector<V>>();
		this.rawValueList = new Vector<V>();
	}

	/**
	 * Adds the.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void add(final K key, final V value){
		if(!this.multiMap.containsKey(key)){
			this.multiMap.put(key, new Vector<V>());
		}

		this.multiMap.get(key).add(value);
		this.rawValueList.add(0, value);
	}

	/**
	 * Removes the.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void remove(final K key, final V value){
		if(this.multiMap.containsKey(key)){
			while(this.multiMap.get(key).contains(value)){
				this.multiMap.get(key).remove(value);
			}

			while(this.rawValueList.contains(value)){
				this.rawValueList.remove(value);
			}

			if(this.multiMap.get(key).isEmpty()){
				this.multiMap.remove(key);
			}
		}
	}

	/**
	 * Gets the value list.
	 *
	 * @param key the key
	 * @return the value list
	 */
	public Vector<V> getValueList(final K key){
		if(this.multiMap.containsKey(key)){
			return this.multiMap.get(key);
		}

		return null;
	}

	/**
	 * Gets the all values.
	 *
	 * @return the all values
	 */
	public Vector<V> getAllValues(){
		return this.rawValueList;
	}

	/**
	 * Gets the all keys.
	 *
	 * @return the all keys
	 */
	public Set<K> getAllKeys(){
		return this.multiMap.keySet();
	}
}
