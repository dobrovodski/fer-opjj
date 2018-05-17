package hr.fer.zemris.java.gui.prim;

import org.junit.Before;
import org.junit.Test;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import static org.junit.Assert.assertEquals;

public class PrimListModelTest {
    private PrimListModel model;

    @Before
    public void init() {
        model = new PrimListModel();
    }

    @Test
    public void GetSize_NextNotCalled_One() {
        assertEquals(1, model.getSize());
    }

    @Test
    public void GetSize_NextCalledNTimes_NPlusOne() {
        int n = 10;
        for (int i = 0; i < n; i++) {
            model.next();
        }
        assertEquals(n + 1, model.getSize());
    }

    @Test
    public void GetElementAt_AtZero_One() {
        assertEquals(1, (int) model.getElementAt(0));
    }

    @Test
    public void GetElementAt_AtFiveThousand_48611() {
        int n = 5000;
        for (int i = 0; i < n; i++) {
            model.next();
        }

        assertEquals(48611, (int) model.getElementAt(n));
    }

    @Test
    public void GetElementAt_AtFive_Eleven() {
        int n = 5;
        for (int i = 0; i < n; i++) {
            model.next();
        }

        assertEquals(11, (int) model.getElementAt(n));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void GetElementAt_InvalidIndex_Exception() {
        model.getElementAt(10);
    }

    @Test
    public void AddListDataListener_ListenerAdded_ListenerNotified() {
        final int[] z = {0};
        model.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                z[0] += 1;
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
            }
        });

        model.next();

        assertEquals(1, z[0]);
    }

    @Test
    public void AddListDataListener_ListenerRemoved_ListenerNotNotified() {
        final int[] z = {0};
        ListDataListener l = new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                z[0] += 1;
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
            }
        };
        model.addListDataListener(l);
        model.removeListDataListener(l);

        model.next();

        assertEquals(0, z[0]);
    }
}
