package com.tomtre.android.architecture.shoppinglistmvp.ui.addeditproduct;

import com.tomtre.android.architecture.shoppinglistmvp.di.FragmentScope;

import dagger.Subcomponent;

@Subcomponent(modules = AddEditProductFragmentModule.class)
@FragmentScope
public interface AddEditProductFragmentComponent {

    void inject(AddEditProductFragment addEditProductFragment);

}
