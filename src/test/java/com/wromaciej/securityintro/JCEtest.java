package com.wromaciej.securityintro;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hamcrest.Matchers;
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

	static class MutualClass implements Cloneable {
		private int mutualValue;

		public MutualClass(int mutualValue) {
			super();
			this.mutualValue = mutualValue;
		}
		
		

		@Override
		public String toString() {
			return "MutualClass [mutualValue=" + mutualValue + "]";
		}



		public int getMutualValue() {
			return mutualValue;
		}

		public void setMutualValue( int mutualValue ) {
			this.mutualValue = mutualValue;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + mutualValue;
			return result;
		}

		@Override
		public boolean equals( Object obj ) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			MutualClass other = (MutualClass) obj;
			if (mutualValue != other.mutualValue) {
				return false;
			}
			return true;
		}



		@Override
		protected Object clone() throws CloneNotSupportedException {
			return super.clone();
		}
		
		

	}

	static class ValuedClass implements Comparable<ValuedClass>, Cloneable {
		int value;
		static int objectsCreated;
		int objectNumber = objectsCreated + 1;

		private MutualClass mutualObject;

		public MutualClass getMutualObject() {
			return mutualObject;
		}

		public void setMutualObject( MutualClass mutualObject ) {
			this.mutualObject = mutualObject;
		}

		public ValuedClass(int value) {
			this.value = value;
			objectsCreated++;
			mutualObject = new MutualClass(value);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((mutualObject == null) ? 0 : mutualObject.hashCode());
			result = prime * result + objectNumber;
			result = prime * result + value;
			return result;
		}

		@Override
		public boolean equals( Object obj ) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ValuedClass other = (ValuedClass) obj;
			if (mutualObject == null) {
				if (other.mutualObject != null) {
					return false;
				}
			} else if (!mutualObject.equals(other.mutualObject)) {
				return false;
			}
			if (objectNumber != other.objectNumber) {
				return false;
			}
			if (value != other.value) {
				return false;
			}
			return true;
		}

		@Override
		public int compareTo( ValuedClass o ) {
			return value - o.value;
		}

		@Override
		public String toString() {
			return "ValuedClass [value=" + value + ", objectNumber=" + objectNumber
					+ ", mutualObject=" + mutualObject + "]";
		}

		@Override
		protected ValuedClass clone() throws CloneNotSupportedException {
			return (ValuedClass) super.clone();
		}

	}

	@Test
	public void shouldSortListWithDefaultComparator() {
		// given
		ValuedClass big = new ValuedClass(9);
		ValuedClass medium = new ValuedClass(5);
		ValuedClass small = new ValuedClass(1);

		List<ValuedClass> list = new ArrayList<>();
		list.add(big);
		list.add(small);
		list.add(medium);
		// when
		Collections.sort(list);
		// then
		assertThat(list.get(0), is(small));
		assertThat(list.get(1), is(medium));
		assertThat(list.get(2), is(big));

		assertThat(big.objectNumber, is(1));
		assertThat(medium.objectNumber, is(2));
		assertThat(small.objectNumber, is(3));

	}

	@Test
	public void shouldSortListWithDescendingOrderWithAdHocComparator() {
		// given
		ValuedClass big = new ValuedClass(9);
		ValuedClass medium = new ValuedClass(5);
		ValuedClass small = new ValuedClass(1);

		List<ValuedClass> list = new ArrayList<>();
		list.add(big);
		list.add(small);
		list.add(medium);
		// when
		Comparator<ValuedClass> comparator = ( v1, v2 ) -> v2.value - v1.value;

		Collections.sort(list, comparator);
		// then
		assertThat(list.get(0), is(big));
		assertThat(list.get(1), is(medium));
		assertThat(list.get(2), is(small));

		assertThat(big.objectNumber, is(1));
		assertThat(medium.objectNumber, is(2));
		assertThat(small.objectNumber, is(3));

	}

	@Test
	public void shouldDeepClone() throws CloneNotSupportedException {
		// given
		ValuedClass original = new ValuedClass(8);
		// when
		ValuedClass copy = original.clone();
		// then
		assertThat(copy, is(original));
		original.mutualObject.mutualValue = 0;
		assertThat(copy, Matchers.not(original));

	}

}
