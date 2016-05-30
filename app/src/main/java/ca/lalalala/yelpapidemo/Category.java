package ca.lalalala.yelpapidemo;// File created by llin on 30/05/2016

public enum Category {
    Chinese(R.string.category_chinese),
    Sushi(R.string.category_sushi),
    Ramen(R.string.category_ramen),
    Koran(R.string.category_korean);

    private int titleResId;

    Category(int titleResId) {
        this.titleResId = titleResId;
    }

    public int getTitleResId() {
        return titleResId;
    }
}
