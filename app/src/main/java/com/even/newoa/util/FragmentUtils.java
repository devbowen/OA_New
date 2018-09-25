package com.even.newoa.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.even.newoa.R;
import com.even.newoa.ui.fragment.HomeFragment;
import com.even.newoa.ui.fragment.employee.AddEmployeeFragment;
import com.even.newoa.ui.fragment.employee.EmployeeFragment;
import com.even.newoa.ui.fragment.employee.ManageEmployeeFragment;
import com.even.newoa.ui.fragment.location.LocationFragment;

public class FragmentUtils {
    private static final String TAG = "FragmentUtils";
    private FragmentTransaction fragmentTransaction;
    private HomeFragment homeFragment;
    private EmployeeFragment employeeFragment;
    private ManageEmployeeFragment manageEmployeeFragment;
    private AddEmployeeFragment addEmployeeFragment;
    private LocationFragment locationFragment;


    private FragmentUtils() {
    }

    public static FragmentUtils getInstance() {
        return SingletonInner.getFragmentUtils;
    }


    public void addFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction().add(R.id.fragment_holder, fragment).commit();
    }

    public void changeFragment(FragmentManager fragmentManager, Fragment currentFragment, Fragment nextFragment) {

        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.hide(currentFragment);

        if (nextFragment.isAdded()) {
            fragmentTransaction.show(nextFragment);
            Log.d(TAG, "show: ");
        } else {
            fragmentTransaction.add(R.id.fragment_holder, nextFragment);
            Log.d(TAG, "add: ");
        }
        fragmentTransaction.addToBackStack(null).commit();
    }

    public void replaceFragment(FragmentManager fragmentManager, Fragment nextFragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        if (nextFragment != fragmentManager.findFragmentById(R.id.fragment_holder)) {
            fragmentTransaction.replace(R.id.fragment_holder, nextFragment).commit();
        }
    }

    //返回栈空时返回false
    public boolean popFragment(FragmentManager fragmentManager) {
        if (fragmentManager.getBackStackEntryCount() == 0) {
            return false;
        } else {
            fragmentManager.popBackStack();
            return true;
        }
    }

    public HomeFragment getHomeFragment() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

    public EmployeeFragment getEmployeeFragment() {
        if (employeeFragment == null) {
            employeeFragment = new EmployeeFragment();
        }
        return employeeFragment;
    }

    public ManageEmployeeFragment getManageEmployeeFragment() {
        if (manageEmployeeFragment == null) {
            manageEmployeeFragment = new ManageEmployeeFragment();
        }
        return manageEmployeeFragment;
    }

    public AddEmployeeFragment getAddEmployeeFragment() {
        if (addEmployeeFragment == null) {
            addEmployeeFragment = new AddEmployeeFragment();
        }
        return addEmployeeFragment;
    }

    public LocationFragment getLocationFragment() {
        if (locationFragment == null) {
            locationFragment = new LocationFragment();
        }
        return locationFragment;
    }

    //保证单例
    private static class SingletonInner {
        private static FragmentUtils getFragmentUtils = new FragmentUtils();
    }

}
