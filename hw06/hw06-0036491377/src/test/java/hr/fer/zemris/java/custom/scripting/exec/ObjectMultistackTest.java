package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.EmptyStackException;

/**
 * JUnit tests for SimpleHashtable.
 *
 * @author matej
 * @see <a href= "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html"> Naming standards for unit
 *         tests </a>
 */
public class ObjectMultistackTest {
    @Test(expected = NullPointerException.class)
    public void Push_NameNull_ExceptionThrown() {
        ObjectMultistack ms = new ObjectMultistack();
        ms.push(null, new ValueWrapper(10));
    }

    @Test(expected = NullPointerException.class)
    public void Push_WrapperNull_ExceptionThrown() {
        ObjectMultistack ms = new ObjectMultistack();
        ms.push("AAA", null);
    }

    @Test
    public void Push_SimpleValue_PopReturnsValue() {
        ObjectMultistack ms = new ObjectMultistack();
        String stack = "key";
        ValueWrapper vw = new ValueWrapper(10);

        ms.push(stack, vw);
        Assert.assertEquals(vw, ms.pop(stack));
    }

    @Test
    public void Push_MultiplePushes_PopReturnsThem() {
        ObjectMultistack ms = new ObjectMultistack();
        String stack = "key";
        int count = 5;
        ValueWrapper[] vws = new ValueWrapper[count];

        for (int i = 0; i < count; i++) {
            vws[i] = new ValueWrapper(i);
            ms.push(stack, vws[i]);
        }

        for (int i = count - 1; i >= 0; i--) {
            Assert.assertEquals(vws[i], ms.pop(stack));
        }
    }

    @Test(expected = EmptyStackException.class)
    public void Pop_EmptyStack_ExceptionThrown() {
        ObjectMultistack ms = new ObjectMultistack();
        ms.pop("AAA");
    }

    @Test(expected = NullPointerException.class)
    public void Pop_NameNull_ExceptionThrown() {
        ObjectMultistack ms = new ObjectMultistack();
        ms.pop(null);
    }

    @Test(expected = NullPointerException.class)
    public void IsEmpty_NameNull_ExceptionThrown() {
        ObjectMultistack ms = new ObjectMultistack();
        ms.isEmpty(null);
    }

    @Test
    public void IsEmpty_Empty_True() {
        ObjectMultistack ms = new ObjectMultistack();
        Assert.assertEquals(true, ms.isEmpty("any"));
    }

    @Test
    public void IsEmpty_NotEmpty_False() {
        ObjectMultistack ms = new ObjectMultistack();
        String stack = "key";
        ms.push(stack, new ValueWrapper("z"));
        Assert.assertEquals(false, ms.isEmpty(stack));
    }

    @Test
    public void IsEmpty_AddedThenRemoved_True() {
        ObjectMultistack ms = new ObjectMultistack();
        String stack = "key";
        ms.push(stack, new ValueWrapper("z1"));
        ms.push(stack, new ValueWrapper("z2"));
        ms.pop(stack);
        ms.pop(stack);
        Assert.assertEquals(true, ms.isEmpty(stack));
    }

    @Test(expected = NullPointerException.class)
    public void Peek_NameNull_ExceptionThrown() {
        ObjectMultistack ms = new ObjectMultistack();
        ms.peek(null);
    }

    @Test(expected = EmptyStackException.class)
    public void Peek_EmptyStack_ExceptionThrown() {
        ObjectMultistack ms = new ObjectMultistack();
        ms.peek("any");
    }

    @Test
    public void Peek_ValuesAdded_ReturnsLastValueAdded() {
        ObjectMultistack ms = new ObjectMultistack();
        String stack = "any";
        ms.push(stack, new ValueWrapper(10));
        Assert.assertEquals(10, ms.peek(stack).getValue());
        ms.push(stack, new ValueWrapper(20));
        Assert.assertEquals(20, ms.peek(stack).getValue());
    }
}
