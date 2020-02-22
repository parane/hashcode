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
    public List<Book> finalScannedBooks=new ArrayList<>();
    public List<Book> books=new ArrayList<>();

    @Override
    public int compareTo(Library o) {
        return ((Library) o).bookTotalscore -this.bookTotalscore ;
    }

    public Object clone() throws
            CloneNotSupportedException
    {
        return super.clone();
    }
}
