package edu.iastate.cs228.hw3;

/**
 *  
 * @author jgmartin
 *
 */

import java.util.ListIterator;

public class PrimeFactorization implements Iterable<PrimeFactor>
{
	private static final long OVERFLOW = -1;
	private long value; 	// the factored integer 
							// it is set to OVERFLOW when the number is greater than 2^63-1, the
						    // largest number representable by the type long. 
	
	/**
	 * Reference to dummy node at the head.
	 */
	private Node head;
	  
	/**
	 * Reference to dummy node at the tail.
	 */
	private Node tail;
	
	private int size;     	// number of distinct prime factors


	// ------------
	// Constructors 
	// ------------
	
    /**
	 *  Default constructor constructs an empty list to represent the number 1.
	 *  
	 *  Combined with the add() method, it can be used to create a prime factorization.  
	 */
	public PrimeFactorization() 
	{	 
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
		size = 0;
	}

	
	/** 
	 * Obtains the prime factorization of n and creates a doubly linked list to store the result.   
	 * Follows the direct search factorization algorithm in Section 1.2 of the project description. 
	 * 
	 * @param n
	 * @throws IllegalArgumentException if n < 1
	 */
	public PrimeFactorization(long n) throws IllegalArgumentException 
	{
		this();
		if(n<1) throw new IllegalArgumentException();
		PrimeFactorizationIterator iter = iterator();
		int d = 2;
		int m = 0;
		long num = n;
		while((d*d)<=n)
		{
			if(isPrime(num))
			{
				PrimeFactor prime = new PrimeFactor((int)num,1);
				iter.add(prime);
				break;
			}
			if(isPrime(d)) 
			{
				while(num%d==0)
				{
					num=num/d;
					m++;
				}
				if(m!=0) 
				{
					PrimeFactor pf = new PrimeFactor(d,m);
					m=0;
					iter.add(pf);
				}
			}
			d++;
		}
		updateValue();
	}
	
	
	/**
	 * Copy constructor. It is unnecessary to verify the primality of the numbers in the list.
	 * 
	 * @param pf
	 */
	public PrimeFactorization(PrimeFactorization pf)
	{
		head=pf.head;
		tail=pf.tail;
		value=pf.value;
		size = pf.size;
	}
	
	/**
	 * Constructs a factorization from an array of prime factors.  Useful when the number is 
	 * too large to be represented even as a long integer. 
	 * 
	 * @param pflist
	 */
	public PrimeFactorization (PrimeFactor[] pfList)
	{
		this();
		int i = 0;
		while(i<pfList.length)
		{
			if(isPrime(pfList[i].prime)) 
			{
				add(pfList[i].prime,pfList[i].multiplicity);
			}
			i++;
		}
		updateValue();
	}
	
	

	// --------------
	// Primality Test
	// --------------
	
    /**
	 * Test if a number is a prime or not.  Check iteratively from 2 to the largest 
	 * integer not exceeding the square root of n to see if it divides n. 
	 * 
	 *@param n
	 *@return true if n is a prime 
	 * 		  false otherwise 
	 */
    public static boolean isPrime(long n) 
	{
    	boolean prime = true;
    	if(n<=1) return false;
    	for(int i = 2; i<=Math.sqrt(n); i++)
    	{
    		if(n % i == 0)
    		{
    			prime = false;
    			break;
    		}
    	}
    	return prime;
	}   

   
	// ---------------------------
	// Multiplication and Division 
	// ---------------------------
	
