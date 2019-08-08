import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringHashSet implements Set<String> {
    private final double LOAD_FACTOR = 0.5;
    private final int INITIAL_BUCKET_SIZE = 4;
    private final int INCREASE_FACTOR = 2;

    private List<List<String>> buckets;
    int currentSize = 0;

    public StringHashSet() {
        buckets = new ArrayList<>();
        for (int i = 0; i < INITIAL_BUCKET_SIZE; i++) {
            buckets.add(new ArrayList<>());
        }
    }

    @Override
    public int size() {
        return this.currentSize;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return this.getBucket(o).contains(o);
    }

    @Override
    public Iterator<String> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(String potentialMember) {
        int hash = potentialMember.hashCode();
        int index = Math.abs(hash % this.buckets.size());
        List<String> bucket = this.buckets.get(index);
        if (bucket.contains(potentialMember)) {
            return false;
        }
        bucket.add(potentialMember);
        this.currentSize++;
//        System.out.println(this.currentSize);
        if (1.0 * currentSize / this.buckets.size() > this.LOAD_FACTOR) {
            increaseBucketCount();
        }

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int hash = o.hashCode();
        int index = Math.abs(hash % this.buckets.size());
        List<String> bucket = this.buckets.get(index);
        if (bucket.contains(o)) {
            bucket.remove(o);
            this.currentSize--;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends String> stringsToAdd) {
        int potentialSize = this.size() + stringsToAdd.size();
        if (1.0 * potentialSize / this.buckets.size() > this.LOAD_FACTOR) {
            increaseBucketCount();
        }
        boolean success = false;
        for (String actual : stringsToAdd) {
            int hash = actual.hashCode();
            int index = Math.abs(hash % this.buckets.size());
            List<String> bucket = this.buckets.get(index);
            if (!bucket.contains(actual)) {
                this.add(actual);
                success = true;
            }
        }
        return success;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        for (List<String> bucket : this.buckets) {
            bucket.clear();
        }
    }

    @Override
    public Spliterator<String> spliterator() {
        return null;
    }

    @Override
    public boolean removeIf(Predicate<? super String> filter) {
        boolean removed = false;
        for (List<String> bucket : this.buckets) {
            removed = removed || bucket.removeIf(filter);
        }
        return removed;
    }

    @Override
    public Stream<String> stream() {
        return null;
    }

    @Override
    public Stream<String> parallelStream() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super String> action) {

    }

    @Override
    public String toString() {
        return "{" + this.buckets.stream()
                .flatMap(bucket -> bucket.stream())
                .collect(Collectors.joining(", ")) + "}";
    }

    private List<String> getBucket(Object o) {
        int hash = o.hashCode();
        int index = Math.abs(hash % this.buckets.size());
        return this.buckets.get(index);
    }

    private void increaseBucketCount() {
        this.increaseBucketCount(size());
    }

    private void increaseBucketCount(int capacity) {

        int newBucketSize = this.buckets.size();
        while (1.0 * capacity / newBucketSize > LOAD_FACTOR) {
            newBucketSize *= this.INCREASE_FACTOR;
        }

        List<List<String>> oldBuckets = this.buckets;
        this.buckets = new ArrayList<>();
        for (int i = 0; i < newBucketSize; i++) {
            this.buckets.add(new ArrayList<>());
        }
        this.currentSize = 0;
        for (List<String> oldBucket : oldBuckets) {
            this.addAll(oldBucket);
        }
    }

}
