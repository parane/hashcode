package com.code;

public class Book implements Comparable<Book>{
    public Integer id;
    public Integer score;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Book other = (Book) obj;
        return this.id==(other.id);}
    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public int compareTo(Book o) {
        return ((Book) o).score -this.score ;
    }
}
