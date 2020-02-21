package com.code;

import java.util.ArrayList;
import java.util.List;

public class Library {
    public int id;
    public Integer noOfBook;
    public Integer signDay;
    public Integer scanDay;
    public Integer score=0;
    public List<Book> scannedBooks=new ArrayList<>();
    public List<Book> books=new ArrayList<>();
}
