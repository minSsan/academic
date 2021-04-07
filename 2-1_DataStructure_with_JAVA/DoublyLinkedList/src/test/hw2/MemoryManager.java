package hw2;

import cse2010.assignment2.DLinkedList;
import hw2.Node;

/* Block will be used as a type argument */
class Block {
    private int size;    // size of a block
    private int start;   // start address of a block
    private int end;     // end address of a block

    public Block(int size, int start, int end) {
        this.size = size;
        this.start = start;
        this.end = end;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "(" + size +", " + start + ", " + end + ")";
    }
}

public class MemoryManager {

    private DLinkedList<Block> heap = new DLinkedList<Block>();

    /**
     * Create a memeory manager with capacity given as parameter.
     * @param capacity
     */
    public MemoryManager(int capacity) {
        if(capacity > 0) {
        /* [Exception] */
            Block new_block = new Block(capacity, 0, capacity - 1);
            heap.addFirst(new_block);
        }
    }

    /**
     * Returns a block information allocated for the requested size.
     * @param size  the size of requested memtory
     * @return  a block of the requested size if satisfieed, throws `OutOfMemoryException` exception otherwise.
     */
    public Block malloc(int size) {
        if(heap.isEmpty()) { throw new OutOfMemoryException("cannot allocate - free memory is empty."); }

        Node<Block> current = heap.getFirst();
        while(current.getPrev() != heap.getLast() && size > (current.getItem()).getSize()) {
            current = current.getNext();
        }

        // current.getPrev() == heap.getLast() : current is trailer.
        // In this case, we cannot allocate the memory(all node's items have insufficient size.)
        if(current.getPrev() == heap.getLast()) { throw new OutOfMemoryException("cannot allocate - Large capacity requested."); }

        Block current_Item = current.getItem();

        // start: allocated block's start address.
        int start = current_Item.getStart();

        /* modify the information of current_Item. */
        current_Item.setStart(current_Item.getStart() + size);
        current_Item.setSize(current_Item.getSize() - size);

        // end: allocated block's end address.
        int end = start + size - 1;

        // if a item's size is zero after allocating a memory, remove it from heap.
        if(current_Item.getSize() == 0) {
            heap.remove(current);
        }

        return new Block(size, start, end);
    }

    /**
     * Add a freed block to the free memory block list.
     * @param block  the size of requested memtory
     */
    public void free(Block block) {
        if(heap.isEmpty()) {
            heap.addFirst(block);
            return;
        }

        Node<Block> current_node = heap.getFirst();
        Block current_Item = current_node.getItem();
        // find the location to insert 'block'.
        while(current_node != heap.getLast().getNext() && // while current_node is not trailer.
                block.getStart() > current_Item.getStart()) {
            current_node = current_node.getNext();
            current_Item = current_node.getItem();
        }
        /*  if current_node is trailer after while loop end, current_Item is "null".
           => consider 'NullPointerException' */
        Node<Block> block_node = new Node<Block>(block, null, null);
        heap.addBefore(current_node, block_node);

        /* have contiguous memory address => merge */

        /* case① - the memory address of block is right before current_Item
                    => merge block_node and current_node */
        if(current_node != heap.getLast().getNext() &&
        // [Exception] - if current_node is trailer, we don't have to merge them.
                block.getEnd() + 1 == current_Item.getStart()) {
            // modify the information of block_node's item(block) and remove the current_node.
            block.setEnd(current_Item.getEnd());
            block.setSize((block.getSize()) + current_Item.getSize());
            heap.remove(current_node);
        }

        /* case② - the memory address of block is right after its previous node's item.
                    => merge block_node and block_node.getPrev() */
        if(heap.getSize() != 1 && heap.getFirst() != block_node &&
        /* [Exception] - if there were two nodes before checking the case①,
                        heap can have only 'one' node after then. If so, we don't have to merge them.
                        Also, if block_node is the first node, we don't have to merge them
                        because block_node.getPrev() is header. */
                block.getStart() == block_node.getPrev().getItem().getEnd() + 1) {
            // modify the information of block_node's item(block) and remove block_node's previous node.
            block.setStart(block_node.getPrev().getItem().getStart());
            block.setSize(block.getSize() + block_node.getPrev().getItem().getSize());
            heap.remove(block_node.getPrev());
        }
    }

    /**
     * Returns the capacity of the free storage.
     * @return  capacity of the free storage
     */
    public int getCapacity() {
        if(heap.isEmpty()) { return 0; }
        int result = 0;
        Node<Block> current_node = heap.getFirst();

        result += current_node.getItem().getSize();
        while(current_node != heap.getLast()) {
            current_node = current_node.getNext();
            result += current_node.getItem().getSize();
        }

        return result;
    }

    // for testing purpose only
    DLinkedList<Block> getHeap() {
        return heap;
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}


