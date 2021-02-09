package by.seobility.kinoclub.ui.filter;

public class ListRowType implements RowType {
    private String text;

    public ListRowType(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }
}
