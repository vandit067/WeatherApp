package com.example.vandit.weatherapp.presenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V> {

    protected final V view;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    BasePresenter(V view) {
        this.view = view;
    }

    /**
     * Contains common setup actions needed for all presenters, if any.
     * Subclasses may override this.
     */
    public void start() {
    }

    /**
     * Contains common cleanup actions needed for all presenters, if any.
     * Subclasses may override this.
     */
    public void onDestroy(){
        compositeDisposable.clear();
    }

    void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }
}
