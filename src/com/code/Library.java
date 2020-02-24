package com.code;

import java.util.ArrayList;
import java.util.List;

public class Library  implements Cloneable,Comparable<Library> {
    public int id;
    public Integer noOfBook;
    public Integer signDay;
    public Integer scanDay;
    public Integer score;
    public Integer bookTotalscore=0;
    public List<Book> scannedBooks=new ArrayList<>();
    public List<Book> books=new ArrayList<>();

    @Override
    public int compareTo(Library o) {
        return ((Library) o).bookTotalscore/((Library) o).signDay -this.bookTotalscore/(this).signDay  ;
    }

    public Object clone() throws
            CloneNotSupportedException
    {
        return super.clone();
    }
}
