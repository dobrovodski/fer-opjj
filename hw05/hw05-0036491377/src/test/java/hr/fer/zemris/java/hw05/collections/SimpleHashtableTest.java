package hr.fer.zemris.java.hw05.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * JUnit tests for SimpleHashtable.
 *
 * @author matej
 * @see <a href= "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html"> Naming standards for unit
 *         tests </a>
 */
public class SimpleHashtableTest {
    @Test(expected = IllegalArgumentException.class)
    public void SimpleHashtable_InitialCapacityLessThanOne_ExceptionThrown() {
        int initialCapacity = 0;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);
    }

    @Test
    public void Put_ALotOfSimpleValues_GetReturnsValue() {
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 1000;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        for (int i = 0; i < numberOfEntries; i++) {
            Assert.assertEquals(valueTemplate + i, ht.get(keyTemplate + i));
        }
    }

    @Test(expected = NullPointerException.class)
    public void Put_KeyNull_ExceptionThrown() {
        String key = null;
        String value = "value";
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();

        ht.put(key, value);
    }

    @Test
    public void Put_OverwriteValue_ValueOverwrittenSizeSame() {
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 20;
        int hashTableSize = 10;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(hashTableSize);

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        String key = keyTemplate + (numberOfEntries - 1);
        String value = valueTemplate + (numberOfEntries - 1);
        String newValue = valueTemplate + (numberOfEntries + 1);
        int size = ht.size();

        Assert.assertEquals(value, ht.get(key));
        ht.put(key, newValue);
        Assert.assertEquals(newValue, ht.get(key));
        Assert.assertEquals(size, ht.size());
    }

    @Test
    public void Put_ALotOfSimpleValuesOverwrite_Overwritten() {
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 1000;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + "2_" + i);
        }

        for (int i = 0; i < numberOfEntries; i++) {
            Assert.assertEquals(false, ht.containsValue(keyTemplate + i));
        }

        for (int i = 0; i < numberOfEntries; i++) {
            Assert.assertEquals(valueTemplate + "2_" + i, ht.get(keyTemplate + i));
        }
    }

    @Test
    public void Get_KeyNull_ReturnNull() {
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 4;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        Assert.assertEquals(null, ht.get(null));
    }

    @Test
    public void Get_NonExistentValueNonEmptyHashSlot_ReturnNull() {
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 4;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(1);

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        Assert.assertEquals(null, ht.get(keyTemplate + numberOfEntries));
    }

    @Test
    public void Get_NonExistentValueEmptyHashSlot_ReturnNull() {
        String key = "key";
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();

        Assert.assertEquals(null, ht.get(key));
    }

    @Test
    public void Get_DifferentTypeThanTable_ReturnNull() {
        int key = 10;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();

        Assert.assertEquals(null, ht.get(key));
    }

    @Test
    public void Size_NothingAdded_Zero() {
        int initialCapacity = 20;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

        Assert.assertEquals(0, ht.size());
    }

    @Test
    public void Size_NumValuesAdded_NumReturned() {
        int initialCapacity = 20;
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 10;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        Assert.assertEquals(numberOfEntries, ht.size());
    }

    @Test
    public void ContainsKey_NullKey_False() {
        int initialCapacity = 20;
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 10;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        Assert.assertEquals(false, ht.containsKey(null));
    }

    @Test
    public void ContainsKey_NonExistentKey_False() {
        String key = "key";
        int initialCapacity = 20;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

        Assert.assertEquals(false, ht.containsKey(key));
    }

    @Test
    public void ContainsKey_ExistentKey_True() {
        int initialCapacity = 10;
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 4;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        Assert.assertEquals(true, ht.containsKey(keyTemplate + (numberOfEntries - 1)));
    }

    @Test
    public void ContainsKey_DifferentTypeThanTable_False() {
        int key = 10;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();

        Assert.assertEquals(false, ht.containsKey(key));
    }

    @Test
    public void IsEmpty_NothingAdded_True() {
        int initialCapacity = 10;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

        Assert.assertEquals(true, ht.isEmpty());
    }

    @Test
    public void IsEmpty_NumValuesAdded_False() {
        int initialCapacity = 10;
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 5;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        Assert.assertEquals(false, ht.isEmpty());
    }

    @Test
    public void Remove_KeyNull_NoExceptionThrown() {
        int initialCapacity = 10;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);
        ht.remove(null);
    }

    @Test
    public void Remove_ExistentValue_SizeReducedByOne() {
        int initialCapacity = 10;
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 5;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        int originalSize = ht.size();
        ht.remove(keyTemplate + (numberOfEntries - 1));
        Assert.assertEquals(originalSize - 1, ht.size());
    }

    @Test
    public void Remove_ExistentValue_GetReturnsNull() {
        int initialCapacity = 10;
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 5;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        String key = keyTemplate + (numberOfEntries - 1);
        ht.remove(key);
        Assert.assertEquals(null, ht.get(key));
    }

    @Test
    public void Remove_ALotOfValues_ContainsFalseSizeZero() {
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 1000;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(1);

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        for (int i = numberOfEntries - 1; i >= 0; i--) {
            ht.remove(keyTemplate + i);
        }

        for (int i = 0; i < numberOfEntries; i++) {
            Assert.assertEquals(false, ht.containsKey(keyTemplate + i));
        }

        Assert.assertEquals(0, ht.size());
    }

    @Test
    public void Remove_NonExistentValue_NoExceptionThrown() {
        int initialCapacity = 10;
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 5;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        String key = keyTemplate + (numberOfEntries + 1);
        ht.remove(key);
    }

    @Test
    public void ContainsValue_ExistentValue_True() {
        int initialCapacity = 10;
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 5;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        String value = valueTemplate + (numberOfEntries - 1);
        Assert.assertEquals(true, ht.containsValue(value));
    }

    @Test
    public void ContainsValue_NonExistentValue_False() {
        int initialCapacity = 10;
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 5;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        String value = valueTemplate + (numberOfEntries + 1);
        Assert.assertEquals(false, ht.containsValue(value));
    }

    @Test
    public void ContainsValue_DifferentTypeThanTable_False() {
        int key = 10;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();

        Assert.assertEquals(false, ht.containsValue(key));
    }

    @Test
    public void ToString_Numbers_CorrectString() {
        SimpleHashtable<Integer, Integer> ht = new SimpleHashtable<>(4);
        ht.put(0, 10);
        ht.put(1, 11);
        ht.put(4, 14);

        String correct = "[0=10, 4=14, 1=11]";
        Assert.assertEquals(correct, ht.toString());
    }

    @Test
    public void Clear_SimpleValues_SizeZero() {
        int initialCapacity = 10;
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 5;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        ht.clear();

        Assert.assertEquals(0, ht.size());
    }

    @Test
    public void Clear_ALotOfValues_ContainsFalse() {
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 1000;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        ht.clear();

        for (int i = 0; i < numberOfEntries; i++) {
            Assert.assertEquals(false, ht.containsKey(keyTemplate + i));
        }
    }

    @Test
    public void IteratorHasNext_NextCalled_True() {
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 10;
        int size = 3;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(size);

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        Iterator it = ht.iterator();
        it.next();
        it.next();
        Assert.assertEquals(true, it.hasNext());
    }

    @Test
    public void IteratorHasNext_NextNotCalled_True() {
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 10;
        int size = 3;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(size);

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        Iterator it = ht.iterator();
        Assert.assertEquals(true, it.hasNext());
    }

    @Test
    public void IteratorHasNext_NothingAdded_False() {
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();

        Iterator it = ht.iterator();
        Assert.assertEquals(false, it.hasNext());
    }

    @Test
    public void IteratorHasNext_HasNextSameSlot_Retrieved() {
        SimpleHashtable<Integer, Integer> ht = new SimpleHashtable<>(4);
        ht.put(0, 10);
        ht.put(4, 20);

        Iterator<SimpleHashtable.TableEntry<Integer, Integer>> it = ht.iterator();
        it.next();
        Assert.assertEquals(true, it.hasNext());
    }

    @Test
    public void IteratorHasNext_AllValuesIteratedOVer_False() {
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 10;
        int size = 3;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>(size);

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        Iterator it = ht.iterator();
        for (int i = 0; i < numberOfEntries; i++) {
            it.next();
        }

        Assert.assertEquals(false, it.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void IteratorNext_NoNext_ExceptionThrown() {
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 10;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        Iterator it = ht.iterator();
        for (int i = 0; i < numberOfEntries; i++) {
            it.next();
        }

        it.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void IteratorNext_NoElementsInTable_ExceptionThrown() {
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();
        Iterator it = ht.iterator();
        it.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void IteratorNext_ConcurrentModification_ExceptionThrown() {
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 10;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        Iterator it = ht.iterator();
        it.next();
        ht.remove(keyTemplate + (numberOfEntries - 1));
        it.next();
    }

    @Test
    public void IteratorNext_HasNext_Retrieved() {
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 10;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        Iterator<SimpleHashtable.TableEntry<String, String>> it = ht.iterator();
        for (int i = 0; i < numberOfEntries; i++) {
            Assert.assertEquals(ht.get(keyTemplate + i), it.next().getValue());
        }
    }

    @Test
    public void IteratorNext_HasNextSameSlot_Retrieved() {
        SimpleHashtable<Integer, Integer> ht = new SimpleHashtable<>(4);
        ht.put(0, 10);
        ht.put(4, 20);

        Iterator<SimpleHashtable.TableEntry<Integer, Integer>> it = ht.iterator();
        Assert.assertEquals(ht.get(0), it.next().getValue());
        Assert.assertEquals(ht.get(4), it.next().getValue());
    }

    @Test(expected = ConcurrentModificationException.class)
    public void IteratorRemove_ConcurrentModification_ExceptionThrown() {
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 10;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        Iterator it = ht.iterator();
        it.next();
        ht.remove(keyTemplate + (numberOfEntries - 1));
        it.remove();
    }

    @Test(expected = IllegalStateException.class)
    public void IteratorRemove_CalledAgainWithoutCallingNext_ExceptionThrown() {
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 10;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        Iterator it = ht.iterator();
        it.next();
        it.remove();
        it.remove();
    }

    @Test(expected = IllegalStateException.class)
    public void IteratorRemove_CalledBeforeNext_ExceptionThrown() {
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 10;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        Iterator it = ht.iterator();
        it.remove();
    }

    @Test
    public void IteratorRemove_CalledAfterNext_ElementRemoved() {
        String keyTemplate = "key";
        String valueTemplate = "value";
        int numberOfEntries = 10;
        SimpleHashtable<String, String> ht = new SimpleHashtable<>();

        for (int i = 0; i < numberOfEntries; i++) {
            ht.put(keyTemplate + i, valueTemplate + i);
        }

        Iterator<SimpleHashtable.TableEntry<String, String>> it = ht.iterator();
        String key = it.next().getKey();
        Assert.assertEquals(true, ht.containsKey(key));
        it.remove();
        Assert.assertEquals(false, ht.containsKey(key));
    }

    @Test
    public void TableEntryEquals_AllCombinations_True() {
        SimpleHashtable.TableEntry te1 = new SimpleHashtable.TableEntry("key1", "value1");
        SimpleHashtable.TableEntry te2 = new SimpleHashtable.TableEntry("key1", "value2");
        Assert.assertEquals(true, te1.equals(te1));
        Assert.assertEquals(true, te2.equals(te2));
        Assert.assertEquals(true, te1.equals(te2));
        Assert.assertEquals(true, te2.equals(te1));
    }
}
