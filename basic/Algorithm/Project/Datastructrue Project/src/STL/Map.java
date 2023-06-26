package STL;

public class Map<K, V> {
    private Entry<K,V> [] table = new Entry[1000];
    private int size = 0;

    public class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value){
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

    }

    public void put(K key, V value){
        Entry<K,V> entry = new Entry<>(key, value);
        for(int i = 0; i < size; i++) {
            if(key.equals(table[i].getKey())) {
                table[i] = entry;
                return;
            }
        }
        table[size] = entry;
        size ++;
    }

    public V get(K key) {
        for(int i = 0; i < size; i++) {
            if(table[i].getKey().equals(key))
                return table[i].getValue();
        }
        return null;
    }

}
