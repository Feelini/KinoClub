package by.seobility.kinoclub.ui.filter.rowtypes;

public class ListRowType implements RowType {
    private String text;

    public ListRowType(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

    @Override
    public int getItemViewType() {
        return RowType.LIST_ROW_TYPE;
    }
}
