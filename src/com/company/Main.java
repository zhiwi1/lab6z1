package com.company;


import java.util.*;

import java.util.LinkedHashSet;
public class Main {

    public static void main(String[] args) {
        BinTree tree = new BinTree();
        Random rand = new Random();
        LinkedHashSet<Integer> set = new LinkedHashSet<Integer>();
        //Создаем 1000 ключей и кладем их во множество
        for (int i = 0; i < 10; i++)
            set.add(rand.nextInt(100));
        //Проходимся по множеству и каждый его элемент вставляем в дерево двоичного поиска
        System.out.println("Порядок вставки элементов в двоичное дерево поиска: ");
        for (Integer it : set){
            System.out.print(it + " ");
            tree.insert(it);
        }
        System.out.println();

        //Симметричный обход
        tree.getInOrder();

        //Делаем прошивку
        tree.makeSymmetricallyThreaded();

        //Выводим после прошивки
        tree.threadedInOrderTraverse();
        tree.threadedInOrderTraverseReverse();

        //Вставка ключей в прошитое
        tree.insertToSymmetricTree(-1);
        tree.insertToSymmetricTree(-52);
        tree.insertToSymmetricTree(-250);
        tree.insertToSymmetricTree(120);
        tree.insertToSymmetricTree(170);
        tree.insertToSymmetricTree(130);
        tree.insertToSymmetricTree(150);

        System.out.println("После вставки новых ключей:");
        tree.threadedInOrderTraverse();

        //Попытки удалить несуществующие ключи
        tree.deleteFromSymmetricTree(-998);
        tree.deleteFromSymmetricTree(998);

        //Удаляем первую половину того, что вставили
        int N = set.size() / 2;
        for (Integer it : set){
            tree.deleteFromSymmetricTree(it);
            N--;
            if (N == 0) break;
        }

        //После удаления
        System.out.println("После удаления:");
        tree.threadedInOrderTraverse();
    }

}
