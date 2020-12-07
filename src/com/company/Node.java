package com.company;

public class Node {

    public int data;
    public boolean lIsNotThread;        //Истина, если ссылка на левое поддерево НЕ является нитью
    public boolean rIsNotThread;        //Истина, если ссылка на правое поддерево НЕ является нитью
    public Node left;                   //Ссылка на левое поддерево
    public Node right;                  //Ссылка на правое поддерево

    public Node(int data){              //Конструтор для инициализации
        this.data = data;
        lIsNotThread = true;
        rIsNotThread = true;
        left  = null;
        right = null;
    }
    public void show() { System.out.print(data + " "); }

}
