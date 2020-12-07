package com.company;





public class BinTree {

    private Node root;                              //Корень дерева
    private Node head;                              //Голова дерева
    private final boolean symThreadingDone;         //Истина, когда дерево прошили

    //Конструктор для инициализации
    public BinTree(){
        root = null;
        symThreadingDone = false;
        head = null;
    }

    //Вставка нового ключа в дерево
    public void insert(int data){
        if (symThreadingDone){
            System.out.println("Дерево прошито");
        } else {
            //Создаем узел
            Node newNode = new Node(data);
            if (root == null) {
                root = newNode;
            } else {
                //Становимся в начало дерево
                Node curr = root, prev;

                while (true) {
                    //Запоминаем предыдущее положение
                    prev = curr;
                    if (data < curr.data) {
                        //Текущим становится левое поддерево
                        curr = curr.left;
                        //Нашли место вставки - запоминаем ссылку
                        if (curr == null) {
                            prev.left = newNode;
                            return;
                        }
                    } else {
                        curr = curr.right;
                        //Нашли место вставки - запоминаем ссылку
                        if (curr == null) {
                            prev.right = newNode;
                            return;
                        }
                    }
                }
            }
        }
    }
    // методы для удаления
    public void deleteFromSymmetricTree(int data){
        Node prev = null;
        Node curr  = root;                                                                  //Становимся в корень дерева
        boolean found = false;                                                              //Пока узел не нашли
//проходим все дерево
        while (curr != null) {
            if (data == curr.data) {
                found = true;
                break;
            }
            prev = curr;                                                                    //Запоминаем предыдущего
            if (data < curr.data) {                                                         //Идем в левое поддерево
                if (curr.lIsNotThread)
                    curr = curr.left;
                else
                    break;                                                                  //заканчиваем, если ссылка на левое поддерево - НИТЬ
            }
            else {
                if (curr.rIsNotThread)
                    curr = curr.right;
                else
                    break;                                                                  //Останавливаемся, если ссылка на правое поддерево - НИТЬ
            }
        }

        if (!found)
            System.out.println("Такого ключа нет в дереве!");
        else if (curr.lIsNotThread && curr.rIsNotThread)
            deleteNodeWithTwoChildren(prev, curr);                                          //Удаляем узел, у которого два потомка
        else if (curr.lIsNotThread)
            deleteNodeWithOneChild(prev, curr);                                             //Удаляем узел, у которого один потомок (левый)
        else if (curr.rIsNotThread)
            deleteNodeWithOneChild(prev, curr);                                             //Удаляем узел, у которого один потомок (правый)
        else
            deleteLeaf(prev, curr);                                                         //Удаляем узел-лист
    }

    private void deleteLeaf(Node prev, Node curr){                                          //Метод для удаления узла-листа
        if (prev == null)                                                                   //Удаляемый узел - корень
            root = null;
        else if (curr == prev.left) {                                                       //Удаляемый узел является левым потомком своего родителя
            prev.lIsNotThread = false;
            prev.left = curr.left;                                                          //Переносим ссылку. Теперь родитель будет ссылаться на голову / узел со следующим меньшим ключом
        } else {
            prev.rIsNotThread = false;
            prev.right = curr.right;                                                        //Переносим ссылку. Теперь родитель будет ссылаться на голову / узел со следующим большим ключом
        }
    }

    private void deleteNodeWithOneChild(Node prev, Node curr){                              //Метод для удаления узла с одним потомком
        Node child;

        if (curr.lIsNotThread)
            child = curr.left;                                                              //Потомок у удаляемого узла - левый
        else
            child = curr.right;                                                             //Потомок у удаляемого узла - правый

        if (prev == null)                                                                   //Переносим корень дерева на единственного потомка
            root = child;
        else if (curr == prev.left)
            prev.left = child;
        else
            prev.right = child;

        Node s = inSuccessor(curr);                                                         //Находим узел со следующим бОльшим ключом
        Node p = inPredecessor(curr);                                                       //Находим узел со следующим мЕньшим ключом

        if (curr.lIsNotThread)
            p.right = s;
        else {
            if (curr.rIsNotThread)
                s.left = p;
        }
    }

    private void deleteNodeWithTwoChildren(Node prev, Node curr){                           //Метод для удаления узла с двумя потомками
        Node prevSucc = curr;                                                               //Запоминаем предыдущего
        Node succ = curr.right;

        while (succ.lIsNotThread) {
            prevSucc = succ;
            succ = succ.left;
        }
        curr.data = succ.data;                                                              //Заменяем ключ
        if (!succ.lIsNotThread && !succ.rIsNotThread)                                       //Преемник - лист. Удаляем его как лист
            deleteLeaf(prevSucc, succ);
        else
            deleteNodeWithOneChild(prevSucc, succ);                                         //У преемника есть хоть один потомок
    }

    private Node inSuccessor(Node ptr) {                                                    //Метод для поиска преемника
        if (!ptr.rIsNotThread)                                                              //Правая ссылка - это нить. Возвращаем её сразу
            return ptr.right;

        ptr = ptr.right;                                                                    //Ищем подходящую ссылку
        while (ptr.lIsNotThread)
            ptr = ptr.left;
        return ptr;
    }

