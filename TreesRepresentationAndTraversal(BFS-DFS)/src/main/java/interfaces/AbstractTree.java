package interfaces;

import implementations.Tree;

import java.util.List;

public interface AbstractTree<E> {
    List<E> orderBfs();
    List<E> orderDfs();
    void addChild(E parentKey, Tree<E> child);
}
