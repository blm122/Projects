package cs445.a4;

import java.util.Arrays;
import cs445.a4.Primes;

public class HashingLookup<I, C> extends Primes implements LookupInterface<I, C>{
	
	int size;
	int loadsize = 0;
	
	//I[] key = (I[])new Object[1];
    //C[] identifier = (C[])new Object[1];
    
    private Pair[] hashContents;
    
    private final Pair TOMBSTONE = new Pair(null, null);
    
    private class Pair {
        I key;
        C value;
        Pair(I k, C v) {
            key = k;
            value = v;
        }
        public String toString() {
            return "{" + key + ", " + value + "}";
        }
    }
    
    
    private int hash(I key) {
        int h = key.hashCode() % hashContents.length;
        // Ensure non-negative
        if (h < 0) h += hashContents.length;
        return h;
    }

	
	
	@SuppressWarnings("unchecked")
	public HashingLookup(int capacity)
	{
		int x = primeNoLessThan(capacity);
		hashContents = (Pair[])new HashingLookup<?,?>.Pair[x];	//get the next smallest prime of size
		size = 0;
		loadsize = 0;
	}

	@SuppressWarnings("unchecked")
	public HashingLookup() 
	{
		hashContents = (Pair[])new HashingLookup<?,?>.Pair[19];
		size = 0;
		loadsize = 0;
	}
	
	@Override
	public int getSize() {
		
		return size;
	}

	@Override
	public boolean isEmpty() {
		
		if(size != 0)
		{
			return false;
		}
		
		else
		{
		return false;
		}
	}

	@Override
	public boolean add(I newId, C newCon) throws NullPointerException {
		
		if(newId == null)
		{
			throw new NullPointerException();
		}
		
		if(get(newId) != null)
		{
			return false;
		}
		
		if(isTooFull())
		{
			increaseCapacity();
		}
		
	    int h = hash(newId);
		int i = 0;
		int tombLoc = -1;
		int index =  (h + i) % hashContents.length;
		
		while (hashContents[index] != null &&
                (hashContents[index] == TOMBSTONE || !hashContents[index].key.equals(newId))) {		//&& i<hashContents.length
            if (hashContents[index] == TOMBSTONE && tombLoc < 0) tombLoc = index;
            i++;
            index = (h + i) % hashContents.length;
        }
																			//if index == hashContents.length - 1 -> index = 0
        if (hashContents[index] == null) {
            size++;
            if (tombLoc >= 0) {
                index = tombLoc;
            } else {
                loadsize++;
            }
        } 

        hashContents[index] = new Pair(newId, newCon);
        
       System.out.println(hashContents[index] + " " + index);
		
		
		return true;
	}

	@Override
	public C replace(I id, C newCon) throws IdentifierNotFoundException {
		
		C result = null;
		int h = hash(id);
		int i = 0;
		int index =  (h + i) % hashContents.length;
		
		if( get(id) == null)
		{
			throw new IdentifierNotFoundException();
		}
		else
		{
			while (hashContents[index] != null || hashContents[index] == TOMBSTONE)
			{
				if(hashContents[index].key.equals(id))
						{
							result = hashContents[index].value;
							hashContents[index] = new Pair(id, newCon);
							System.out.println("replaced");
							return result;
						}
				else
				{
					 i++;
			         index = (h + i) % hashContents.length;
			         result = null;
				}
			}
		}
		
		
		return result;
	}

	@Override
	public C get(I id) throws IdentifierNotFoundException {
		
		//C result;
		int h = hash(id);
		int i = 0;
		int index =  (h + i) % hashContents.length;
		
		while (hashContents[index] != null || hashContents[index] == TOMBSTONE)
		{
			if (hashContents[index].key.equals(id))
			{
				System.out.println("get method returned " + hashContents[index].toString() + " " + index);
				return hashContents[index].value;
			}
			else
			{
				 i++;
		         index = (h + i) % hashContents.length; 
			}
		}
		
		return null;
	}

	@Override
	public C remove(I id) throws IdentifierNotFoundException, NullPointerException {
		
		C result;
		int h = hash(id);
		int i = 0;
		int index =  (h + i) % hashContents.length;
		
		if(id == null)
		{
			throw new NullPointerException();
		}
		
		while (hashContents[index] != null || hashContents[index] == TOMBSTONE)
		{
			if (hashContents[index].key.equals(id))
			{
				result = hashContents[index].value;
				hashContents[index] = TOMBSTONE;
				System.out.println("Specified removal of " + result.toString() + " " + index);
				size--;
				return result;
			}
			else
			{
				 i++;
		         index = (h + i) % hashContents.length; 
			}
		}
		
		throw new IdentifierNotFoundException();
		//return null;
		
	}

	@Override
	public Object[] remove() {
		
		int x = 0;
		Object[] result = new Object[2];
		
		if(size == 0)
		{
		return null;
		}
		else
		{
			while(hashContents[x] == null || hashContents[x] == TOMBSTONE)
			{
				x++;
			}
			
			//System.out.println("arbitrarily removed " + hashContents[x].toString() + " " + x);
			result[0] = hashContents[x].key;
			result[1] = hashContents[x].value;
			hashContents[x] = TOMBSTONE;
			
			size--;
			
			return result;
			
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		
		hashContents = (Pair[])new HashingLookup<?,?>.Pair[19];
		size = 0;
		
	}

	@Override
	public boolean contains(I id) throws NullPointerException {
		
		return get(id) != null;

	}

	@Override
	public Object[] getAllIdentifiers() {
		
		Object[] array = new Object[size];
		int y = 0;
		
		for(int x = 0; x < hashContents.length; x++)
		{
			if(hashContents[x] != null && hashContents[x] != TOMBSTONE)
			{
				array[y] = hashContents[x].key;
				y++;
			}
		}
		
		return array;
	}
	
	
	
	private boolean isTooFull() 
	 	{
	        return (loadsize > 0.5 * hashContents.length);
	    }

	    @SuppressWarnings("unchecked")
	private void increaseCapacity() 
	    {
	        Pair[] old = hashContents;
	        int newCap = Primes.primeNoLessThan(hashContents.length * 2);

	        hashContents = (Pair[])new HashingLookup<?,?>.Pair[newCap];
	        size = 0; 
	        loadsize = 0;

	        for (int i = 0; i < old.length; i++) {
	            if (old[i] != null && old[i] != TOMBSTONE) {
	                add(old[i].key, old[i].value);
	            }
	        }
	    }

}