	/**
	 * Multiplies the integer v represented by this object with another number n.  Note that v may 
	 * be too large (in which case this.value == OVERFLOW). You can do this in one loop: Factor n and 
	 * traverse the doubly linked list simultaneously. For details refer to Section 3.1 in the 
	 * project description. Store the prime factorization of the product. Update value and size. 
	 * 
	 * @param n
	 * @throws IllegalArgumentException if n < 1
	 */
	public void multiply(long n) throws IllegalArgumentException 
	{
		if(n<1) throw new IllegalArgumentException();
		PrimeFactorization p = new PrimeFactorization(n);
		PrimeFactorizationIterator og = iterator();
		PrimeFactorizationIterator iter = p.iterator();
		PrimeFactor ogelement = og.next();
		PrimeFactor newelement = iter.next();
		iter.previous();
		while(og.hasNext()&&iter.hasNext())
		{
			if(ogelement.prime<newelement.prime)
			{
				add(newelement.prime,newelement.multiplicity);
				newelement = iter.next();
			}
			else if(ogelement.prime==newelement.prime)
			{
				ogelement.multiplicity+=newelement.multiplicity;
				newelement = iter.next();
				ogelement = og.next();
			}
			else
			{
				ogelement=og.next();
			}
		}
		while(!og.hasNext()&&iter.hasNext())//adds to end of list if necessary
		{
			add(newelement.prime,newelement.multiplicity);
			newelement = iter.next();
		}
		updateValue();
	}
	
