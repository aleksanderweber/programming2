import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.*;

public class StringHashSetTest {

    @Test
    public void sizeShouldReturnCountOfElementsInserted() {
        Set<String> set = new StringHashSet();
        set.add("Adam");
        set.add("Asia");
        set.add("Aleks");
        set.add("Rafał");
        set.add("Łukasz");
        set.add("Adrian");
        set.addAll(Arrays.asList("A", "B"));

        assertEquals(8, set.size());
    }

    @Test
    public void sizeShouldReturn0forEmptySet() {
        Set<String> set = new StringHashSet();
        assertEquals(0, set.size());
        set.add("Adam");
        set.remove("Adam");
        assertEquals(0, set.size());
    }

    @Test
    public void addingShouldntLetTheSameElement() {
        Set<String> set = new StringHashSet();
        set.add("Aleks");
        assertEquals(1, set.size());
        set.add("Aleks");
        assertEquals(1, set.size());
    }

    @Test
    public void isEmptyShouldReturnTrue() {
        Set<String> set = new StringHashSet();
        assertTrue(set.isEmpty());
        set.add("Aleks");
        assertFalse(set.isEmpty());
        set.remove("Aleks");
        assertTrue(set.isEmpty());
    }

    @Test
    public void removeShouldReturnTrue() {
        Set<String> set = new StringHashSet();
        set.add("Aleks");
        assertTrue(set.remove("Aleks"));
    }

    @Test
    public void removeShouldReturnFalse() {
        Set<String> set = new StringHashSet();
        set.add("Aleks");
        assertFalse(set.remove("Alek"));
    }

}