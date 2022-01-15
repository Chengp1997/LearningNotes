package boundedpipe;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListPipeTest {

    private Pipe<String> pipeABC6;
    private Pipe<String> pipeEmpty3;
    private Pipe<String> pipeFull2;

    @Before
    public void setUp() {
        pipeEmpty3 = new ListPipe<>(3);
        pipeABC6 = new ListPipe<>(6);
        pipeABC6.append("A");
        pipeABC6.append("B");
        pipeABC6.append("C");
        pipeFull2 = new ListPipe<>(2);
        pipeFull2.append("E");
        pipeFull2.append("F");
    }

    @Test
    public void testPrependEmpty3Success() {
        pipeEmpty3.prepend("A");
        Assert.assertEquals("[A]:3",pipeEmpty3.toString());
        Assert.assertEquals(1,pipeEmpty3.length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrependEmpty3NullElement(){
        pipeEmpty3.prepend(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testPrependEmpty3Full(){
        pipeEmpty3.prepend("A");
        pipeEmpty3.prepend("B");
        pipeEmpty3.prepend("C");
        pipeEmpty3.prepend("D");
    }

    @Test
    public void testPrependABC6Success() {
        pipeABC6.prepend("A");
        Assert.assertEquals("[A, A, B, C]:6",pipeABC6.toString());
        Assert.assertEquals(4,pipeABC6.length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrependABC6NullElement(){
        pipeABC6.prepend(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testPrependABC6Full(){
        pipeABC6.prepend("A");
        pipeABC6.prepend("B");
        pipeABC6.prepend("C");
        pipeABC6.prepend("D");
    }

    @Test(expected = IllegalStateException.class)
    public void testPrependFull2Full(){
        pipeFull2.prepend("A");
    }

    @Test
    public void testAppendEmpty3Success() {
        pipeEmpty3.append("A");
        Assert.assertEquals("[A]:3",pipeEmpty3.toString());
        Assert.assertEquals(1,pipeEmpty3.length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAppendEmpty3NullElement(){
        pipeEmpty3.append(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testAppendEmpty3Full(){
        pipeEmpty3.append("A");
        pipeEmpty3.append("B");
        pipeEmpty3.append("C");
        pipeEmpty3.append("D");
    }

    @Test
    public void testAppendABC6Success() {
        pipeABC6.append("A");
        Assert.assertEquals("[A, B, C, A]:6",pipeABC6.toString());
        Assert.assertEquals(4,pipeABC6.length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAppendABC6NullElement(){
        pipeABC6.append(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testAppendABC6Full(){
        pipeABC6.append("A");
        pipeABC6.append("B");
        pipeABC6.append("C");
        pipeABC6.append("D");
    }

    @Test(expected = IllegalStateException.class)
    public void testAppendFull2Full(){
        pipeFull2.append("A");
    }

    @Test(expected = IllegalStateException.class)
    public void removeFirstEmpty3Empty() {
        pipeEmpty3.removeFirst();
    }

    @Test
    public void removeFirstABC6Success(){
        String s=pipeABC6.removeFirst();
        Assert.assertEquals("A",s);
        Assert.assertEquals(2,pipeABC6.length());
    }

    @Test(expected = IllegalStateException.class)
    public void removeFirstABC6Empty() {
        pipeABC6.removeFirst();
        pipeABC6.removeFirst();
        pipeABC6.removeFirst();
        pipeABC6.removeFirst();
    }

    @Test
    public void removeFirstFull2Success(){
        String s=pipeFull2.removeFirst();
        Assert.assertEquals("E",s);
        Assert.assertEquals(1,pipeFull2.length());
    }

    @Test(expected = IllegalStateException.class)
    public void removeFirstFull2Empty() {
        pipeFull2.removeFirst();
        pipeFull2.removeFirst();
        pipeFull2.removeFirst();
    }

    @Test(expected = IllegalStateException.class)
    public void removeLastEmpty3Empty() {
        pipeEmpty3.removeLast();
    }

    @Test
    public void removeLastABC6Success(){
        String s=pipeABC6.removeLast();
        Assert.assertEquals("C",s);
        Assert.assertEquals(2,pipeABC6.length());
    }

    @Test(expected = IllegalStateException.class)
    public void removeLastABC6Empty() {
        pipeABC6.removeLast();
        pipeABC6.removeLast();
        pipeABC6.removeLast();
        pipeABC6.removeLast();
    }

    @Test
    public void removeLastFull2Success(){
        String s=pipeFull2.removeLast();
        Assert.assertEquals("F",s);
        Assert.assertEquals(1,pipeFull2.length());
    }

    @Test(expected = IllegalStateException.class)
    public void removeLastFull2Empty() {
        pipeFull2.removeLast();
        pipeFull2.removeLast();
        pipeFull2.removeLast();
    }

    @Test
    public void testEmpty3Length() {
        Assert.assertEquals(0,pipeEmpty3.length());
    }

    @Test
    public void testABC6Length(){
        Assert.assertEquals(3,pipeABC6.length());
    }

    @Test
    public void testFull2Length(){
        Assert.assertEquals(2,pipeFull2.length());
    }

    @Test
    public void testClear() {
        pipeABC6.clear();
        Assert.assertEquals(0,pipeABC6.length());
    }

    @Test
    public void testABCIterator(){
        StringBuilder result= new StringBuilder();
        for (String s:pipeABC6){
            result.append(s);
        }
        Assert.assertEquals("ABC", result.toString());
    }

    @Test
    public void testEmpty3ListPipeNormalCapacity() {
        Assert.assertEquals(3, pipeEmpty3.capacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmpty3ListPipeIllegalCapacity() {
        pipeEmpty3 = new ListPipe<>(-1);
    }

    @Test
    public void testABC6ListPipeNormalCapacity() {
        Assert.assertEquals(6, pipeABC6.capacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testABC6ListPipeIllegalCapacity() {
        pipeABC6 = new ListPipe<>(0);
    }

    @Test
    public void testFull2ListPipeNormalCapacity() {
        Assert.assertEquals(2, pipeFull2.capacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFull2ListPipeIllegalCapacity() {
        pipeFull2 = new ListPipe<>(0);
    }

    @Test
    public void testEmptyListPipeIsEmpty() {
        Assert.assertTrue(pipeEmpty3.isEmpty());
    }

    @Test
    public void testABC6ListPipeIsEmpty() {
        Assert.assertFalse(pipeABC6.isEmpty());
        pipeABC6.clear();
        Assert.assertTrue(pipeABC6.isEmpty());
    }

    @Test
    public void testFull2ListPipeIsEmpty() {
        Assert.assertFalse(pipeFull2.isEmpty());
        pipeFull2.clear();
        Assert.assertTrue(pipeFull2.isEmpty());
    }

    @Test
    public void testEmpty3ListPipeIsFull() {
        Assert.assertFalse(pipeEmpty3.isFull());
        pipeEmpty3.append("1");
        pipeEmpty3.append("2");
        pipeEmpty3.append("3");
        Assert.assertTrue(pipeEmpty3.isFull());
    }

    @Test
    public void testABC6ListPipeIsFull() {
        Assert.assertFalse(pipeABC6.isFull());
        pipeABC6.append("E");
        pipeABC6.append("F");
        pipeABC6.append("G");
        Assert.assertTrue(pipeABC6.isFull());
    }

    @Test
    public void testFull2ListPipeIsFull() {
        Assert.assertTrue(pipeFull2.isFull());
        pipeFull2.removeFirst();
        Assert.assertFalse(pipeFull2.isFull());
    }

    @Test
    public void testEmpty3ListPipeAppendAllSuccessAddIn() {
        pipeEmpty3.appendAll(pipeFull2);
        Assert.assertEquals(2, pipeEmpty3.length());
    }

    @Test(expected = IllegalStateException.class)
    public void testEmpty3ListPipeAppendAllLengthOutOfCapacity() {
        pipeEmpty3.append("X");
        pipeEmpty3.appendAll(pipeABC6);
    }

    @Test
    public void testABC6ListPipeAppendAllSuccessAddIn() {
        pipeABC6.appendAll(pipeFull2);
        Assert.assertEquals(5, pipeABC6.length());
    }

    @Test(expected = IllegalStateException.class)
    public void testABC6ListPipeAppendAllLengthOutOfCapacity() {
        pipeABC6.append("X");
        pipeABC6.append("Y");
        pipeABC6.appendAll(pipeFull2);
    }

    @Test
    public void testFull2ListPipeAppendAllSuccessAddIn() {
        pipeFull2.appendAll(pipeEmpty3);
        Assert.assertEquals(2, pipeFull2.length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testABC6ListPipeAppendAllNullPipe() {
        pipeABC6.appendAll(null);
    }

    @Test
    public void testEmpty3ListPipeCopy() {
        Pipe<String> newPipe = pipeEmpty3.copy();
        Assert.assertEquals(0, newPipe.length());
        Assert.assertEquals(3, newPipe.capacity());
        Assert.assertEquals(pipeEmpty3, newPipe);
    }

    @Test
    public void testABC6ListPipeCopy() {
        Pipe<String> newPipe = pipeABC6.copy();
        Assert.assertEquals(3, newPipe.length());
        Assert.assertEquals(6, newPipe.capacity());
        Assert.assertEquals(pipeABC6, newPipe);
        String s1 = pipeABC6.removeFirst();
        String s2 = newPipe.removeFirst();
        Assert.assertEquals(s1, s2);
    }

    @Test
    public void testFull2ListPipeCopy() {
        Pipe<String> newPipe = pipeFull2.copy();
        Assert.assertEquals(2, newPipe.length());
        Assert.assertEquals(2, newPipe.capacity());
        Assert.assertEquals(pipeFull2, newPipe);
    }

    @Test
    public void testEmpty3NewInstance() {
        Pipe<String> newPipe = pipeEmpty3.newInstance();
        Assert.assertEquals(0, newPipe.length());
        Assert.assertEquals(3, newPipe.capacity());
    }

    @Test
    public void testABC6NewInstance() {
        Pipe<String> newPipe = pipeABC6.newInstance();
        Assert.assertEquals(0, newPipe.length());
        Assert.assertEquals(6, newPipe.capacity());
    }

    @Test
    public void testFull2NewInstance() {
        Pipe<String> newPipe = pipeFull2.newInstance();
        Assert.assertEquals(0, newPipe.length());
        Assert.assertEquals(2, newPipe.capacity());
    }

    @Test
    public void testHashCodeEmpty3AndDifferentEmpty3(){
        Pipe<String> pipeEmpty3New=new ListPipe<>(3);
        Assert.assertEquals(pipeEmpty3.hashCode(), pipeEmpty3New.hashCode());
    }

    @Test
    public void testHashCodeEmpty3AndEmpty4(){
        Pipe<String> pipeEmpty4=new ListPipe<>(4);
        Assert.assertNotEquals(pipeEmpty3.hashCode(), pipeEmpty4.hashCode());
    }

    @Test
    public void testHashCodeEmpty3AndA3(){
        Pipe<String> pipeA3= new ListPipe<>(3);
        pipeA3.append("A");
        Assert.assertNotEquals(pipeEmpty3.hashCode(), pipeA3.hashCode());
    }

    @Test
    public void testHashCodeABC6AndDifferentABC6(){
        Pipe<String> pipeABC6New=new ListPipe<>(6);
        pipeABC6New.append("A");
        pipeABC6New.append("B");
        pipeABC6New.append("C");
        Assert.assertEquals(pipeABC6.hashCode(), pipeABC6New.hashCode());
    }

    @Test
    public void testHashCodeABC6AndABC4(){
        Pipe<String> pipeABC4=new ListPipe<>(4);
        Assert.assertNotEquals(pipeABC6.hashCode(), pipeABC4.hashCode());
    }

    @Test
    public void testHashCodeABC6AndAB6(){
        Pipe<String> pipeAB6= new ListPipe<>(6);
        pipeAB6.append("A");
        pipeAB6.append("B");
        Assert.assertNotEquals(pipeABC6.hashCode(), pipeAB6.hashCode());
    }

    @Test
    public void testHashCodeFull2AndDifferentFull2(){
        Pipe<String> pipeFull2New=new ListPipe<>(2);
        pipeFull2New.append("E");
        pipeFull2New.append("F");
        Assert.assertEquals(pipeFull2.hashCode(), pipeFull2New.hashCode());
    }

    @Test
    public void testHashCodeFull2AndFull4(){
        Pipe<String> pipeFull4=new ListPipe<>(4);
        Assert.assertNotEquals(pipeFull2.hashCode(), pipeFull4.hashCode());
    }

    @Test
    public void testHashCodeFull2AndE2(){
        Pipe<String> pipeFullE2= new ListPipe<>(2);
        pipeFullE2.append("E");
        Assert.assertNotEquals(pipeFull2.hashCode(), pipeFullE2.hashCode());
    }

    @Test
    public void testDifferentTypeEmpty3(){
        List<String> list= new ArrayList<>(3);
        Assert.assertNotEquals(pipeEmpty3, list);
    }

    @Test
    public void testDifferentContentABC6(){
        Pipe<String> pipeABD6=new ListPipe<>(6);
        pipeABD6.append("A");
        pipeABD6.append("B");
        pipeABD6.append("D");
        Assert.assertFalse(pipeABC6.equals(pipeABD6));
    }

    @Test
    public void testEmpty3EqualsNull(){
        Pipe<String> newPipe=null;
        Assert.assertNotEquals(pipeEmpty3, newPipe);
    }

    @Test
    public void testEmpty3EqualsSelf(){
        Assert.assertEquals(pipeEmpty3, pipeEmpty3);
    }

    @Test
    public void testEmpty3EqualsNonPipe(){
        Assert.assertNotEquals("[]:3", pipeEmpty3);
    }

    @Test
    public void testEmpty3EqualsDifferentEmpty3(){
        Pipe<String> pipeEmpty3New=new ListPipe<>(3);
        Assert.assertEquals(pipeEmpty3, pipeEmpty3New);
    }

    @Test
    public void testEmpty3EqualsEmpty4(){
        Pipe<String> pipeEmpty4=new ListPipe<>(4);
        Assert.assertNotEquals(pipeEmpty3, pipeEmpty4);
    }

    @Test
    public void testEmpty3EqualsA3(){
        Pipe<String> pipeA3= new ListPipe<>(3);
        pipeA3.append("A");
        Assert.assertNotEquals(pipeEmpty3, pipeA3);
    }

    @Test
    public void testABC6EqualsNull(){
        Pipe<String> newPipe=null;
        Assert.assertNotEquals(pipeABC6, newPipe);
    }

    @Test
    public void testABC6EqualsSelf(){
        Assert.assertEquals(pipeABC6, pipeABC6);
    }

    @Test
    public void testABC6EqualsNonPipe(){
        Assert.assertNotEquals("[A, B, C]:6", pipeABC6);
    }

    @Test
    public void testABC6EqualsDifferentABC6(){
        Pipe<String> pipeABC6New=new ListPipe<>(6);
        pipeABC6New.append("A");
        pipeABC6New.append("B");
        pipeABC6New.append("C");
        Assert.assertEquals(pipeABC6, pipeABC6New);
    }

    @Test
    public void testABC6EqualsABC4(){
        Pipe<String> pipeABC4=new ListPipe<>(4);
        Assert.assertNotEquals(pipeABC6, pipeABC4);
    }

    @Test
    public void testABC6EqualsAB6(){
        Pipe<String> pipeAB6= new ListPipe<>(6);
        pipeAB6.append("A");
        pipeAB6.append("B");
        Assert.assertNotEquals(pipeABC6, pipeAB6);
    }

    @Test
    public void testFull2EqualsNull(){
        Pipe<String> newPipe=null;
        Assert.assertNotEquals(pipeFull2, newPipe);
    }

    @Test
    public void testFull2EqualsSelf(){
        Assert.assertEquals(pipeFull2, pipeFull2);
    }

    @Test
    public void testFull2EqualsNonPipe(){
        Assert.assertNotEquals("[E, F]:2", pipeFull2);
    }

    @Test
    public void testFull2EqualsDifferentFull2(){
        Pipe<String> pipeFull2New=new ListPipe<>(2);
        pipeFull2New.append("E");
        pipeFull2New.append("F");
        Assert.assertEquals(pipeFull2, pipeFull2New);
    }

    @Test
    public void testFull2EqualsFull4(){
        Pipe<String> pipeFull4=new ListPipe<>(4);
        Assert.assertNotEquals(pipeFull2, pipeFull4);
    }

    @Test
    public void testFull2EqualsE2(){
        Pipe<String> pipeFullE2= new ListPipe<>(2);
        pipeFullE2.append("E");
        Assert.assertNotEquals(pipeFull2, pipeFullE2);
    }

    @Test
    public void testEmpty3ToString(){
        Assert.assertEquals("[]:3",pipeEmpty3.toString());
    }

    @Test
    public void testABC6ToString(){
        Assert.assertEquals("[A, B, C]:6",pipeABC6.toString());
    }

    @Test
    public void testFull2ToString(){
        Assert.assertEquals("[E, F]:2",pipeFull2.toString());
    }

    @Test
    public void testFirstABC6(){
        Assert.assertEquals("A",pipeABC6.first());
    }

    @Test
    public void testEmptyFirst(){
        Assert.assertNull(pipeEmpty3.first());
    }

    @Test
    public void testLastABC6(){
        Assert.assertEquals("C",pipeABC6.last());
    }

    @Test
    public void testEmptyLast(){
        Assert.assertNull(pipeEmpty3.last());
    }
}