	/**
	 * Multiplies the represented integer v with another number in the factorization form.  Traverse both 
	 * linked lists and store the result in this list object.  See Section 3.1 in the project description 
	 * for details of algorithm. 
	 * 
	 * @param pf 
	 */
	public void multiply(PrimeFactorization pf)
	{
		PrimeFactorizationIterator iter1 = iterator();
		PrimeFactorizationIterator iter2 = pf.iterator();
		PrimeFactor p1 = iter1.next();
		PrimeFactor p2 = iter2.next();
		int i = 1;
		while(i>0)
		{
			if(iter1.hasNext())
			{
				add(p1.prime,p1.multiplicity);
				p1=iter1.next();
			}
			else if(iter2.hasNext())
			{
				add(p2.prime,p2.multiplicity);
				p2=iter2.next();
			}
			else
			{
				add(p1.prime,p1.multiplicity);
				add(p2.prime,p2.multiplicity);
				break;
			}
		}
		updateValue();
	}
	
	
	/**
	 * Multiplies the integers represented by two PrimeFactorization objects.  
	 * 
	 * @param pf1
	 * @param pf2
	 * @return object of PrimeFactorization to represent the product 
	 */
	public static PrimeFactorization multiply(PrimeFactorization pf1, PrimeFactorization pf2)
	{
		PrimeFactorization ret = new PrimeFactorization();
		PrimeFactorizationIterator iter1 = pf1.iterator();
		PrimeFactorizationIterator iter2 = pf2.iterator();
		PrimeFactor p1 = iter1.next();
		PrimeFactor p2 = iter2.next();
		int i = 1;
		while(i>0)
		{
			if(iter1.hasNext())
			{
				ret.add(p1.prime,p1.multiplicity);
				p1=iter1.next();
			}
			else if(iter2.hasNext())
			{
				ret.add(p2.prime,p2.multiplicity);
				p2=iter2.next();
			}
			else
			{
				ret.add(p1.prime,p1.multiplicity);
				ret.add(p2.prime,p2.multiplicity);
				break;
			}
		}
		ret.updateValue();
		return ret;
	}

	
	/**
	 * Divides the represented integer v by n.  Make updates to the list, value, size if divisible.  
	 * No update otherwise. Refer to Section 3.2 in the project description for details. 
	 *  
	 * @param n
	 * @return  true if divisible 
	 *          false if not divisible 
	 * @throws IllegalArgumentException if n <= 0
	 */
	public boolean dividedBy(long n) throws IllegalArgumentException
	{
		if(this.value!=-1&&this.value<n) return false;
		PrimeFactorization pf1 = new PrimeFactorization(n);
		return dividedBy(pf1);
	}

	
	/**
	 * Division where the divisor is represented in the factorization form.  Update the linked 
	 * list of this object accordingly by removing those nodes housing prime factors that disappear  
	 * after the division.  No update if this number is not divisible by pf. Algorithm details are 
	 * given in Section 3.2. 
	 * 
	 * @param pf
	 * @return	true if divisible by pf
	 * 			false otherwise
	 */
	public boolean dividedBy(PrimeFactorization pf)
	{
		if((this.value != -1 && pf.value != -1 && this.value < pf.value)||(this.value != -1 && pf.value == -1)) return false;
		else if(this.value==pf.value)
		{
			clearList();
			value = 1;
			return true;
		}
		else
		{
			PrimeFactorization copy = new PrimeFactorization(this);
			PrimeFactorizationIterator iterCopy = copy.iterator();
			PrimeFactorizationIterator iterPf = pf.iterator();
			while(iterCopy.hasNext())
			{
				if(iterCopy.cursor.pFactor.prime >= iterPf.cursor.pFactor.prime)
				{
					if(iterCopy.cursor.pFactor.prime > iterPf.cursor.pFactor.prime)
					{
						return false;
					}
					else if((iterCopy.cursor.pFactor.prime == iterPf.cursor.pFactor.prime)&&(iterCopy.cursor.pFactor.multiplicity < iterPf.cursor.pFactor.multiplicity))
					{
						return false;
					}
					else
					{
						if((iterCopy.cursor.pFactor.prime == iterPf.cursor.pFactor.prime)&&(iterCopy.cursor.pFactor.multiplicity >= iterPf.cursor.pFactor.multiplicity))
						{
							 iterCopy.cursor.pFactor.multiplicity -= iterPf.cursor.pFactor.multiplicity;
							 if(iterCopy.cursor.pFactor.multiplicity==0) 
							 {
								 iterCopy.remove();
							 }
							 iterCopy.next();
							 iterPf.next();
						}
					}
				}
				if((!iterCopy.hasNext() && iterPf.hasNext()))
				{
					return false;
				}
				if(iterPf.cursor == pf.tail)
				{
					copy.updateValue();
					head = copy.head;
					tail = copy.tail;
					size = copy.size;
					value = copy.value;
				}
			}
			
		}
		return true;
	}

	
	/**
	 * Divide the integer represented by the object pf1 by that represented by the object pf2. 
	 * Return a new object representing the quotient if divisible. Do not make changes to pf1 and 
	 * pf2. No update if the first number is not divisible by the second one. 
	 *  
	 * @param pf1
	 * @param pf2
	 * @return quotient as a new PrimeFactorization object if divisible
	 *         null otherwise 
	 */
	public static PrimeFactorization dividedBy(PrimeFactorization pf1, PrimeFactorization pf2)
	{
		PrimeFactorization ret = null;
		if((pf1.value != -1 && pf2.value != -1 && pf1.value < pf2.value)||(pf1.value != -1 && pf2.value == -1)) return null;
		else if(pf1.value==pf2.value)
		{	
			ret = new PrimeFactorization();
			ret.value = 1;
			return ret;
		}
		else
		{
			PrimeFactorization pfone = new PrimeFactorization(pf1);
			PrimeFactorization pftwo = new PrimeFactorization(pf2);
			PrimeFactorizationIterator iter1 = pfone.iterator();
			PrimeFactorizationIterator iter2 = pftwo.iterator();
			while(iter1.hasNext())
			{
				if(iter1.cursor.pFactor.prime >= iter2.cursor.pFactor.prime)
				{
					if(iter1.cursor.pFactor.prime > iter2.cursor.pFactor.prime)
					{
						return null;
					}
					else if((iter1.cursor.pFactor.prime == iter2.cursor.pFactor.prime)&&(iter1.cursor.pFactor.multiplicity < iter2.cursor.pFactor.multiplicity))
					{
						return null;
					}
					else
					{
						if((iter1.cursor.pFactor.prime == iter2.cursor.pFactor.prime)&&(iter1.cursor.pFactor.multiplicity >= iter2.cursor.pFactor.multiplicity))
						{
							 iter1.cursor.pFactor.multiplicity -= iter2.cursor.pFactor.multiplicity;
							 if(iter1.cursor.pFactor.multiplicity==0) 
							 {
								 iter1.remove();
							 }
							 iter1.next();
							 iter2.next();
						}
					}
				}
				if((!iter1.hasNext() && iter2.hasNext()))
				{
					return null;
				}
				if(iter1.cursor == pfone.tail)
				{
					pfone.updateValue();
					ret.head = pfone.head;
					ret.tail = pfone.tail;
					ret.size = pfone.size;
					ret.value = pfone.value;
				}
			}
			
		}
		return ret;
	}

	
	// -----------------------
	// Greatest Common Divisor
	// -----------------------

