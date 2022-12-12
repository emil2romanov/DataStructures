package implementations;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TreeTest {

    private Tree<Integer> tree;

    @Before
    public void setUp() {
        this.tree =  new Tree<>(7,
                new Tree<>(19,
                        new Tree<>(1),
                        new Tree<>(12),
                        new Tree<>(31)),
                new Tree<>(21),
                new Tree<>(14,
                        new Tree<>(23),
                        new Tree<>(6))
        );
    }

    @Test
    public void testTreeConstructor() {
        assertNotNull(tree);
    }

    @Test
    public void testTreeBfs() {
        Integer[] expected = {7, 19, 21, 14, 1, 12, 31, 23, 6};
        int index = 0;
        for (Integer num : tree.orderBfs()) {
            assertEquals(expected[index++], num);
        }
    }

    @Test
    public void testTreeDfs() {
        Integer[] expected = {1, 12, 31, 19, 21, 23, 6, 14, 7};
        int index = 0;
        for (Integer num : tree.orderDfs()) {
            assertEquals(expected[index++], num);
        }
    }

    @Test
    public void testAddTree() {
        tree.addChild(1, new Tree<>(-1, new Tree<>(-2), new Tree<>(-3)));
        Integer[] expected = {-2, -3, -1, 1, 12, 31, 19, 21, 23, 6, 14, 7};
        int index = 0;
        for (Integer num : tree.orderDfs()) {
            assertEquals(expected[index++], num);
        }
    }
}