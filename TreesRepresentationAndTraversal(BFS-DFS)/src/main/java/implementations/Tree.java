package implementations;

import interfaces.AbstractTree;

import java.util.*;

@SuppressWarnings("all")


public class Tree<E> implements AbstractTree<E> {
    private E key;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E key, Tree<E>... children) {
        this.key = key;
        this.children = new ArrayList<>();
        for (Tree<E> child : children) {
            this.children.add(child);
        }
    }


    @Override
    public List<E> orderDfs() {
        List<E> order = new ArrayList<>();
        this.dfs(this, order);
        return order;
    }

    @Override
    public void addChild(E parentKey, Tree<E> child) {
        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);
        while (queue.size() > 0) {
            Tree<E> current = queue.poll();
            for (Tree<E> c : current.children) {
                if (c.key.equals(parentKey)) {
                    c.children.add(child);
                    return;
                }
                queue.offer(c);
            }
        }
    }

    private void dfs(Tree<E> tree, List<E> order) {
        for (Tree<E> child : tree.children) {
            this.dfs(child, order);
        }
        order.add(tree.key);
    }

    @Override
    public List<E> orderBfs() {
        List<E> result = new ArrayList<>();
        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);
        while (queue.size() > 0) {
            Tree<E> current = queue.poll();
            result.add(current.key);
            for (Tree<E> child : current.children)
                queue.offer(child);
        }
        return result;
    }
}