	/**
	 * Computes the greatest common divisor (gcd) of the represented integer v and an input integer n.
	 * Returns the result as a PrimeFactor object.  Calls the method Euclidean() if 
	 * this.value != OVERFLOW.
	 *     
	 * It is more efficient to factorize the gcd than n, which can be much greater. 
	 *     
	 * @param n
	 * @return prime factorization of gcd
	 * @throws IllegalArgumentException if n < 1
	 */
	public PrimeFactorization gcd(long n) throws IllegalArgumentException
	{
		if(n<1) throw new IllegalArgumentException();
		if(this.value!=OVERFLOW)
		{
			PrimeFactorization ret = new PrimeFactorization(Euclidean(this.value,n));
			return ret;
		}
		return null;
	}
	

	/**
	  * Implements the Euclidean algorithm to compute the gcd of two natural numbers m and n. 
	  * The algorithm is described in Section 4.1 of the project description. 
	  * 
	  * @param m
	  * @param n
	  * @return gcd of m and n. 
	  * @throws IllegalArgumentException if m < 1 or n < 1
	  */
 	public static long Euclidean(long m, long n) throws IllegalArgumentException
	{
 		if(m<1||n<1) throw new IllegalArgumentException();
 		long bigger;
 		long smaller;
 		long temp;
 		if(m>n)
 		{
 			bigger = m;
 			smaller = n;
 		}
 		else if(n>m)
 		{
 			bigger = n;
 			smaller = m;
 		}
 		else //can set bigger or smaller to m or n if they are equal
 		{
 			bigger = n;
 			smaller = m;
 		}
 		while(true)
 		{
 			if(bigger%smaller==0) return smaller; // once the remainder is zero, the greatest common factor will be what was just divided
 			temp = bigger%smaller;
 			bigger = smaller;
 			smaller = temp;
 		}
	}

 	
	/**
	 * Computes the gcd of the values represented by this object and pf by traversing the two lists.  No 
	 * direct computation involving value and pf.value. Refer to Section 4.2 in the project description 
	 * on how to proceed.  
	 * 
	 * @param  pf
	 * @return prime factorization of the gcd
	 */
	public PrimeFactorization gcd(PrimeFactorization pf)
	{
		PrimeFactorizationIterator iter1 = iterator();
		PrimeFactorizationIterator iter2 = pf.iterator();
		PrimeFactorization ret = new PrimeFactorization();
		PrimeFactorizationIterator iter3 = ret.iterator();
		PrimeFactor p1 = iter1.next();
		PrimeFactor p2 = iter2.next();
		for(int i = 0; i<size; i++)
		{if(i!=0) p1 = iter1.next();
			for(int j = 0; j<pf.size;j++)
			{
				if(p1.prime==p2.prime)
				{
					if(p1.multiplicity<p2.multiplicity) iter3.add(p1);
					else iter3.add(p2);
				}
				p2 = iter2.next();
			}
		}
		ret.updateValue();
		return ret;
	}
	
	
	/**
	 * 
	 * @param pf1
	 * @param pf2
	 * @return prime factorization of the gcd of two numbers represented by pf1 and pf2
	 */
	public static PrimeFactorization gcd(PrimeFactorization pf1, PrimeFactorization pf2)
	{
		PrimeFactorizationIterator iter1 = pf1.iterator();
		PrimeFactorizationIterator iter2 = pf2.iterator();
		PrimeFactorization ret = new PrimeFactorization();
		PrimeFactorizationIterator iter3 = ret.iterator();
		PrimeFactor p1 = iter1.next();
		PrimeFactor p2 = iter2.next();
		for(int i = 0; i<pf1.size; i++)
		{if(i!=0) p1 = iter1.next();
			for(int j = 0; j<pf2.size;j++)
			{
				if(p1.prime==p2.prime)
				{
					if(p1.multiplicity<p2.multiplicity) iter3.add(p1);
					else iter3.add(p2);
				}
				p2 = iter2.next();
			}
		}
		ret.updateValue();
		return ret;
	}

	// ------------
	// List Methods
	// ------------
	
