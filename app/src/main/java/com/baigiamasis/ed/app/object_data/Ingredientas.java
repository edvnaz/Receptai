package com.baigiamasis.ed.app.object_data;


import android.os.Parcel;
import android.os.Parcelable;

// TODO sutvarkyti Parlecable metodus, atrinktireikalingus, sumazinti kodo.
public class Ingredientas implements Parcelable {
    private String ingred_pav, amount;
    private int ingred_ID, type;
    private boolean checked = false;

    public Ingredientas() {
    }

    public Ingredientas(int ingred_ID, String ingred_pav) {
        this.ingred_pav = ingred_pav;
        this.ingred_ID = ingred_ID;
    }

    public Ingredientas(Ingredientas toCopy) {
        ingred_pav = toCopy.ingred_pav;
        ingred_ID = toCopy.ingred_ID;
        amount = toCopy.amount;
        type = toCopy.type;
        checked = toCopy.checked;
    }

    public int getIngred_ID() {
        return ingred_ID;
    }

    public void setIngred_ID(int ingred_ID) {
        this.ingred_ID = ingred_ID;
    }

    public String getIngred_pav() {
        return ingred_pav;
    }

    public void setIngred_pav(String ingred_pav) {
        this.ingred_pav = ingred_pav;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    //parcalable
    // The following methods that are required for using Parcelable
    private Ingredientas(Parcel in) {
        // This order must match the order in writeToParcel()
        ingred_pav = in.readString();
        ingred_ID = in.readInt();
        amount = in.readString();
        type = in.readInt();
        // Continue doing this for the rest of your member data
    }

    public void writeToParcel(Parcel out, int flags) {
        // Again this order must match the Question(Parcel) constructor
        out.writeString(ingred_pav);
        out.writeInt(ingred_ID);
        out.writeString(amount);
        out.writeInt(type);

        // Again continue doing this for the rest of your member data
    }


    @Override
    public int describeContents() {
        return 0;
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Creator<Ingredientas> CREATOR = new Creator<Ingredientas>() {
        public Ingredientas createFromParcel(Parcel in) {
            return new Ingredientas(in);
        }

        public Ingredientas[] newArray(int size) {
            return new Ingredientas[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
