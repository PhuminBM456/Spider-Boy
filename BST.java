class Node{
    // field
    int hp;
    Node left;
    Node right;
    Enemy enemy;

    // constructor
    Node(Enemy enemy){
        this.enemy = enemy;
        this.hp = enemy.hp;
        left = right = null;
    }
}

public class BST {
    // field
    Node root;

    // constructor
    BST(){
        root = null;
    }

    // method
    void Insert(Enemy enemy){
        Node newNode = new Node(enemy);
        Node temp = null;

        if(this.root == null){
            this.root = newNode;
            return;
        }

        temp = this.root;

        while(true){
            if(hp < temp.hp && temp.left != null){
                temp = temp.left;
            }else if(hp > temp.hp && temp.right != null){
                temp = temp.right;
            }else if(hp < temp.hp && temp.left == null){
                temp.left = newNode;
                break;
            }else if(hp > temp.hp && temp.right == null){
                temp.right = newNode;
                break;
            }else{
                break;
            }
        }
    }

    void Preorder(Node root){
        if(root == null)
            return;

        System.out.print(root.hp + " ");
        Preorder(root.left);
        Preorder(root.right);
    }
    
    Node findMin(){
        Node temp = this.root;

        if(temp == null)
            return null;

        while(true){
            if(temp.left != null){
                temp = temp.left;
            }else{
                return temp;
            }
        }
    }
}