    private Node inPredecessor(Node ptr) {                                                  //Метод для поиска предшественника
        if (!ptr.lIsNotThread)                                                              //Левая ссылка - это нить. Возвращаем её сразу
            return ptr.left;

        ptr = ptr.left;                                                                     //Ищем подходящую ссылку
        while (ptr.rIsNotThread)
            ptr = ptr.right;
        return ptr;
    }
    //метод вставки уже в прошитое дерево
    public void insertToSymmetricTree(int data) {
        Node curr = root;
        Node prev = null;
        while (curr != null) {
            prev = curr;
            if (data < curr.data) {
                if (curr.lIsNotThread)
                    curr = curr.left;
                else
                    break;
            } else {
                if (curr.rIsNotThread)
                    curr = curr.right;
                else
                    break;
            }
        }

        Node newNode = new Node(data);
        newNode.lIsNotThread = false;
        newNode.rIsNotThread = false;

        if (prev == null) {
            root = newNode;
            newNode.left = null;
            newNode.right = null;
        } else if (data < (prev.data)) {
            newNode.left = prev.left;
            newNode.right = prev;
            prev.lIsNotThread = true;
            prev.left = newNode;
        } else {
            newNode.left = prev;
            newNode.right = prev.right;
            prev.rIsNotThread = true;
            prev.right = newNode;
        }
    }

    //прошивание

    Node y;                                     //Глобальный указатель
    public void makeSymmetricallyThreaded(){
        if (symThreadingDone){
            System.out.println("Невозможно создать дерево с симметричным прошиванием! Оно уже имеет симметричное прошивание!");
        } else {
            head = new Node(-1);
            head.left  = root;
            head.right = head;

            y = head;

            recursiveSymmetricalThreadingR(root);           //Вызываем рекурсивный метод для ПРАВОСТОРОННЕЙ ПРОШИВКИ
            y.right = head;
            y.rIsNotThread = false;                         //Самый крайний правый лист должен ссылаться на голову

            y = head;

            recursiveSymmetricalThreadingL(root);           //Вызываем рекурсивный метод для ЛЕВОСТОРОННЕЙ ПРОШИВКИ
            y.left = head;
            y.lIsNotThread = false;                         //Самый крайний левый лист должен ссылаться на голову
        }
    }

    //методы для правосторонней прошивки

    private void recursiveSymmetricalThreadingR(Node x){
        if (x != null){
            recursiveSymmetricalThreadingR(x.left);
            doSymmetricalThreadingR(x);
            recursiveSymmetricalThreadingR(x.right);
        }
    }

    private void doSymmetricalThreadingR(Node p){
        if (y != null){
            if (y.right == null){
                y.rIsNotThread = false;
                y.right = p;
            } else {
                y.rIsNotThread = true;
            }
        }
        y = p;
    }

    //левосторонняя прошивка

    private void recursiveSymmetricalThreadingL(Node x){
        if (x != null){
            if (x.rIsNotThread) recursiveSymmetricalThreadingL(x.right);
            doSymmetricalThreadingL(x);
            recursiveSymmetricalThreadingL(x.left);
        }
    }

    private void doSymmetricalThreadingL(Node p){
        if (y != null){
            if (y.left == null){
                y.lIsNotThread = false;
                y.left = p;
            } else {
                y.lIsNotThread = true;
            }
        }
        y = p;
    }

    //обход прошитого дерева

    public void threadedInOrderTraverse(){
        Node curr = root;
        if (root == null) { System.out.println("Дерево пусто!"); return; }
        System.out.println("Симметричный обход дерева при помощи метода прошивки:");
        while (curr != head){
            while (curr.left != null && curr.lIsNotThread) curr = curr.left;
            curr.show();
            while (!curr.rIsNotThread && curr.right != null) {
                curr = curr.right;
                if (curr == head) {
                    System.out.println();
                    return;
                }
                curr.show();
            }
            curr = curr.right;
        }
        System.out.println();
    }

    public void threadedInOrderTraverseReverse(){
        Node curr = root;
        if (root == null) { System.out.println("Дерево пусто!"); return; }
        System.out.println("Симметричный обход дерева при помощи метода прошивки (по убыванию ключа):");
        while (curr != head){
            while (curr.right != null && curr.rIsNotThread) curr = curr.right;
            curr.show();
            while (!curr.lIsNotThread) {
                curr = curr.left;
                if (curr == head) {
                    System.out.println();
                    return;
                }
                curr.show();
            }
            curr = curr.left;
        }
        System.out.println();
    }
    //Метод для поиска ключа в дереве
    public Node find(int x){
        //Становимся в корень дерева
        Node curr = root;

        while (curr.data != x){
            if (x < curr.data)
                //Идем влево
                curr = curr.left;
            else
                //Иначе идем вправо
                curr = curr.right;
            //Дошли до "дна" дерева. Возвращаем null, так как нет узла с таким ключом
            if (curr == null) return null;
        }
        //Мы вышли из цикла. Значит, нужный узел найден. Возвращаем его
        return curr;
    }

