package com.photoviewer.ViewModel;

/**
 * Created by user on 2018. 1. 15..
 */

abstract public class AbstractViewModel {

    protected ClickListener clickListener;
    public AbstractViewModel(){}
    public AbstractViewModel(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    abstract public BindListViewType getViewType();
}
