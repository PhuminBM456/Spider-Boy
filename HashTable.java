class HashTable{
    // field
    final int cap = 3;
    LinkedList[] arr = new LinkedList[cap];

    //constructor
    HashTable(){
        for(int i=0;i<cap;i++){
            arr[i] = new LinkedList();
        }
    }

    // method
    int hashFunc(String power){
        int len = power.length();
        int sum = 0;

        for(int i=0;i<len;i++){
            sum += power.charAt(i) + 0;
        }

        return sum % cap;
    }

    void Insert(String power){
        int idx;

        idx = hashFunc(power);
        arr[idx].Insert(power);
    }

    void Delete(String power){
        int idx;

        idx = hashFunc(power);
        arr[idx].delNode();
    }

    int Search(String power){
        int idx;

        idx = hashFunc(power);

        return arr[idx].countNode();
    }

    public static void main(String[] args){
        HashTable ht = new HashTable();

        ht.Insert("Impact");
        System.out.println(ht.Search("Impact"));
    }
}