    //Вспомогательный метод для вызова симмметричного обхода дерева.
    public void getInOrder(){
        if (root == null) { System.out.println("Дерево пусто!"); return; }
        System.out.println("Симметричный обход дерева:");
        inOrder(root);
        System.out.println();
    }

    //Вспомогательный метод для вызова прямого обхода дерева.
    public void getPreOrder(){
        if (root == null) { System.out.println("Дерево пусто!"); return; }
        System.out.println("Прямой обход дерева:");
        preOrder(root);
        System.out.println();
    }

    //Вспомогательный метод для вызова обратного обхода дерева.
    public void getPostOrder(){
        if (root == null) { System.out.println("Дерево пусто!"); return; }
        System.out.println("Обратный обход дерева:");
        postOrder(root);
        System.out.println();
    }

    //Рекурсивный симметричный обход дерева.
    private void inOrder(Node root){
        if (root != null){
            inOrder(root.left);
            root.show();
            inOrder(root.right);
        }
    }

    //Рекурсивный прямой обход дерева.
    private void preOrder(Node root){
        if (root != null){
            root.show();
            preOrder(root.left);
            preOrder(root.right);
        }
    }

    //Рекурсивный обратный обход дерева.
    private void postOrder(Node root){
        if (root != null){
            postOrder(root.left);
            postOrder(root.right);
            root.show();
        }
    }

    //Вспомогательный метод для поиска преемника узлу delNode.
    //Преемник - либо (1) правый потомок delNode
    //           либо (2) "самый левый" потомок правого потомка delNode
    private Node getSuccessor(Node delNode) {
        //Становимся в правое поддерево удаляемого узла
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.right;
        //Находим "самого левого"
        while(current != null) {
            successorParent = successor;
            successor = current;
            current = current.left;
        }
        //Если "самый левый" не является (1), а является (2), то
        //правый потомком преемника должно стать правое поддерево delNode
        if(successor != delNode.right) {
            successorParent.left = successor.right;
            successor.right = delNode.right;
        }
        //Возвращаем преемника
        return successor;
    }

    //Метод для удаления некоторого ключа из дерева.
    public boolean delete(int key) {
        Node current = root;
        Node parent = root;
        //Пока предполагаем, что удаляемый узел будет являться
        //левым потомком узла-родителя
        boolean isLeftChild = true;
        //Цикл аналогичен циклу в методе find(int x)
        //Только запоминаем родителя
        //и обновляем, является ли найденный удаляемый узел левым
        // потомком либо нет
        while (current.data != key) {
            parent = current;
            if (key < current.data) {
                isLeftChild = true;
                current = current.left;
            } else {
                isLeftChild = false;
                current = current.right;
            }
            //То, что просили удалить в дереве, не обнаружено, уведомляем
            //о неуспешном удалении
            if (current == null)
                return false;
        }
        //Удаляемый узел все-таки найден.

        //Рассматриваем случай, когда удаляемый узел - лист
        if (current.left == null &&
                current.right == null) {
            if (current == root)
                root = null;                //Удаляемый узел - корень. Просто обнуляем его
            else if (isLeftChild)
                parent.left = null;         //Удаляемый узел - левый потомок своего родителя. Обнуляем левую ссылку родителя
            else
                parent.right = null;        //Удаляемый узел - правый потомок своего родителя. Обнуляем правую ссылку родителя
        }
        else
            //Рассматриваем случай, когда у удаляемого узла есть только левый потомок
            if (current.right == null) {
                if (current == root)
                    root = current.left;       //Удаляемый узел - корень. Правого поддерева нет. Просто перезапоминаем корень.
                else
                if (isLeftChild)          //Удаляемый узел - не корень. При этом удаляемый узел - левый потомок. Правого поддерева нет.
                    parent.left = current.left;         //Исключаем удаляемый узел из цепочки. Теперь родитель будет ссылаться на нового левого потомка.
                else
                    parent.right = current.left;        //Исключаем удаляемый узел из цепочки. Теперь родитель будет ссылаться на нового правого потомка.
            }
            else
                //Рассматриваем аналогичный случай, когда у удаляемого узла есть только правый потомок
                if (current.left == null) {
                    if (current == root)
                        root = current.right;
                    else
                    if (isLeftChild)
                        parent.left = current.right;
                    else
                        parent.right = current.right;
                }
                else
                //Рассматриваем случай, когда у удаляемого узла есть и левый, и правфй потомок
                {
                    //Находим преемника. Преемник - это узел со следующим по величине ключом.
                    Node successor = getSuccessor(current);
                    if (current == root)
                        root = successor;               //Если удаляемый узел - корень, то перезапоминаем его
                    else if (isLeftChild)
                        parent.left = successor;        //Удаляемый узел - левый потомок. Заменяем его преемником.
                    else
                        parent.right = successor;       //Аналогично в случае правого потомка.

                    successor.left = current.left;      //Переносим левое поддерево удаляемого узла.
                }
        return true;
    }

}
