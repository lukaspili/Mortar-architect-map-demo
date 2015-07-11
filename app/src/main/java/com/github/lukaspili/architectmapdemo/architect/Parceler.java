package com.github.lukaspili.architectmapdemo.architect;

import android.os.Parcelable;

import org.parceler.Parcels;

import architect.StackableParceler;
import architect.StackablePath;

/**
 * PathParceler that uses Parceler library
 *
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class Parceler implements StackableParceler {

    @Override
    public Parcelable wrap(StackablePath path) {
        return Parcels.wrap(path);
    }

    @Override
    public StackablePath unwrap(Parcelable parcelable) {
        return Parcels.unwrap(parcelable);
    }
}
