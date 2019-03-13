package com.wromaciej.securityintro;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class JCEtest {
	

	@Test
	public void shouldHaveUnlimitedKey() throws NoSuchAlgorithmException {
		// given data in JDK
		// when
		int maxKeySize = javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
		// then
		assertThat(maxKeySize, greaterThan(1024));

	}
	
	static class ValuedClass implements Comparable<ValuedClass> {
		int value;
		static int objectsCreated;
		int objectNumber = objectsCreated+1;
		
		public ValuedClass(int value) {
			this.value = value;
			objectsCreated++;
		}

		@Override
		public int compareTo( ValuedClass o ) {
			return value - o.value;
		}

		@Override
		public String toString() {
			return "ValuedClass [value=" + value + ", objectNumber=" + objectNumber + "]";
		}
				

	}
	
	@Test
	public void shouldSortListWithDefaultComparator() {
		//given
		ValuedClass big = new ValuedClass(9);
		ValuedClass medium = new ValuedClass(5);
		ValuedClass small = new ValuedClass(1);

		List<ValuedClass> list = new ArrayList<>();
		list.add(big);
		list.add(small);
		list.add(medium);
		//when
		Collections.sort(list);
		//then
		assertThat(list.get(0), is(small));
		assertThat(list.get(1), is(medium));
		assertThat(list.get(2), is(big));
		
		assertThat(big.objectNumber, is(1));
		assertThat(medium.objectNumber, is(2));
		assertThat(small.objectNumber, is(3));
		
	}
	
	@Test
	public void shouldSortListWithDescendingOrderWithAdHocComparator() {
		//given
		ValuedClass big = new ValuedClass(9);
		ValuedClass medium = new ValuedClass(5);
		ValuedClass small = new ValuedClass(1);

		List<ValuedClass> list = new ArrayList<>();
		list.add(big);
		list.add(small);
		list.add(medium);
		//when
		Comparator<ValuedClass> comparator = (v1, v2) -> v2.value - v1.value;
		
		Collections.sort(list, comparator);
		//then
		assertThat(list.get(0), is(big));
		assertThat(list.get(1), is(medium));
		assertThat(list.get(2), is(small));
		
		assertThat(big.objectNumber, is(1));
		assertThat(medium.objectNumber, is(2));
		assertThat(small.objectNumber, is(3));
		
	}

}