	/**
	 * Traverses the list to determine if p is a prime factor. 
	 * 
	 * Precondition: p is a prime. 
	 * 
	 * @param p  
	 * @return true  if p is a prime factor of the number v represented by this linked list
	 *         false otherwise 
	 * @throws IllegalArgumentException if p is not a prime
	 */
	public boolean containsPrimeFactor(int p) throws IllegalArgumentException
	{
		if(!isPrime(p)) throw new IllegalArgumentException();
		PrimeFactorizationIterator iter = iterator();
		PrimeFactor p1 = iter.next();
		while(iter.hasNext())
		{
			if(p1.prime==p)
			{
				return true;
			}
			p1 = iter.next();
		}
		return false;
	}
	
	// The next two methods ought to be private but are made public for testing purpose. Keep
	// them public 
	
	/**
	 * Adds a prime factor p of multiplicity m.  Search for p in the linked list.  If p is found at 
	 * a node N, add m to N.multiplicity.  Otherwise, create a new node to store p and m. 
	 *  
	 * Precondition: p is a prime. 
	 * 
	 * @param p  prime 
	 * @param m  multiplicity
	 * @return   true  if m >= 1
	 *           false if m < 1   
	 */
    public boolean add(int p, int m) 
    {
    	PrimeFactorizationIterator iter = iterator();
    	PrimeFactorizationIterator iter2 = iterator();
    	if(size==0)
    	{
    		PrimeFactor pnew = new PrimeFactor(p,m);
    		iter.add(pnew);
    		return !(m<1);
    	}
    	PrimeFactor p1 = iter2.next();
    	while(true)
    	{
    		if(p<p1.prime)
    		{
    			PrimeFactor pnew2 = new PrimeFactor(p,m);
    			p1=iter2.previous();
    			iter2.add(pnew2);
    			return !(m<1);
    		}
    		if(p==p1.prime)
    		{
    			p1.multiplicity+=m;
    			return !(m<1);
    		}
    		if(!iter2.hasNext()) break;
    		p1 = iter2.next();
    	}
    	PrimeFactor pnew3 = new PrimeFactor(p,m);
    	iter2.add(pnew3);
    	return !(m<1);
    	
    	
    }

	    
    /**
     * Removes m from the multiplicity of a prime p on the linked list.  It starts by searching 
     * for p.  Returns false if p is not found, and true if p is found. In the latter case, let 
     * N be the node that stores p. If N.multiplicity > m, subtracts m from N.multiplicity.  
     * If N.multiplicity <= m, removes the node N.  
     * 
     * Precondition: p is a prime. 
     * 
     * @param p
     * @param m
     * @return true  when p is found. 
     *         false when p is not found. 
     * @throws IllegalArgumentException if m < 1
     */
    public boolean remove(int p, int m) throws IllegalArgumentException
    {
    	if(!isPrime(p)) return false;
    	if(m<1) throw new IllegalArgumentException();
    	PrimeFactorizationIterator iter = iterator();
    	PrimeFactor p1 = iter.next();
    	while(iter.hasNext())
    	{
    		if(p1.prime == p)
    		{
    			if(p1.multiplicity<=m)
    			{
    				iter.remove();
    			}
    			else
    			{
    				p1.multiplicity-=m;
    			}
    			return true;
    		}
    		p1 = iter.next();
    	}
    	return false;
    }


    /**
     * 
     * @return size of the list
     */
	public int size() 
	{
		return size; 
	}

	
	/**
	 * Writes out the list as a factorization in the form of a product. Represents exponentiation 
	 * by a caret.  For example, if the number is 5814, the returned string would be printed out 
	 * as "2 * 3^2 * 17 * 19". 
	 */
	@Override 
	public String toString()
	{
		String ret = "";
		PrimeFactorizationIterator iter = iterator();
		PrimeFactor p = null;
		if(size>0) 
		{
			p = iter.next();
		}
		for(int i = 0; i<size; i++)
		{
			if(i==size-1)
			{
				if(p.multiplicity==1)
				{
					ret+=p.prime;
					p=iter.next();
				}
				else {
				ret+=p.prime+"^"+p.multiplicity;
				}
				break;
			}
			if(p.multiplicity==1)
			{
				ret+=p.prime+" * ";
				p=iter.next();
			}
			else 
			{
			ret+=p.prime+"^"+p.multiplicity+" * ";
			p=iter.next();
			}
		}
		return ret;
	}

	
	// The next three methods are for testing, but you may use them as you like.  

	/**
	 * @return true if this PrimeFactorization is representing a value that is too large to be within 
	 *              long's range. e.g. 999^999. false otherwise.
	 */
	public boolean valueOverflow() {
		return value == OVERFLOW;
	}

	/**
	 * @return value represented by this PrimeFactorization, or -1 if valueOverflow()
	 */
	public long value() {
		return value;
	}

	
	public PrimeFactor[] toArray() {
		PrimeFactor[] arr = new PrimeFactor[size];
		int i = 0;
		for (PrimeFactor pf : this)
			arr[i++] = pf;
		return arr;
	}


	
	@Override
	public PrimeFactorizationIterator iterator()
	{
	    return new PrimeFactorizationIterator();
	}
	
	/**
	 * Doubly-linked node type for this class.
	 */
    private class Node 
    {
		public PrimeFactor pFactor;			// prime factor 
		public Node next;
		public Node previous;
		
		/**
		 * Default constructor for creating a dummy node.
		 */
		public Node()
		{
			pFactor = null;
		}
	    
		/**
		 * Precondition: p is a prime
		 * 
		 * @param p	 prime number 
		 * @param m  multiplicity 
		 * @throws IllegalArgumentException if m < 1 
		 */
		public Node(int p, int m) throws IllegalArgumentException 
		{	
			if((m<1)||(!isPrime(p))) throw new IllegalArgumentException();
			pFactor = new PrimeFactor(p,m);
		}   

		
		/**
		 * Constructs a node over a provided PrimeFactor object. 
		 * 
		 * @param pf
		 * @throws IllegalArgumentException
		 */
		public Node(PrimeFactor pf)  
		{
			this(pf.prime,pf.multiplicity);
		}


		/**
		 * Printed out in the form: prime + "^" + multiplicity.  For instance "2^3". 
		 * Also, deal with the case pFactor == null in which a string "dummy" is 
		 * returned instead.  
		 */
		@Override
		public String toString() 
		{
			if(pFactor==null)
			{
				return "dummy";
			}
			return pFactor.prime+"^"+pFactor.multiplicity; 
		}
    }

    
    private class PrimeFactorizationIterator implements ListIterator<PrimeFactor>
    {  	
        // Class invariants: 
        // 1) logical cursor position is always between cursor.previous and cursor
        // 2) after a call to next(), cursor.previous refers to the node just returned 
        // 3) after a call to previous() cursor refers to the node just returned 
        // 4) index is always the logical index of node pointed to by cursor

        private Node cursor = head.next;
        private Node pending = null;    // node pending for removal
        private int index = 0;      
  	  	private static final int BEHIND = -1;
  	  	private static final int AHEAD = 1;
  	  	private static final int NONE = 0;
  	  	private int direction;
    	// other instance variables ... 
    	  
      
        /**
    	 * Default constructor positions the cursor before the smallest prime factor.
    	 */
    	public PrimeFactorizationIterator()
    	{
    		cursor = head.next;
    	}

    	@Override
    	public boolean hasNext()
    	{
    		return index < size;
    	}

    	
    	@Override
    	public boolean hasPrevious()
    	{ 
    		return index > 0;
    	}

 
    	@Override 
    	public PrimeFactor next() 
    	{
    		if(hasNext()) 
    		{
	    		PrimeFactor ret = cursor.pFactor;
	    		pending = cursor;
	    		cursor = cursor.next;
	    		++index;
	    		direction = BEHIND;
	    		return ret;
    		}
    		return null;
    	}

 
    	@Override 
    	public PrimeFactor previous() 
    	{
    		if(hasPrevious()) 
    		{
    			pending = cursor;
	    		cursor = cursor.previous;
	    		--index;
	    		direction = AHEAD;
	    		return cursor.pFactor;
    		}
    		return null;
    	}

   
    	/**
    	 *  Removes the prime factor returned by next() or previous()
    	 *  
    	 *  @throws IllegalStateException if pending == null 
    	 */
    	@Override
    	public void remove() throws IllegalStateException
    	{
    		if ((direction == NONE)||(pending == null)) {
    			throw new IllegalStateException();
    		} else {
    			if (direction == AHEAD) {
	    			// remove node at cursor and move to next node
	    			Node n = cursor.next;
	    			unlink(cursor);
	    			cursor = n;
    			} else {
	    			// remove node behind cursor and adjust index
	    			unlink(cursor.previous);
	    			--index;
    				}
    			}
    			--size;
    			direction = NONE;
    			pending = null;

    	}
 
 
    	/**
    	 * Adds a prime factor at the cursor position.  The cursor is at a wrong position 
    	 * in either of the two situations below: 
    	 * 
    	 *    a) pf.prime < cursor.previous.pFactor.prime and cursor.previous != head. 
    	 *    b) pf.prime > cursor.pFactor.prime and cursor != tail. 
    	 * 
    	 * Take into account the possibility that pf.prime == cursor.pFactor.prime. 
    	 * 
    	 * Precondition: pf.prime is a prime. 
    	 * 
    	 * @param pf  
    	 * @throws IllegalArgumentException if the cursor is at a wrong position. 
    	 */
    	@Override
        public void add(PrimeFactor pf) throws IllegalArgumentException 
        {
//        	if((!isPrime(pf.prime))||((pf.prime < cursor.previous.pFactor.prime)&&(cursor.previous != head))||((pf.prime > cursor.pFactor.prime)&&(cursor != tail)))
    		if(!isPrime(pf.prime))
    		{
        		throw new IllegalArgumentException();
        	}
        	else
        	{
        		Node temp = new Node(pf);
        		link(cursor.previous, temp);
        		++index;
        		++size;
        		direction = NONE;

        	}
        }


    	@Override
		public int nextIndex() 
		{
			return index;
		}


    	@Override
		public int previousIndex() 
		{
			return index - 1;
		}

		@Deprecated
		@Override
		public void set(PrimeFactor pf) 
		{
			throw new UnsupportedOperationException(getClass().getSimpleName() + " does not support set method");
		}
        
    	// Other methods you may want to add or override that could possibly facilitate 
    	// other operations, for instance, addition, access to the previous element, etc.
    	// 
    	// ...
    	// 
    }

    
    // --------------
    // Helper methods 
    // -------------- 
    
    /**
     * Inserts toAdd into the list after current without updating size.
     * 
     * Precondition: current != null, toAdd != null
     */
    private void link(Node current, Node toAdd)
    {
    	toAdd.previous = current;
    	toAdd.next = current.next;
    	current.next.previous = toAdd;
    	current.next = toAdd;
    }

	 
    /**
     * Removes toRemove from the list without updating size.
     */
    private void unlink(Node toRemove)
    {
    	toRemove.previous.next = toRemove.next;
    	toRemove.next.previous = toRemove.previous;

    }
    /**
	  * Remove all the nodes in the linked list except the two dummy nodes. 
	  * 
	  * Made public for testing purpose.  Ought to be private otherwise. 
	  */
	public void clearList()
	{
		link(head,tail); 
	}	
	
	/**
	 * Multiply the prime factors (with multiplicities) out to obtain the represented integer.  
	 * Use Math.multiplyExact(). If an exception is throw, assign OVERFLOW to the instance variable value.  
	 * Otherwise, assign the multiplication result to the variable. 
	 * 
	 */
	private void updateValue()
	{
		try 
		{		
			PrimeFactorizationIterator iter = iterator();	
			PrimeFactor p = iter.next();
			while(iter.hasNext())
			{
				if(p.multiplicity>1) 
				{
					for(int i = 0; i<p.multiplicity;i++) // if prime number is 3 and multiplicity is 3, this will do 3*3 3 times and add them up(which will be 9+9+9 which is also 3^3)
					{
						value+=Math.multiplyExact(p.prime,p.prime);
					}
					p = iter.next();	
				}
				else
				{
					value=Math.multiplyExact(p.prime,value);
					p = iter.next();
				}
			}
		} 
			
		catch (ArithmeticException e) 
		{
			value = OVERFLOW;
		}
		
	}
}
